package MonopolyRPG.Controller;

import MonopolyRPG.*;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;

public abstract class Game {
    protected int player_num;
    protected Player[] players;;
    protected int remaining_player;
    protected Dice dice;
    protected Map map ;
    protected Board board;

    //display all the commands that user should input
    public void displayCommands(){
        System.out.println(Command.ROLL + ": Roll dice");
        System.out.println(Command.CHECKSTATS + ": Check your status");
        System.out.println(Command.CHECKITEM + ": Check your items");
        System.out.println(Command.CHECKEQUIPMENT + ": Check your equipments");
        System.out.println(Command.QUIT + ": Quit the game");
    }


    public void roll(Player player){
        int dice_rolled = dice.roll();
        System.out.println(player + " rolls " + dice_rolled);

        this.movePlayer(player, dice_rolled, board.getTiles());
    }


    public abstract void start();

    public void end(){

        Player winner = null;
        //simply put one player thats not dead as the winner
        for(int i = 0 ; i < players.length ; i ++ ){
            if(players[i].isDead()){
                continue;
            }else{
                winner = players[i];
                break;
            }
        }

        //if more than 1 player survived then compare them and find who is the winner
        if(remaining_player > 1) {
            for (int i = 0; i < players.length - 1; i++) {
                if(players[i].isDead()){
                    continue;
                }
                if (players[i].compareTo(players[i + 1]) < 0) {
                    winner = players[i + 1];
                }
            }
        }
        System.out.println("Remaining player: " + remaining_player);
        System.out.println(winner + " wins the game.");

        for(int i = 0 ; i < players.length; i++){
            players[i].displayStats();
        }
        System.out.println("Gold: " + winner.getGold());
        System.out.println("<<< Game Ends >>>");
    }

    public void arrangePlayer(){
        Dice dice = new Dice();
        System.out.println("Determining who first");
        int [] priority = new int[player_num];
        for(int i = 0 ; i < player_num ; i++){
            int value = dice.roll();
            System.out.println(players[i] + " rolls " + value);
            priority[i] = value;
            board.getTiles()[0].playerEnter(players[i]);
        }

        //bubble sort
        for(int i = 0 ; i < priority.length ; i ++){
            for(int j = 0 ; j < priority.length - 1 ; j ++){
                if(priority[j] < priority[j + 1]){
                    int temp = priority[j];
                    priority[j] = priority[j + 1];
                    priority[j + 1] = temp;

                    Player tempPlayer = players[j];
                    players[j] = players[j + 1];
                    players[j + 1] = tempPlayer;
                }
            }
        }
        for(int i = 0; i  < players.length ; i ++){
            players[i].setPlayer_index(i+1);
            System.out.println("Player " + players[i].getPlayer_index() + ": " + players[i]);
        }

    }

    public abstract void inputPlayerName();

    public void inputPlayerNumber() {
        System.out.println("Enter number of players [2-4]");
        Scanner input = new Scanner(System.in);
        this.player_num = input.nextInt();
        this.players = new Player[player_num];
        this.remaining_player = player_num;
    }


    public void movePlayer(Player player , int steps, Tile[] tiles){
        //move the location
        int previousLocation = player.getLocation();
        player.setLocation((player.getLocation() + steps) % Board.getNum_of_tiles());
        int currentLocation = player.getLocation();

        //record in the tiles
        tiles[previousLocation].playerLeave(player);
        tiles[currentLocation].playerEnter(player);
        map.renderMap();


        //passes a round
        if(currentLocation < previousLocation){
            player.earnExp(player.getEXP_nextLevel());
            player.levelup();
        }

        //check if battle with player triggered
        if(tiles[currentLocation].isBattleDeclared()){

            LinkedList<Player> playerOnTile = tiles[currentLocation].getPlayerOnTile();

            BattleSystem.startPlayerBattle(playerOnTile.get(0), playerOnTile.get(1));
        }else{
            //trigger what happens on the tiles
            tiles[currentLocation].trigger(player);
        }
    }


    public void calculateRemainingPlayer(){
        //calculate the remaining player survives
        //quite redundant, but I cant get a better way to do yet
        //would think on it later
        remaining_player = 0;
        for(int i = 0 ; i < players.length ; i++){
            if(!players[i].isDead()){
                remaining_player ++;
            }
        }
    }
}
