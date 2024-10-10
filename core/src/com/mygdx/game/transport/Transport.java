package com.mygdx.game.transport;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.block.Block;
import com.mygdx.game.main.Main;
import com.mygdx.game.build.Build;
import com.mygdx.game.bull.bull_acid;
import com.mygdx.game.bull.bull_flame;
import com.mygdx.game.bull.bull_fragmentation;
import com.mygdx.game.bull.bull_tank;
import com.mygdx.game.metod.*;
import com.mygdx.game.particle.acid;
import com.mygdx.game.particle.bang;
import com.mygdx.game.particle.flame_spawn;
import com.mygdx.game.particle.flame_static;
import com.mygdx.game.soldat.Soldat;
import com.mygdx.game.soldat.soldat_bull;
import com.mygdx.game.soldat.soldat_flame;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

import static com.mygdx.game.main.Main.*;
import static java.lang.StrictMath.*;
import static java.sql.Types.NULL;

public abstract class Transport implements Serializable {

    public double x, y, speed, rotation_corpus, acceleration=0.2, speed_inert, rotation_inert, rotation_tower, speed_tower=0.2, max_speed=4, min_speed=-4, speed_rotation=0.2,
    tower_x,tower_y, tower_x_const, tower_y_const, tower_width_2, tower_height_2,reload,reload_max,corpus_width,corpus_height,corpus_width_2,corpus_height_2,
    damage,penetration,damage_fragment,penetration_fragment,t,t_damage,armor,hill,fire_x,fire_y,tower_width ,tower_height,aim_x,aim_y;
    public int  difference,difference_2,hp,max_hp,time_spawn_soldat,time_spawn_soldat_max,x_rend,y_rend,x_tower_rend,y_tower_rend, id_unit, x_tower,y_tower,
    time_max_sound_motor = 20,time_sound_motor = time_max_sound_motor;
    public File sound_fire;
    public int time_max_relocation = 300,time_relocation = 0;
    public double x_relocation,y_relocation,rotation_relocation,priority_paint = 0,ai_x_const = 24,ai_y_const = 62;
    public int range_see=800,range_see_2 = (int)(range_see*1.5),time_trigger_bull_bot,time_trigger_bull = 700;

    public byte behavior,behavior_buffer, medic_help, crite_life, team,height = 1,trigger_drive;
    double g,g_right, g_left;

    int i;
    public double difference_x,difference_y;
    public int width_corpus_zoom,height_corpus_zoom,width_tower_zoom,height_tower_zoom;
    public Block block;
    public ArrayList<int[]>path;
    public String teg_unit = "tank";
    public ArrayList<Transport>spisok,enemy_spisok,tower_obj = new ArrayList<>();
    public int const_x_corpus,const_y_corpus,const_x_tower,const_y_tower,const_tower_x = 7,const_tower_y = 10;
    public boolean sost_fire_bot,guidance,left_mouse,right_mouse,trigger_attack,trigger_fire;
    public boolean press_w,press_a,press_s,press_d;
    public String tower_image,corpus_image;
    public Texture tower_img,corpus_img;
    public void data(){
        path = new ArrayList<>();
        this.id_unit = rand.rand(10000,99999);
        for(int i = 0;i<this.spisok.size()-1;i++){
            if(this.spisok.get(i).id_unit == this.id_unit){
                while (this.id_unit == this.spisok.get(i).id_unit) {
                    this.id_unit = rand.rand(10000, 99999);
                }
            }
        }
        this.reload = this.reload_max;
        this.hp = this.max_hp;
        this.time_spawn_soldat = this.time_spawn_soldat_max;
        this.corpus_width_2 = this.corpus_width/2;
        this.corpus_height_2 = this.corpus_height/2;
        if(Main.player_obj == this.spisok){
            this.enemy_spisok = Main.enemy_obj;
        }
        if(Main.enemy_obj == this.spisok){
            this.enemy_spisok = Main.player_obj;
        }
        if(tower_image != null){
            this.difference_x = this.difference - this.x_tower;
            this.difference_y = this.difference - this.y_tower;
            this.tower_width_2 = this.tower_width/2;
            this.tower_height_2 = this.tower_height/2;
            this.const_x_tower = (int)(const_tower_x*Main.zoom);
            this.const_y_tower = (int)(const_tower_y*Main.zoom);}
        this.width_corpus_zoom = (int)(corpus_width*Main.zoom);
        this.height_corpus_zoom = (int)(corpus_height*Main.zoom);
        this.width_tower_zoom = (int)(tower_width*Main.zoom);
        this.height_tower_zoom = (int)(tower_height*Main.zoom);
        this.const_x_corpus = (int)(corpus_width_2*Main.zoom);
        this.const_y_corpus = (int)(corpus_height_2*Main.zoom);
    }
    public void data_tower(){
        this.teg_unit = "tower";
        if(Main.player_obj == this.spisok){
            this.enemy_spisok = Main.enemy_obj;
        }
        else if(Main.enemy_obj == this.spisok){
            this.enemy_spisok = Main.player_obj;
        }
        this.reload = this.reload_max;
        this.difference_x = this.difference - this.x_tower;
        this.difference_y = this.difference - this.y_tower;
        this.tower_width_2 = this.tower_width/2;
        this.tower_height_2 = this.tower_height/2;
        this.width_tower_zoom = (int)(tower_width*Main.zoom);
        this.height_tower_zoom = (int)(tower_height*Main.zoom);

        this.const_x_tower = (int)(const_tower_x*Main.zoom);
        this.const_y_tower = (int)(const_tower_y*Main.zoom);
    }
    public void tower_iteration(){
        for (i = 0;i<tower_obj.size();i++){
            tower_obj.get(i).tower_action(i,this.x,this.y,this.rotation_corpus,this.right_mouse);
        }
    }
    public void tower_iteration_bot(int iTr){
        for (i = 0;i<tower_obj.size();i++){
            tower_obj.get(i).tower_action(iTr,this.x,this.y,this.rotation_corpus,trigger_attack,trigger_fire,aim_x,aim_y);
        }
    }
    public void behavior_bot(ArrayList<Transport>tr, int i){
        try {
            review_field(i, tr);
            if (!this.trigger_attack) {
                if (this.time_trigger_bull_bot > 0) {
                    motor_bot_bypass(i, this.spisok, tr);
                    this.time_trigger_bull_bot -= 1;
                } else {
                    peaceful_behavior(i);
                }
            } else {
                motor_bot_bypass(i, this.spisok, tr);
            }
        }
        catch (Exception ignore){
            System.out.println(ignore);
        }



    }
    public void review_field(int i,ArrayList<Transport>tr){
        if(tr.size()!= 0) {
            int[] sp = metod.detection_near_transport_i_def(this.spisok, i, tr);
            //g = atan2(this.y - tr.get(sp[0]).y, this.x - tr.get(sp[0]).x) / 3.1415926535 * 180;
            if (sp[1] < this.range_see) {
                this.trigger_attack = true;

            } else {
                this.trigger_attack = false;
            }
        }
        else{this.trigger_attack = false; }

    }

