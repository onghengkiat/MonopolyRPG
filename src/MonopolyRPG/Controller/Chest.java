package MonopolyRPG.Controller;

import MonopolyRPG.Item;
import MonopolyRPG.Player;
import MonopolyRPG.Weapon;

import java.util.LinkedList;
import java.util.Random;

public class Chest {
    LinkedList<Item> items ;
    LinkedList<Weapon> weapons;
    public Chest() {
        items = new LinkedList<>();
        weapons = new LinkedList<>();
        this.generateWeapon();
        this.generateItems();
    }

    public void generateWeapon(){
        Random rnd = new Random();
        if(rnd.nextInt(5) < 2){
            weapons.add(Weapon.AXE);
        }
    }

    public void generateItems(){
        Random rnd = new Random();
        if(rnd.nextInt(5) < 2){
            items.add(Item.HIPOTION);
        }else{
            items.add(Item.POTION);
        }

    }

    public void open(Player player){
        //can put the animation of opening chest here
        System.out.println(player + " opened a chest");
        System.out.println("The chest contains");
        for(Weapon weapon : weapons){
            System.out.println(weapon);
            player.obtainWeapon(weapon);
        }
        for(Item item: items){
            System.out.println(item);
            player.obtainItem(item, 1);
        }
    }

    public LinkedList<Item> getItems() {
        return items;
    }

    public LinkedList<Weapon> getWeapons() {
        return weapons;
    }
}
