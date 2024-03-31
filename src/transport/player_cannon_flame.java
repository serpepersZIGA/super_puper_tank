package transport;

import main.Game;
import main.Main;
import metod.render_metod;

import java.util.ArrayList;

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
        this.difference =-18;
        this.tower_x_const = 8;
        this.tower_y_const = 30;
        this.reload_max = 2;
        this.height = 1;
        this.tower_image = "src/image/player/tower_player_1.png";
        this.corpus_image = "src/image/player/corpus_player_many_tower_1.png";
        this.t = 0;
        this.t_damage = 1;
        this.x_tower = 0;
        this.y_tower = -8;


        this.speed_tower = 1;this.speed_rotation = 1;
        this.sound_fire = Main.sa.get(0).flame_attack;
        data();
        this.tower_obj.add(new tower_flame_player(18,55,52,-12,4,2,65,12,2, this.id_unit,
                (byte)1,(byte)1,"src/image/player/tower_auxiliart_player_1.png",this.spisok, Main.sa.get(0).flame_attack));
        this.tower_obj.add(new tower_flame_player(18,55,52,12,4,2,65,12,2, this.id_unit,
                (byte)1,(byte)1,"src/image/player/tower_auxiliart_player_1.png",this.spisok, Main.sa.get(0).flame_attack));

    }
    public void all_action(int i) {
        try {
            boolean[]mouse_e = Main.m_control.get(0).mouse_event();
            this.left_mouse = mouse_e[0];
            this.right_mouse = mouse_e[1];

        }
        catch (Exception es){}
        super.host_control();
        super.motor_player();
        super.fire_player_flame();
        super.build_corpus(Main.build);
        super.corpus_corpus(this.enemy_spisok,(byte)1);
        corpus_corpus_def_xy(this.enemy_spisok,(byte)1);
        super.tower_xy();
        super.tower_player();
        //corpus_bull(main.Main.bull_obj,this.team);
        super.transport_delete(i,this.spisok);
        Main.rc.get(0).x = this.x;
        Main.rc.get(0).y = this.y;
        super.all_action(i);
        center_render();
        render_metod.transorm_img(this.x_rend,this.y_rend,this.corpus_width,this.corpus_height,this.rotation_corpus,this.corpus_image);
        tower_iteration();
        render_metod.transorm_img(this.x_tower_rend,this.y_tower_rend,this.tower_width,this.tower_height,this.rotation_tower,this.tower_image,this.x_tower* Game.zoom,this.y_tower* Game.zoom);
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
        render_metod.transorm_img(this.x_rend,this.y_rend,this.corpus_width,this.corpus_height,this.rotation_corpus,this.corpus_image);
        tower_iteration();
        render_metod.transorm_img(this.x_tower_rend,this.y_tower_rend,this.tower_width,this.tower_height,this.rotation_tower,this.tower_image,this.x_tower* Game.zoom,this.y_tower* Game.zoom);
    }

    @Override
    public void all_action_client() {
        center_render();
        render_metod.transorm_img(this.x_rend,this.y_rend,this.corpus_width,this.corpus_height,this.rotation_corpus,this.corpus_image);
        tower_iteration();
        render_metod.transorm_img(this.x_tower_rend,this.y_tower_rend,this.tower_width,this.tower_height,this.rotation_tower,this.tower_image,this.x_tower* Game.zoom,this.y_tower* Game.zoom);

    }
    @Override
    public void all_action_client_1() {
        Main.rc.get(0).x = this.x;
        Main.rc.get(0).y = this.y;
        center_render();
        render_metod.transorm_img(this.x_rend,this.y_rend,this.corpus_width,this.corpus_height,this.rotation_corpus,this.corpus_image);
        tower_iteration();
        render_metod.transorm_img(this.x_tower_rend,this.y_tower_rend,this.tower_width,this.tower_height,this.rotation_tower,this.tower_image,this.x_tower* Game.zoom,this.y_tower* Game.zoom);

    }

}
