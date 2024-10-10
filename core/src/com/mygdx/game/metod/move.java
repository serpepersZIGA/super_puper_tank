package com.mygdx.game.metod;
import com.mygdx.game.main.Main;
import static java.lang.Math.*;

public class move {
    public static double move_sin(double speed,double rotation){
        return speed * sin(rotation *3.1415926535 /180);
    }public static double move_cos(double speed,double rotation){
        return speed * cos(rotation *3.1415926535 /180);
    }
}
