package MonopolyRPG;

import java.util.LinkedList;
import java.util.Random;

public class Monster extends Role{
    private final int HP=10, STRENGTH = 3, DEFENCE = 3, AGILITY = 3;
    private final int LEVEL = 1, GOLD = 10, EXP = 20;
    private final double EVASION = 0.1, ACCURACY = 0.8;

    public Monster(String name, int level) {
        this.exp = EXP;
        this.level = LEVEL;
        this.name = name + " Level " + this.level;
        this.gold = GOLD;
        this.weapon = null;
        Random rnd = new Random();
        int coeeficient = this.level -1;
        int hp = HP + coeeficient * rnd.nextInt(3);
        int strength = STRENGTH + coeeficient * 1;
        int defence = DEFENCE + coeeficient * 1;
        int agility = AGILITY + coeeficient * 1;
        double accuracy = ACCURACY + coeeficient * 1/100.0;
        double evasion = EVASION + coeeficient * 1/100.0;
        this.status = new Status(hp,hp,strength,defence,agility,accuracy,evasion);
    }

    public LinkedList<Item> dropItem(){
        LinkedList<Item> itemsDropped = new LinkedList<>();
        //see if the monster drops a potion, can add more stuff here if you want to
        if(Probability.dropped(0.3)) {
            itemsDropped.add(Item.POTION);
        }

        return itemsDropped;

    }
}
