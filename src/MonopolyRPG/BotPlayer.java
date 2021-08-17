package MonopolyRPG;

import MonopolyRPG.Controller.ItemBag;
import MonopolyRPG.Controller.Shop;
import MonopolyRPG.Controller.WeaponBag;

import java.util.LinkedList;

public class BotPlayer extends Player{
    public BotPlayer(String name) {
        super(name);
    }

    public boolean isHpLow(){
        return this.getStatusWithEquipment().getCurrenthp() <= 10;
    }

    public int selectActionCommand(){
        return Command.ROLL;
    }

    public int selectBattleCommand(boolean battlingPlayer){
        if(isHpLow()){
            ItemBag itemBag = this.getItemBag();
            if(itemBag.searchItemBySpecialEffect(Item.SpecialEffect.HEALING) != null){
                return Command.ITEM;
            }else if(itemBag.searchItemBySpecialEffect(Item.SpecialEffect.ONE_HUNDRED_PERCENT_RUN) != null){
                return Command.ITEM;
            }else{
                if(battlingPlayer){
                    return Command.ATTACK;
                }else{
                    return Command.FLEE;
                }
            }
        }else{
            return Command.ATTACK;
        }
    }

    public int selectMonsterToAttack(LinkedList<Monster> monsters){
        int monsterIndex = 0;
        int count = 0;
        int lowestHP = monsters.getFirst().getStatusWithEquipment().getCurrenthp();
        int highestAttack = monsters.getFirst().getStatusWithEquipment().getStrength();
        for(Monster monster : monsters){
            //attack the highest attack
            if(monster.getStatusWithEquipment().getStrength() > highestAttack){
                monsterIndex = count;
                lowestHP = monster.getStatusWithEquipment().getCurrenthp();
                highestAttack = monster.getStatusWithEquipment().getStrength();
            }else if(monster.getStatusWithEquipment().getStrength() == highestAttack){// if same attack
                //attack the one who has lower hp
                if(monster.getStatusWithEquipment().getCurrenthp() < lowestHP){
                    monsterIndex = count;
                    lowestHP = monster.getStatusWithEquipment().getCurrenthp();
                    highestAttack = monster.getStatusWithEquipment().getStrength();
                }
            }
            count ++;
        }
        return monsterIndex;
    }

    public int selectShopCommand(){
        if(this.getGold() >= Shop.POTION_PRICE){
            return Command.BUY;
        }else{
            return Command.EXIT;
        }
    }

    public int selectEmptyTileCommand(){
        if(isHpLow()){
            ItemBag itemBag = this.getItemBag();
            Item item = itemBag.searchItemBySpecialEffect(Item.SpecialEffect.HEALING);
            if(item != null){
                return Command.TILE_ITEM;
            }
        }

        WeaponBag weaponBag = this.getWeaponBag();
        if(weaponBag.contains(Weapon.AXE) != -1 && this.getWeapon() != Weapon.AXE){
            return Command.TILE_EQUIPMENT;
        }
        return Command.TILE_QUIT;
    }

    public int selectWeaponToUse(){
        WeaponBag weaponBag = this.getWeaponBag();
        if(this.getWeapon() != Weapon.AXE){
            return weaponBag.contains(Weapon.AXE);
        }
        return -1;
    }

    public Item selectItemToUse(boolean isEmptyTile){
        if(isHpLow()){

            ItemBag itemBag = this.getItemBag();
            Item item = itemBag.searchItemBySpecialEffect(Item.SpecialEffect.HEALING);
            if(item != null){
                return item;
            }

            if(!isEmptyTile) {
                item = itemBag.searchItemBySpecialEffect(Item.SpecialEffect.ONE_HUNDRED_PERCENT_RUN);
                if (item != null) {
                    return item;
                }
            }
        }
        return null;
    }

    public Item selectItemToBuy(){
        return Item.POTION;
    }

    public int selectAmountToBuy(Item item){
        return (int)Math.floor(this.getGold()/Shop.getPrice(item));
    }
}
