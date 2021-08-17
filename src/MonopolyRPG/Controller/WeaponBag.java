package MonopolyRPG.Controller;

import MonopolyRPG.Bag;
import MonopolyRPG.BotPlayer;
import MonopolyRPG.Player;
import MonopolyRPG.Weapon;

import java.util.LinkedList;
import java.util.Scanner;

public class WeaponBag extends Bag {

    private LinkedList<Weapon> weapons ;

    public WeaponBag(){

        this.weapons = new LinkedList<>();
    }

    //index starts from 0
    public Weapon get(int index){
        return weapons.get(index);
    }

    public boolean put(Weapon weapon){
        if(this.isFull()){
            return false;
        }
        weapons.add(weapon);
        return true;
    }

    @Override
    public void display() {
        //display the weapon bag
        int count = 1;
        for(Weapon weapon : weapons){
            System.out.println("Weapon " + count + ": " + weapon);
            count ++;
        }
    }

    public void promptUser(Player player){
        int weaponID = -1;
        if(player instanceof BotPlayer){
            weaponID = ((BotPlayer) player).selectWeaponToUse();
        }else {
            Scanner input = new Scanner(System.in);
            System.out.println("Which equipment you want to equip?");
            weaponID = input.nextInt() - 1;
        }

        Weapon weapon = this.get(weaponID);
        if(weapon != null){
            System.out.println("Player unequip " + player.getWeapon());
            player.unequip();
            System.out.println("Player equip " + weapon);
            player.equip(weapon);
        }
    }

    public int contains(Weapon weaponToSearch){
        int index =0 ;
        for(Weapon weapon : weapons){
            if(weapon == weaponToSearch){
                return index;
            }
            index ++;
        }
        return -1;
    }

    @Override
    public boolean isFull() {
        return this.weapons.size() >= this.capacity;
    }
}
