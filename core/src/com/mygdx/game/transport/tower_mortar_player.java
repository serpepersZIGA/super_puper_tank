package com.mygdx.game.transport;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.main.Main;
import com.mygdx.game.metod.render_metod;

import java.io.File;
import java.util.ArrayList;

public class tower_mortar_player extends Transport {
    public tower_mortar_player(double x_const, double y_const, int difference, int difference_2, double reload_max, double speed_rotation, double damage, double penetration,
                               double damage_fragment, double penetration_fragment, int ind_unit, byte height, byte team, Texture str, ArrayList<Transport>spisok,
                               File sound){
        this.tower_x_const = x_const;
        this.tower_y_const = y_const;
        this.spisok = spisok;
        this.sound_fire = sound;
        this.speed_tower = speed_rotation;
        this.damage_fragment = damage_fragment;
        this.penetration_fragment = penetration_fragment;
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
        data_tower();
        this.tower_width = 15;
        this.tower_height = 20;
        x_tower =7;
        y_tower =10;


    }
    public void tower_action(int i,double x,double y,double rotation,boolean sost_fire_bot) {
        this.x = x;
        this.y = y;
        this.rotation_corpus = rotation;
        this.left_mouse = sost_fire_bot;
        tower_xy_2();
        tower_player();
        fire_player_fragmentation_bull();
        center_render_tower();
        render_metod.transorm_img(this.x_tower_rend,this.y_tower_rend,this.tower_width*Main.zoom,this.tower_height*Main.zoom,this.rotation_tower,this.tower_img,x_tower* Main.zoom,y_tower* Main.zoom);

    }
}
