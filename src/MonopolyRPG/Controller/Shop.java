package MonopolyRPG.Controller;

import MonopolyRPG.BotPlayer;
import MonopolyRPG.Command;
import MonopolyRPG.Item;
import MonopolyRPG.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Shop {
    //items and their price
    public final static int POTION_PRICE = 30, SMOKEBOMB_PRICE = 30, HIPOTION_PRICE = 100;
    private static HashMap<Item, Integer> items;

    public Shop() {
        items = new HashMap<>();
        items.put(Item.POTION,POTION_PRICE);
        items.put(Item.SMOKEBOMB,SMOKEBOMB_PRICE);
        items.put(Item.HIPOTION,HIPOTION_PRICE);
    }

    public void enter(Player player){
        Scanner input = new Scanner(System.in);
        int command = -1 ;
        while(command != Command.EXIT){
            System.out.println("Your money: " + player.getGold());
            System.out.println("Enter your command: ");
            System.out.println(Command.BUY + ": Buy");
            System.out.println(Command.SELL + ": Sell");
            System.out.println(Command.EXIT + ": Exit");
            if(player instanceof BotPlayer){
                command = ((BotPlayer) player).selectShopCommand();
            }else {
                command = input.nextInt();
            }
            switch (command){
                case Command.BUY:
                    this.display();
                    this.promptBuy(player);
                    break;
                case Command.SELL:
                    player.displayItemBag();
                    this.promptSell(player);
                    break;
                case Command.EXIT:
                    break;
            }
        }
        this.leave();
    }

    public void promptBuy(Player player){
        Item item = null;
        int amount = 0;
        if(player instanceof BotPlayer) {
            item = ((BotPlayer) player).selectItemToBuy();
            amount = ((BotPlayer) player).selectAmountToBuy(item);
        }else {
            Scanner input = new Scanner(System.in);
            System.out.println("Which items you want to buy?");
            int itemID = input.nextInt();
            System.out.println("How many you want to buy?");
            amount = input.nextInt();
            item = getItemByID(itemID);
        }

        int price = items.get(item) * amount;
        if (player.getGold() < price) {
            System.out.println("You don't have enough amount of money");
        } else {
            buyItem(player, item, amount, price);
        }
    }

    public void buyItem(Player player, Item item, int amount, int price){
        //if you want to, can put purchased succesful eh background music here
        System.out.println("Purchased successful");
        player.obtainItem(item,amount);
        player.spendGold(price);
        System.out.println("It costs " + price + ", your remaining money: " + player.getGold());
    }

    public void promptSell(Player player){

        Scanner input = new Scanner(System.in);
        System.out.println("Which items you want to sell?");
        int itemID = input.nextInt();
        System.out.println("How many you want to sell?");
        int amount = input.nextInt();
        ItemBag bag = player.getItemBag();
        Item item = bag.get(itemID);

        if(bag.getAmount(item) < amount){
            System.out.println("You don't have enough amount of items");
        }else{
            sellItem(player,item,amount);
        }
    }

    public void sellItem(Player player, Item item, int amount){

        //the price of the items sold will be half of the price to buy it
        int price = (items.get(item) * amount) /2;

        //if you want to, can put selling succesful eh background music here
        System.out.println("Sold successful");
        player.sellItem(item,amount, price);
        System.out.println("You earns " + price + ", your remaining money: " + player.getGold());
    }

    public void leave(){
        System.out.println("You leave the shop");
    }

    public void display(){
        for (Map.Entry item : items.entrySet()) {
            System.out.println("Item: "+item.getKey() + " & Price: " + item.getValue());
        }

    }

    //actually is by counts
    public Item getItemByID(int itemID){
        int count = 1 ;
        Item item = null;
        for (Map.Entry itemInMap : items.entrySet()) {
            if(itemID == count){
                item = (Item) itemInMap.getKey();
                break;
            }
            count ++;
        }
        return item;
    }

    public static int getPrice(Item item){
        return items.get(item);
    }
}
