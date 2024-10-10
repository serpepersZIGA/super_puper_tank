package com.mygdx.game.transport;

import com.mygdx.game.metod.render_metod;

import java.util.ArrayList;
import com.mygdx.game.main.Main;

public class track_remont_f1 extends Transport {
    public track_remont_f1(double x,double y, ArrayList<Transport> tr){
        this.x = x;this.y = y;
        this.speed_inert = 0;this.speed = 0;
        this.max_speed = 4;this.min_speed = -4;
        this.damage = 5;
        this.teg_unit = "support";
        this.penetration = 20;
        this.spisok = tr;
        this.max_hp = 1500;
        this.acceleration = 0.2;
        this.rotation_tower = 0;
        this.rotation_corpus = 70;
        this.difference = 0;
        this.tower_x_const = -10;
        this.tower_y_const = 3;
        this.tower_x = 0;
        this.tower_y = 0;
        this.medic_help = 0;
        this.height = 1;
        this.behavior = 4;
        this.reload_max = 180;
        this.team = 2;
        this.t = 0;
        this.hill = 2;
        this.corpus_img = Main.content_base.track_enemy_1lvl;
        this.corpus_width = 50;
        this.corpus_height = 129;

        this.speed_tower = 1;this.speed_rotation = 1;
        data();
    }
    public void all_action(int i){
        super.all_action(i);
        super.bypass_hiller(i);
        super.build_corpus(Main.build);
        super.hill_bot(Main.enemy_obj);
        //super.hill_bot(Main.en);
        super.corpus_corpus_def_xy(Main.enemy_obj,(byte)1);
        super.transport_delete_2(i,Main.enemy_obj);
        center_render();
        render_metod.transorm_img((int) (this.x_rend), (int)(this.y_rend),this.width_corpus_zoom,this.height_corpus_zoom,this.rotation_corpus,this.corpus_img,const_x_corpus,const_y_corpus);

    }
    public void update(){
        indicator_hp_2();
    }

}
