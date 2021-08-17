package MonopolyRPG;

import MonopolyRPG.Controller.*;

import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

public class Tile {
    final static int START=1,SHOP=2,CHEST=3,EMPTY=4,SINM=5,DUOM=6,TRIM=7;

    private int type;
    private String name;
    private LinkedList <Player> playerOnTile ;

    public Tile(int type) {
        //you can build how the tiles look like in the map here
        this.playerOnTile = new LinkedList<>();
        this.type = type;
        switch(type){
            case START:
                this.name = "<-START";
                break;
            case SHOP:
                this.name = "SHOP";
                break;
            case CHEST:
                this.name = "CHEST";
                break;
            case EMPTY:
                this.name = "";
                break;
            case SINM:
                this.name = "SIN-M";
                break;
            case DUOM:
                this.name = "DUO-M";
                break;
            case TRIM:
                this.name = "TRI-M";
                break;
        }
    }

    public int getNum_of_player() {
        return playerOnTile.size();
    }

    public void playerEnter(Player player){
        this.playerOnTile.add(player);
    }

    public void playerLeave(Player player){
        this.playerOnTile.remove(player);
    }

    public boolean isBattleDeclared(){
        //battle declared if number of player == 2
        // and both players at least level 5
        if( getNum_of_player() == 2){
            for(Player player : playerOnTile){
                if(player.getLevel() < 5){
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public LinkedList<Player> getPlayerOnTile(){
        return this.playerOnTile;
    }

    public void trigger(Player player){
        switch (this.type) {
            case START:
                //nothing to do with this? or take it like empty tile maybe, yea I think it is a good idea
            case EMPTY:
                EmptyTile emptyTile = new EmptyTile();
                emptyTile.enter(player);
                break;
            case SHOP:
                Shop shop = new Shop();
                shop.enter(player);
                break;
            case CHEST:
                Chest chest = new Chest();
                chest.open(player);
                break;
            case SINM:
                BattleSystem.startMonsterBattle(player, spawnMonster(1,player.getLevel()));
                break;
            case DUOM:
                BattleSystem.startMonsterBattle(player,spawnMonster(2,player.getLevel()));
                break;
            case TRIM:
                BattleSystem.startMonsterBattle(player,spawnMonster(3,player.getLevel()));
                break;

        }
    }

    public LinkedList<Monster> spawnMonster(int number, int playerLevel){
        LinkedList<Monster> monsters = new LinkedList<>();
        Random rnd = new Random();
        for (int i = 0; i < number; i++){
            int level = playerLevel;
            //determine the monster level is lower than or higher than player
            if(rnd.nextInt(2) == 0) {
                //higher less than 3 levels
                level += rnd.nextInt(3);
            }else{
                //lower less than 3 levels
                level -= rnd.nextInt(3);
            }
            if(level < 1)
                level = 1;
            Monster monster = new Monster("Monster", level);
            monsters.add(monster);
        }
        return monsters;
    }


    @Override
    public String toString() {
        return this.name;
    }
}

