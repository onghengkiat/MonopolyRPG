package MonopolyRPG;

import java.util.HashMap;
 public enum Weapon {
     //weapon dictionary
     SWORD (1, "Sword",new Status(0,0,3,0,1,0,0)),
     AXE (2, "Axe", new Status(0,10,5,0,-3,-0.05,-0.05));

    private int weaponID;
    private String description;
    private boolean isEquipped;
    private Status status;

    Weapon(int weaponID, String description, Status status) {
        this.weaponID = weaponID;
        this.status = status;
        this.description = description;
        this.isEquipped = false;
    }

    public void setEquipped(boolean isEquipped){
        this.isEquipped = isEquipped;
    }

    public boolean isEquipped(){
        return isEquipped;
    }

     public Status getStatus() {
         return status;
     }

     @Override
    public String toString() {
        return "Name=" + description +
                "\nAttributes= " + status +
                "\nEquipped= " + isEquipped;
    }
}