    public void peaceful_behavior(int i){
        this.time_relocation-= 1;
        if(this.time_relocation<0){
            if(rand.rand(1,2)== 1) {
                this.x_relocation = this.x + rand.rand(200.0, 500.0);
            }
            else{this.x_relocation = this.x + rand.rand(-500.0, -200.0);}
            if(rand.rand(1,2)== 1) {
                this.y_relocation = this.y + rand.rand(200.0, 500.0);
            }
            else{this.y_relocation = this.y + rand.rand(-500.0, -200.0);}
            for (i = 0; i< Main.build.size(); i++){
                if(Main.build.get(i).x- Main.build.get(i).width_2>this.x_relocation & Main.build.get(i).x+ Main.build.get(i).width<this.x_relocation){
                    if(Main.build.get(i).y- Main.build.get(i).height_2>this.y_relocation & Main.build.get(i).y+ Main.build.get(i).height<this.y_relocation) {
                        if(rand.rand(1,2)== 1) {
                            this.x_relocation = this.x + rand.rand(200.0, 500.0);
                        }
                        else{this.x_relocation = this.x + rand.rand(-500.0, -200.0);}
                        if(rand.rand(1,2)== 1) {
                            this.y_relocation = this.y + rand.rand(200.0, 500.0);
                        }
                        else{this.y_relocation = this.y + rand.rand(-500.0, -200.0);}
                    }
                }
            }
        }
            //this.rotation_relocation = atan2(this.y-this.y_relocation,this.x-this.x_relocation)*3.1415926535/180;
            this.time_relocation = this.time_max_relocation;
        this.rotation_relocation = (atan2(this.y-this.y_relocation,this.x-this.x_relocation)/3.1415926535*180)-90;
        //rotation_bot(rotation_relocation,g_left,g_right);
        //g = sqrt(pow(this.x-this.x_relocation,2)+pow(this.y-this.y_relocation,2));
        g_right = rotation_corpus + 10;
        g_left = rotation_corpus - 10;
        bypass_build(Main.build,this.x_relocation,this.y_relocation,this.rotation_relocation,g_right,g_left,i);

    }
    public void bypass_build(ArrayList<Build> obj_build, double x, double y, double g, double g_left, double g_right, int i3) {
        byte triger_rotation = 0;
        if(obj_build.size()!= 0) {
            int i = metod.detection_near_build_i(this.spisok, i3,obj_build);
            //System.out.println(1);

            if (sqrt(pow(this.x - obj_build.get(i).x, 2) + pow(this.y - obj_build.get(i).y, 2)) <= obj_build.get(i).width * 1.5) {
                if (y < obj_build.get(i).y && this.y > obj_build.get(i).y) {
                    //System.out.println(2);
                    triger_rotation = 1;
                    if (obj_build.get(i).x-this.corpus_width > this.x || obj_build.get(i).x + obj_build.get(i).width + this.corpus_width_2 < this.x) {
                        if (0 < this.rotation_corpus) {
                            this.rotation_corpus -= this.speed_rotation;
                        } else if (0 > this.rotation_corpus) {
                            this.rotation_corpus += this.speed_rotation;
                        }
                        else{this.trigger_drive = 1;}
                    } else if (this.x < obj_build.get(i).x+obj_build.get(i).width_2) {
                        if (-90 < this.rotation_corpus) {
                            this.rotation_corpus -= this.speed_rotation;
                        } else if (-90 > this.rotation_corpus) {
                            this.rotation_corpus += this.speed_rotation;
                        }
                        else{this.trigger_drive = 1;}
                    } else if (this.x > obj_build.get(i).x + obj_build.get(i).width_2) {
                        if (90 < this.rotation_corpus) {
                            this.rotation_corpus -= this.speed_rotation;
                        } else if (90 > this.rotation_corpus) {
                            this.rotation_corpus += this.speed_rotation;
                        }
                        else{this.trigger_drive = 1;}
                    }
                } else if (y > obj_build.get(i).y && this.y < obj_build.get(i).y) {
                    //System.out.println(3);
                    triger_rotation = 1;
                    this.trigger_drive = 1;
                    if (obj_build.get(i).x + obj_build.get(i).width+ this.corpus_width_2 < this.x || obj_build.get(i).x-this.corpus_width > this.x) {
                        if (-180 < this.rotation_corpus) {
                            this.rotation_corpus -= this.speed_rotation;
                        } else if (-180 > this.rotation_corpus) {
                            this.rotation_corpus += this.speed_rotation;
                        }
                        else{this.trigger_drive = 1;}
                    }
                    else if (this.x < obj_build.get(i).x +obj_build.get(i).width_2) {
                        if (-90 < this.rotation_corpus) {
                            this.rotation_corpus -= this.speed_rotation;
                        } else if (-90 > this.rotation_corpus) {
                            this.rotation_corpus += this.speed_rotation;
                        }
                        else{this.trigger_drive = 1;}
                    }
                    else if (this.x > obj_build.get(i).x+obj_build.get(i).width_2) {
                        if (90 < this.rotation_corpus) {
                            this.rotation_corpus -= this.speed_rotation;
                        } else if (90 > this.rotation_corpus) {
                            this.rotation_corpus += this.speed_rotation;
                        }
                        else{this.trigger_drive = 1;}
                    }
                }
            }
        }
        if (triger_rotation == 0) {
            rotation_bot(g,g_right,g_left);
        }
    }
    public void tower_ii(int i){
        if (this.trigger_attack) {
            tower_bot_enemy(i);
        }
        else{
            if(this.rotation_tower>this.rotation_corpus+180){this.rotation_tower -= this.speed_tower;}
            if(this.rotation_tower<this.rotation_corpus+180){this.rotation_tower += this.speed_tower;}
        }
    }
    public void tower_ii_2(){
        if (this.trigger_attack) {
            tower_bot();
        }
        else{
            if(this.rotation_tower>this.rotation_corpus+180){this.rotation_tower -= this.speed_tower;}
            if(this.rotation_tower<this.rotation_corpus+180){this.rotation_tower += this.speed_tower;}
        }
    }
    //public Image image;
    //String img;
    public void host_control(){

        this.left_mouse = Main.left_mouse;
        this.right_mouse = Main.right_mouse;
        this.press_w = Main.press_w;//(boolean) buffer[2];
        this.press_a = Main.press_a;//(boolean) buffer[3];
        this.press_s = Main.press_s;//(boolean) buffer[4];
        this.press_d = Main.press_d;//(boolean) buffer[5];
    }
    public void motor_player(){
        this.time_sound_motor -= 1;
        if (this.press_w) {
            if (this.time_sound_motor < 0) {
                sound.sound(Main.sa.get(0).motor_back, (float) sqrt(pow(this.x_rend, 2) + pow(this.y_rend, 2)) / -60);
                this.time_sound_motor = this.time_max_sound_motor;

            }
            if (this.min_speed < this.speed) {
                this.speed -= this.acceleration;
            }
        }
        if (this.press_s) {
            if (this.time_sound_motor < 0) {
                sound.sound(Main.sa.get(0).motor, (float) sqrt(pow(this.x_rend, 2) + pow(this.y_rend, 2)) / -60);
                this.time_sound_motor = this.time_max_sound_motor;
            }
            if(this.max_speed > this.speed) {
                this.speed += this.acceleration;
            }

        }

        if (this.press_a){
            this.rotation_tower += this.speed_rotation;
            this.rotation_corpus += this.speed_rotation;
        }
        if (this.press_d){
            this.rotation_tower -= this.speed_rotation;
            this.rotation_corpus -= this.speed_rotation;
        }

        if (this.speed > 0 && !Main.press_w && !Main.press_s) {
            this.speed -= this.acceleration;
            if (this.speed<this.acceleration){this.speed = 0;}
        } else if (this.speed < 0 && !Main.press_w && !Main.press_s) {
            this.speed += this.acceleration;
            if (this.speed>this.acceleration){this.speed = 0;}
        }
        this.x += move.move_sin(this.speed, -this.rotation_corpus);
        this.y += move.move_cos(this.speed, -this.rotation_corpus);
        //this.tower_x += metod.move.move_sin(this.speed, this.rotation_corpus);
        //this.tower_y += metod.move.move_cos(this.speed, this.rotation_corpus);
    }

    public void tower_bot_enemy(int i) {
        if(this.enemy_spisok.size() != 0) {
            try{
                //System.out.println(this.teg_unit);
                int i2 = metod.detection_near_transport_i(this.spisok, i,this.enemy_spisok);
                this.aim_x = this.enemy_spisok.get(i2).x;
                this.aim_y = this.enemy_spisok.get(i2).y;
                this.rotation_tower = metod.tower(this.tower_x, this.tower_y,this.enemy_spisok.get(i2).x, this.enemy_spisok.get(i2).y, this.rotation_tower, this.speed_tower);}
            catch(Exception ignored){

            }
        }
    }
    public void tower_bot() {
        if(this.enemy_spisok.size() != 0) {
            try{
                this.rotation_tower = metod.tower(this.tower_x, this.tower_y,this.aim_x,this.aim_y, this.rotation_tower, this.speed_tower);}
            catch(Exception ignored){

            }
        }
    }

    public void tower_player() {
        this.rotation_tower = metod.tower_player(this.x_tower_rend+this.tower_width_2,this.y_tower_rend+this.tower_height_2, this.rotation_tower, this.speed_tower);
    }
    public void tower_player_client(double mouse_x,double mouse_y) {
        this.rotation_tower = metod.tower(mouse_x,mouse_y,this.x_tower_rend, this.y_tower_rend, this.rotation_tower, this.speed_tower);
    }

