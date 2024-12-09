package Content.Transport.Tower;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.method.RenderMethod;
import com.mygdx.game.main.Main;
import com.mygdx.game.transport.Transport;

import java.io.File;
import java.util.ArrayList;

public class TowerBullTankEnemy extends Transport {
    public TowerBullTankEnemy(float x_const, float y_const, int difference, int difference_2, float reload_max, float speed_rotation, float damage, float penetration,
                              int ind_unit, byte height, byte team, Sprite tower_image, ArrayList<Transport> spisok, Sound sound){
        this.tower_x_const = x_const;
        this.tower_y_const = y_const;
        this.spisok = spisok;
        this.sound_fire = sound;
        this.speed_tower = speed_rotation;
        this.difference = -difference;
        this.reload_max = reload_max;
        this.reload = this.reload_max;
        this.damage = damage;
        this.penetration = penetration;
        this.tower_img = tower_image;
        this.id_unit = ind_unit;
        this.teg_unit = "tower";
        this.height = height;
        this.team = team;
        this.difference_2 = -difference_2;
        this.width_tower = 15;
        this.height_tower = 20;
        data_tower();
        x_tower =7;
        y_tower =10;



    }
    public void tower_action(int i,float x,float y,float rotation,boolean sost,boolean sost_2,float aim_x,float aim_y) {
        this.x = x;
        this.y = y;
        this.rotation_corpus = rotation;
        this.trigger_attack = sost;
        this.trigger_fire = sost_2;
        this.aim_x = aim_x;
        this.aim_y = aim_y;
        tower_xy_2();
        tower_ii_2();
        bot_bull_tank_fire(i,spisok.get(i).tower_obj, this.enemy_spisok);
        center_render_tower();
        RenderMethod.transorm_img(this.x_tower_rend,this.y_tower_rend,this.width_tower *Main.Zoom,this.height_tower *Main.Zoom,this.rotation_tower,this.tower_img,7* Main.Zoom,10* Main.Zoom);
    }
    public void tower_action_client(int i,float x,float y,float rotation,boolean sost,boolean sost_2) {
        this.x = x;
        this.y = y;
        this.rotation_corpus = rotation;
        this.trigger_attack = sost;
        this.trigger_fire = sost_2;
        tower_xy_2();
        tower_ii_2();
        center_render_tower();
        RenderMethod.transorm_img(this.x_tower_rend,this.y_tower_rend,this.width_tower *Main.Zoom,this.height_tower *Main.Zoom,this.rotation_tower,this.tower_img,x_tower* Main.Zoom,y_tower* Main.Zoom);

    }
}

