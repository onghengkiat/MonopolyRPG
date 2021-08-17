package MonopolyRPG;

public abstract class Role {
    protected int level,exp, gold;
    protected Status status;
    protected String name;
    protected Weapon weapon;

    public int getLevel() {
        return level;
    }

    public int getGold() {
        return gold;
    }

    public int getExp(){
        return this.exp;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public String getName() {
        return name;
    }

    public boolean isDead(){
        return this.status.getCurrenthp() <= 0;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public Status getStatus(){return this.status;}
    public Status getStatusWithEquipment() {
        if(this.weapon == null) {
            return status;
        }else{
            return this.status.addStatus(this.weapon.getStatus());
        }
    }
}
