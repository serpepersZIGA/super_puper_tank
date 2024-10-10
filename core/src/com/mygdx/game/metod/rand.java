package com.mygdx.game.metod;
import com.mygdx.game.main.Main;
public class rand {
    public static float rand (float min,float max) {
        return (float)(Math.random() * ((max - min) + 1)) + min;
    }
    public static int rand (int min,int max) {
        return (int)((Math.random() * ((max - min) + 1)) + min);
    }
    public static short rand (short min,short max) {
        return (short)((Math.random() * ((max - min) + 1)) + min);
    }
    public static byte rand (byte min,byte max) {
        return (byte)((Math.random() * ((max - min) + 1)) + min);
    }
    public static double rand (double min,double max) {
        return (Math.random() * ((max - min) + 1)) + min;
    }

}
