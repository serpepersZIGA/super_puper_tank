package Content.Transport.Transport;

import Content.Transport.Tower.TowerBullTankEnemy;
import Content.Transport.Tower.TowerFlameEnemy;
import com.mygdx.game.method.RenderMethod;

import java.util.ArrayList;
import com.mygdx.game.main.Main;
import com.mygdx.game.transport.Transport;
import com.mygdx.game.transport.UnitType;

public class PanzerT1 extends Transport {
    public PanzerT1(float x, float y, ArrayList<Transport> tr){
        this.type_unit = UnitType.PanzerT1;
        this.x = x;this.y = y;
        this.speed_inert = 0;this.speed = 0;
        this.max_speed = 4;this.min_speed = -4;
        this.damage = 100;
        this.penetration = 20;
        this.max_hp = 1500;
        this.armor = 50;
        this.allyList = tr;
        this.acceleration = 0.2f;
        this.rotation_tower = 0;
        this.rotation_corpus = 70;
        this.tower_x = 0;
        this.tower_y = 0;
        this.medic_help = 0;
        this.height = 1;
        this.behavior = 3;
        this.reload_max = 180;
        this.team = 2;
        this.t = 0;
        this.x_tower = 15;
        this.y_tower = 20;
        this.distance_target = 150;
        this.distance_target_2 = 30;
        this.host = true;

        this.tower_img = Main.ContentImage.tower_enemy;
        this.corpus_img = Main.ContentImage.corpus_enemy;

        this.corpus_width = 50;
        this.corpus_height = 129;
        this.width_tower = 35;
        this.height_tower = 55;
        this.speed_tower = 1;this.speed_rotation = 1;
        this.sound_fire = Main.ContentSound.acid_attack;
        data();
        this.tower_obj.add(new TowerBullTankEnemy(18,55,52,-12,4,2,65,12, this.id_unit,
                (byte)1,this.team,Main.ContentImage.tower_enemy_auxiliary_1,this.allyList, Main.ContentSound.flame_attack));
        this.tower_obj.add(new TowerFlameEnemy(18,55,52,12,4,2,65,12,2, this.id_unit,
                (byte)1,this.team,Main.ContentImage.tower_enemy_auxiliary_1,this.allyList, Main.ContentSound.flame_attack));
        this.difference = 18;
        const_tower_x = (int)(width_tower/2);
        const_tower_y = 21;
        this.tower_x_const = (int) (corpus_width/2)-(width_tower/2);
        this.tower_y_const = (int) (corpus_height/2)-(height_tower/2)+7;
        center_render();


    }
    public void all_action(int i) {
        super.all_action(i);
        super.tower_ii(i);
        super.bot_bull_tank_fire(this.allyList.get(i), this.enemyList);
        super.behavior_bot(this.enemyList, i);
        super.build_corpus(i);
        super.corpus_corpus_def_xy(this.allyList);
        super.tower_xy();
        center_render();
        RenderMethod.transorm_img(this.x_rend, this.y_rend,this.corpus_width_zoom,this.corpus_height_zoom,this.rotation_corpus,this.corpus_img,const_x_corpus,const_y_corpus);
        tower_iteration_bot(i);
        RenderMethod.transorm_img(this.x_tower_rend,this.y_tower_rend,this.width_tower_zoom,this.height_tower_zoom,this.rotation_tower,this.tower_img,const_x_tower,const_y_tower
        );
        super.transportDeleteBot(i,this.allyList);
    }
    public void all_action_client(int i) {
        super.all_action_client(i);
        super.tower_xy();
        center_render();
        RenderMethod.transorm_img(this.x_rend, this.y_rend,this.corpus_width_zoom,this.corpus_height_zoom,this.rotation_corpus,this.corpus_img,const_x_corpus,const_y_corpus);
        tower_iteration_bot_client(i);
        RenderMethod.transorm_img(this.x_tower_rend,this.y_tower_rend,this.width_tower_zoom,this.height_tower_zoom,this.rotation_tower,this.tower_img,const_x_tower,const_y_tower
        );
    }
    public void update(){
        indicator_reload();
        indicator_hp_2();
    }


}
