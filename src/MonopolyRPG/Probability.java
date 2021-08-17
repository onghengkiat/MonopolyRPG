package MonopolyRPG;

import java.util.Random;

public class Probability {
    public static boolean escaped(int escaper_agility, int enemy_agility, int trials){
        //probability = ability1 * 32 /ability2 + (30 * number of tries)
        if (enemy_agility <= 0 ){
            return true;
        }
        int threshold = escaper_agility * 32 / enemy_agility + (30 * trials);
        System.out.println(threshold);
        if(threshold >= 256){
            return true;
        }
        Random rnd = new Random();
        if(rnd.nextInt(256) < threshold){
            return true;
        }else{
            return false;
        }
    }

    public static boolean hit(double attacker_accuracy, double defender_evasion){
        //miss rate
        int miss = (int)Math.round(( (1 - attacker_accuracy) * defender_evasion) * 100);
        Random rnd = new Random();
        if(miss > rnd.nextInt(100)){
            return false;
        }else{
            return true;
        }

    }

    public static boolean dropped(double droprate){
        Random rnd = new Random();
        return rnd.nextInt(100) <= (int)Math.round(droprate*100);
    }
}
