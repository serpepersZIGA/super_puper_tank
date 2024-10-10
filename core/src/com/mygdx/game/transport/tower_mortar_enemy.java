package com.mygdx.game.transport;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.metod.render_metod;
import com.mygdx.game.main.Main;

import java.io.File;
import java.util.ArrayList;

public class tower_mortar_enemy extends Transport {
    public tower_mortar_enemy(double x_const, double y_const, int difference, int difference_2, double reload_max, double speed_rotation, double damage, double penetration,
                              double damage_fragment, double penetration_fragment, int ind_unit, byte height, byte team, Texture str, ArrayList<Transport> spisok
    , File sound){

        this.tower_x_const = x_const;
        this.tower_y_const = y_const;
        this.damage_fragment = damage_fragment;
        this.penetration_fragment = penetration_fragment;
        this.spisok = spisok;
        this.sound_fire = sound;
        this.speed_tower = speed_rotation;
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
    public void tower_action(int i,double x,double y,double rotation,boolean sost,boolean sost_2,double aim_x,double aim_y) {
        this.x = x;
        this.y = y;
        this.rotation_corpus = rotation;
        this.trigger_attack = sost;
        this.trigger_fire = sost_2;
        this.aim_x = aim_x;
        this.aim_y = aim_y;
        tower_xy_2();
        tower_ii_2();
        bot_fragmentation_bull_fire(i, Main.tower_obj, this.enemy_spisok);
        center_render_tower();
        render_metod.transorm_img(this.x_tower_rend,this.y_tower_rend,this.tower_width,this.tower_height,this.rotation_tower,this.tower_img,0,-3* Main.zoom);
    }
}

