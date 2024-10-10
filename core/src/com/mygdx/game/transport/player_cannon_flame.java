package com.mygdx.game.transport;

import com.mygdx.game.metod.render_metod;
import com.mygdx.game.main.Main;

import java.util.ArrayList;

import static java.lang.StrictMath.cos;
import static java.lang.StrictMath.sin;

public class player_cannon_flame extends Transport {
    public player_cannon_flame(double x, double y, ArrayList<Transport> tr){
        this.x = x;this.y = y;
        this.speed_inert = 0;this.speed = 0;
        this.max_speed = 4;this.min_speed = -4;
        this.max_hp = 1200;
        this.damage = 10;
        this.spisok = tr;
        this.armor = 50;
        this.penetration = 2;
        this.acceleration = 0.2;
        this.team = 1;
        this.tower_x_const = 8;
        this.tower_y_const = 40;
        this.reload_max = 2;
        this.height = 1;
        this.tower_image = "src/image/player/tower_player_1.png";
        this.corpus_image = "src/image/player/corpus_player_many_tower_1.png";
        this.tower_img = Main.content_base.tower_player;
        this.corpus_img = Main.content_base.corpus_player;
        this.t = 0;
        this.t_damage = 1;
        this.x_tower = 15;
        this.y_tower = 20;
        this.difference = -18;

        this.corpus_width = 50;
        this.corpus_height = 129;
        this.tower_width = 35;
        this.tower_height = 55;
        this.speed_tower = 1;this.speed_rotation = 1;
        this.sound_fire = Main.sa.get(0).flame_attack;
        data();
        this.tower_obj.add(new tower_mortar_player(18,55,52,-12,40,2,65,12,15,15, this.id_unit,
                (byte)1,(byte)1,Main.content_base.tower_player_auxiliart_1,this.spisok, Main.sa.get(0).cannon));
        this.tower_obj.add(new tower_mortar_player(18,55,52,12,40,2,65,12,15,15, this.id_unit,
                (byte)1,(byte)1,Main.content_base.tower_player_auxiliart_1,this.spisok, Main.sa.get(0).cannon));
        const_tower_x = 17;
        const_tower_y = 20;

    }
    public void all_action(int i) {
        super.all_action(i);
        super.host_control();
        super.motor_player();
        super.fire_player_flame();
        super.build_corpus(Main.build);
        super.corpus_corpus(this.enemy_spisok,(byte)1);
        //corpus_corpus_def_xy(this.enemy_spisok,(byte)1);
        super.tower_xy();
        super.tower_player();
        //corpus_bull(main.Main.bull_obj,this.team);
        Main.rc.get(0).x = this.tower_x;
        Main.rc.get(0).y = this.tower_y;
        center_render();
        render_metod.transorm_img((int) (this.x_rend), (int)(this.y_rend),this.width_corpus_zoom,this.height_corpus_zoom,this.rotation_corpus,this.corpus_img,const_x_corpus,const_y_corpus);
        tower_iteration();
        render_metod.transorm_img((int) (this.x_tower_rend), (int)(this.y_tower_rend),this.width_tower_zoom,this.height_tower_zoom,this.rotation_tower,this.tower_img,const_x_tower,const_y_tower
        );
        super.transport_delete(i,this.spisok);

    }
    public void all_action_client(int i,boolean left_mouse,boolean right_mouse,double mx,double my,boolean w,
    boolean a,boolean s,boolean d){
        this.left_mouse = left_mouse;
        this.right_mouse = right_mouse;
        this.press_w = w;
        this.press_a = a;
        this.press_s = s;
        this.press_d = d;
        super.motor_player();
        super.fire_player_flame();
        super.build_corpus(Main.build);
        super.corpus_corpus(this.enemy_spisok,(byte)1);
        corpus_corpus_def_xy(this.enemy_spisok,(byte)1);
        super.tower_xy();
        super.tower_player_client(mx,my);
        //corpus_bull(main.Main.bull_obj,this.team);
        super.transport_delete(i,this.spisok);
        //main.Main.rc.get(0).x = this.x;
        //main.Main.rc.get(0).y = this.y;
        super.all_action(i);
        center_render();
        render_metod.transorm_img(this.x_rend,this.y_rend,this.corpus_width,this.corpus_height,this.rotation_corpus,this.corpus_img);
        tower_iteration();
        render_metod.transorm_img(this.x_tower_rend,this.y_tower_rend,this.tower_width,this.tower_height,this.rotation_tower,this.tower_img,this.x_tower* Main.zoom,this.y_tower* Main.zoom);
    }

    @Override
    public void all_action_client() {
        center_render();
        render_metod.transorm_img(this.x_rend,this.y_rend,this.corpus_width,this.corpus_height,this.rotation_corpus,this.corpus_img);
        tower_iteration();
        this.fire_x = (this.tower_x+((this.y_tower) *sin(-this.rotation_tower*3.1415926535/180)));
        this.fire_y = (this.tower_y+((this.y_tower) *cos(-this.rotation_tower*3.1415926535/180)));
        render_metod.transorm_img(this.x_tower_rend,this.y_tower_rend,this.tower_width,this.tower_height,this.rotation_tower,this.tower_img,this.x_tower* Main.zoom,this.y_tower* Main.zoom);

    }
    @Override
    public void all_action_client_1() {
        Main.rc.get(0).x = this.x;
        Main.rc.get(0).y = this.y;
        center_render();
        render_metod.transorm_img(this.x_rend,this.y_rend,this.corpus_width,this.corpus_height,this.rotation_corpus,this.corpus_img);
        tower_iteration();
        render_metod.transorm_img(this.x_tower_rend,this.y_tower_rend,this.tower_width,this.tower_height,this.rotation_tower,this.tower_img,this.x_tower* Main.zoom,this.y_tower* Main.zoom);

    }
    public void update(){
        indicator_reload();
        indicator_hp();
    }

}
