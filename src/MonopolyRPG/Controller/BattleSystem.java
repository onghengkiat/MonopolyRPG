package MonopolyRPG.Controller;

import MonopolyRPG.*;

import java.util.LinkedList;
import java.util.Scanner;

public class BattleSystem {
    private static int trials = 0;

    public static boolean selectActionPlayerBattle(Player attacker, Player defender){

        System.out.println(attacker + "'s turn, enter your command");
        System.out.println(Command.ATTACK + " for attack, "+Command.ITEM+" for using item, ");
        Scanner input = new Scanner(System.in);
        int command = -1;
        if(attacker instanceof BotPlayer){
            command = ((BotPlayer) attacker).selectBattleCommand(true);
        }else{
            command = input.nextInt();
        }
        switch(command){
            case Command.ATTACK:
                attack(attacker, defender);
                return true;
            case Command.ITEM:
                //display and choose item to be used
                ItemBag bag = attacker.getItemBag();
                bag.display();
                return bag.promptUser(attacker, true);
        }
        return true;
    }

    public static boolean selectActionMonsterBattle(Player player, LinkedList<Monster> monsters, LinkedList<Monster> deadMonsters){
        System.out.println(player + "'s turn, enter your command");
        System.out.println(Command.ATTACK + " for attack, "+Command.ITEM+" for using item, "+Command.FLEE+" for flee");
        Scanner input = new Scanner(System.in);
        int command = -1;
        if(player instanceof BotPlayer){
            command = ((BotPlayer) player).selectBattleCommand(false);
        }else{
            command = input.nextInt();
        }

        switch(command){
            case Command.ATTACK:
                int monster_index = 0;
                if(player instanceof BotPlayer){
                    monster_index = ((BotPlayer) player).selectMonsterToAttack(monsters);
                }
                //if number of monsters more than 1, ask which to be attacked
                if(monsters.size() > 1) {
                    if(player instanceof BotPlayer){

                    }else {
                        System.out.println("Which monster to be attacked?");
                        for (int i = 0; i < monsters.size(); i++) {
                            System.out.println((i + 1) + " for " + monsters.get(i));
                        }
                        monster_index = input.nextInt() - 1;
                    }
                    attack(player , monsters.get(monster_index ));

                    //check if the monster is dead
                    if(monsters.get(monster_index).isDead()){
                        deadMonsters.add(monsters.remove(monster_index));
                    }
                }else{
                    attack(player, monsters.get(monster_index));

                    //check if the monster is dead
                    if(monsters.get(monster_index).isDead()){
                        deadMonsters.add(monsters.remove(monster_index));
                    }
                }
                return true;
            case Command.ITEM:
                //switch to itemBag scene
                //displays items in the bag
                ItemBag bag = player.getItemBag();
                bag.display();

                //asks for which item to be used
                return bag.promptUser(player, true);
            case Command.FLEE:

                trials ++;
                int highest_agility = monsters.get(0).getStatusWithEquipment().getAgility();
                //I will compare with the highest agility among the monsters
                for(Monster monster : monsters){
                    if(highest_agility < monster.getStatusWithEquipment().getAgility()){
                        highest_agility = monster.getStatusWithEquipment().getAgility();
                    }
                }

                //check if successfully escaped
                if(Probability.escaped(player.getStatusWithEquipment().getAgility(),highest_agility, trials)){
                    //animate running
                    run(player);
                    return false;
                }
                return true;
        }
        return true;
    }


    private static int calculateDamage(Role attacker, Role defender){
        int damage = attacker.getStatusWithEquipment().getStrength() - (int)(0.5 * defender.getStatusWithEquipment().getDefence());
        if(damage <= 0 ){
            damage = 1;
        }
        return damage;
    }

    public static void startMonsterBattle(Player player, LinkedList<Monster> monsters){
        trials = 0;
        LinkedList <Monster> deadMonsters = new LinkedList<>();
        for(Monster monster : monsters){
            System.out.println(player + " encounters " + monster);
        }
        while(!player.isDead() && monsters.size() > 0){
            //if flee successfully will return false
            if(!selectActionMonsterBattle(player, monsters, deadMonsters)){
                endBattle();
                return;
            }

            //monsters' turn to attack player
            for(Monster monster : monsters){
                attack(monster, player);
                //if player is dead then can end liao
                if (player.isDead()) {
                    //here you can put defeat background music
                    endBattle();
                    return;
                }
            }
        }

        //if player wins the battle, monsters drop items
        for(Monster monster : deadMonsters){
            drop(player,monster);
        }

        endBattle();
    }

    public static void startPlayerBattle(Player player1, Player player2){
        System.out.println(player1 + " encounters " + player2);

        while(!player1.isDead() && !player2.isDead()){

            //player 1's turn, if flee away then stop the battle
            //but got no drops
            if(!selectActionPlayerBattle(player1,player2)){
                endBattle();
                break;
            }

            //if player1 kills the player2, ends the game also
            if(player2.isDead()){
                drop(player1,player2);
                endBattle();
                break;
            }

            //same goes to player 2's turn
            if(!selectActionPlayerBattle(player2,player1)){
                endBattle();
                break;
            }

            if(player1.isDead()) {
                drop(player2, player1);
                endBattle();
                break;
            }
        }
    }

    private static void attack(Role attacker, Role defender){
        //check if the attack miss
        if(!Probability.hit(attacker.getStatusWithEquipment().getAccuracy(), defender.getStatusWithEquipment().getEvasion())){
            System.out.println(attacker + " missed!");
            return;
        }

        //calculate the damage
        int damage = calculateDamage(attacker, defender);
        //decrease the hp
        defender.getStatus().setCurrenthp(defender.getStatusWithEquipment().getCurrenthp() - damage);

        //display it
        System.out.println(attacker + " deals " + damage + " damage to " + defender);

        //check if the defender is dead
        if(defender.isDead()){
            System.out.println(defender + " is dead");
        }else {
            //if the defender is not dead, displays hp left
            System.out.println(defender + " left " + defender.getStatusWithEquipment().getCurrenthp() + "HP");
        }
    }

    //the loser drops exp, gold and items (if it is monster)
    public static void drop(Role winner, Role loser){
        //only if the winner is player
        //monster will be despawned although they win
        if( winner instanceof Player){
            //if you want to, can put the winning background music here maybe
            System.out.println(winner + " obtains " + loser.getExp() + " EXP");
            ((Player) winner).earnExp(loser.getExp());

            System.out.println(winner + " obtains " + loser.getGold() + " GOLD");
            ((Player) winner).earnGold(loser.getGold());

            //only monster will drop items
            if(loser instanceof Monster){
                LinkedList<Item> itemsDropped = ((Monster) loser).dropItem();;
                for(Item item : itemsDropped){
                    System.out.println(loser + " drops " + item);
                    ((Player) winner).obtainItem(item, 1);
                }
            }
            //then the music should stop here
        }
    }

    //run away from the battle
    public static void run(Player player){
        System.out.println(player + " runs away..");
        //you can do animation of running maybe, if you want to;
    }

    //end the battle
    public static void endBattle(){
        System.out.println("Battle ends!");
        //you can change screen here
    }
}
