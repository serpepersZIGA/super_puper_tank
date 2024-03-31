package transport;

import main.Game;
import main.Main;
import metod.render_metod;

import java.io.File;
import java.util.ArrayList;

public class tower_bull_tank_enemy extends Transport {
    public tower_bull_tank_enemy(double x_const, double y_const, int difference, int difference_2, double reload_max, double speed_rotation, double damage, double penetration,
                                 int ind_unit, byte height, byte team, String tower_image, ArrayList<Transport> spisok, File sound){
        this.tower_x_const = x_const;
        this.tower_y_const = y_const;
        this.spisok = spisok;
        this.sound_fire = sound;
        this.speed_tower = speed_rotation;
        this.difference = -difference;
        this.reload_max = reload_max;
        this.reload = this.reload_max;
        this.damage = damage;
        this.penetration = penetration;
        this.tower_image = tower_image;
        this.id_unit = ind_unit;
        this.teg_unit = "tower";
        this.height = height;
        this.team = team;
        this.difference_2 = -difference_2;
        data_tower();



    }
    public void tower_action(int i,double x,double y,double rotation,boolean s) {
        this.x = x;
        this.y = y;
        this.rotation_corpus = rotation;
        tower_xy_2();
        super.tower_ii(i);
        bot_bull_tank_fire(i, Main.tower_obj, this.enemy_spisok);
        update();
    }
    public void update(){
        //this.fire_x = this.tower_x+this.tower_width/2-(this.tower_width*sin(-this.rotation_corpus*3.1415926535/180));
        //this.fire_y = this.tower_y+this.tower_height/2-(this.tower_height*cos(-this.rotation_corpus*3.1415926535/180));
        center_render();
        render_metod.transorm_img(this.x_tower_rend,this.y_tower_rend,this.tower_width,this.tower_height,this.rotation_tower,this.tower_image,0,-3* Game.zoom);
    }
}

