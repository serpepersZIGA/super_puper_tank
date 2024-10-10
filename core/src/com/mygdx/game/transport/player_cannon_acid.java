package com.mygdx.game.transport;

import com.mygdx.game.metod.render_metod;

import java.util.ArrayList;
import com.mygdx.game.main.Main;

public class player_cannon_acid extends Transport {
    public player_cannon_acid(double x, double y, ArrayList<Transport> tr){
        this.x = x;this.y = y;
        this.speed_inert = 0;this.speed = 0;
        this.max_speed = 7;this.min_speed = -7;
        this.max_hp = 1200;
        this.damage = 12;
        this.spisok = tr;
        this.armor = 50;
        this.penetration = 20;
        this.acceleration = 0.2;
        this.rotation_tower = 0;
        this.rotation_corpus = 0;
        this.team = 1;
        this.difference =-18;
        this.tower_x = 0;
        this.tower_y = 0;
        this.reload_max = 2;
        this.height = 1;
        this.tower_image = "src/image/player/tower_player_1.png";
        this.corpus_image = "src/image/player/corpus_player_many_tower_1.png";
        this.tower_img = Main.content_base.tower_player;
        this.corpus_img = Main.content_base.corpus_player;
        this.corpus_width = 50;
        this.corpus_height = 129;
        this.tower_width = 35;
        this.tower_height = 55;
        this.t = 0;
        this.tower_x_const = 8;
        this.tower_y_const = 40;
        this.x_tower = 15;
        this.y_tower = 20;
        const_tower_x = 17;
        const_tower_y = 20;
        this.speed_tower = 1;this.speed_rotation = 3;
        this.sound_fire = Main.sa.get(0).acid_attack;
        data();

    }
    public void all_action(int i) {
        indicator_reload();
        indicator_hp();
        super.all_action(i);
        super.host_control();
        super.motor_player();
        super.fire_player_acid();
        super.build_corpus(Main.build);
        super.corpus_corpus(this.enemy_spisok,(byte)1);
        corpus_corpus_def_xy(this.enemy_spisok,(byte)1);
        super.tower_xy();
        super.tower_player();
        //corpus_bull(main.Main.bull_obj,this.team);
        Main.rc.get(0).x = this.tower_x;
        Main.rc.get(0).y = this.tower_y;
        center_render();
        render_metod.transorm_img((int) (this.x_rend), (int)(this.y_rend),this.width_corpus_zoom,this.height_corpus_zoom,this.rotation_corpus,this.corpus_img,const_x_corpus,const_y_corpus);
        tower_iteration();
        render_metod.transorm_img((int) (this.x_tower_rend), (int)(this.y_tower_rend),this.width_tower_zoom,this.height_tower_zoom,this.rotation_tower,this.tower_img,const_x_tower,const_y_tower);
        super.transport_delete(i,this.spisok);


    }
    public void update(){
        indicator_reload();
        indicator_hp();
    }
}
