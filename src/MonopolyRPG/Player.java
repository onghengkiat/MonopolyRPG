package MonopolyRPG;

import MonopolyRPG.Controller.ItemBag;
import MonopolyRPG.Controller.WeaponBag;

import java.util.Random;

public class Player extends Role implements Comparable<Player>{
    //initial status
    private final int HP=25, STRENGTH = 5, DEFENCE = 5, AGILITY = 5;
    private final int LEVEL = 1, GOLD = 200, EXP = 0;
    private final double ACCURACY = 0.7 , EVASION = 0.1;
    private final Weapon WEAPON = Weapon.SWORD;

    private int location;
    private int remainingEXP;
    private ItemBag itemBag;
    private WeaponBag weaponBag;
    private int player_index;

    public Player(String name){
        this.respawn();
        this.name = name;
        this.equip(WEAPON);
        this.itemBag = new ItemBag();
        this.weaponBag = new WeaponBag();
        weaponBag.put(this.weapon);
        player_index = 0;
    }

    public void equip(Weapon weapon){
        this.weapon = weapon;
        this.weapon.setEquipped(true);
    }

    public void unequip(){
        this.weapon.setEquipped(false);
        this.weapon = null;

    }

    public void levelup(){
        Random rnd = new Random();
        Status levelUpStatus = new Status(0,
                rnd.nextInt(5),
                rnd.nextInt(3),
                rnd.nextInt(3),
                rnd.nextInt(3),
                rnd.nextInt(3)/100.0,
                rnd.nextInt(3)/100.0
        );
        this.status = this.status.addStatus(levelUpStatus);
        this.level ++;
    }

    public void displayItemBag(){
        itemBag.display();
    }

    public void displayWeaponBag(){
        weaponBag.display();
    }

    public ItemBag getItemBag() {
        return itemBag;
    }

    public WeaponBag getWeaponBag() {
        return weaponBag;
    }

    public void displayStats(){
        System.out.println("<<< " + this.name + " >>>");
        System.out.println("Level: " + this.level);
        System.out.println("Total EXP: " + this.exp);
        System.out.println("Remaining EXP: " + this.remainingEXP);
        System.out.println("EXP to level up: " + this.getEXP_nextLevel());
        System.out.println("EXP required to level up: " + this.getEXP_required());
        System.out.println("< Status without Equipment >");
        System.out.println(status);
        System.out.println("Weapon: " + this.weapon);

        System.out.println("< Status with Equipment >");
        System.out.println(this.getStatusWithEquipment());
    }

    public int getEXP_nextLevel(){
        //25*X*(1+X)
        int expToLevelUp = 25*this.level * (1 + this.level);
        return expToLevelUp;
    }

    private int getEXP_required(){
        int expRequired = getEXP_nextLevel() - this.remainingEXP;
        return expRequired;
    }

    public int getLocation() {
        return location;
    }

    public void setLocation(int location) {
        this.location = location;
    }

    public void earnGold(int amount){
        this.gold += amount;
    }

    public void spendGold(int amount){
        this.gold -= amount;
    }

    public void obtainItem(Item item, int amount){
        this.itemBag.put(item,amount);
    }

    public void obtainWeapon(Weapon weapon){
        this.weaponBag.put(weapon);
    }

    public void earnExp(int amount){
        this.exp += amount;
        this.remainingEXP += amount;

        if(this.remainingEXP >= getEXP_nextLevel()){
            this.remainingEXP -= getEXP_nextLevel();
            this.levelup();
        }
    }

    public void useItem(Item item){

        itemBag.use(item);
        if (item.getSpecialEffect() == Item.SpecialEffect.ONE_HUNDRED_PERCENT_RUN) {
        }else if (item.getSpecialEffect() == Item.SpecialEffect.HEALING){
            this.status = this.status.addStatus(item.getStatus());
        }
    }

    public void sellItem(Item item, int amount, int money){
        itemBag.sell(item,amount);
        this.gold += money;
    }

    public void respawn(){
        this.status = new Status(HP,HP,STRENGTH,DEFENCE,AGILITY,ACCURACY,EVASION);
        this.gold = GOLD;
        this.exp = EXP;
        this.level = LEVEL;
        this.location = 0;
        this.remainingEXP = EXP;
    }

    @Override
    public int compareTo(Player o) {
        if(this.getExp() == o.getExp()){
            return this.getGold() - o.getGold();
        }
        return this.getExp() - o.getExp();
    }

    public int getPlayer_index() {
        return player_index;
    }

    public void setPlayer_index(int player_index) {
        this.player_index = player_index;
    }
}
