package MonopolyRPG.Controller;

import MonopolyRPG.BotPlayer;
import MonopolyRPG.Command;
import MonopolyRPG.Player;

import java.util.Scanner;

public class EmptyTile {

    public void enter(Player player){
        Scanner input = new Scanner(System.in);
        int command = -1;
        while(command != Command.TILE_QUIT) {
            System.out.println("Do you want to use item or equip equipment?");
            System.out.println(Command.TILE_ITEM + " for item, "+Command.TILE_EQUIPMENT+" for equipment, "+Command.TILE_QUIT+" for quit");
            if(player instanceof BotPlayer){
                command = ((BotPlayer) player).selectEmptyTileCommand();
            }else {
                command = input.nextInt();
            }
            if (command == Command.TILE_ITEM) {
                ItemBag bag = player.getItemBag();
                bag.display();
                bag.promptUser(player, false);

            } else if (command == Command.TILE_EQUIPMENT) {
                WeaponBag bag = player.getWeaponBag();
                bag.display();
                bag.promptUser(player);
            }
        }

        //quit
        //close scene here
    }
}