    private void bot_fire(int i, ArrayList<Transport>obj_1, ArrayList<Transport>obj_2){
        guidance = reload_bot();
        if(obj_2.size() != 0) {
            try {
                int i2 = metod.detection_near_transport_i(obj_1, i,obj_2);
                this.sost_fire_bot = fire_bot(obj_2.get(i2).x, obj_2.get(i2).y);
            }
            catch (Exception ignored){

            }
        }
    }
    private void enemy_fire_not_tower(ArrayList<Transport>tr, int i){
        if(this.enemy_spisok.size() != 0) {
            int i2 = metod.detection_near_transport_i(tr, i,this.enemy_spisok);
            this.sost_fire_bot = fire_bot_not_tower(this.enemy_spisok.get(i2).x,this.enemy_spisok.get(i2).y);
        }
    }
    public void bot_bull_tank_fire_not_tower(ArrayList<Transport>tr, int i){
        enemy_fire_not_tower(tr,i);
        if(this.sost_fire_bot && this.trigger_attack){
            sound.sound( this.sound_fire,(float) sqrt(pow(this.x_rend, 2) + pow(this.y_rend, 2)) / -60);
            Main.bull_obj.add(new bull_tank(this.tower_x,this.tower_y,-this.rotation_corpus,this.damage,this.penetration,this.team,this.height));
        }
    }
    public void bot_bull_tank_fire(int i, ArrayList<Transport>obj_1, ArrayList<Transport>obj_2){
        bot_fire(i,obj_1,obj_2);
        if(this.sost_fire_bot && this.trigger_attack){
            sound.sound( this.sound_fire,(float) sqrt(pow(this.x_rend, 2) + pow(this.y_rend, 2)) / -60);
            this.fire_x = this.tower_x+this.tower_width_2+((this.tower_height_2+this.y_tower) *sin(-this.rotation_tower*3.1415926535/180));
            this.fire_y = this.tower_y+this.tower_height_2+((this.tower_height_2+this.y_tower) *cos(-this.rotation_tower*3.1415926535/180));
            Main.bull_obj.add(new bull_tank(this.fire_x,this.fire_y,-this.rotation_tower,this.damage,this.penetration,this.team,this.height));

        }
    }
    public void blade_helicopter(){
        this.rotation_tower += 10;
    }
    //public void
    public void bot_fragmentation_bull_fire(int i, ArrayList<Transport>obj_1, ArrayList<Transport>obj_2){
        bot_fire(i,obj_1,obj_2);
        if(this.sost_fire_bot && this.trigger_attack){
            sound.sound( this.sound_fire,(float) sqrt(pow(this.x_rend, 2) + pow(this.y_rend, 2)) / -60);
            this.fire_x = this.tower_x+this.tower_width_2+((this.tower_height_2+this.y_tower) *sin(-this.rotation_tower*3.1415926535/180));
            this.fire_y = this.tower_y+this.tower_height_2+((this.tower_height_2+this.y_tower) *cos(-this.rotation_tower*3.1415926535/180));
            Main.bull_obj.add(new bull_fragmentation(this.fire_x,this.fire_y,-this.rotation_tower+180,this.damage,this.penetration,this.damage_fragment,
                    this.penetration_fragment,this.team,this.height));
        }

    }
    public void tower_clear(int i){
        for(Transport tr : this.spisok){
            if(this.id_unit == tr.id_unit){
                return;
            }
        }
        Main.tower_obj.remove(i);


    }
    public void bot_flame_fire(int i, ArrayList<Transport>obj_1, ArrayList<Transport>obj_2){
        bot_fire(i,obj_1,obj_2);
        if(this.sost_fire_bot && this.trigger_attack){
            sound.sound( this.sound_fire,(float) sqrt(pow(this.x_rend, 2) + pow(this.y_rend, 2)) / -60);
            this.fire_x = this.tower_x+this.tower_width_2+((this.tower_height_2+this.y_tower) *sin(-this.rotation_tower*3.1415926535/180));
            this.fire_y = this.tower_y+this.tower_height_2+((this.tower_height_2+this.y_tower) *cos(-this.rotation_tower*3.1415926535/180));
            Main.bull_obj.add(new bull_flame(this.fire_x,this.fire_y,-this.rotation_tower+ rand.rand(-10,10),this.damage,this.t_damage,this.penetration,this.team,this.height));
            Main.bull_obj.add(new bull_flame(this.fire_x,this.fire_y,-this.rotation_tower+ rand.rand(-10,10),this.damage,this.t_damage,this.penetration,this.team,this.height));
        }
    }
    public void bot_acid_fire(int i, ArrayList<Transport>obj_1, ArrayList<Transport>obj_2){
        bot_fire(i,obj_1,obj_2);
        if(this.sost_fire_bot && this.trigger_attack){
            sound.sound( this.sound_fire,(float) sqrt(pow(this.x_rend, 2) + pow(this.y_rend, 2)) / -60);
            this.fire_x = this.tower_x+this.tower_width_2+((this.tower_height_2+this.y_tower) *sin(-this.rotation_tower*3.1415926535/180));
            this.fire_y = this.tower_y+this.tower_height_2+((this.tower_height_2+this.y_tower) *cos(-this.rotation_tower*3.1415926535/180));
            Main.bull_obj.add(new bull_acid(this.fire_x,this.fire_y,-this.rotation_tower+ rand.rand(-10,10),this.damage,this.penetration,this.team,this.height));
            Main.bull_obj.add(new bull_acid(this.fire_x,this.fire_y,-this.rotation_tower+ rand.rand(-10,10),this.damage,this.penetration,this.team,this.height));
        }
    }

    public void motor_bot_bypass(int i, ArrayList<Transport>bot, ArrayList<Transport>finding) {
        if(finding.size() != 0) {

            double[]list = less_hp_bot(bot,finding,bot,i);
            g=list[0];
            double e =list[1];
            g_right = this.rotation_corpus - 10;
            g_left = this.rotation_corpus + 10;
            bypass_build(Main.build,this.enemy_spisok, e,g, g_right, g_left, i);
            //else if(e == 2)bypass_build(Main.build, bot, g, g_right, g_left, i);
            speed_balance();
        }

        //System.out.println(xy[0]+"ss"+xy[1]);
    }
    public boolean reload_bot(){
        if(this.reload <= 0){
            return true;
        }
        this.reload-=1;
        return false;
    }
    public void indicator_hp(){
        double len_indicator = 50;
        //double red_len_2 = len_indicator/2;
        double green_len = ((double)this.hp/this.max_hp)*len_indicator;
        render.setColor((float)1/255*180,0,0,1);
        render.rect((int)((this.x_rend-20)),(int)((this.y_rend-25)),(int)(len_indicator* Main.zoom),(int)(8* Main.zoom));
        render.setColor(0,(float)1/255*180,0,1);
        render.rect((int)((this.x_rend-20)),(int)((this.y_rend-25)),(int)(green_len* Main.zoom),(int)(8* Main.zoom));
    }
    public void indicator_hp_2(){
        double len_indicator = 50;
        //double red_len_2 = len_indicator/2;
        double green_len = ((double)this.hp/this.max_hp)*len_indicator;
        render.setColor((float)1/255*180,0,0,1);
        render.rect((int)((this.x_rend-20)),(int)((this.y_rend-25)),(int)(len_indicator* Main.zoom),(int)(8* Main.zoom));
        if(this.crite_life == 0){
            render.setColor(0,(float)1/255*180,0,1);
            render.rect((int)((this.x_rend-20)),(int)((this.y_rend-25)),(int)(green_len* Main.zoom),(int)(8* Main.zoom));
            }
        else{
            render.setColor((float)1/255*182, (float)1/255*114, (float)1/255*16,1);
            render.rect((int)((this.x_rend-20)),(int)((this.y_rend-25)),(int)(green_len* Main.zoom),(int)(8* Main.zoom));
        }
    }
    public void indicator_reload(){
        double len_indicator = 50;
        //double red_len_2 = len_indicator/2;
        double green_len = (this.reload/this.reload_max)*len_indicator;
        render.setColor((float)1/255*180,(float)1/255*180,0,1);
        render.rect((int)(this.x_rend-20),(int)(this.y_rend-17),(int)(len_indicator* Main.zoom),(int)(8* Main.zoom));
        render.setColor((float)1/255*180,0,0,1);
        render.rect((int)(this.x_rend-20),(int)(this.y_rend-17),(int)(green_len* Main.zoom),(int)(8* Main.zoom));
    }
    public void fire_player_bull_tank(){
        reload_bot();
        if(this.left_mouse && this.reload <= 0){
            for(int i = 0;i<this.enemy_spisok.size();i++){
                if(sqrt(pow(this.x-this.enemy_spisok.get(i).x,2)+pow(this.y-this.enemy_spisok.get(i).y,2)) < this.enemy_spisok.get(i).range_see_2) {
                    this.enemy_spisok.get(i).time_trigger_bull_bot = this.enemy_spisok.get(i).time_trigger_bull;
                }
            }
            sound.sound( this.sound_fire,(float) sqrt(pow(this.x_rend, 2) + pow(this.y_rend, 2)) / -60);
            this.fire_x = this.tower_x+this.tower_width_2+((this.tower_height_2+this.y_tower) *sin(-this.rotation_tower*3.1415926535/180));
            this.fire_y = this.tower_y+this.tower_height_2+((this.tower_height_2+this.y_tower) *cos(-this.rotation_tower*3.1415926535/180));
            this.reload = this.reload_max;
            Main.bull_obj.add(new bull_tank(this.fire_x,this.fire_y,
                    -this.rotation_tower,this.damage,this.penetration,this.team,(byte)1));
        }
    }
    public void fire_player_flame(){
        reload_bot();
        if(this.left_mouse && this.reload <= 0){

            for (Transport enemy : this.enemy_spisok) {
                if (sqrt(pow(this.x - enemy.x, 2) + pow(this.y - enemy.y, 2)) < enemy.range_see_2) {
                    enemy.time_trigger_bull_bot = enemy.time_trigger_bull;
                }
            }
            sound.sound( this.sound_fire,(float) sqrt(pow(this.x_rend, 2) + pow(this.y_rend, 2)) / -60);
            this.reload = this.reload_max;
            this.fire_x = this.tower_x+this.tower_width_2+((this.tower_height_2+this.y_tower) *sin(-this.rotation_tower*3.1415926535/180));
            this.fire_y = this.tower_y+this.tower_height_2+((this.tower_height_2+this.y_tower) *cos(-this.rotation_tower*3.1415926535/180));
            Main.bull_obj.add(new bull_flame(this.fire_x,this.fire_y,
                    -this.rotation_tower+ rand.rand(-10,10),this.damage,this.t_damage,this.penetration,this.team,(byte)1));
            Main.bull_obj.add(new bull_flame(this.fire_x,this.fire_y,
                    -this.rotation_tower+ rand.rand(-10,10),this.damage,this.t_damage,this.penetration,this.team,(byte)1));
        }
    }
    public void fire_player_fragmentation_bull(){
        reload_bot();
        if(this.left_mouse && this.reload <= 0){
            for(int i = 0;i<this.enemy_spisok.size();i++){
                if(sqrt(pow(this.x-this.enemy_spisok.get(i).x,2)+pow(this.y-this.enemy_spisok.get(i).y,2)) < this.enemy_spisok.get(i).range_see_2) {
                    this.enemy_spisok.get(i).time_trigger_bull_bot = this.enemy_spisok.get(i).time_trigger_bull;
                }
            }
            sound.sound( this.sound_fire,(float) sqrt(pow(this.x_rend, 2) + pow(this.y_rend, 2)) / -60);
            this.reload = this.reload_max;
            this.fire_x = this.tower_x+this.tower_width_2+((this.tower_height_2+this.y_tower) *sin(-this.rotation_tower*3.1415926535/180));
            this.fire_y = this.tower_y+this.tower_height_2+((this.tower_height_2+this.y_tower) *cos(-this.rotation_tower*3.1415926535/180));
            Main.bull_obj.add(new bull_fragmentation(this.fire_x,this.fire_y,
                    -this.rotation_tower+180,this.damage,this.penetration,this.damage_fragment,this.penetration_fragment,this.team,(byte)1));
        }
    }

