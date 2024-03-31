package transport;

import main.Game;
import main.Main;
import metod.render_metod;
import transport.Transport;

import java.util.ArrayList;

public class player_cannon_bull_tank extends Transport {
    public player_cannon_bull_tank(double x, double y, ArrayList<Transport> tr){
        this.x = x;this.y = y;
        this.speed_inert = 0;this.speed = 0;
        this.max_speed = 4;this.min_speed = -4;
        this.max_hp = 1200;
        this.damage = 180;
        this.armor = 50;
        this.spisok = tr;
        this.penetration = 25;
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
        this.sound_fire = Main.sa.get(0).cannon;

        this.speed_tower = 1;this.speed_rotation = 1;
        data();

    }
    public void all_action(int i) {
        super.all_action(i);
        super.motor_player();
        super.fire_player_bull_tank();
        super.build_corpus(Main.build);
        corpus_corpus(Main.enemy_obj,(byte)1);
        super.tower_xy();
        super.tower_player();
        //corpus_bull(main.Main.bull_obj,this.team);
        super.transport_delete(i,Main.player_obj);
        Main.rc.get(0).x = this.x;
        Main.rc.get(0).y = this.y;



    }
    public void update(){
        //g.fillOval((int) this.x, (int) this.y,10,10);
        center_render();
        render_metod.transorm_img(this.x_rend,this.y_rend,this.corpus_width,this.corpus_height,this.rotation_corpus,this.corpus_image);
        render_metod.transorm_img(this.x_tower_rend,this.y_tower_rend,this.tower_width,this.tower_height,this.rotation_tower,this.tower_image,x_tower* Game.zoom,y_tower* Game.zoom);
    }

}
