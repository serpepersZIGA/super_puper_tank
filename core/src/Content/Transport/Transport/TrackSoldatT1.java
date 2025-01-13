package Content.Transport.Transport;

import com.mygdx.game.main.Main;
import com.mygdx.game.method.RenderMethod;
import com.mygdx.game.transport.Transport;
import com.mygdx.game.transport.UnitType;

import java.util.ArrayList;

public class TrackSoldatT1 extends Transport {
    public TrackSoldatT1(float x, float y, ArrayList<Transport> tr) {
        this.type_unit = UnitType.TrackSoldatT1;
        this.x = x;
        this.y = y;
        this.speed_inert = 0;
        this.speed = 0;
        this.max_speed = 4;
        this.min_speed = -4;
        this.damage = 5;
        this.allyList = tr;
        this.penetration = 20;
        this.max_hp = 1500;
        this.hp = this.max_hp;
        this.acceleration = 0.2f;
        this.rotation_tower = 0;
        this.rotation_corpus = 70;
        this.difference = 0;
        this.tower_x_const = -10;
        this.tower_y_const = 3;
        this.tower_x = 0;
        this.tower_y = 0;
        this.medic_help = 0;
        this.height = 1;
        this.behavior = 2;
        this.reload_max = 180;
        this.reload = this.reload_max;
        this.team = 2;
        this.t = 0;
        this.time_spawn_soldat_max = 200;
        this.hill = 4;
        this.corpus_img = Main.ContentImage.track_enemy_1lvl;
        this.corpus_width = 50;
        this.corpus_height = 129;


        this.speed_tower = 1;
        this.speed_rotation = 1;
        data();
    }
    public void all_action(int i){
        super.all_action(i);
        super.behavior_bot(this.enemyList, i);
        super.build_corpus();
        super.corpus_corpus_def_xy(Main.EnemyList);
        super.spawn_soldat(Main.SoldatList);
        center_render();
        RenderMethod.transorm_img(this.x_rend,this.y_rend,this.corpus_width_zoom,this.corpus_height_zoom,this.rotation_corpus,this.corpus_img,const_x_corpus,const_y_corpus);
        super.transport_delete_2(i,Main.EnemyList);
    }
    public void all_action_client(int i){
        super.all_action_client(i);
        super.tower_xy();
        center_corpus_render();
        RenderMethod.transorm_img(this.x_rend, this.y_rend,this.corpus_width_zoom,this.corpus_height_zoom,this.rotation_corpus,this.corpus_img,const_x_corpus,const_y_corpus);
    }
    public void update(){
        indicator_hp_2();
    }
}