    public void fire_player_acid(){
        reload_bot();
        if(this.left_mouse && this.reload <= 0){
            for(int i = 0;i<enemy_spisok.size();i++){
                if(sqrt(pow(this.x-this.enemy_spisok.get(i).x,2)+pow(this.y-this.enemy_spisok.get(i).y,2)) < this.enemy_spisok.get(i).range_see_2) {
                    this.enemy_spisok.get(i).time_trigger_bull_bot = this.enemy_spisok.get(i).time_trigger_bull;
                }
            }
            sound.sound( this.sound_fire,(float) sqrt(pow(this.x_rend, 2) + pow(this.y_rend, 2)) / -60);
            this.reload = this.reload_max;
            this.fire_x = this.tower_x+this.tower_width_2+((this.tower_height_2+this.y_tower) *sin(-this.rotation_tower*3.1415926535/180));
            this.fire_y = this.tower_y+this.tower_height_2+((this.tower_height_2+this.y_tower) *cos(-this.rotation_tower*3.1415926535/180));
            Main.bull_obj.add(new bull_acid(this.fire_x,this.fire_y,-this.rotation_tower+ rand.rand(-10,10),this.damage,this.penetration,this.team,(byte)1));
            Main.bull_obj.add(new bull_acid(this.fire_x,this.fire_y,-this.rotation_tower+ rand.rand(-10,10),this.damage,this.penetration,this.team,(byte)1));
        }
    }

    public void tower_xy(){
    double []xy = metod.tower_xy(this.x,this.y,this.tower_x_const,this.tower_y_const,this.difference,-this.rotation_corpus);
        this.tower_x = xy[0];this.tower_y = xy[1];}
    public void tower_xy_2(){
        double []xy = metod.tower_xy_2(this.x,this.y,this.tower_x_const,this.tower_y_const,this.difference,this.difference_2,-this.rotation_corpus);
        this.tower_x = xy[0];this.tower_y = xy[1];}
    public boolean fire_bot(double obj_x,double obj_y){
        g = atan2(this.tower_y - obj_y,this.tower_x-obj_x ) / 3.1415926535 * 180;
        g_right = this.rotation_tower - 10;
        g_left = this.rotation_tower + 10;
        g +=90;
        if (g_right < g && g_left > g && guidance && trigger_fire) {
            this.reload = this.reload_max;
            return true;
        }
        return false;
    }
    public boolean fire_bot_not_tower(double obj_x,double obj_y){
        g = atan2(this.tower_y - obj_y,this.tower_x-obj_x ) / 3.1415926535 * 180;
        g_right = this.rotation_corpus - 10;
        g_left = this.rotation_corpus + 10;
        g +=90;
        if (g_right < g && g_left > g) {
            sost_fire_bot = true;
        }
        else{sost_fire_bot = false;}
        this.reload-= 1;
        guidance = reload_bot();
        if(sost_fire_bot && guidance){
            this.reload = this.reload_max;
            return true;
        }
        return false;
    }
    public int distance_target = 200;
    public int distance_target_2 = 230;
    public void motor_bot_base(double g,byte behavior){
        this.time_sound_motor -=1;

        if (this.trigger_drive == 1 && this.crite_life == 0) {
            switch (behavior) {
                case 1:{
                    if (this.speed > this.min_speed) {
                        this.speed -= this.acceleration;
                        if (this.time_sound_motor < 0) {
                            sound.sound(Main.sa.get(0).motor, (float) sqrt(pow(this.x_rend, 2) + pow(this.y_rend, 2)) / -60);
                            this.time_sound_motor = this.time_max_sound_motor;
                        }
                    }
                }
                case 2:{
                    if (g > distance_target && this.speed < this.max_speed) {
                        this.speed += this.acceleration;
                        if (this.time_sound_motor < 0) {
                            sound.sound(Main.sa.get(0).motor_back, (float) sqrt(pow(this.x_rend, 2) + pow(this.y_rend, 2)) / -60);
                            this.time_sound_motor = this.time_max_sound_motor;
                        }
                    } else if(this.speed > this.min_speed){
                        this.speed -= this.acceleration;
                        if (this.time_sound_motor < 0) {
                            sound.sound(Main.sa.get(0).motor, (float) sqrt(pow(this.x_rend, 2) + pow(this.y_rend, 2)) / -60);
                            this.time_sound_motor = this.time_max_sound_motor;
                        }
                    }
                }
                case 3:{
                    if (g > distance_target && this.speed < this.max_speed) {
                        this.speed += this.acceleration;
                        if (this.time_sound_motor < 0) {
                            sound.sound(Main.sa.get(0).motor_back, (float) sqrt(pow(this.x_rend, 2) + pow(this.y_rend, 2)) / -60);
                            this.time_sound_motor = this.time_max_sound_motor;
                        }
                    } else if (g > distance_target_2 && this.speed > this.min_speed) {
                        this.speed -= this.acceleration;
                        if (this.time_sound_motor < 0) {
                            sound.sound(Main.sa.get(0).motor, (float) sqrt(pow(this.x_rend, 2) + pow(this.y_rend, 2)) / -60);
                            this.time_sound_motor = this.time_max_sound_motor;
                        }

                    } else {
                        if (this.speed < 0) {
                            this.speed -= this.acceleration;
                            if (this.time_sound_motor < 0) {
                                sound.sound(Main.sa.get(0).motor, (float) sqrt(pow(this.x_rend, 2) + pow(this.y_rend, 2)) / -60);
                                this.time_sound_motor = this.time_max_sound_motor;
                            }
                        } else if (this.speed > 0) {
                            this.speed += this.acceleration;
                            if (this.time_sound_motor < 0) {
                                sound.sound(Main.sa.get(0).motor_back, (float) sqrt(pow(this.x_rend, 2) + pow(this.y_rend, 2)) / -60);
                                this.time_sound_motor = this.time_max_sound_motor;
                            }
                        }
                    }
                }
            }
        } else {
            if (this.speed < 0) {
                this.speed += this.acceleration;
            } else if (this.speed > 0) {
                this.speed -=this.acceleration;
            }
        }
        this.x -= move.move_sin(this.speed, -this.rotation_corpus);
        this.y -= move.move_cos(this.speed, -this.rotation_corpus);
    }
    public void motor_bot_base(int g,byte behavior){
        this.time_sound_motor -=1;

        if (this.trigger_drive == 1 && this.crite_life == 0) {
            switch (behavior) {
                case 1:{
                    if (this.speed > this.min_speed) {
                        this.speed -= this.acceleration;
                        if (this.time_sound_motor < 0) {
                            sound.sound(Main.sa.get(0).motor, (float) sqrt(pow(this.x_rend, 2) + pow(this.y_rend, 2)) / -60);
                            this.time_sound_motor = this.time_max_sound_motor;
                        }
                    }
                }
                case 2:{
                    if (g > distance_target && this.speed < this.max_speed) {
                        this.speed += this.acceleration;
                        if (this.time_sound_motor < 0) {
                            sound.sound(Main.sa.get(0).motor_back, (float) sqrt(pow(this.x_rend, 2) + pow(this.y_rend, 2)) / -60);
                            this.time_sound_motor = this.time_max_sound_motor;
                        }
                    } else if(this.speed > this.min_speed){
                        this.speed -= this.acceleration;
                        if (this.time_sound_motor < 0) {
                            sound.sound(Main.sa.get(0).motor, (float) sqrt(pow(this.x_rend, 2) + pow(this.y_rend, 2)) / -60);
                            this.time_sound_motor = this.time_max_sound_motor;
                        }
                    }
                }
                case 3:{
                    if (g > distance_target && this.speed < this.max_speed) {
                        this.speed += this.acceleration;
                        if (this.time_sound_motor < 0) {
                            sound.sound(Main.sa.get(0).motor_back, (float) sqrt(pow(this.x_rend, 2) + pow(this.y_rend, 2)) / -60);
                            this.time_sound_motor = this.time_max_sound_motor;
                        }
                    } else if (g > distance_target_2 && this.speed > this.min_speed) {
                        this.speed -= this.acceleration;
                        if (this.time_sound_motor < 0) {
                            sound.sound(Main.sa.get(0).motor, (float) sqrt(pow(this.x_rend, 2) + pow(this.y_rend, 2)) / -60);
                            this.time_sound_motor = this.time_max_sound_motor;
                        }

                    } else {
                        if (this.speed < 0) {
                            this.speed -= this.acceleration;
                            if (this.time_sound_motor < 0) {
                                sound.sound(Main.sa.get(0).motor, (float) sqrt(pow(this.x_rend, 2) + pow(this.y_rend, 2)) / -60);
                                this.time_sound_motor = this.time_max_sound_motor;
                            }
                        } else if (this.speed > 0) {
                            this.speed += this.acceleration;
                            if (this.time_sound_motor < 0) {
                                sound.sound(Main.sa.get(0).motor_back, (float) sqrt(pow(this.x_rend, 2) + pow(this.y_rend, 2)) / -60);
                                this.time_sound_motor = this.time_max_sound_motor;
                            }
                        }
                    }
                }
            }
        } else {
            if (this.speed < 0) {
                this.speed += this.acceleration;
            } else if (this.speed > 0) {
                this.speed -=this.acceleration;
            }
        }
        this.x -= move.move_sin(this.speed, -this.rotation_corpus);
        this.y -= move.move_cos(this.speed, -this.rotation_corpus);
    }
    public void rotation_bot(double g,double g_right,double g_left) {

        if (g > 20 && this.rotation_corpus < -180) {
            g = -272;
            //if (this.rotation_corpus <= g) {
                //this.rotation_corpus = 91;
            //}
        }
        else if (g < -160 && this.rotation_corpus > 0) {
            g = 92;
            //if (this.rotation_corpus >= g) {
                //this.rotation_corpus = -271;
            //}
        }
        if (this.rotation_corpus > 91) {
            this.rotation_corpus = -269;
        }
        else if (this.rotation_corpus < -271) {
            this.rotation_corpus = 89;
        }
        if (g > this.rotation_corpus) {
                this.rotation_corpus += this.speed_rotation;
        } else if (g < this.rotation_corpus) {
            this.rotation_corpus -= this.speed_rotation;
        }
        if (g_left < g && g_right > g && this.crite_life == 0){
            this.trigger_drive = 1;
        }
        else{
            this.trigger_drive = 0;
        }
    }
    public int ai_sost;
    public void motor_bot_base() {
        this.time_sound_motor -= 1;
        if (this.trigger_drive == 1 && this.crite_life == 0) {
            if (this.speed < this.max_speed) {
                this.speed += this.acceleration;
                if (this.time_sound_motor < 0) {
                    sound.sound(Main.sa.get(0).motor, (float) sqrt(pow(this.x_rend, 2) + pow(this.y_rend, 2)) / -60);
                    this.time_sound_motor = this.time_max_sound_motor;
                }
            }
        }
        else {
            if (this.speed < 0) {
                this.speed += this.acceleration;
            } else if (this.speed > 0) {
                this.speed -=this.acceleration;
            }
        }
        this.x -= move.move_sin(this.speed, -this.rotation_corpus);
        this.y -= move.move_cos(this.speed, -this.rotation_corpus);
    }

