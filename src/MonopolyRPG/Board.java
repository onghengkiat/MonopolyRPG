package MonopolyRPG;

import java.util.LinkedList;
import java.util.Random;

public class Board {
    final private static int NUM_OF_TILES = 32;
    final private int NUM_OF_START = 1, NUM_OF_SHOP =2, NUM_OF_CHEST = 1,
    NUM_OF_EMPTY = 4, NUM_OF_SINM = 12, NUM_OF_DUOM = 8 ,NUM_OF_TRIM = 4;

    final private Tile[] tiles = new Tile[NUM_OF_TILES];

    public Board(boolean random) {
        //START=1,SHOP=2,CHEST=3,EMPTY=4,SINM=5,DUOM=6,TRIM=7;
        //can build the imageview of the board here
        //then return to the game controller to be rendered
        if(!random) {
            tiles[0] = new Tile(Tile.START);
            tiles[1] = new Tile(Tile.DUOM);
            tiles[2] = new Tile(Tile.TRIM);
            tiles[3] = new Tile(Tile.SINM);
            tiles[4] = new Tile(Tile.EMPTY);
            tiles[5] = new Tile(Tile.DUOM);
            tiles[6] = new Tile(Tile.SINM);
            tiles[7] = new Tile(Tile.SINM);
            tiles[8] = new Tile(Tile.SHOP);
            tiles[9] = new Tile(Tile.DUOM);
            tiles[10] = new Tile(Tile.SINM);
            tiles[11] = new Tile(Tile.DUOM);
            tiles[12] = new Tile(Tile.EMPTY);
            tiles[13] = new Tile(Tile.SINM);
            tiles[14] = new Tile(Tile.SINM);
            tiles[15] = new Tile(Tile.TRIM);
            tiles[16] = new Tile(Tile.CHEST);
            tiles[17] = new Tile(Tile.SINM);
            tiles[18] = new Tile(Tile.DUOM);
            tiles[19] = new Tile(Tile.TRIM);
            tiles[20] = new Tile(Tile.EMPTY);
            tiles[21] = new Tile(Tile.SINM);
            tiles[22] = new Tile(Tile.DUOM);
            tiles[23] = new Tile(Tile.SINM);
            tiles[24] = new Tile(Tile.SHOP);
            tiles[25] = new Tile(Tile.SINM);
            tiles[26] = new Tile(Tile.DUOM);
            tiles[27] = new Tile(Tile.DUOM);
            tiles[28] = new Tile(Tile.EMPTY);
            tiles[29] = new Tile(Tile.TRIM);
            tiles[30] = new Tile(Tile.SINM);
            tiles[31] = new Tile(Tile.SINM);
        }else{
            tiles[0] = new Tile(Tile.START);
            for(int i = 0 ; i< NUM_OF_CHEST; i++) {
                tiles[generateRandomPosition()] = new Tile(Tile.CHEST);
            }

            for(int i = 0 ; i< NUM_OF_SHOP; i++){
                tiles[generateRandomPosition()] = new Tile(Tile.SHOP);
            }
            for(int i = 0 ; i< NUM_OF_EMPTY; i++){
                tiles[generateRandomPosition()] = new Tile(Tile.EMPTY);
            }
            for(int i = 0 ; i< NUM_OF_SINM; i++){
                tiles[generateRandomPosition()] = new Tile(Tile.SINM);
            }
            for(int i = 0 ; i< NUM_OF_DUOM; i++){
                tiles[generateRandomPosition()] = new Tile(Tile.DUOM);
            }

            for(int i = 0 ; i< NUM_OF_TRIM; i++){
                tiles[generateRandomPosition()] = new Tile(Tile.TRIM);
            }

        }
    }

    public static int getNum_of_tiles() {
        return NUM_OF_TILES;
    }

    public Tile[] getTiles() {
        return tiles;
    }

    public int generateRandomPosition(){
        Random rnd = new Random();
        int random_position = rnd.nextInt(NUM_OF_TILES - 1) + 1;
        while(tiles[random_position] != null){
            random_position = rnd.nextInt(NUM_OF_TILES - 1) + 1;
        }
        return random_position;
    }

}
