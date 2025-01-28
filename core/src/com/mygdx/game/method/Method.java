package com.mygdx.game.method;

import com.mygdx.game.build.Building;
import com.mygdx.game.soldat.Soldat;
import com.mygdx.game.transport.Transport;

import java.util.ArrayList;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.StrictMath.atan2;
import static java.lang.StrictMath.sqrt;

public class Method {
    public static float tower(double x, double y, double x_2, double y_2, float rotation_tower, float speed_tower) {
        double gh = atan2(y - y_2, x - x_2) / 3.1415926535 *180;
        gh +=90;
        if(gh>180 && rotation_tower<0){
            gh= -90;
        }
        if(gh<0 && rotation_tower>180){
            gh= 270;
        }
        if (rotation_tower > 269){rotation_tower = -89;}
        else if (rotation_tower < -89){rotation_tower = 269;}
        if (rotation_tower < gh) {
            rotation_tower += speed_tower;
        } else if (rotation_tower > gh) {
            rotation_tower -= speed_tower;
        }
        return rotation_tower;
    }
    public static float difference_rotation_sin(float x,float difference,float rotation){
        return (float) (x - (difference * sin(rotation * 3.1415926535 / 180.0)));
    }
    public static float difference_rotation_cos(float x,float difference,float rotation){
        return (float) (x - (difference * cos(rotation * 3.1415926535f / 180.0f)));
    }
    public static float[]tower_xy(float x,float y,float fire_x,float fire_y,float difference,float rotation){
        float tower_x = difference_rotation_sin(x+fire_x,difference,rotation);
        float tower_y = difference_rotation_cos(y+fire_y,difference,rotation);
        return new float[]{tower_x,tower_y};
    }
    public static float[]tower_xy_2(float x,float y,float fire_x,float fire_y,float difference,float difference_2,float rotation){
        float tower_x = difference_rotation_sin(x+fire_x,difference,rotation);
        float tower_y = difference_rotation_cos(y+fire_y,difference,rotation);
        tower_x = difference_rotation_sin(tower_x,difference_2,rotation-90);
        tower_y = difference_rotation_cos(tower_y,difference_2,rotation-90);
        return new float[]{tower_x,tower_y};
    }
    public static double tower_player(float x, float y, float rotation_tower, float speed_tower) {
        return tower(x, y, Keyboard.MouseX, Keyboard.MouseY, rotation_tower, speed_tower);

    }
    public static int detection_near_transport_i(Transport objBot, ArrayList<Transport> obj) {
        int ind = 0;
        int radius = 0;
        System.out.println();
        for (int i2 = 0; i2 < obj.size(); i2++) {
            double g = sqrt(pow2.pow2(objBot.x - obj.get(i2).x) + pow2.pow2(objBot.y - obj.get(i2).y));
            if (radius == 0 || radius > g) {
                ind = i2;
                radius = (int)g;

            }
        }
        return ind;
    }
    public static int[] detection_near_transport_xy_def(Transport objBot, ArrayList<Transport> obj) {
        int ind = 0;
        int radius = 0;
        double g;
        for (int i2 = 0; i2 < obj.size(); i2++) {
            g = sqrt(pow2.pow2(objBot.x - obj.get(i2).x) + pow2.pow2(objBot.y - obj.get(i2).y));
            if (radius > g || radius == 0) {
                if(obj.get(i2).x != objBot.x && obj.get(i2).y != objBot.y) {
                    ind = i2;
                    radius = (int) g;
                }
            }
        }
        return new int[]{ind,radius};
    }
    public static int[] detection_near_transport_i_def(ArrayList<Transport> obj_bot, int i, ArrayList<Transport> obj) {
        int ind = 0;
        int radius = 0;
        double g;
        for (int i2 = 0; i2 < obj.size(); i2++) {
            g = sqrt(pow2.pow2(obj_bot.get(i).x - obj.get(i2).x)) + pow2.pow2(obj_bot.get(i).y - obj.get(i2).y);
            if (radius > g || radius == 0) {
                if(i2 != i) {
                    ind = i2;
                    radius = (int) g;
                }
            }
        }
        return new int[]{ind,radius};
    }

    public static int[] detection_near_soldat_transport_i_def(ArrayList<Soldat> obj_bot, int i, ArrayList<Transport> obj) {
        int ind = 0;
        int radius = 0;
        double g;
        for (int i2 = 0; i2 < obj.size(); i2++) {
            g = sqrt(pow2.pow2(obj_bot.get(i).x - obj.get(i2).x) + pow2.pow2(obj_bot.get(i).y - obj.get(i2).y));
            if (radius > g || radius == 0) {
                if(obj.get(i2).x != obj_bot.get(i).x && obj.get(i2).y != obj_bot.get(i).y) {
                    ind = i2;
                    radius = (int) g;
                }
            }
        }
        return new int[]{ind,radius};
    }

    public static int detection_near_soldat_transport(ArrayList<Soldat> obj_bot, int i, ArrayList<Transport> obj) {
        int ind = 0;
        int radius = 0;
        for (int i2 = 0; i2 < obj.size(); i2++) {
            double g = sqrt(pow2.pow2(obj_bot.get(i).x - obj.get(i2).x) + pow2.pow2(obj_bot.get(i).y - obj.get(i2).y));
            if (radius == 0 || radius > g) {
                ind = i2;
                radius = (int) g;

            }
        }
        return ind;
    }
    public static int detection_near_soldat_build(ArrayList<Soldat> obj_bot, int i, ArrayList<Building> obj) {
        int ind = 0;
        int radius = 0;
        for (int i2 = 0; i2 < obj.size(); i2++) {
            double g = sqrt(pow2.pow2(obj_bot.get(i).x - obj.get(i2).x) + pow2.pow2(obj_bot.get(i).y - obj.get(i2).y));
            if (radius == 0 || radius > g) {
                ind = i2;
                radius = (int) g;
            }
        }
        return ind;
    }

}