    public void bypass_build(ArrayList<Build> obj_build, ArrayList<Transport> obj_player, double e,double g, double g_left, double g_right, int i3) {
        if(obj_build.size()!= 0&& this.enemy_spisok.size()!=0) {
            int i2 = metod.detection_near_transport_i(this.spisok, i3, obj_player);
            if (ai_sost == 0) {
                if (null != findIntersection(this.tower_x, this.tower_y, obj_player.get(i2).tower_x, obj_player.get(i2).tower_y)) {
                    Main.flame_static_obj.add(new flame_static(x, y));
                    double []xy = metod.tower_xy_2(this.x,this.y,this.ai_x_const,this.ai_y_const,0,0,-this.rotation_corpus);
                    ai.path_AI(this.spisok.get(i3), this.enemy_spisok.get(0),xy[0],xy[1]);
                    ai_sost = 200;
                    trigger_fire = false;
                } else {
                    path.clear();
                    trigger_fire = true;
                }
            }
            if (ai_sost != 0) {
                ai_sost -= 1;
            }
            //int i = metod.detection_near_build_i(this.spisok, i3, obj_build);
            //System.out.println(1);
            double []xy = metod.tower_xy_2(this.x,this.y,this.ai_x_const,this.ai_y_const,0,0,-this.rotation_corpus);
            if(path.size() > 0) {
                //this.tower_x = xy[0];this.tower_y = xy[1];
                this.g = sqrt(pow((xy[0] - matrix.get(path.get(0)[1]).get(path.get(0)[0]).x_center), 2) + pow(xy[1] - matrix.get(path.get(0)[1]).get(path.get(0)[0]).y_center, 2));
                double gr = (atan2(xy[1] - matrix.get(path.get(0)[1]).get(path.get(0)[0]).y_center,xy[0] - matrix.get(path.get(0)[1]).get(path.get(0)[0]).x_center)/3.1415926535*180)-90;
                rotation_bot(gr, g_right, g_left);
                motor_bot_base();
                if(this.g< 55){
                    path.remove(0);
                }
            }
            else if(e == 1){
                int[] sp = metod.detection_near_transport_i_def(this.spisok, i3, obj_player);
                motor_bot_base((double)sp[1], this.behavior);
                rotation_bot(g, g_right, g_left);
            }
            else if(e == 2){
                int[] sp = metod.detection_near_transport_i_def(this.spisok, i3,this.spisok);
                motor_bot_base((double)sp[1], (byte)4);
                rotation_bot(g, g_right, g_left);
            }
        }
    }
    public double[] findIntersection(double x0, double y0, double dx, double dy, double x1, double y1, double x2, double y2) {
        double xy_r = atan2(dy-y0, dx-x0);
        //double xy_r = atan2(y0-dy, x0-dx)*-1;
        double x = x0;
        double y = y0;
        double xe = dx;
        double ye = dy;
        double speed_x = 10 * cos(xy_r);
        double speed_y = 10 * sin(xy_r);
        render.setColor(1,1,0,1);

        if (y > ye) {
            if (x > xe) {
                while (x > xe && y > ye) {
                    x += speed_x;
                    y += speed_y;
                    //System.out.println(x+" "+y+" "+speed_x+" "+speed_y+" "+dx+" "+dy+" "+" "+xy_r+" w");
                    //double[]xy = Main.rc.get(0).render_obj(x0,y0);
                    if (x > x1 && x < x2 && y > y1 && y < y2) {
                        return new double[]{x, y};
                    }

                }
            } else {
                while (x < xe && y > ye) {
                    x += speed_x;
                    y += speed_y;
                    //System.out.println(x+" "+y+" "+speed_x+" "+speed_y+" "+dx+" "+dy+" "+" "+xy_r+" s");
                    //double[]xy = Main.rc.get(0).render_obj(x0,y0);
                    if (x > x1 & x < x2 & y > y1 & y < y2) {
                        return new double[]{x, y};
                    }
                }
            }
        } else{
            if (x > xe) {
                while (x > xe && y < ye) {
                    x += speed_x;
                    y += speed_y;
                    //System.out.println(x+" "+y+" "+speed_x+" "+speed_y+" "+dx+" "+dy+" "+" "+xy_r+" x");
                    //double[]xy = Main.rc.get(0).render_obj(x0,y0);
                    if (x > x1 & x < x2 & y > y1 & y < y2) {
                        return new double[]{x, y};
                    }
                }
            } else {
                while (x < xe && y < ye) {
                    x += speed_x;
                    y += speed_y;
                   // System.out.println(x+" "+y+" "+speed_x+" "+speed_y+" "+dx+" "+dy+" "+" "+xy_r+" z");
                    //double[]xy = Main.rc.get(0).render_obj(x0,y0);
                    if (x > x1 & x < x2 & y > y1 & y < y2) {
                        return new double[]{x, y};
                    }
                }
            }
        }
        return null;

    }
    protected ArrayList<int[]>ints = new ArrayList<>();
    public double[] findIntersection(double x0, double y0, double dx, double dy) {
        double xy_r = atan2(dy-y0, dx-x0);
        for (i = 0; i < Main.build.size(); i++) {
            for (int j = 0; j < Main.build.get(i).xy_area_list.size(); j++) {

                double xy = sqrt(pow(dx-x0,2)+pow(dy-y0,2));
                double xy_2 = sqrt(pow(build.get(i).xy_area_list.get(j)[0]-x0,2)+pow(build.get(i).xy_area_list.get(j)[1]-y0,2));
                if(xy < xy_2){
                    break;
                }

                if(x0 > dx){
                    if(build.get(i).xy_area_list.get(j)[0]>x0){
                        break;
                    }
                    if(y0 > dy){
                        if(build.get(i).xy_area_list.get(j)[1]>y0){
                            break;
                        }
                    }
                    else{
                        if(build.get(i).xy_area_list.get(j)[1]<y0){
                            break;
                        }
                    }
                }
                else{
                    if(build.get(i).xy_area_list.get(j)[0]<x0){
                        break;
                    }
                    if(y0 > dy){
                        if(build.get(i).xy_area_list.get(j)[1]>y0){
                            break;
                        }
                    }
                    else{
                        if(build.get(i).xy_area_list.get(j)[1]<y0){
                            break;
                        }
                    }
                }
                ints.add(new int []{i,j});

            }
        }
        double speed_x = 10 * cos(xy_r);
        double speed_y = 10 * sin(xy_r);
        for(i=0;i<ints.size();i++) {
            double x = x0;
            double y = y0;
            if (y > dy) {
                if (x > dx) {
                    while (x > dx && y > dy) {
                        x += speed_x;
                        y += speed_y;
                        //System.out.println(x+" "+y+" "+speed_x+" "+speed_y+" "+dx+" "+dy+" "+" "+xy_r+" w");
                        //double[]xy = Main.rc.get(0).render_obj(x0,y0);
                        if (x > build.get(ints.get(i)[0]).xy_area_list.get(ints.get(i)[1])[0]
                                && x < build.get(ints.get(i)[0]).xy_area_list.get(ints.get(i)[1])[0]+build.get(ints.get(i)[0]).xy_area_list.get(ints.get(i)[1])[2]
                                && y > build.get(ints.get(i)[0]).xy_area_list.get(ints.get(i)[1])[1]
                                && y < build.get(ints.get(i)[0]).xy_area_list.get(ints.get(i)[1])[1]+build.get(ints.get(i)[0]).xy_area_list.get(ints.get(i)[1])[3]) {
                            return new double[]{x, y};
                        }

                    }
                } else {
                    while (x < dx && y > dy) {
                        x += speed_x;
                        y += speed_y;
                        //System.out.println(x+" "+y+" "+speed_x+" "+speed_y+" "+dx+" "+dy+" "+" "+xy_r+" s");
                        //double[]xy = Main.rc.get(0).render_obj(x0,y0);
                        if (x > build.get(ints.get(i)[0]).xy_area_list.get(ints.get(i)[1])[0]
                                && x < build.get(ints.get(i)[0]).xy_area_list.get(ints.get(i)[1])[0]+build.get(ints.get(i)[0]).xy_area_list.get(ints.get(i)[1])[2]
                                && y > build.get(ints.get(i)[0]).xy_area_list.get(ints.get(i)[1])[1]
                                && y < build.get(ints.get(i)[0]).xy_area_list.get(ints.get(i)[1])[1]+build.get(ints.get(i)[0]).xy_area_list.get(ints.get(i)[1])[3]) {
                            return new double[]{x, y};
                        }
                    }
                }
            } else {
                if (x > dx) {
                    while (x > dx && y < dy) {
                        x += speed_x;
                        y += speed_y;
                        //System.out.println(x+" "+y+" "+speed_x+" "+speed_y+" "+dx+" "+dy+" "+" "+xy_r+" x");
                        //double[]xy = Main.rc.get(0).render_obj(x0,y0);
                        if (x > build.get(ints.get(i)[0]).xy_area_list.get(ints.get(i)[1])[0]
                                && x < build.get(ints.get(i)[0]).xy_area_list.get(ints.get(i)[1])[0]+build.get(ints.get(i)[0]).xy_area_list.get(ints.get(i)[1])[2]
                                && y > build.get(ints.get(i)[0]).xy_area_list.get(ints.get(i)[1])[1]
                                && y < build.get(ints.get(i)[0]).xy_area_list.get(ints.get(i)[1])[1]+build.get(ints.get(i)[0]).xy_area_list.get(ints.get(i)[1])[3]) {
                            return new double[]{x, y};
                        }
                    }
                } else {
                    while (x < dx && y < dy) {
                        x += speed_x;
                        y += speed_y;
                        // System.out.println(x+" "+y+" "+speed_x+" "+speed_y+" "+dx+" "+dy+" "+" "+xy_r+" z");
                        //double[]xy = Main.rc.get(0).render_obj(x0,y0);
                        if (x > build.get(ints.get(i)[0]).xy_area_list.get(ints.get(i)[1])[0]
                                && x < build.get(ints.get(i)[0]).xy_area_list.get(ints.get(i)[1])[0]+build.get(ints.get(i)[0]).xy_area_list.get(ints.get(i)[1])[2]
                                && y > build.get(ints.get(i)[0]).xy_area_list.get(ints.get(i)[1])[1]
                                && y < build.get(ints.get(i)[0]).xy_area_list.get(ints.get(i)[1])[1]+build.get(ints.get(i)[0]).xy_area_list.get(ints.get(i)[1])[3]) {
                            return new double[]{x, y};
                        }
                    }
                }
            }
        }
        return null;

    }



