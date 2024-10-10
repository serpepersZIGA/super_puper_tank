package com.mygdx.game.transport;

import com.mygdx.game.metod.render_metod;

import java.util.ArrayList;
import com.mygdx.game.main.Main;
public class panzer_mt1 extends Transport {
    public panzer_mt1(double x, double y, ArrayList<Transport> tr){
        this.x = x;this.y = y;
        this.speed_inert = 0;this.speed = 0;
        this.max_speed = 4;this.min_speed = -4;
        this.damage = 100;
        this.penetration = 20;
        this.max_hp = 1500;
        this.armor = 50;
        this.spisok = tr;
        this.acceleration = 0.2;
        this.rotation_tower = 0;
        this.rotation_corpus = 70;
        this.tower_x = 0;
        this.tower_y = 0;
        this.medic_help = 0;
        this.height = 1;
        this.behavior = 3;//(byte)metod.rand.metod.rand(1,3);
        this.reload_max = 180;
        this.team = 2;
        this.t = 0;
        this.difference =-18;
        this.tower_x_const = 8;
        this.tower_y_const = 30;
        this.x_tower = 0;
        this.y_tower = -8;
        this.sound_fire = Main.sa.get(0).cannon;


        this.corpus_image = "src/image/enemy/corpus_enemy_many_tower_1.png";
        this.tower_image = "src/image/enemy/tower_enemy_1.png";

        this.speed_tower = 1;this.speed_rotation = 1;
        super.data();
        this.tower_obj.add(new tower_flame_enemy(18,55,50,-10,10,2,65,12,2, this.id_unit,
                (byte)1,this.team,Main.content_base.tower_player,this.spisok,Main.sa.get(0).flame_attack));
        this.tower_obj.add(new tower_flame_enemy(18,55,50,10,10,2,65,12,2, this.id_unit,
                (byte)1,this.team,Main.content_base.tower_player,this.spisok,Main.sa.get(0).flame_attack));


    }
    public void all_action(int i) {
        super.all_action(i);
        super.tower_ii(i);
        super.bot_bull_tank_fire(i, this.spisok, this.enemy_spisok);
        super.behavior_bot(this.enemy_spisok, i);
        super.build_corpus(Main.build);
        super.corpus_corpus_def_xy(this.spisok,(byte)1);
        super.tower_xy();
        super.transport_delete_2(i,this.spisok);
        center_render();
        render_metod.transorm_img((int) (this.x_rend), (int)(this.y_rend),this.width_corpus_zoom,this.height_corpus_zoom,this.rotation_corpus,this.corpus_img,const_x_corpus,const_y_corpus);
        tower_iteration_bot(i);
        render_metod.transorm_img((int) (this.x_tower_rend), (int)(this.y_tower_rend),this.width_tower_zoom,this.height_tower_zoom,this.rotation_tower,this.tower_img,const_x_tower,const_y_tower
        );
    }
    public void update(){
        indicator_reload();
        indicator_hp_2();
    }


}
