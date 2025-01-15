package Content.Transport.Transport;

import com.mygdx.game.method.RenderMethod;
import com.mygdx.game.method.rand;

import java.util.ArrayList;
import com.mygdx.game.main.Main;
import com.mygdx.game.transport.Transport;
import com.mygdx.game.transport.UnitType;

public class HelicopterT1 extends Transport {
    public HelicopterT1(float x, float y, ArrayList<Transport>tr){
        this.type_unit = UnitType.HelicopterT1;
        this.corpus_img = Main.ContentImage.helicopter_enemy_t1;
        this.tower_img = Main.ContentImage.helicopter_blade;
        this.x = x;this.y = y;
        corpus_width = 60;
        corpus_height = 200;
        width_tower = 120;
        height_tower = 120;
        this.speed_inert = 0;this.speed = 0;
        this.max_speed = 6;this.min_speed = -6;
        this.damage = 200;
        this.allyList = tr;
        this.penetration = 20;
        this.max_hp = 850;
        this.armor = 50;
        this.acceleration = 0.5f;
        this.height = 2;
        this.behavior = (byte) (2);
        this.reload_max = 180;



        this.distance_target = 350;
        this.distance_target_2 = 120;

        this.team = 2;
        this.priority_paint = 1;
        this.sound_fire = Main.ContentSound.flame_attack;

        this.speed_rotation = 3;
        data();
        this.difference = 42;
        speed_tower = 10;
        const_tower_x = (int)(width_tower/2);
        const_tower_y = (int)(height_tower/2);
        this.tower_x_const = (int) (corpus_width/2)-(width_tower/2)+1;
        this.tower_y_const = (int) (corpus_height/2)-(height_tower/2);
        center_render();
    }
    public void all_action(int i) {
        super.all_action(i);
        super.helicopter_ii(this.enemyList,i);
        super.bot_bull_tank_fire_not_tower(i);
        super.tower_xy();
        super.blade_helicopter();
        center_render();
        RenderMethod.transorm_img(this.x_rend, this.y_rend,this.corpus_width_zoom,this.corpus_height_zoom,this.rotation_corpus,this.corpus_img,const_x_corpus,const_y_corpus);
        //tower_iteration_bot_client(i);
        RenderMethod.transorm_img(this.x_tower_rend,this.y_tower_rend,this.width_tower_zoom,this.height_tower_zoom,this.rotation_tower,this.tower_img,const_x_tower,const_y_tower
        );
        super.transport_delete(i,this.allyList);

    }
    public void all_action_client(int i) {
        super.all_action_client(i);
        super.tower_xy();
        center_render();
        RenderMethod.transorm_img(this.x_rend, this.y_rend,this.corpus_width_zoom,this.corpus_height_zoom,this.rotation_corpus,this.corpus_img,const_x_corpus,const_y_corpus);
        //tower_iteration_bot_client(i);
        RenderMethod.transorm_img(this.x_tower_rend,this.y_tower_rend,this.width_tower_zoom,this.height_tower_zoom,this.rotation_tower,this.tower_img,const_x_tower,const_y_tower
        );
    }
    public void update(){
        indicator_reload();
        indicator_hp_2();
    }


}