    public void speed_balance(){
        if(this.speed<this.acceleration && this.speed>-this.acceleration){
            this.speed = 0;
        }
    }
    public void helicopter_ii(ArrayList<Transport>obj_search, int i3){
        if (this.enemy_spisok.size()!= 0) {
            int[]sp = metod.detection_near_transport_i_def(this.spisok, i3, obj_search);
            g_right = this.rotation_corpus - 10;
            g_left = this.rotation_corpus + 10;
            g = atan2(this.y - obj_search.get(sp[0]).y, this.x - obj_search.get(sp[0]).x) / 3.1415926535 * 180;
            g -= 90;
            rotation_bot(g, g_left, g_right);
            motor_bot_base(sp[1], this.behavior);
            speed_balance();
        }

    }
    public double[] less_hp_bot(ArrayList<Transport>bot, ArrayList<Transport>obj_player, ArrayList<Transport>obj_support, int i){
        int i2;
        if (this.hp > this.max_hp / 3 && this.medic_help == 0) {
            i2 = metod.detection_near_transport_i(bot,i,obj_player);
            g = atan2(this.y - obj_player.get(i2).y, this.x - obj_player.get(i2).x) / 3.1415926535 * 180;
            g -= 90;
            double[]list = {g,1};
            return list;

        } else{
            this.behavior_buffer = this.behavior;
            this.behavior = 4;
            this.medic_help = 1;
            if (this.hp >= this.max_hp - 20) {
                this.medic_help = 0;
                this.behavior = this.behavior_buffer;
            }
            int ind = NULL;
            int radius = NULL;
            for (int i3 = 0; i3 < obj_support.size(); i3++) {
                if(this.x != obj_support.get(i3).x && obj_support.get(i3).y != this.y && Objects.equals(obj_support.get(i3).teg_unit, "support")) {
                    double g = sqrt(pow((this.x - obj_support.get(i3).x), 2) + pow(this.y - obj_support.get(i3).y, 2));
                    if (radius == NULL || radius > g) {
                        ind = i3;
                        radius = (int) g;

                    }
                }
            }
            if(ind != NULL){
                g = atan2(this.y - obj_support.get(ind).y, this.x - obj_support.get(ind).x) / 3.1415926535 * 180;
                g -= 90;
                double[]list = {g,2};
                return list;
            }
            i2 = metod.detection_near_transport_i(bot,i,obj_player);
            g = atan2(this.y - obj_player.get(i2).y, this.x - obj_player.get(i2).x) / 3.1415926535 * 180;
            g -= 90;
            double[]list = {g,1};
            return list;
        }

    }
    public void corpus_corpus(ArrayList<Transport>obj_2, byte type){
        boolean z;
        for(int i = 0;i<obj_2.size();i++) {
            z = rect_collision((int)this.x,(int)this.y,(int)this.corpus_width,(int)this.corpus_height,this.rotation_corpus,(int)obj_2.get(i).x,
                    (int)obj_2.get(i).y,(int)obj_2.get(i).corpus_width,(int)obj_2.get(i).corpus_height,obj_2.get(i).rotation_corpus);

            if (z & obj_2.get(i).priority_paint == this.priority_paint) {
                sound.sound(Main.sa.get(0).hit,(float) sqrt(pow(this.x_rend,2)+pow(this.y_rend,2))/-60);
                switch (type) {
                    case 1 : {
                        metod_1(obj_2, i, (byte) 1);
                        physic_collision(obj_2,i);
                    }
                }
            }
        }
    }
    public void corpus_corpus_def_xy(ArrayList<Transport>obj_2, byte type){
        boolean z;
        for(int i = 0;i<obj_2.size();i++) {
            if (obj_2.get(i).x != this.x && obj_2.get(i).y != this.y) {
                z = rect_collision((int) this.x, (int) this.y, (int) this.corpus_width, (int) this.corpus_height, this.rotation_corpus, (int) obj_2.get(i).x,
                        (int) obj_2.get(i).y, (int) obj_2.get(i).corpus_width, (int) obj_2.get(i).corpus_height, obj_2.get(i).rotation_corpus);

                if (z && obj_2.get(i).priority_paint == this.priority_paint) {
                    sound.sound(Main.sa.get(0).hit, (float) sqrt(pow(this.x_rend, 2) + pow(this.y_rend, 2)) / -60);
                    switch (type) {
                        case 1 : {
                            metod_1(obj_2, i, (byte) 1);
                            physic_collision(obj_2,i);
                        }
                    }
                }
            }
        }
    }
    public void physic_collision(ArrayList<Transport>obj_2, int i){
        double x = this.x+corpus_width_2;
        double y = this.y+corpus_height_2;
        double[]xy;
        boolean z;
        double v = 4;
        double x_2 = obj_2.get(i).x+obj_2.get(i).corpus_width_2;
        double y_2 = obj_2.get(i).y+obj_2.get(i).corpus_height_2;
        xy = metod.tower_xy(x,y,0,0,-corpus_height_2,-rotation_corpus);
        double x_1_2 = xy[0];
        double y_1_2 = xy[1];
        xy = metod.tower_xy(x_2,y_2,0,0,-obj_2.get(i).corpus_height_2,-obj_2.get(i).rotation_corpus);
        double x_2_2 = xy[0];
        double y_2_2 = xy[1];
        if(sqrt(pow(x_1_2 - x_2_2, 2) + pow(y_1_2 - y_2_2, 2))<(obj_2.get(i).corpus_width_2+corpus_width_2)*1.4){
            xy = metod.tower_xy_2(x_2,y_2,0,0,-obj_2.get(i).corpus_height_2/1.5,obj_2.get(i).corpus_width_2*1.2,-obj_2.get(i).rotation_corpus);
            double x_2_2_1 = xy[0];
            double y_2_2_1 = xy[1];
            xy = metod.tower_xy_2(x_2,y_2,0,0,-obj_2.get(i).corpus_height_2/1.5,-obj_2.get(i).corpus_width_2*1.2,-obj_2.get(i).rotation_corpus);
            double x_2_2_2 = xy[0];
            double y_2_2_2 = xy[1];
            xy = metod.tower_xy_2(x,y,0,0,-corpus_height_2/1.5,corpus_width_2*1.2,-rotation_corpus);
            double x_1_2_1 = xy[0];
            double y_1_2_1 = xy[1];
            xy = metod.tower_xy_2(x,y,0,0,-corpus_height_2/1.5,-corpus_width_2*1.2,-rotation_corpus);
            double x_1_2_2 = xy[0];
            double y_1_2_2 = xy[1];
            if(sqrt(pow(x_2_2_1 - x_1_2, 2) + pow(y_2_2_1 - y_1_2, 2))<(obj_2.get(i).corpus_width_2+corpus_width_2)/1.5) {
                obj_2.get(i).rotation_corpus += (abs(obj_2.get(i).speed) + 1) * v;
            }
            if(sqrt(pow(x_2_2_2 - x_1_2, 2) + pow(y_2_2_2 - y_1_2, 2))<(obj_2.get(i).corpus_width_2+corpus_width_2)/1.5) {
                obj_2.get(i).rotation_corpus -= (abs(obj_2.get(i).speed) + 1) * v;
            }
            if(sqrt(pow(x_1_2_1 - x_2_2, 2) + pow(y_1_2_1 - y_2_2, 2))<(obj_2.get(i).corpus_width_2+corpus_width_2)/1.5) {
                this.rotation_corpus += (abs(obj_2.get(i).speed) + 1) * v;
            }
            if(sqrt(pow(x_1_2_2 - x_2_2, 2) + pow(y_1_2_2 - y_2_2, 2))<(obj_2.get(i).corpus_width_2+corpus_width_2)/1.5) {
                this.rotation_corpus -= (abs(obj_2.get(i).speed) + 1) * v;
            }
            return;
        }
        xy = metod.tower_xy(x,y,0,0,corpus_height_2,-rotation_corpus);
        double x_1_1 = xy[0];
        double y_1_1 = xy[1];
        xy = metod.tower_xy(x_2,y_2,0,0,obj_2.get(i).corpus_height_2,-obj_2.get(i).rotation_corpus);
        double x_2_1 = xy[0];
        double y_2_1 = xy[1];
        if(sqrt(pow(x_1_1 - x_2_1, 2) + pow(y_1_1 - y_2_1, 2))<(obj_2.get(i).corpus_width_2+corpus_width_2)*1.2){
            xy = metod.tower_xy_2(x_2,y_2,0,0,obj_2.get(i).corpus_height_2/1.5,obj_2.get(i).corpus_width_2*1.2,-obj_2.get(i).rotation_corpus);
            double x_2_1_1 = xy[0];
            double y_2_1_1 = xy[1];
            xy = metod.tower_xy_2(x_2,y_2,0,0,obj_2.get(i).corpus_height_2/1.5,-obj_2.get(i).corpus_width_2*1.2,-obj_2.get(i).rotation_corpus);
            double x_2_1_2 = xy[0];
            double y_2_1_2 = xy[1];
            xy = metod.tower_xy_2(x,y,0,0,corpus_height_2/1.5,corpus_width_2*1.2,-rotation_corpus);
            double x_1_1_1 = xy[0];
            double y_1_1_1 = xy[1];
            xy = metod.tower_xy_2(x,y,0,0,corpus_height_2/1.5,-corpus_width_2*1.2,-rotation_corpus);
            double x_1_1_2 = xy[0];
            double y_1_1_2 = xy[1];
            if(sqrt(pow(x_2_1_1 - x_1_1, 2) + pow(y_2_1_1 - y_1_1, 2))<(obj_2.get(i).corpus_width_2+corpus_width_2)/1.2) {
                obj_2.get(i).rotation_corpus -= (abs(obj_2.get(i).speed) + 1) * v;
            }
            if(sqrt(pow(x_2_1_2 - x_1_1, 2) + pow(y_2_1_2 - y_1_1, 2))<(obj_2.get(i).corpus_width_2+corpus_width_2)/1.2) {
                obj_2.get(i).rotation_corpus += (abs(obj_2.get(i).speed) + 1) * v;
            }
            if(sqrt(pow(x_1_1_1 - x_2_1, 2) + pow(y_1_1_1 - y_2_1, 2))<(obj_2.get(i).corpus_width_2+corpus_width_2)/1.2) {
                this.rotation_corpus -= (abs(obj_2.get(i).speed) + 1) * v;
            }
            if(sqrt(pow(x_1_1_2 - x_2_1, 2) + pow(y_1_1_2 - y_2_1, 2))<(obj_2.get(i).corpus_width_2+corpus_width_2)/1.2) {
                this.rotation_corpus += (abs(obj_2.get(i).speed) + 1) * v;
            }
            return;
        }
        if(sqrt(pow(x_1_1 - x_2_2, 2) + pow(y_1_1 - y_2_2, 2))<(obj_2.get(i).corpus_width_2+corpus_width_2)*1.2){
            xy = metod.tower_xy_2(x_2,y_2,0,0,-obj_2.get(i).corpus_height_2/1.5,obj_2.get(i).corpus_width_2*1.2,-obj_2.get(i).rotation_corpus);
            double x_2_2_1 = xy[0];
            double y_2_2_1 = xy[1];
            xy = metod.tower_xy_2(x_2,y_2,0,0,-obj_2.get(i).corpus_height_2/1.5,-obj_2.get(i).corpus_width_2*1.2,-obj_2.get(i).rotation_corpus);
            double x_2_2_2 = xy[0];
            double y_2_2_2 = xy[1];
            xy = metod.tower_xy_2(x,y,0,0,corpus_height_2/1.5,corpus_width_2*1.2,-rotation_corpus);
            double x_1_1_1 = xy[0];
            double y_1_1_1 = xy[1];
            xy = metod.tower_xy_2(x,y,0,0,corpus_height_2/1.5,-corpus_width_2*1.2,-rotation_corpus);
            double x_1_1_2 = xy[0];
            double y_1_1_2 = xy[1];
            if(sqrt(pow(x_2_2_1 - x_1_1, 2) + pow(y_2_2_1 - y_1_1, 2))<(obj_2.get(i).corpus_width_2+corpus_width_2)/1.2) {
                obj_2.get(i).rotation_corpus -= (abs(obj_2.get(i).speed) + 1) * v;
            }
            if(sqrt(pow(x_2_2_2 - x_1_1, 2) + pow(y_2_2_2 - y_1_1, 2))<(obj_2.get(i).corpus_width_2+corpus_width_2)/1.2) {
                obj_2.get(i).rotation_corpus += (abs(obj_2.get(i).speed) + 1) * v;
            }
            if(sqrt(pow(x_1_1_1 - x_2_2, 2) + pow(y_1_1_1 - y_2_2, 2))<(obj_2.get(i).corpus_width_2+corpus_width_2)/1.2) {
                this.rotation_corpus -= (abs(obj_2.get(i).speed) + 1) * v;
            }
            if(sqrt(pow(x_1_1_2 - x_2_2, 2) + pow(y_1_1_2 - y_2_2, 2))<(obj_2.get(i).corpus_width_2+corpus_width_2)/1.2) {
                this.rotation_corpus += (abs(obj_2.get(i).speed) + 1) * v;
            }
            return;
        }




    }
    public boolean rect_circle(int x1,int y1,int width,int height,int x,int y,int size,double rotation){
        Rectangle2D rect1 = new Rectangle2D.Double(x1,y1,width,height);
        AffineTransform transform1 = new AffineTransform();
        transform1.rotate(Math.toRadians(rotation), rect1.getCenterX(), rect1.getCenterY());


        Ellipse2D circle = new Ellipse2D.Double(x,y,size,size);
        if (rect1.intersects(circle.getBounds2D())) {
            return true;
        }
        return false;

    }
    public void damage_temperature(){
        if(this.t > 25){
            this.hp -= this.t/2;
            this.t -=2;
        }
        else if(this.t<-25){
            this.hp += this.t/2;
            this.t +=2;
        }
    }
    public void transport_delete(int i,ArrayList<Transport>obj){
        if(this.hp<0){
            event_dead();
            Main.debris.add(new debris_transport(this.x,this.y,this.rotation_corpus,this.speed,this.rotation_inert,this.speed_inert,
                    this.corpus_img,this.corpus_width,this.corpus_height));
            obj.remove(i);
        }
    }
    public void transport_delete_2(int i,ArrayList<Transport>obj){
        if(this.hp<0){
            if(3 <= rand.rand(1,4) || this.crite_life == 1){
                Main.debris.add(new debris_transport(this.x,this.y,this.rotation_corpus,this.speed,this.rotation_inert,this.speed_inert,
                        this.corpus_img,this.corpus_width,this.corpus_height));
                event_dead();
                obj.remove(i);
            }
            else{
                this.crite_life = 1;
                this.hp = this.max_hp/2;
            }

        }
    }
    public void debris_delete(int i,ArrayList<Transport>obj){
        if(this.hp<0){
            obj.remove(i);
        }
    }
    private void event_dead(){
        for(int i = 0;i<4;i++){
        Main.bang_obj.add(new bang((this.x+corpus_width_2 + rand.rand(-20,20)),(this.y+corpus_height_2 + rand.rand(-20,20)),new float[]{236,124,38},5));}
        for(int i = 0;i<5;i++){
            Main.flame_spawn.add(new flame_spawn(this.x + rand.rand(-5,45),this.y + rand.rand(-5,45)));}
        sound.sound(Main.sa.get(0).kill,(float)(sqrt(pow(this.x_rend,2)+pow(this.y_rend,2))/-80));
    }
    public boolean rect_collision(int x1,int y1,int width,int height,double rotation,
                                  int x2,int y2,int width2,int height2,double rotation_2){

        Rectangle rect1 = new Rectangle(x1,y1,width,height); // Прямоугольник 1
        Rectangle rect2 = new Rectangle(x2,y2,width2,height2); // Прямоугольник 2

        // Создаем аффинное преобразование для поворота
        AffineTransform transform1 = new AffineTransform();
        transform1.rotate(Math.toRadians(rotation), rect1.getCenterX(), rect1.getCenterY());
        AffineTransform transform2 = new AffineTransform();
        transform2.rotate(Math.toRadians(rotation_2), rect2.getCenterX(), rect2.getCenterY());

        // Преобразование прямоугольников с учетом поворота
        Area area1 = new Area(rect1);
        area1.transform(transform1);
        Area area2 = new Area(rect2);
        area2.transform(transform2);

        // Вычисление пересечения двух преобразованных прямоугольников
        area1.intersect(area2);

        // Проверка наличия пересечения
        if (!area1.isEmpty()) {
            //Rectangle intersection = area1.getBounds();
            //System.out.println("Прямоугольники пересекаются. Результат: " + intersection);
            return true;

        }
        return false;
    }
    public boolean rect_1_collision(int x1,int y1,int width,int height,double rotation,Area area2){

        Rectangle rect1 = new Rectangle(x1,y1,width,height); // Прямоугольник 1

        // Создаем аффинное преобразование для поворота
        AffineTransform transform1 = new AffineTransform();
        transform1.rotate(Math.toRadians(rotation), rect1.getCenterX(), rect1.getCenterY());
        // Преобразование прямоугольников с учетом поворота
        Area area1 = new Area(rect1);
        area1.transform(transform1);


        // Вычисление пересечения двух преобразованных прямоугольников
        area1.intersect(area2);

        // Проверка наличия пересечения
        if (!area1.isEmpty()) {
            //Rectangle intersection = area1.getBounds();
            //System.out.println("Прямоугольники пересекаются. Результат: " + intersection);
            return true;

        }
        return false;
    }
    public void build_corpus(ArrayList<Build>build){
        for(int i =0;i<build.size();i++){
            for(int j = 0;j<build.get(i).area_list.size();j++) {
                boolean z = rect_1_collision((int) this.x, (int) this.y, (int) this.corpus_width, (int) this.corpus_height, this.rotation_corpus,
                       build.get(i).area_list.get(j));
                if (z) {
                    if (this.speed > 2 || this.speed < -2) {
                        sound.sound(Main.sa.get(0).break_wooden, (float) sqrt(pow(this.x_rend, 2) + pow(this.y_rend, 2)) / -60);
                    }
                    metod_1(build.get(i).xy_area_list.get(j));
                }
            }
        }
    }
    private void metod_1(ArrayList<Transport>tr_2, int i2, byte type){
        //tr_1.get(i).speed_x += tr_2.get(i2).speed_x;
        //tr_1.get(i).speed_y += tr_2.get(i2).speed_y;
        //tr_2.get(i2).speed_x += tr_1.get(i2).speed_x;
        //tr_2.get(i2).speed_y += tr_1.get(i2).speed_y;
        //tr_1.get(i).speed *= -0.5;
        //tr_2.get(i2).speed *= -0.5;

        if(this.x<tr_2.get(i2).x) {
            switch (type) {
                case 1 : {
                    this.x -= 2;
                    tr_2.get(i2).x += 2;
                    this.speed_inert += tr_2.get(i2).speed*0.5;
                    tr_2.get(i2).speed_inert += this.speed*0.5;
                    this.speed *= -0.8;
                    tr_2.get(i2).speed *= -0.8;
                    this.rotation_inert = tr_2.get(i2).rotation_corpus;
                    tr_2.get(i2).rotation_inert = this.rotation_corpus;
                }
                case 2 : {
                    this.x -= 2;
                    this.speed_inert += tr_2.get(i2).speed*0.7;
                    this.speed *= -0.7;
                    this.rotation_inert = tr_2.get(i2).rotation_inert;

                }
                case 3 : {
                    tr_2.get(i2).x += 2;
                    tr_2.get(i2).speed_inert += this.speed*0.7;
                    tr_2.get(i2).speed *= -0.7;
                    tr_2.get(i2).rotation_inert = this.rotation_inert;

                }
            }
        }
        else if(this.x>tr_2.get(i2).x) {
            switch (type) {
                case 1 : {
                    this.x += 2;
                    tr_2.get(i2).x -= 2;
                    this.speed_inert += tr_2.get(i2).speed*0.5;
                    tr_2.get(i2).speed_inert += this.speed*0.5;
                    this.speed *= -0.5;
                    tr_2.get(i2).speed *= -0.7;
                    this.rotation_inert = tr_2.get(i2).rotation_corpus;
                    tr_2.get(i2).rotation_inert = this.rotation_corpus;
                }
                case 2 : {
                    this.x += 2;
                    this.speed_inert += tr_2.get(i2).speed*0.7;
                    this.speed *= -0.7;
                    this.rotation_inert = tr_2.get(i2).rotation_inert;
                }
                case 3 : {
                    tr_2.get(i2).x -= 2;
                    tr_2.get(i2).speed_inert += this.speed*0.7;
                    tr_2.get(i2).speed *= -0.7;
                    tr_2.get(i2).rotation_inert = this.rotation_inert;
                }

            }
        }
        if(this.y<tr_2.get(i2).y) {
            switch (type) {
                case 1 : {
                    this.y -= 2;
                    tr_2.get(i2).y += 2;

                }
                case 2 : {
                    this.y -= 2;

                }
                case 3 : {
                    tr_2.get(i2).y += 2;

                }
            }
        }
        else if(this.y>tr_2.get(i2).y) {
            switch (type) {
                case 1 : {
                    this.y += 2;
                    tr_2.get(i2).y -= 2;

                }
                case 2 : {
                    this.y += 2;

                }
                case 3 : {
                    tr_2.get(i2).y -= 2;

                }
            }
        }
    }
    private void metod_1(int[]z){
        if(this.x<z[0]) {
            this.x -= 2;
            this.speed *= -0.8;
            this.speed_inert *= -0.8;
        }
        else if(this.x>z[0]) {
            this.x += 2;
            this.speed *= -0.8;
            this.speed_inert *= -0.8;
        }
        if(this.y<z[1]) {
            this.y -= 2;
        }
        else if(this.y>z[1]) {
            this.y += 2;
        }
    }
    public void move_debris(){
        this.x += move.move_sin(this.speed,-this.rotation_corpus);
        this.y += move.move_cos(this.speed,-this.rotation_corpus);
        if(this.speed<0){
            this.speed +=this.acceleration;
            if(this.speed >-this.acceleration &&this.speed <this.acceleration){
                this.speed = 0;
            }
        }
        else if(this.speed>0){
            this.speed -=acceleration;
            if(this.speed >-this.acceleration &&this.speed <this.acceleration){
                this.speed = 0;
            }
        }
    }
    public void inertia_xy() {
        this.x += move.move_sin(this.speed_inert,-this.rotation_inert);
        this.y += move.move_cos(this.speed_inert,-this.rotation_inert);
        if (this.speed_inert > 0.1) {
            this.speed_inert -= this.acceleration;
            if (this.speed_inert < acceleration && this.speed_inert > -acceleration) {
                this.speed_inert = 0;
            }
        } else if (this.speed_inert < -0.1) {
            this.speed_inert += acceleration;
            if (this.speed_inert < acceleration && this.speed_inert > -acceleration) {
                this.speed_inert = 0;
            }
        }
    }
    public void hill_bot(ArrayList<Transport>obj){
        for (Transport transport : obj) {
            if (sqrt(pow(this.x - transport.x, 2) + pow(this.y - transport.y, 2)) < 230 && transport.max_hp > transport.hp) {
                transport.hp += this.hill;
                if(transport.hp >= transport.max_hp -20 && transport.crite_life == 1){
                    transport.crite_life = 0;
                }
            }
        }
    }
    public void bypass_hiller(int i) {
        if(this.spisok.size() > 1) {
            int[] sp = metod.detection_near_transport_i_def(this.spisok, i,this.spisok);
            g_right = this.rotation_corpus - 10;
            g_left = this.rotation_corpus + 10;
            g = atan2(this.y - this.spisok.get(sp[0]).y, this.x - this.spisok.get(sp[0]).x) / 3.1415926535 * 180;
            g -= 90;
            //bypass_build(Main.build,this.spisok, g, g_right,g_left, i);
            speed_balance();
        }
    }
    public void spawn_soldat(ArrayList<Soldat>soldat){
        this.time_spawn_soldat -= 1;
        if(this.time_spawn_soldat <= 0){
            int z = rand.rand(1,2);
            this.time_spawn_soldat = this.time_spawn_soldat_max;
            switch(z){
                case 1:{soldat.add(new soldat_bull(this.x,this.y));}
                case 2:{soldat.add(new soldat_flame(this.x,this.y));}
                //case 3->{soldat.add(new soldat_(this.x,this.y));}
            }
        }
    }
    public void borders_display(){
        if(this.x> Main.screenWidth+10){
            this.x = 0;
        }
        else if(this.x<-10){
            this.x = Main.screenWidth+10;
        }
        if(this.y> Main.screenHeight+10){
            this.y = 0;
        }
        else if(this.y<-10){
            this.y = Main.screenHeight+10;
        }
    }



