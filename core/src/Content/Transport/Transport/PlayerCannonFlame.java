package Content.Transport.Transport;

import Content.Transport.Tower.TowerMortarPlayer;
import com.mygdx.game.method.RenderMethod;
import com.mygdx.game.main.Main;
import com.mygdx.game.transport.Transport;
import com.mygdx.game.transport.UnitType;

import java.util.ArrayList;

public class PlayerCannonFlame extends Transport {
    public PlayerCannonFlame(float x, float y, ArrayList<Transport> tr, boolean host){
        type_unit = UnitType.PlayerFlameT1;
        this.x = x;this.y = y;
        this.host = host;
        this.speed_inert = 0;this.speed = 0;
        this.max_speed = 4;this.min_speed = -4;
        this.max_hp = 1200;
        this.damage = 10;
        this.allyList = tr;
        this.armor = 50;
        this.penetration = 5;
        this.acceleration = 0.2f;
        this.team = 1;
        this.reload_max = 2;
        this.height = 1;
        this.tower_img = Main.ContentImage.tower_player;
        this.corpus_img = Main.ContentImage.corpus_player;
        this.t = 0;
        this.t_damage = 1;
//        this.x_tower = 15;
//        this.y_tower = 20;

        //this.tower_x_const = 6;
        //this.tower_y_const = 35;


        this.corpus_width = 50;
        this.corpus_height = 129;
        this.width_tower = 35;
        this.height_tower = 55;
        this.speed_tower = 1;this.speed_rotation = 1;
        this.sound_fire = Main.ContentSound.flame_attack;
        data();
        this.tower_obj.add(new TowerMortarPlayer(18,55,52,-12,40,2,
                65,12,15,15, this.id_unit,
                (byte)1,this.team,Main.ContentImage.tower_player_auxiliary_1,this.allyList, Main.ContentSound.cannon));
        this.tower_obj.add(new TowerMortarPlayer(18,55,52,12,40,2,
                65,12,15,15, this.id_unit,
                (byte)1,this.team,Main.ContentImage.tower_player_auxiliary_1,this.allyList, Main.ContentSound.cannon));

        this.difference = 18;
        const_tower_x = (int)(width_tower/2);
        const_tower_y = 21;
        this.tower_x_const = (int) (corpus_width/2)-(width_tower/2);
        this.tower_y_const = (int) (corpus_height/2)-(height_tower/2)+7;
        center_render();
    }
    public PlayerCannonFlame(float x, float y, ArrayList<Transport> tr, boolean host,byte team){
        type_unit = UnitType.PlayerFlameT1;
        this.x = x;this.y = y;
        this.host = host;
        this.speed_inert = 0;this.speed = 0;
        this.max_speed = 4;this.min_speed = -4;
        this.max_hp = 1200;
        this.damage = 10;
        this.allyList = tr;
        this.armor = 50;
        this.penetration = 5;
        this.acceleration = 0.2f;
        this.team = team;
        this.reload_max = 2;
        this.height = 1;
        this.tower_img = Main.ContentImage.tower_player;
        this.corpus_img = Main.ContentImage.corpus_player;
        this.t = 0;
        this.t_damage = 1;
//        this.x_tower = 15;
//        this.y_tower = 20;

        //this.tower_x_const = 6;
        //this.tower_y_const = 35;


        this.corpus_width = 50;
        this.corpus_height = 129;
        this.width_tower = 35;
        this.height_tower = 55;
        this.speed_tower = 1;this.speed_rotation = 1;
        this.sound_fire = Main.ContentSound.flame_attack;
        data();
        this.tower_obj.add(new TowerMortarPlayer(18,55,52,-12,40,2,
                65,12,15,15, this.id_unit,
                (byte)1,(byte)1,Main.ContentImage.tower_player_auxiliary_1,this.allyList, Main.ContentSound.cannon));
        this.tower_obj.add(new TowerMortarPlayer(18,55,52,12,40,2,
                65,12,15,15, this.id_unit,
                (byte)1,(byte)1,Main.ContentImage.tower_player_auxiliary_1,this.allyList, Main.ContentSound.cannon));

        this.difference = 18;
        const_tower_x = (int)(width_tower/2);
        const_tower_y = 21;
        this.tower_x_const = (int) (corpus_width/2)-(width_tower/2);
        this.tower_y_const = (int) (corpus_height/2)-(height_tower/2)+7;
        center_render();

    }
    public void all_action(int i) {
        super.all_action(i);
        super.host_control();
        super.motor_player();
        super.fire_player_flame();
        super.build_corpus(i);
        super.corpus_corpus(this.enemyList);
        super.tower_xy();
        super.tower_player();
        Main.RC.x = this.tower_x;
        Main.RC.y = this.tower_y;
        center_render();
        RenderMethod.transorm_img(this.x_rend, this.y_rend,this.corpus_width_zoom,this.corpus_height_zoom,this.rotation_corpus,this.corpus_img,const_x_corpus,const_y_corpus);
        tower_iteration(i);
        RenderMethod.transorm_img(this.x_tower_rend,this.y_tower_rend,this.width_tower_zoom,this.height_tower_zoom,this.rotation_tower,this.tower_img,const_x_tower,const_y_tower
        );
        super.transportDeletePlayer(i,this.allyList);
    }
    @Override
    public void all_action_client(int i) {
        super.all_action_client(i);
        super.client_control();
        super.motor_player();
        super.fire_player_flame();
        super.build_corpus(i);
        super.corpus_corpus(this.enemyList);
        super.corpus_corpus_def_xy(this.allyList);
        super.tower_xy();
        center_render();
        RenderMethod.transorm_img(this.x_rend, this.y_rend,this.corpus_width_zoom,this.corpus_height_zoom,this.rotation_corpus,this.corpus_img,const_x_corpus,const_y_corpus);
        tower_iteration_client(i);
        RenderMethod.transorm_img(this.x_tower_rend,this.y_tower_rend,this.width_tower_zoom,this.height_tower_zoom,this.rotation_tower,this.tower_img,const_x_tower,const_y_tower
        );
        super.transportDeletePlayer(i,this.allyList);
    }
    @Override
    public void all_action_client_1(int i) {
        super.all_action_client_1(i);
        move_xy_transport();
        super.tower_xy();
        super.tower_player();
        Main.RC.x = this.tower_x;
        Main.RC.y = this.tower_y;
        center_render();
        RenderMethod.transorm_img(this.x_rend, this.y_rend,this.corpus_width_zoom,this.corpus_height_zoom,this.rotation_corpus,this.corpus_img,const_x_corpus,const_y_corpus);
        tower_iteration_client_2(i);
        RenderMethod.transorm_img(this.x_tower_rend, this.y_tower_rend,this.width_tower_zoom,this.height_tower_zoom,this.rotation_tower,this.tower_img,const_x_tower,const_y_tower
        );
        Main.PacketClient.rotation_tower_client = rotation_tower;

    }
    public void all_action_client_2(int i) {
        super.all_action_client_2(i);
        super.tower_xy();
        center_render();
        move_xy_transport();
        RenderMethod.transorm_img(this.x_rend,this.y_rend,this.corpus_width_zoom,this.corpus_height_zoom,this.rotation_corpus,this.corpus_img,const_x_corpus,const_y_corpus);
        tower_iteration_client_1(i);
        RenderMethod.transorm_img(this.x_tower_rend,this.y_tower_rend,this.width_tower_zoom,this.height_tower_zoom,this.rotation_tower,this.tower_img,const_x_tower,const_y_tower
        );

    }
    public void update(){
        indicator_reload();
        indicator_hp();
    }

}
