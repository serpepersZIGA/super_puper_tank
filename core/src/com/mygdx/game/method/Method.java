package com.mygdx.game.method;

import com.mygdx.game.build.Building;
import com.mygdx.game.main.Main;
import com.mygdx.game.particle.Particle;
import com.mygdx.game.soldat.Soldat;
import com.mygdx.game.transport.Transport;

import java.util.ArrayList;
import java.util.LinkedList;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.StrictMath.atan2;
import static java.lang.StrictMath.sqrt;
import static java.sql.Types.NULL;

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
        float tower_x = difference_rotation_sin(x+fire_x,-difference,rotation);
        float tower_y = difference_rotation_cos(y+fire_y,-difference,rotation);
        return new float[]{tower_x,tower_y};
    }
    public static float[]tower_xy_2(float x,float y,float fire_x,float fire_y,float difference,float difference_2,float rotation){
        float tower_x = difference_rotation_sin(x+fire_x,-difference,rotation);
        float tower_y = difference_rotation_cos(y+fire_y,-difference,rotation);
        tower_x = difference_rotation_sin(tower_x,-difference_2,rotation-90);
        tower_y = difference_rotation_cos(tower_y,-difference_2,rotation-90);
        return new float[]{tower_x,tower_y};
    }
    public static double tower_player(float x, float y, float rotation_tower, float speed_tower) {
        return tower(x, y, Keyboard.MouseX, Keyboard.MouseY, rotation_tower, speed_tower);

    }
    public static int detection_near_transport_i(ArrayList<Transport> obj_bot, int i, ArrayList<Transport> obj) {
        int ind = 0;
        int radius = 0;
        for (int i2 = 0; i2 < obj.size(); i2++) {
            double g = sqrt(pow2.pow2(obj_bot.get(i).x - obj.get(i2).x) + pow2.pow2(obj_bot.get(i).y - obj.get(i2).y));
            if (radius == 0 || radius > g) {
                ind = i2;
                radius = (int)g;

            }
        }
        return ind;
    }
    public static int[] detection_near_transport_xy_def(ArrayList<Transport> obj_bot, int i, ArrayList<Transport> obj) {
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
    public static int[] detection_near_particle_xy_def(LinkedList<Particle> obj_bot, int i, LinkedList<Particle> obj) {
        double g;
        int gh;
        int ind = 0;
        int radius = 0;
        for (int i2 = 0; i2 < obj.size(); i2++) {
            g = sqrt(pow2.pow2(obj_bot.get(i).x - obj.get(i2).x) + pow2.pow2(obj_bot.get(i).y - obj.get(i2).y));
            if(g<obj.get(i).size_2+obj_bot.get(i2).size_2){
                float r = (float) (atan2(obj_bot.get(i).y - obj.get(ind).y, obj_bot.get(i).x - obj.get(ind).x) / 3.14 * 180);
                obj.get(i).x += move.move_sin2(7, r);
                obj.get(i).y += move.move_cos2(7, r);
                obj_bot.get(i2).x -= move.move_sin2(7, r);
                obj_bot.get(i2).y -= move.move_cos2(7, r);
                obj_bot.get(i2).xCollision -= move.move_sin2(7, r);
                obj_bot.get(i2).yCollision -= move.move_cos2(7, r);
                obj.get(i2).xCollision += move.move_sin2(7, r);
                obj.get(i2).yCollision += move.move_cos2(7, r);

            }
            if (radius > g || radius == 0) {
                if (obj.get(i2).x != obj_bot.get(i).x && obj.get(i2).y != obj_bot.get(i).y) {
                    radius = (int)g;
                    ind = i2;}
            }
        }
        gh = (int)(atan2(obj_bot.get(i).y - obj.get(ind).y, obj_bot.get(i).x - obj.get(ind).x) / 3.14 * 180);
        return new int[]{radius,gh,ind, (int) obj.get(ind).x, (int) obj.get(i).y};
    }
    public static float f = 1.4F;
    public static int[] detection_near_particle_xy_def(ArrayList<Particle> obj_bot, int i, ArrayList<Particle> obj) {
        double g;
        int gh;
        int ind = 0;
        int radius = 0;
        for (int i2 = 0; i2 < obj.size(); i2++) {
            g = sqrt(pow2.pow2(obj_bot.get(i).x - obj.get(i2).x) + pow2.pow2(obj_bot.get(i).y - obj.get(i2).y));
            if(g<obj.get(i).size_2+obj_bot.get(i2).size_2){
                float r = (float) (atan2(obj_bot.get(i).y - obj.get(ind).y, obj_bot.get(i).x - obj.get(ind).x) / 3.14f * 180f);
                obj.get(i).x += move.move_sin2(7, r);
                obj.get(i).y += move.move_cos2(7, r);
                obj_bot.get(i2).x -= move.move_sin2(7, r);
                obj_bot.get(i2).y -= move.move_cos2(7, r);

                obj_bot.get(i2).speed_x -= move.move_sin2(7, r)/f;
                obj_bot.get(i2).speed_y -= move.move_cos2(7, r)/f;
                obj.get(i).speed_x += move.move_sin2(7, r);
                obj.get(i).speed_y += move.move_cos2(7, r);

                obj_bot.get(i2).xCollision -= move.move_sin2(7, r);
                obj_bot.get(i2).yCollision -= move.move_cos2(7, r);

            }
            if (radius > g || radius == 0) {
                if (obj.get(i2).x != obj_bot.get(i).x && obj.get(i2).y != obj_bot.get(i).y) {
                    radius = (int)g;
                    ind = i2;}
            }
        }
        obj.get(i).speed_x /=f;
        obj.get(i).speed_y /=f;

        gh = (int)(atan2(obj_bot.get(i).y - obj.get(ind).y, obj_bot.get(i).x - obj.get(ind).x) / 3.14 * 180);
        return new int[]{radius,gh,ind, (int) obj.get(ind).x, (int) obj.get(i).y};
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
