package com.mygdx.game.transport;

import com.mygdx.game.metod.rand;
import com.mygdx.game.metod.render_metod;

import java.util.ArrayList;
import com.mygdx.game.main.Main;

import static java.lang.StrictMath.cos;
import static java.lang.StrictMath.sin;

public class panzer_mt1_flame extends Transport {
    public panzer_mt1_flame(double x, double y, ArrayList<Transport> tr){
        this.x = x;this.y = y;
        this.speed_inert = 0;this.speed = 0;
        this.max_speed = 4;this.min_speed = -4;
        this.max_hp = 1200;
        this.damage = 10;
        this.spisok = tr;
        this.armor = 50;
        this.penetration = 2;
        this.acceleration = 0.2;
        this.team = 2;
        this.tower_x_const = 8;
        this.tower_y_const = 40;
        this.behavior = (byte) rand.rand(2,3);
        this.reload_max = 2;
        this.height = 1;
        this.tower_image = "src/image/player/tower_player_1.png";
        this.corpus_image = "src/image/player/corpus_player_many_tower_1.png";
        this.tower_img = Main.content_base.tower_enemy;
        this.corpus_img = Main.content_base.corpus_enemy;
        this.t = 0;
        this.t_damage = 1;
        this.x_tower = 15;
        this.y_tower = 20;
        this.difference = -18;
        this.distance_target = 150;
        this.distance_target_2 = 30;


        this.corpus_width = 50;
        this.corpus_height = 129;
        this.tower_width = 35;
        this.tower_height = 55;
        this.speed_tower = 1;this.speed_rotation = 1;
        this.sound_fire = Main.sa.get(0).flame_attack;
        data();
//        this.tower_obj.add(new tower_flame_enemy(18,55,52,-12,4,2,65,12,2, this.id_unit,
//                (byte)1,(byte)2,Main.content_base.tower_player_auxiliart_1,this.spisok, Main.sa.get(0).flame_attack));
        this.tower_obj.add(new tower_bull_tank_enemy(18,55,52,-12,4,2,65,12, this.id_unit,
                (byte)1,(byte)2,Main.content_base.tower_enemy_auxiliart_1,this.spisok, Main.sa.get(0).flame_attack));
        this.tower_obj.add(new tower_flame_enemy(18,55,52,12,4,2,65,12,2, this.id_unit,
                (byte)1,(byte)2,Main.content_base.tower_enemy_auxiliart_1,this.spisok, Main.sa.get(0).flame_attack));
        const_tower_x = 17;
        const_tower_y = 20;


    }
    public void all_action(int i) {
        super.all_action(i);
        super.motor_player();
        super.motor_bot_bypass(i,Main.enemy_obj,Main.player_obj);
        super.behavior_bot(this.enemy_spisok, i);
        super.bot_flame_fire(i, spisok,enemy_spisok);
        super.tower_ii(i);
        super.build_corpus(Main.build);
        super.corpus_corpus_def_xy(this.spisok,(byte)1);
        super.tower_xy();
        center_render();
        render_metod.transorm_img((int) (this.x_rend), (int)(this.y_rend),this.width_corpus_zoom,this.height_corpus_zoom,this.rotation_corpus,this.corpus_img,const_x_corpus,const_y_corpus);
        tower_iteration_bot(i);
        render_metod.transorm_img((int) (this.x_tower_rend), (int)(this.y_tower_rend),this.width_tower_zoom,this.height_tower_zoom,this.rotation_tower,this.tower_img,const_x_tower,const_y_tower
        );
        super.transport_delete_2(i,Main.enemy_obj);
    }
    public void update(){
        indicator_reload();
        indicator_hp_2();
    }


}
