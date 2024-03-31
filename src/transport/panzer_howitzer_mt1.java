package transport;

import metod.rand;
import metod.render_metod;
import main.Game;
import main.Main;
import java.util.ArrayList;

public class panzer_howitzer_mt1 extends Transport {
    public panzer_howitzer_mt1(double x, double y, ArrayList<Transport>tr){
        this.x = x;this.y = y;
        this.speed_inert = 0;this.speed = 0;
        this.max_speed = 4;this.min_speed = -4;
        this.damage = 250;
        this.armor = 50;
        this.spisok = tr;
        this.penetration = 7;
        this.max_hp = 1500;
        this.acceleration = 0.2;
        this.rotation_tower = 0;
        this.rotation_corpus = 70;
        this.tower_x = 0;
        this.tower_y = 0;
        this.medic_help = 0;
        this.height = 1;
        this.behavior = (byte) rand.rand(2,3);
        this.reload_max = 180;
        this.team = 2;
        this.t = 0;
        this.damage_fragment = 12;
        this.penetration_fragment = 4;
        this.sound_fire = Main.sa.get(0).cannon;

        this.difference =-18;
        this.tower_x_const = 8;
        this.tower_y_const = 30;
        this.x_tower = 0;
        this.y_tower = -8;





        this.corpus_image = "src/image/enemy/corpus_enemy_many_tower_1.png";
        this.tower_image = "src/image/enemy/tower_enemy_1.png";

        this.speed_tower = 1;this.speed_rotation = 1;
        super.data();
        this.tower_obj.add(new tower_flame_enemy(18,55,50,-10,10,2,65,12,2, this.id_unit,
                (byte)1,this.team,"src/image/enemy/tower_auxiliart_enemy_1.png",this.spisok, Main.sa.get(0).flame_attack));
        this.tower_obj.add(new tower_flame_enemy(18,55,50,10,10,2,65,12,2, this.id_unit,
                (byte)1,this.team,"src/image/enemy/tower_auxiliart_enemy_1.png",this.spisok, Main.sa.get(0).flame_attack));
    }
    public void all_action(int i){
        super.all_action(i);
        super.tower_ii(i);
        super.behavior_bot(this.enemy_spisok, i);
        super.build_corpus(Main.build);
        super.bot_fragmentation_bull_fire(i,this.spisok,this.enemy_spisok);
        super.corpus_corpus_def_xy(this.spisok,(byte)1);
        super.tower_xy();
        super.transport_delete_2(i,this.spisok);
        center_render();
        render_metod.transorm_img(this.x_rend,this.y_rend,this.corpus_width,this.corpus_height,this.rotation_corpus+this.rotation_2,this.corpus_image);
        tower_iteration();
        render_metod.transorm_img(this.x_tower_rend,this.y_tower_rend,this.tower_width,this.tower_height,this.rotation_tower,this.tower_image,this.x_tower* Game.zoom,this.y_tower* Game.zoom);
    }
}
