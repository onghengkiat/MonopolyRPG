package MonopolyRPG;

import java.util.Random;

public class Dice {
    private final int MAX = 6, MIN = 1;

    public Dice() {

    }

    public int roll(){
        Random rnd = new Random();
        return rnd.nextInt(MAX - MIN + 1) + MIN;
    }
}
