package com.mygdx.game.transport;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.main.Main;
import com.mygdx.game.metod.render_metod;

import java.io.File;
import java.util.ArrayList;

import static java.lang.StrictMath.cos;
import static java.lang.StrictMath.sin;

public class tower_flame_player extends Transport {
    public tower_flame_player(double x_const, double y_const, int width_tower, int height_tower, int difference, int difference_2, double reload_max, double speed_rotation, double damage, double penetration,
                              double t_damage, int ind_unit, byte height, byte team, Texture str, ArrayList<Transport> spisok, File sound){
        this.tower_x_const = x_const;
        this.tower_y_const = y_const;
        this.spisok = spisok;
        this.sound_fire = sound;
        this.speed_tower = speed_rotation;
        this.t_damage = t_damage;
        this.difference = -difference;
        this.reload_max = reload_max;
        this.reload = this.reload_max;
        this.damage = damage;
        this.penetration = penetration;
        this.tower_img = str;
        this.id_unit = ind_unit;
        this.teg_unit = "tower";
        this.height = height;
        this.team = team;
        this.tower_width = width_tower;
        this.tower_height = height_tower;
        this.difference_2 = -difference_2;
        data_tower();
        this.tower_width = width_tower;
        this.tower_height = height_tower;


    }
    public tower_flame_player(double x_const, double y_const, int difference, int difference_2, double reload_max, double speed_rotation, double damage, double penetration,
                              double t_damage, int ind_unit, byte height, byte team, Texture str, ArrayList<Transport> spisok, File sound){
        this.tower_x_const = x_const;
        this.tower_y_const = y_const;
        this.spisok = spisok;
        this.sound_fire = sound;
        this.speed_tower = speed_rotation;
        this.t_damage = t_damage;
        this.difference = -difference;
        this.reload_max = reload_max;
        this.reload = this.reload_max;
        this.damage = damage;
        this.penetration = penetration;
        this.tower_img = str;
        this.id_unit = ind_unit;
        this.teg_unit = "tower";
        this.height = height;
        this.team = team;
        this.difference_2 = -difference_2;
        this.tower_width = 15;
        this.tower_height = 20;
        data_tower();
        x_tower =7;
        y_tower =10;


    }
    public void tower_action(int i,double x,double y,double rotation,boolean sost_fire_bot) {
        this.x = x;
        this.y = y;
        this.rotation_corpus = rotation;
        this.left_mouse = sost_fire_bot;
        super.tower_xy_2();
        super.tower_player();
        super.fire_player_flame();
        center_render_tower();
        render_metod.transorm_img(this.x_tower_rend,this.y_tower_rend,this.tower_width*Main.zoom,this.tower_height*Main.zoom,this.rotation_tower,this.tower_img,x_tower* Main.zoom,y_tower* Main.zoom);
    }
}
