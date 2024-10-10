package com.mygdx.game.transport;

import com.mygdx.game.metod.rand;
import com.mygdx.game.metod.render_metod;

import java.util.ArrayList;
import com.mygdx.game.main.Main;
public class helicopter_t1 extends Transport {
        public helicopter_t1(double x, double y, ArrayList<Transport>tr){
            this.x = x;this.y = y;
            this.speed_inert = 0;this.speed = 0;
            this.max_speed = 4;this.min_speed = -4;
            this.damage = 200;
            this.spisok = tr;
            this.penetration = 20;
            this.max_hp = 850;
            this.armor = 50;
            this.acceleration = 0.2;
            this.difference = -22;
            this.tower_x_const = -12;
            this.tower_y_const = 20;
            this.height = 2;
            this.behavior = (byte) rand.rand(2,3);
            this.reload_max = 180;
            this.team = 2;
            this.priority_paint = 1;


            this.corpus_image = "src/image/enemy/helicopter_corpus.png";
            this.tower_image = "src/image/other/blade_helicopter.png";
            this.speed_rotation = 3;
            data();
        }
        public void all_action(int i) {
            super.all_action(i);
            super.helicopter_ii(Main.player_obj,i);
            super.bot_bull_tank_fire_not_tower(this.spisok,i);
            super.tower_xy();
            super.blade_helicopter();
            super.transport_delete(i,this.spisok);

        }
        public void update(){
            center_render();
            render_metod.transorm_img(this.x_rend,this.y_rend,this.corpus_width,this.corpus_height,this.rotation_corpus,this.corpus_img);
            render_metod.transorm_img(this.x_tower_rend,this.y_tower_rend,this.tower_width,this.tower_height,this.rotation_tower,this.tower_img);
        }

}
