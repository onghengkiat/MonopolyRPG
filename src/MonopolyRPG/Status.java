package MonopolyRPG;

public class Status {
    private int currenthp, maxhp, strength, defence, agility;
    private double accuracy, evasion;

    public Status(int currentHP, int maxHP, int strength, int defence, int agility, double accuracy, double evasion) {
        this.currenthp = currentHP;
        this.maxhp = maxHP;
        this.strength = strength;
        this.defence = defence;
        this.agility = agility;
        this.accuracy = accuracy;
        this.evasion = evasion;
    }

    public Status (int currenthp){
        this.currenthp = currenthp;
        this.maxhp = 0;
        this.strength = 0;
        this.defence = 0;
        this.agility = 0;
        this.accuracy = 0;
        this.evasion = 0;
    }

    public Status addStatus(Status anotherStat){
        //check if the new current hp is higher than the maximum hp, if yes then set it to the maximum hp.
        int newCurrentHp = currenthp + anotherStat.getCurrenthp() > maxhp + anotherStat.getMaxhp() ?
                maxhp + anotherStat.getMaxhp() : currenthp + anotherStat.getCurrenthp();

        return new Status( newCurrentHp,
                maxhp + anotherStat.getMaxhp(),
                strength + anotherStat.getStrength(),
                defence + anotherStat.getDefence(),
                agility + anotherStat.getAgility(),
                accuracy + anotherStat.getAgility(),
                evasion + anotherStat.getEvasion());
    }



    public int getCurrenthp() {
        return currenthp;
    }

    public int getMaxhp() {
        return maxhp;
    }

    public int getStrength() {
        return strength;
    }

    public int getDefence() {
        return defence;
    }

    public int getAgility() {
        return agility;
    }

    public double getAccuracy() {
        return accuracy;
    }

    public double getEvasion() {
        return evasion;
    }

    public void setCurrenthp(int currenthp) {
        this.currenthp = currenthp;
    }

    public void setMaxhp(int maxhp) {
        this.maxhp = maxhp;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public void setDefence(int defence) {
        this.defence = defence;
    }

    public void setAgility(int agility) {
        this.agility = agility;
    }

    public void setAccuracy(double accuracy) {
        this.accuracy = accuracy;
    }

    public void setEvasion(double evasion) {
        this.evasion = evasion;
    }

    @Override
    public String toString() {
        return "Status{" +
                "currenthp=" + currenthp +
                ", maxhp=" + maxhp +
                ", strength=" + strength +
                ", defence=" + defence +
                ", agility=" + agility +
                ", accuracy=" + accuracy +
                ", evasion=" + evasion +
                '}';
    }
}
