package Content.Transport.Tower;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.main.Main;
import com.mygdx.game.method.RenderMethod;
import com.mygdx.game.transport.Transport;

import java.io.File;
import java.util.ArrayList;

public class TowerBullTankPlayer extends Transport {
    public TowerBullTankPlayer(float x_const, float y_const, int difference, int difference_2, float reload_max, float speed_rotation, float damage, float penetration,
                               int ind_unit, byte height, byte team, Sprite str, ArrayList<Transport> spisok, Sound sound){
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
        this.tower_img = str;
        this.id_unit = ind_unit;
        this.teg_unit = "tower";
        this.height = height;
        this.team = team;
        this.difference_2 = -difference_2;
        data_tower();
        this.width_tower = 15;
        this.height_tower = 20;
        x_tower =7;
        y_tower =10;


    }
    public void tower_action(int i,float x,float y,float rotation,boolean sost_fire_bot) {
        this.x = x;
        this.y = y;
        this.rotation_corpus = rotation;
        this.left_mouse = sost_fire_bot;
        tower_xy_2();
        tower_player();
        fire_player_bull_tank();
        center_render_tower();
        RenderMethod.transorm_img(this.x_tower_rend,this.y_tower_rend,this.width_tower *Main.Zoom,this.height_tower *Main.Zoom,this.rotation_tower,this.tower_img,x_tower* Main.Zoom,y_tower* Main.Zoom);

    }
    public void tower_action_client(int i,float x,float y,float rotation,boolean sost_fire_bot) {
        this.x = x;
        this.y = y;
        this.rotation_corpus = rotation;
        this.left_mouse = sost_fire_bot;
        tower_xy_2();
        fire_player_bull_tank();
        center_render_tower();
        RenderMethod.transorm_img(this.x_tower_rend,this.y_tower_rend,this.width_tower *Main.Zoom,this.height_tower *Main.Zoom,this.rotation_tower,this.tower_img,x_tower* Main.Zoom,y_tower* Main.Zoom);

    }
    public void tower_action_client_1(int i,float x,float y,float rotation,boolean sost_fire_bot) {
        this.x = x;
        this.y = y;
        this.rotation_corpus = rotation;
        this.left_mouse = sost_fire_bot;
        super.tower_xy_2();
        center_render_tower();
        RenderMethod.transorm_img(this.x_tower_rend,this.y_tower_rend,this.width_tower *Main.Zoom,this.height_tower *Main.Zoom,this.rotation_tower,this.tower_img,x_tower* Main.Zoom,y_tower* Main.Zoom);
    }
    public void tower_action_client_2(int i,float x,float y,float rotation,boolean sost_fire_bot) {
        this.x = x;
        this.y = y;
        this.rotation_corpus = rotation;
        this.left_mouse = sost_fire_bot;
        super.tower_xy_2();
        super.tower_player_2();
        Main.PacketClient.rot_tower.add(this.rotation_tower);
        center_render_tower();
        RenderMethod.transorm_img(this.x_tower_rend,this.y_tower_rend,this.width_tower *Main.Zoom,this.height_tower *Main.Zoom,this.rotation_tower,this.tower_img,x_tower* Main.Zoom,y_tower* Main.Zoom);
    }
}