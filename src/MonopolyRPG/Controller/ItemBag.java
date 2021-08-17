package MonopolyRPG.Controller;

import MonopolyRPG.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ItemBag extends Bag {
    private HashMap<Item,Integer> items ;

    public ItemBag() {
        //default items that will have in the bag at the beginning
        this.items = new HashMap<>();
        this.put(Item.POTION, 5);
        this.put(Item.SMOKEBOMB,3);
    }

    //index counts from 1, triggered mou :P
    public Item get(int index){
        int count = 1 ;
        Item item = null;
        for (Map.Entry itemInMap : items.entrySet()) {
            if(index == count){
                item = (Item) itemInMap.getKey();
                break;
            }
            count ++;
        }
        return item;
    }

    public int getAmount(Item item){
        return items.get(item);
    }

    public boolean put(Item item, int counts){

        if(items.containsKey(item)){
            items.put(item, items.get(item) + counts);
        }else{
            if(this.isFull()){
                return false;
            }
            items.put(item, counts);
        }
        return true;
    }

    //sell items may sell many of same items at the same time
    public void sell(Item item, int amount){

        if(items.get(item) == amount){
            items.remove(item);
        }else{
            items.put(item, items.get(item) - amount);
        }
    }

    //use item only can use one at the same time
    public void use(Item item){
        if(items.get(item) == 1){
            items.remove(item);
        }else{
            items.put(item, items.get(item) - 1);
        }
    }

    @Override
    public void display() {
        //display the item bag
        int count = 1;
        for (Map.Entry item : items.entrySet()) {
            System.out.println("Item " + count + ": "+item.getKey() + " & Count: " + item.getValue());
            count ++;
        }
    }

    public boolean promptUser(Player player, Boolean battling){
        Item item = null;
        if(player instanceof BotPlayer){
            item = ((BotPlayer) player).selectItemToUse(!battling);
        }else {
            Scanner input = new Scanner(System.in);
            System.out.println("Which item you want to use?");
            int itemID = input.nextInt();
            item = this.get(itemID);
        }

        if (item == Item.SMOKEBOMB) {
            if(!battling) {
                System.out.println("You can only use smokebomb in battle");
            }else{
                //smoke bomb effect maybe
                player.useItem(item);
                System.out.println(player + " uses " + item);

                //the battling ends
                return false;
            }

        } else {
            //can put the item using scene here
            Status initialStats = player.getStatusWithEquipment();
            System.out.println(player + " uses " + item);
            player.useItem(item);
            Status afterStats = player.getStatusWithEquipment();
            System.out.println("<<< Before >>>");
            System.out.println(initialStats);
            System.out.println("<<< After >>>");
            System.out.println(afterStats);
        }
        return true;
    }

    @Override
    public boolean isFull() {
        return items.size() >= this.capacity;
    }

    public Item searchItemBySpecialEffect(Item.SpecialEffect specialEffect){
        for (Map.Entry itemInMap : items.entrySet()) {
            Item item = (Item) itemInMap.getKey();
            if (item.getSpecialEffect() == specialEffect){
                return item;
            }
        }
        return null;
    }

}
