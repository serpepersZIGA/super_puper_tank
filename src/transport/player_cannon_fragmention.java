package transport;

import main.Game;
import main.Main;
import metod.render_metod;
import transport.Transport;
import transport.tower_bull_tank_player;

import java.util.ArrayList;

public class player_cannon_fragmention extends Transport {
    public player_cannon_fragmention(double x, double y, ArrayList<Transport> tr){
        this.x = x;this.y = y;
        this.speed_inert = 0;this.speed = 0;
        this.max_speed = 4;this.min_speed = -4;
        this.max_hp = 1200;
        this.spisok = tr;
        this.damage = 250;
        this.armor = 50;
        this.penetration = 7;
        this.acceleration = 0.2;
        this.rotation_tower = 0;
        this.rotation_corpus = 0;
        this.team = 1;
        this.difference =-18;
        this.tower_x_const = 8;
        this.tower_y_const = 30;
        this.tower_x = 0;
        this.tower_y = 0;
        this.reload_max = 120;
        this.height = 1;
        this.tower_image = "src/image/player/tower_player_1.png";
        this.corpus_image = "src/image/player/corpus_player_many_tower_1.png";
        this.t = 0;
        this.damage_fragment = 12;
        this.penetration_fragment = 4;
        this.x_tower = 0;
        this.y_tower = -8;
        this.sound_fire = Main.sa.get(0).cannon;


        this.speed_tower = 1;this.speed_rotation = 0.5;
        data();
        this.tower_obj.add(new tower_bull_tank_player(18,55,50,-10,5,2,20,12, this.id_unit,
                (byte)1,(byte)1,"src/image/player/tower_auxiliart_player_1.png",this.spisok,Main.sa.get(0).machinegun));
        this.tower_obj.add(new tower_bull_tank_player(18,55,50,10,5,2,20,12, this.id_unit,
                (byte)1,(byte)1,"src/image/player/tower_auxiliart_player_1.png",this.spisok,Main.sa.get(0).machinegun));
    }
    public void all_action(int i) {

        center_render();
        try {
            boolean[]mouse_e = Main.m_control.get(0).mouse_event();
            this.left_mouse = mouse_e[0];
            this.right_mouse = mouse_e[1];

        }
        catch (Exception es){}
        super.motor_player();
        super.fire_player_fragmentation_bull();
        super.build_corpus(Main.build);
        corpus_corpus(this.enemy_spisok,(byte)1);
        corpus_corpus_def_xy(this.enemy_spisok,(byte)1);
        super.tower_xy();
        super.tower_player();
        //corpus_bull(main.Main.bull_obj,this.team);
        super.transport_delete(i,this.spisok);
        Main.rc.get(0).x = this.x;
        Main.rc.get(0).y = this.y;
        super.all_action(i);
    }
    public void update() {
        center_render();
        render_metod.transorm_img(this.x_rend,this.y_rend,this.corpus_width,this.corpus_height,this.rotation_corpus,this.corpus_image);
        tower_iteration();
        render_metod.transorm_img(this.x_tower_rend,this.y_tower_rend,this.tower_width,this.tower_height,this.rotation_tower,this.tower_image,this.x_tower* Game.zoom,this.y_tower* Game.zoom);
    }
}
