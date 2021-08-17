package MonopolyRPG.Controller;

import MonopolyRPG.*;

import java.util.Random;
import java.util.Scanner;

public class BotGamePage extends Game {

    final String [] FIRSTNAME = {"Steven", "Halim", "Pretty"};
    final String [] LASTNAME = {"Jack", "James", "King"};
    public BotGamePage() {
        //can determine wanna random mapping or not
        this.board = new Board(true);
        this.map = new Map(this.board);
        this.dice = new Dice();
    }

    //start the game
    @Override
    public void start(){
        this.inputPlayerNumber();
        this.inputPlayerName();
        this.arrangePlayer();
        System.out.println("<<< Game Starts >>>");

        map.renderMap();
        Scanner input = new Scanner(System.in);

        /*
        2 conditions for game to end
        1) Only 1 player remaining in the game
        2) All player agree to end the game
         */

        int round = 0;
        while(remaining_player > 1){
            //game will stop if it reaches 300 rounds
            round ++;
            if (round == 300){
                this.end();
                break;
            }

            //each player's turn
            for(int i = 0 ; i < player_num ; i ++){
                //if the player is dead, skip his/her turn
                if (players[i].isDead()){
                    continue;
                }

                if(players[i] instanceof BotPlayer){
                    int command = ((BotPlayer) players[i]).selectActionCommand();
                    if(!executeCommand(players[i],command )){
                        return;
                    }
                }else {
                    map.renderMap();
                    //the player's round ends only when he/she rolls the dice
                    int command = -1;
                    while (command != Command.ROLL) {
                        System.out.println("Now is " + players[i] + "'s turn, enter your command");
                        this.displayCommands();
                        command = input.nextInt();
                        //if it returns false means the game ends
                        if(!executeCommand(players[i], command) ){
                            return;
                        }
                    }
                }
            }
        }

    }


    //return false if the game ends after the command
    public boolean executeCommand(Player player, int command){
        switch (command) {
            case Command.ROLL:
                this.roll(player);
                calculateRemainingPlayer();
                if (remaining_player <= 1) {
                    this.end();
                    return false;
                }
                break;
            case Command.CHECKSTATS:
                player.displayStats();
                break;
            case Command.CHECKITEM:
                player.displayItemBag();
                break;
            case Command.CHECKEQUIPMENT:
                player.displayWeaponBag();
                break;
            case Command.QUIT:
                this.end();
                return false;
        }
        return true;
    }
    @Override
    public void inputPlayerName() {

        Scanner input =new Scanner(System.in);
        System.out.println("Please enter you name in one line:");
        players[0] = new Player(input.nextLine());

        //randomly generates the name for the bot
        Random rnd = new Random();
        for(int i = 1 ; i < player_num ; i ++){
            String firstname = FIRSTNAME[rnd.nextInt(FIRSTNAME.length)];
            String lastname = LASTNAME[rnd.nextInt(LASTNAME.length)];
            players[i] = new BotPlayer(firstname + " " + lastname);
            System.out.println("Player " + (i+1) + "'s name is " + players[i]);
        }
    }

}