    public void all_action(int i){
        damage_temperature();
        inertia_xy();
    }
    public void all_action_client(){

    }
    public void all_action_client_1(){

    }
    public void all_action_client(boolean left_mouse,boolean right_mouse,double mx,double my,boolean w,
                                  boolean a,boolean s,boolean d){

    }
    public void tower_action(int i,double x,double y,double rotation,boolean sost_fire_bot){
        this.update();

    }
    public void tower_action(int i,double x,double y,double rotation,boolean sost_atack,boolean sost_fire_bot,double x_fire,double y_fire){
        this.update();

    }

    public void update(){

    }
    public void center_render(){
        double[]xy = Main.rc.get(0).render_obj(this.x,this.y);
        this.x_rend = (int)(xy[0]*Main.zoom);
        this.y_rend = (int)(xy[1]*Main.zoom);
        xy = Main.rc.get(0).render_obj(this.tower_x,this.tower_y);
        this.x_tower_rend = (int)(xy[0]*Main.zoom);
        this.y_tower_rend = (int)(xy[1]*Main.zoom);

    }
    public void render_corpus(){
        render_metod.transorm_img(this.x_rend,this.y_rend,this.corpus_width*Main.zoom,this.corpus_height*Main.zoom,this.rotation_corpus,this.corpus_img,corpus_width_2*Main.zoom,corpus_height_2*Main.zoom);
    }
    public void center_render_tower(){
        double[]xy = Main.rc.get(0).render_obj(this.tower_x,this.tower_y);
        this.x_tower_rend = (int)(xy[0]*Main.zoom);
        this.y_tower_rend = (int)(xy[1]*Main.zoom);

    }
}

