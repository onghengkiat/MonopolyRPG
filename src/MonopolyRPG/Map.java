package MonopolyRPG;

import java.util.Arrays;
import java.util.LinkedList;

public class Map {

    private Board board;
    private Tile [] tiles ;
    private int size_of_board, row_length, column_length;

    public Map( Board board){
        this.board = board;
        tiles = board.getTiles();
        size_of_board = tiles.length;
        row_length = size_of_board / 4 + 1;
        column_length = size_of_board / 4 - 1;
    }

    public void renderMap(){
        generateRowDash();
        generateRowTile(false);
        generateRowDash();
        generateColumnTile();


        generateRowDash();
        generateRowTile(true);
        generateRowDash();

    }

    public void generateRowDash(){
        for(int i = 0 ; i < row_length ; i ++){
            System.out.print("-".repeat(11));
        }
        System.out.println("-");
    }

    public void generateColumnTile( ){
        int starting_point = row_length + column_length - 1;
        for(int i = starting_point ; i >= row_length ; i --){
            //print out the tile with name
            System.out.print("");
            //first tile on the row
            System.out.printf("|%-10s|",tiles[i]);

            //space in the middle
            for(int j = 0 ; j < row_length - 2 ; j ++) {
                if(j == row_length - 3){
                    System.out.printf("%11s", "|");
                }else{
                    System.out.printf("%-11s", "");
                }
            }

            //second tile on the row
            int second_tile_index = column_length + row_length*2 + (starting_point - i );
            System.out.printf("%-10s|\n",tiles[second_tile_index]);

            //print out players on first tile
            System.out.printf("|%-10s|",getPlayerOnTile(i));

            //middle space
            for(int j = 0 ; j < row_length - 2 ; j ++) {
                if(j == row_length - 3){
                    System.out.printf("%11s", "|");
                }else{
                    System.out.printf("%-11s", "");
                }
            }

            //print out players on second tile
            System.out.printf("%-10s|",getPlayerOnTile(second_tile_index));

            if( i != row_length) {
                //print out the dash line
                System.out.printf("\n" + "-".repeat(12) + " ".repeat((row_length - 2) * 11 - 1) + "-".repeat(12) + "\n");
                System.out.printf("");
            }else{
                System.out.println();
            }
        }
    }

    public void generateRowTile( boolean start_row){

        System.out.print("|");
        if (start_row){
            int starting_point = this.row_length - 1;
            //print out the first row of tiles
            for(int i = starting_point ; i >= 0 ; i -- ) {
                System.out.printf("%-10s|",tiles[i]);
            }

            //move to second row of the tiles
            System.out.println();

            System.out.print("|");

            //print out the player on the second row of the tile
            for(int i = starting_point ; i >= 0; i --){
                System.out.printf("%-10s|",getPlayerOnTile(i));
            }
        }else{
            //same logic as above
            int starting_point = row_length + column_length;
            for(int i = starting_point ; i < starting_point + row_length; i ++) {
                System.out.printf("%-10s|",tiles[i]);
            }
            System.out.println();

            System.out.print("|");
            for(int i = starting_point ; i < starting_point +  row_length; i ++){
                System.out.printf("%-10s|",getPlayerOnTile(i));
            }
        }

        System.out.println();
    }

    public String getPlayerOnTile(int index){
        String player = "";
        LinkedList<Player> players = tiles[index].getPlayerOnTile();
        for(Player ply : players){
            player += ply.getPlayer_index() + " ";
        }
        return player;
    }

}
