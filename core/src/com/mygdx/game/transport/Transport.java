package com.mygdx.game.transport;

import Content.Bull.*;
import Content.Soldat.SoldatBull;
import Content.Soldat.SoldatFlame;
import Content.Transport.Transport.DebrisTransport;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.build.Building;
import com.mygdx.game.main.Main;
import com.mygdx.game.method.*;
import Content.Particle.FlameSpawn;
import Content.Particle.Bang;
import com.mygdx.game.soldat.*;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.util.ArrayList;
import java.util.Objects;

import static Content.Bull.BullRegister.PacketBull;
import static com.mygdx.game.main.Main.*;
import static com.mygdx.game.method.Option.SoundConst;
import static com.mygdx.game.method.pow2.pow2;
import static java.lang.StrictMath.abs;
import static java.lang.StrictMath.sin;
import static java.lang.StrictMath.cos;
import static java.lang.StrictMath.sqrt;
import static java.lang.StrictMath.atan2;
import static java.sql.Types.NULL;

public abstract class Transport{
    public UnitType type_unit;
    public float x, y;
    public int  difference,difference_2,hp,max_hp,time_spawn_soldat,time_spawn_soldat_max,x_rend,y_rend,x_tower_rend,y_tower_rend, id_unit, x_tower,y_tower,
    time_max_sound_motor = 20,time_sound_motor = time_max_sound_motor,nConnect;
    protected Sound sound_fire;
    private float fire_x,fire_y;
    public float max_speed=4, min_speed=-4,damage,penetration,damage_fragment,penetration_fragment,t,t_damage,armor,reload_max,acceleration=0.2f,speed,speed_inert, rotation_inert, rotation_tower, speed_tower=0.2f, speed_rotation=0.2f
            , rotation_corpus,tower_x,tower_y
            , tower_x_const, tower_y_const, tower_width_2, tower_height_2,reload,corpus_width,corpus_height,corpus_width_2,corpus_height_2,
            hill, width_tower, height_tower,aim_x,aim_y,corpus_height_3,corpus_width_3;
    protected float slowing = 0.05f;
    public static float speed_minimum = 0.5f;
    public int time_max_relocation = 300,time_relocation = 0;
    public float x_relocation,y_relocation,rotation_relocation,priority_paint = 0,ai_x_const = 24f,ai_y_const = 62f;
    public int range_see=800,range_see_2 = (int)(range_see*1.5),time_trigger_bull_bot,time_trigger_bull = 700;

    public byte behavior,behavior_buffer, medic_help, crite_life, team,height = 1,trigger_drive;
    private float g;
    public static int BorderDetected = 200;
    public boolean host;

    private int i;
    protected int distance_target = 200;
    protected int distance_target_2 = 230;
    public float difference_x,difference_y,green_len,green_len_reload;
    public float rotation_fire;
    public int corpus_width_zoom, corpus_height_zoom,width_tower_zoom,height_tower_zoom;
    public static int ai_sost;
    public ArrayList<int[]>path;
    public String teg_unit = "tank";
    public ArrayList<Transport> allyList, enemyList,tower_obj = new ArrayList<>();
    public int const_x_corpus,const_y_corpus,const_x_tower,const_y_tower,const_tower_x = 7,const_tower_y = 10;
    public boolean sost_fire_bot,guidance,left_mouse,right_mouse,trigger_attack,trigger_fire;
    private boolean press_w,press_a,press_s,press_d;
    public Sprite tower_img,corpus_img;
    public Transport(){

    }
    protected final void data(){
        path = new ArrayList<>();
        this.id_unit = 10000+rand.rand(89999);
        for(int i = 0; i<this.allyList.size()-1; i++){
            if(this.allyList.get(i).id_unit == this.id_unit){
                while (this.id_unit == this.allyList.get(i).id_unit) {
                    this.id_unit = 10000+rand.rand(89999);
                }
            }
        }
        this.reload = this.reload_max;
        this.hp = this.max_hp;
        this.time_spawn_soldat = this.time_spawn_soldat_max;
        this.corpus_width_2 = this.corpus_width/2;
        this.corpus_height_2 = this.corpus_height/2;
        corpus_height_3 = (float) (corpus_height_2/1.5);
        corpus_width_3 = (float)(corpus_width_2*1.2);
        if(Main.PlayerList == this.allyList){
            this.enemyList = Main.EnemyList;
        }
        else if(Main.EnemyList == this.allyList){
            this.enemyList = Main.PlayerList;
        }
        if(tower_img != null){
            this.difference_x = this.difference - this.x_tower;
            this.difference_y = this.difference - this.y_tower;
            this.tower_width_2 = this.width_tower/2;
            this.tower_height_2 = this.height_tower/2;
            this.const_x_tower = (int)(const_tower_x*Main.Zoom);
            this.const_y_tower = (int)(const_tower_y*Main.Zoom);}
        this.corpus_width_zoom = (int)(corpus_width*Main.Zoom);
        this.corpus_height_zoom = (int)(corpus_height*Main.Zoom);
        this.width_tower_zoom = (int)(width_tower *Main.Zoom);
        this.height_tower_zoom = (int)(height_tower *Main.Zoom);
        this.const_x_corpus = (int)(corpus_width_2*Main.Zoom);
        this.const_y_corpus = (int)(corpus_height_2*Main.Zoom);
        green_len = ((float)this.hp/this.max_hp)* Option.size_x_indicator;
    }
    protected final void data_tower(){
        this.teg_unit = "tower";
        if(Main.PlayerList == this.allyList){
            this.enemyList = Main.EnemyList;
        }
        else if(Main.EnemyList == this.allyList){
            this.enemyList = Main.PlayerList;
        }
        this.reload = this.reload_max;
        this.difference_x = this.difference - this.x_tower;
        this.difference_y = this.difference - this.y_tower;
        this.tower_width_2 = this.width_tower /2;
        this.tower_height_2 = this.height_tower /2;
        this.width_tower_zoom = (int)(width_tower *Main.Zoom);
        this.height_tower_zoom = (int)(height_tower *Main.Zoom);

        this.const_x_tower = (int)(const_tower_x*Main.Zoom);
        this.const_y_tower = (int)(const_tower_y*Main.Zoom);
    }
    protected final void tower_iteration(int iTr){
        for (i = 0;i<tower_obj.size();i++){
            tower_obj.get(i).tower_action(iTr,i,this.x,this.y,this.rotation_corpus,this.right_mouse);
        }
    }
    protected final void tower_iteration_client(int iTr){
        for (i = 0;i<tower_obj.size();i++){
            tower_obj.get(i).tower_action_client(iTr,i,this.x,this.y,this.rotation_corpus,this.right_mouse);
        }
    }
    protected final void tower_iteration_client_1(int iTr){
        for (i = 0;i<tower_obj.size();i++){
            tower_obj.get(i).tower_action_client_1(iTr,i,this.x,this.y,this.rotation_corpus,this.right_mouse);
        }
    }
    protected final void tower_iteration_client_2(int iTr){
        for (i = 0;i<tower_obj.size();i++){
            tower_obj.get(i).tower_action_client_2(iTr,i,this.x,this.y,this.rotation_corpus,this.right_mouse);
        }
    }
    protected final void tower_iteration_bot(int iTr){
        for (i = 0;i<tower_obj.size();i++){
            tower_obj.get(i).tower_action(iTr,i,this.x,this.y,this.rotation_corpus,trigger_attack,trigger_fire,aim_x,aim_y);
        }
    }
    protected final void tower_iteration_bot_client(int iTr){
        for (i = 0;i<tower_obj.size();i++){
            tower_obj.get(i).tower_action_client(iTr,i,this.x,this.y,this.rotation_corpus,trigger_attack,trigger_fire);
        }
    }
    protected final void behavior_bot(ArrayList<Transport>trTarget, int i){
        review_field(i, trTarget);
        if (!this.trigger_attack) {
            if (this.time_trigger_bull_bot > 0) {
                motor_bot_bypass(i);
                this.time_trigger_bull_bot -= 1;
            } else {
                peaceful_behavior();
            }
        } else {
            motor_bot_bypass(i);
        }
    }
    protected final void review_field(int i,ArrayList<Transport>trTarget){
        if(trTarget.size()!= 0) {
            int[] sp = Method.detection_near_transport_xy_def(this.allyList.get(i), trTarget);
            //g = atan2(this.y - tr.get(sp[0]).y, this.x - tr.get(sp[0]).x) / 3.1415926535 * 180;
            this.trigger_attack = sp[1] < this.range_see;
        }
        else{this.trigger_attack = false; }

    }

    private void peaceful_behavior(){
        this.time_relocation-= 1;
        if(this.time_relocation<0){
            if(rand.rand(2)== 1) {
                this.x_relocation = this.x + (200.0f+rand.rand(300.0f));
            }
            else{this.x_relocation = this.x + (-500f+rand.rand(300f));}
            if(rand.rand(2)== 1) {
                this.y_relocation = this.y + (200.0f+rand.rand(300f));
            }
            else{this.y_relocation = this.y + -500f+rand.rand(300f);}
            for (i = 0; i< Main.BuildingList.size(); i++){
                if(Main.BuildingList.get(i).x- Main.BuildingList.get(i).width_2>this.x_relocation & Main.BuildingList.get(i).x+ Main.BuildingList.get(i).width<this.x_relocation){
                    if(Main.BuildingList.get(i).y- Main.BuildingList.get(i).height_2>this.y_relocation & Main.BuildingList.get(i).y+ Main.BuildingList.get(i).height<this.y_relocation) {
                        if(rand.rand(2)== 1) {
                            this.x_relocation = this.x + (200.0f+rand.rand(300f));
                        }
                        else{this.x_relocation = this.x + (-500f+rand.rand(300f));}
                        if(rand.rand(2)== 1) {
                            this.y_relocation = this.y + (200.0f+rand.rand(300f));
                        }
                        else{this.y_relocation = this.y + (-500f+rand.rand(300f));}
                    }
                }
            }
        }
            //this.rotation_relocation = atan2(this.y-this.y_relocation,this.x-this.x_relocation)*3.1415926535/180;
        this.time_relocation = this.time_max_relocation;
        this.rotation_relocation = (float) ((atan2(this.y-this.y_relocation,this.x-this.x_relocation)/3.1415926535f*180f)-90f);
        //rotation_bot(rotation_relocation,g_left,g_right);
        //g = sqrt(pow(this.x-this.x_relocation,2)+pow(this.y-this.y_relocation,2));
        //bypass_build(Main.build,this.x_relocation,this.y_relocation,this.rotation_relocation,g_right,g_left,i);

    }
    protected final void tower_ii(int i){
        if (this.trigger_attack) {
            tower_bot_enemy(i);
        }
        else{
            if(this.rotation_tower>this.rotation_corpus+180){this.rotation_tower -= this.speed_tower;}
            if(this.rotation_tower<this.rotation_corpus+180){this.rotation_tower += this.speed_tower;}
        }
    }
    protected void tower_ii_2(){
        if (this.trigger_attack) {
            tower_bot();
        }
        else{
            if(this.rotation_tower>this.rotation_corpus+180){this.rotation_tower -= this.speed_tower;}
            if(this.rotation_tower<this.rotation_corpus+180){this.rotation_tower += this.speed_tower;}
        }
    }
    protected final void host_control(){
        this.left_mouse = Keyboard.LeftMouse;
        this.right_mouse = Keyboard.RightMouse;
        this.press_w = Keyboard.PressW;
        this.press_a = Keyboard.PressA;
        this.press_s = Keyboard.PressS;
        this.press_d = Keyboard.PressD;
    }
    protected final void client_control(){
        for(i = 0;i<Clients.size();i++){
            if(Clients.get(i).IDClient==nConnect){
                this.left_mouse = Clients.get(i).left_mouse;
                this.right_mouse = Clients.get(i).right_mouse;
                this.press_w = Clients.get(i).press_w;
                this.press_a = Clients.get(i).press_a;
                this.press_s = Clients.get(i).press_s;
                this.press_d = Clients.get(i).press_d;
                rotation_tower = Clients.get(i).rotation_tower_client;
                if (tower_obj.size() < Clients.get(i).rot_tower.size()) {
                    for (int i2 = 0; i2 < tower_obj.size(); i2++) {
                        Clients.get(i).rot_tower.size();
                        tower_obj.get(i2).rotation_tower = Clients.get(i).rot_tower.get(i2);
                    }
                } else {
                    for (int i2 = 0; i2 < Clients.get(i).rot_tower.size(); i2++) {
                        Clients.get(i).rot_tower.size();
                        tower_obj.get(i2).rotation_tower = Clients.get(i).rot_tower.get(i2);
                    }

                }


            }
        }
//        this.left_mouse = Main.LeftMouseClient;
//        this.right_mouse = Main.RightMouseClient;
//        this.press_w = Main.PressWClient;
//        this.press_a = Main.PressAClient;
//        this.press_s = Main.PressSClient;
//        this.press_d = Main.PressDClient;
    }
    protected final void motor_player(){
        this.time_sound_motor -= 1;
        if (this.press_w) {
            if (this.time_sound_motor < 0) {
                SoundPlay.sound(Main.ContentSound.motor_back, 1);
                this.time_sound_motor = this.time_max_sound_motor;

            }
            if (this.min_speed < this.speed) {
                this.speed -= this.acceleration;
            }
        }
        if (this.press_s) {
            if (this.time_sound_motor < 0) {
                SoundPlay.sound(Main.ContentSound.motor, 1);
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

        if (this.speed > 0 && !this.press_w && !this.press_s) {
            this.speed -= this.slowing;
            if (this.speed<Transport.speed_minimum){this.speed = 0;}
        } else if (this.speed < 0 && !this.press_w && !this.press_s) {
            this.speed += this.slowing;
            if (this.speed>Transport.speed_minimum){this.speed = 0;}
        }
        move_xy_transport();
    }
    protected final void move_xy_transport(){
        float rotation_corpus2 = (float) (-this.rotation_corpus*3.1415/180);
        this.x += move.move_sin2(this.speed, rotation_corpus2);
        this.y += move.move_cos2(this.speed, rotation_corpus2);
    }
    protected final void move_xy_transport_bot(){
        float rotation_corpus2 = (float) (-this.rotation_corpus*3.1415/180);
        this.x -= move.move_sin2(this.speed, rotation_corpus2);
        this.y -= move.move_cos2(this.speed, rotation_corpus2);
    }
    protected final void tower_bot_enemy(int i) {
        if(this.enemyList.size() != 0) {
            try{
                int i2 = Method.detection_near_transport_i(this.allyList.get(i), this.enemyList);
                this.aim_x = this.enemyList.get(i2).tower_x;
                this.aim_y = this.enemyList.get(i2).tower_y;
                this.rotation_tower = Method.tower(this.tower_x, this.tower_y,this.enemyList.get(i2).tower_x, this.enemyList.get(i2).tower_y, this.rotation_tower, this.speed_tower);}
            catch(Exception ignored){

            }
        }
    }
    protected final void tower_bot() {
        if(this.enemyList.size() != 0) {
            try{
                this.rotation_tower = Method.tower(this.tower_x, this.tower_y,this.aim_x,this.aim_y, this.rotation_tower, this.speed_tower);}
            catch(Exception ignored){

            }
        }
    }

    protected void tower_player() {
        this.rotation_tower = (float) Method.tower_player(this.x_tower_rend+this.tower_width_2,this.y_tower_rend+this.tower_height_2, this.rotation_tower, this.speed_tower);
    }
    protected void tower_player_2() {
        this.rotation_tower = (float) Method.tower_player(RC.width_2, RC.height_2, this.rotation_tower, this.speed_tower);
    }
//    public void tower_player_client(double mouse_x,double mouse_y) {
//        this.rotation_tower = metod.tower(mouse_x,mouse_y,this.x_tower_rend, this.y_tower_rend, this.rotation_tower, this.speed_tower);
//    }

    private void bot_fire(Transport obj_1, ArrayList<Transport>obj_2){
        guidance = reload_bot();
        if (obj_2.size() != 0) {
            int i2 = Method.detection_near_transport_i(obj_1, obj_2);
            this.sost_fire_bot = fire_bot(obj_2.get(i2).tower_x, obj_2.get(i2).tower_y);
        }
    }
    private boolean enemy_fire_not_tower(int i){
        if(this.enemyList.size() != 0) {
            int i2 = Method.detection_near_transport_i(this.allyList.get(i), this.enemyList);
            return fire_bot_not_tower(this.enemyList.get(i2).x,this.enemyList.get(i2).y);
        }
        return false;
    }
    protected void bot_bull_tank_fire_not_tower(int i){
        if(enemy_fire_not_tower(i)){
            SoundPlay.sound( this.sound_fire,1-((float) sqrt(pow2(this.x_rend) + pow2(this.y_rend))/200));
            Main.BulletList.add(new BullTank(this.tower_x,this.tower_y,-this.rotation_corpus+180,this.damage,this.penetration,this.team,this.height));

        }
    }
    protected void bot_bull_tank_fire(Transport obj_1, ArrayList<Transport>obj_2){
        bot_fire(obj_1,obj_2);
        if(this.sost_fire_bot && this.trigger_attack){
            SoundPlay.sound( this.sound_fire,1-((float) sqrt(pow2(this.x_rend) + pow2(this.y_rend))/200));
            this.fire_x = (float) (this.tower_x+this.tower_width_2+((this.tower_height_2+this.y_tower) *sin(-this.rotation_tower*3.1415926535/180)));
            this.fire_y = (float) (this.tower_y+this.tower_height_2+((this.tower_height_2+this.y_tower) *cos(-this.rotation_tower*3.1415926535/180)));
            Main.BulletList.add(new BullTank(this.fire_x,this.fire_y,-this.rotation_tower,this.damage,this.penetration,this.team,this.height));
            PacketBull.add(new BullPacket());
            int i1 = PacketBull.size()-1;
            int i2 = BulletList.size()-1;
            bull_packets(i1,i2);

        }
    }
    protected void blade_helicopter(){
        this.rotation_tower += speed_tower;
    }
    //public void
    protected void bot_fragmentation_bull_fire(Transport obj_1, ArrayList<Transport>obj_2){
        bot_fire(obj_1,obj_2);
        if(this.sost_fire_bot && this.trigger_attack){
            SoundPlay.sound( this.sound_fire,1-((float) sqrt(pow2(this.x_rend) + pow2(this.y_rend))/200));
            this.fire_x = (float) (this.tower_x+this.tower_width_2+((this.tower_height_2+this.y_tower) *sin(-this.rotation_tower*3.1415926535/180)));
            this.fire_y = (float) (this.tower_y+this.tower_height_2+((this.tower_height_2+this.y_tower) *cos(-this.rotation_tower*3.1415926535/180)));
            Main.BulletList.add(new BullMortar(this.fire_x,this.fire_y,-this.rotation_tower+180,this.damage,this.penetration,this.damage_fragment,
                    this.penetration_fragment,this.team,this.height));
            PacketBull.add(new BullPacket());
            int i1 = PacketBull.size()-1;
            int i2 = BulletList.size()-1;
            bull_packets(i1,i2);


        }

    }
    protected void bot_flame_fire(Transport obj_1, ArrayList<Transport>obj_2){
        bot_fire(obj_1,obj_2);
        if(this.sost_fire_bot && this.trigger_attack){
            SoundPlay.sound( this.sound_fire,1-((float) sqrt(pow2(this.x_rend) + pow2(this.y_rend))/200));
            this.fire_x = (float) (this.tower_x+this.tower_width_2+((this.tower_height_2+this.y_tower) *sin(-this.rotation_tower*3.1415926535/180)));
            this.fire_y = (float) (this.tower_y+this.tower_height_2+((this.tower_height_2+this.y_tower) *cos(-this.rotation_tower*3.1415926535/180)));
            Main.BulletList.add(new BullFlame(this.fire_x,this.fire_y,-this.rotation_tower+ -10+rand.rand(20),this.damage,this.t_damage,this.penetration,this.team,this.height));
            Main.BulletList.add(new BullFlame(this.fire_x,this.fire_y,-this.rotation_tower+ -10+rand.rand(20),this.damage,this.t_damage,this.penetration,this.team,this.height));
            PacketBull.add(new BullPacket());
            PacketBull.add(new BullPacket());
            int i1 = PacketBull.size()-2;
            int i2 = BulletList.size()-2;
            bull_packets(i1,i2);
            bull_packets(i1+1,i2+1);
        }
    }
    protected void bot_acid_fire(Transport obj_1, ArrayList<Transport>obj_2){
        bot_fire(obj_1,obj_2);
        if(this.sost_fire_bot && this.trigger_attack){
            SoundPlay.sound( this.sound_fire,1-((float) sqrt(pow2(this.x_rend) + pow2(this.y_rend))/200));
            this.fire_x = (float) (this.tower_x+this.tower_width_2+((this.tower_height_2+this.y_tower) *sin(-this.rotation_tower*3.1415926535/180)));
            this.fire_y = (float) (this.tower_y+this.tower_height_2+((this.tower_height_2+this.y_tower) *cos(-this.rotation_tower*3.1415926535/180)));
            Main.BulletList.add(new BullAcid(this.fire_x,this.fire_y, -this.rotation_tower+ -10+rand.rand(20),  this.damage, this.penetration,this.team,this.height));
            Main.BulletList.add(new BullAcid(this.fire_x,this.fire_y, -this.rotation_tower+ -10+rand.rand(20),  this.damage, this.penetration,this.team,this.height));
            PacketBull.add(new BullPacket());
            PacketBull.add(new BullPacket());
            int i1 = PacketBull.size()-2;
            int i2 = BulletList.size()-2;
            bull_packets(i1,i2);
            bull_packets(i1+1,i2+1);
        }
    }
    private void bull_packets(int i1,int i2){
        PacketBull.get(i1).x = this.fire_x;
        PacketBull.get(i1).y = this.fire_y;
        PacketBull.get(i1).rotation = BulletList.get(i2).rotation;
        PacketBull.get(i1).time = BulletList.get(i2).time;
        PacketBull.get(i1).speed = BulletList.get(i2).speed;
        PacketBull.get(i1).height = BulletList.get(i2).height;
        PacketBull.get(i1).type = BulletList.get(i2).type;
        PacketBull.get(i1).team = this.team;
    }

    protected void motor_bot_bypass(int i) {
        if(PlayerList.size() != 0) {

            float[] list = less_hp_bot(allyList, enemyList, allyList,i);
            if(list[1] == 1) {
                bypass_build(Main.BuildingList, this.enemyList,list[0], i,(int)list[2]);
            }
            else if(list[1] == 2){
                bypass_build(Main.BuildingList, this.allyList,list[0], i,(int)list[2]);
            }
            speed_balance();
        }

        //System.out.println(xy[0]+"ss"+xy[1]);
    }
    protected boolean reload_bot(){
        if(this.reload > 0){
            this.reload -= 1;
            return false;
        }
        return true;

    }
    protected void indicator_hp(){
        Render.setColor(Option.hp_2_r_indicator, Option.hp_2_g_indicator, Option.hp_2_b_indicator,1);
        Render.rect(((this.x_rend- Option.const_hp_x_zoom)),((this.y_rend- Option.const_hp_y_zoom)), Option.size_x_indicator_zoom, Option.size_y_indicator_zoom);
        Render.setColor(Option.hp_r_indicator, Option.hp_g_indicator, Option.hp_b_indicator,1);
        Render.rect(((this.x_rend- Option.const_hp_x_zoom)),((this.y_rend- Option.const_hp_y_zoom)),(int)(green_len* Main.Zoom), Option.size_y_indicator_zoom);
    }
    protected void indicator_hp_2(){
        Render.setColor(Option.hp_2_r_indicator, Option.hp_2_g_indicator, Option.hp_2_b_indicator,1);
        Render.rect(((this.x_rend- Option.const_hp_x_zoom)),((this.y_rend- Option.const_hp_y_zoom)), Option.size_x_indicator_zoom, Option.size_y_indicator_zoom);
        if(this.crite_life == 0){
            Render.setColor(Option.hp_r_indicator, Option.hp_g_indicator, Option.hp_b_indicator,1);
            Render.rect(((this.x_rend- Option.const_hp_x_zoom)),((this.y_rend- Option.const_hp_y_zoom)),(int)(green_len* Main.Zoom), Option.size_y_indicator_zoom);
            }
        else{
            Render.setColor(Option.hp_crite_r_indicator, Option.hp_crite_g_indicator, Option.hp_crite_b_indicator,1);
            Render.rect(((this.x_rend- Option.const_hp_x_zoom)),((this.y_rend- Option.const_hp_y_zoom)),(int)(green_len* Main.Zoom), Option.size_y_indicator_zoom);
        }
    }
    protected void indicator_reload(){
        green_len_reload = (this.reload/this.reload_max)* Option.size_x_indicator;
        Render.setColor(Option.reload_r_indicator, Option.reload_g_indicator, Option.reload_b_indicator,1);
        Render.rect((this.x_rend- Option.const_reload_x_zoom),(this.y_rend- Option.const_reload_y_zoom), Option.size_x_indicator_zoom, Option.size_y_indicator_zoom);
        Render.setColor(Option.reload_2_r_indicator, Option.reload_2_g_indicator, Option.reload_2_b_indicator,1);
        Render.rect((this.x_rend- Option.const_reload_x_zoom),(this.y_rend- Option.const_reload_y_zoom),(int)(green_len_reload* Main.Zoom), Option.size_y_indicator_zoom);
    }
    protected void fire_player_bull_tank(){
        if(reload_bot() && this.left_mouse){
            for (Transport transport : this.enemyList) {
                if (sqrt(pow2(this.x - transport.x) + pow2(this.y - transport.y)) < transport.range_see_2) {
                    transport.time_trigger_bull_bot = transport.time_trigger_bull;
                }
            }
            SoundPlay.sound( this.sound_fire,1-((float) sqrt(pow2(this.x_rend) + pow2(this.y_rend))/SoundConst));
            this.fire_x = (float) (this.tower_x+this.tower_width_2+((this.tower_height_2+this.y_tower) *sin(-this.rotation_tower*3.1415926535/180)));
            this.fire_y = (float) (this.tower_y+this.tower_height_2+((this.tower_height_2+this.y_tower) *cos(-this.rotation_tower*3.1415926535/180)));
            this.reload = this.reload_max;
            Main.BulletList.add(new BullTank(this.fire_x,this.fire_y,
                    -this.rotation_tower,this.damage,this.penetration,this.team,(byte)1));
            PacketBull.add(new BullPacket());
            int i1 = PacketBull.size()-1;
            int i2 = BulletList.size()-1;
            bull_packets(i1,i2);
        }
    }
    protected void fire_player_flame(){
        if(reload_bot() && this.left_mouse){

            for (Transport enemy : this.enemyList) {
                if (sqrt(pow2(this.x - enemy.x) + pow2(this.y - enemy.y)) < enemy.range_see_2) {
                    enemy.time_trigger_bull_bot = enemy.time_trigger_bull;
                }
            }
            try {
                SoundPlay.sound(this.sound_fire, 1-((float) sqrt(pow2(this.x_rend) + pow2(this.y_rend))/SoundConst));
            }catch (NoClassDefFoundError e){
                e.printStackTrace();
            }
            this.reload = this.reload_max;
            this.fire_x = (float) (this.tower_x+this.tower_width_2+((this.tower_height_2+this.y_tower) *sin(-this.rotation_tower*3.1415926535/180)));
            this.fire_y = (float) (this.tower_y+this.tower_height_2+((this.tower_height_2+this.y_tower) *cos(-this.rotation_tower*3.1415926535/180)));
            Main.BulletList.add(new BullFlame(this.fire_x,this.fire_y,
                    -this.rotation_tower+ -10+rand.rand(20),this.damage,this.t_damage,this.penetration,this.team,(byte)1));
            Main.BulletList.add(new BullFlame(this.fire_x,this.fire_y,
                    -this.rotation_tower+ -10+rand.rand(20),this.damage,this.t_damage,this.penetration,this.team,(byte)1));
            PacketBull.add(new BullPacket());
            PacketBull.add(new BullPacket());
            int i1 = PacketBull.size()-2;
            int i2 = BulletList.size()-2;
            bull_packets(i1,i2);
            bull_packets(i1+1,i2+1);
        }
    }
    protected void fire_player_fragmentation_bull(){
        if(reload_bot() && this.left_mouse){
            for (Transport transport : this.enemyList) {
                if (sqrt(pow2(this.x - transport.x) + pow2(this.y - transport.y)) < transport.range_see_2) {
                    transport.time_trigger_bull_bot = transport.time_trigger_bull;
                }
            }
            rotation_fire = this.rotation_tower+180;
            SoundPlay.sound( this.sound_fire,1-((float) sqrt(pow2(this.x_rend) + pow2(this.y_rend))/SoundConst));
            this.reload = this.reload_max;
            this.fire_x = (float) (this.tower_x+this.tower_width_2+((this.tower_height_2+this.y_tower) *sin(-this.rotation_tower*3.1415926535/180)));
            this.fire_y = (float) (this.tower_y+this.tower_height_2+((this.tower_height_2+this.y_tower) *cos(-this.rotation_tower*3.1415926535/180)));
            Main.BulletList.add(new BullMortar(this.fire_x,this.fire_y,
                    -rotation_fire,this.damage,this.penetration,this.damage_fragment,this.penetration_fragment,this.team,(byte)1));
            PacketBull.add(new BullPacket());
            int i1 = PacketBull.size()-1;
            int i2 = BulletList.size()-1;
            bull_packets(i1,i2);


        }
    }

    protected void fire_player_acid(){
        if(reload_bot() && this.left_mouse){
            for (Transport transport : enemyList) {
                if (sqrt(pow2(this.x - transport.x) + pow2(this.y - transport.y)) < transport.range_see_2) {
                    transport.time_trigger_bull_bot = transport.time_trigger_bull;
                }
            }
            SoundPlay.sound( this.sound_fire,1-((float) sqrt(pow2(this.x_rend) + pow2(this.y_rend))/SoundConst));
            this.reload = this.reload_max;
            this.fire_x = (float) (this.tower_x+this.tower_width_2+((this.tower_height_2+this.y_tower) *sin(-this.rotation_tower*3.1415926535/180)));
            this.fire_y = (float) (this.tower_y+this.tower_height_2+((this.tower_height_2+this.y_tower) *cos(-this.rotation_tower*3.1415926535/180)));
            Main.BulletList.add(new BullAcid(this.fire_x,this.fire_y,-this.rotation_tower+ -10+rand.rand(20), this.damage,this.penetration,this.team,(byte)1));
            Main.BulletList.add(new BullAcid(this.fire_x,this.fire_y,-this.rotation_tower+ -10+rand.rand(20), this.damage,this.penetration,this.team,(byte)1));
            PacketBull.add(new BullPacket());
            PacketBull.add(new BullPacket());
            int i1 = PacketBull.size()-2;
            int i2 = BulletList.size()-2;
            bull_packets(i1,i2);
            bull_packets(i1+1,i2+1);
        }
    }

    protected void tower_xy(){
    float []xy = Method.tower_xy(this.x,this.y,this.tower_x_const,this.tower_y_const,this.difference,-this.rotation_corpus);
        this.tower_x = xy[0];this.tower_y = xy[1];}
    protected void tower_xy_2(){
        float []xy = Method.tower_xy_2(this.x,this.y,this.tower_x_const,this.tower_y_const,this.difference,this.difference_2,-this.rotation_corpus);
        this.tower_x = xy[0];this.tower_y = xy[1];}
    protected boolean fire_bot(double obj_x,double obj_y){
        g = (float) (atan2(this.tower_y - obj_y,this.tower_x-obj_x ) / 3.1415926535f * 180f);
        g -=90;
        if (abs(g-rotation_corpus)<20 && guidance && trigger_fire) {
            this.reload = this.reload_max;
            return true;
        }
        return false;
    }
    protected boolean fire_bot_not_tower(double obj_x,double obj_y){
        g = (float) (atan2(this.tower_y - obj_y,this.tower_x-obj_x ) / 3.1415926535f * 180f);
        g -=90;
        sost_fire_bot = abs(g-rotation_corpus)<20;
        if(reload_bot() & sost_fire_bot){
            this.reload = this.reload_max;
            return true;
        }
        return false;
    }
    private void motor_bot_base(float g,byte behavior){
        this.time_sound_motor -=1;

        if (this.trigger_drive == 1 && this.crite_life == 0) {
            switch (behavior) {
                case 1:{
                    if (this.speed > this.min_speed) {
                        this.speed -= this.acceleration;
                        if (this.time_sound_motor < 0) {
                            SoundPlay.sound(Main.ContentSound.motor, 1f-((float) sqrt(pow2(this.x_rend) + pow2((float)this.y_rend))/SoundConst));
                            this.time_sound_motor = this.time_max_sound_motor;
                        }
                    }
                }
                case 2:{
                    if (g > distance_target && this.speed < this.max_speed) {
                        this.speed += this.acceleration;
                        if (this.time_sound_motor < 0) {
                            SoundPlay.sound(Main.ContentSound.motor_back, 1f-((float) sqrt(pow2(this.x_rend) + pow2((float)this.y_rend))/SoundConst));
                            this.time_sound_motor = this.time_max_sound_motor;
                        }
                    } else if(this.speed > this.min_speed){
                        this.speed -= this.acceleration;
                        if (this.time_sound_motor < 0) {
                            SoundPlay.sound(Main.ContentSound.motor, 1f-((float) sqrt(pow2(this.x_rend) + pow2((float)this.y_rend))/SoundConst));
                            this.time_sound_motor = this.time_max_sound_motor;
                        }
                    }
                }
                case 3:{
                    if (g > distance_target && this.speed < this.max_speed) {
                        this.speed += this.acceleration;
                        if (this.time_sound_motor < 0) {
                            SoundPlay.sound(Main.ContentSound.motor_back, 1f-((float) sqrt(pow2(this.x_rend) + pow2((float)this.y_rend))/SoundConst));
                            this.time_sound_motor = this.time_max_sound_motor;
                        }
                    } else if (g > distance_target_2 && this.speed > this.min_speed) {
                        this.speed -= this.acceleration;
                        if (this.time_sound_motor < 0) {
                            SoundPlay.sound(Main.ContentSound.motor, 1f-((float) sqrt(pow2(this.x_rend) + pow2((float)this.y_rend))/SoundConst));
                            this.time_sound_motor = this.time_max_sound_motor;
                        }

                    } else {
                        if (this.speed < 0) {
                            this.speed -= this.acceleration;
                            if (this.time_sound_motor < 0) {
                                SoundPlay.sound(Main.ContentSound.motor, 1f-((float) sqrt(pow2(this.x_rend) + pow2(this.y_rend))/SoundConst));
                                this.time_sound_motor = this.time_max_sound_motor;
                            }
                        } else if (this.speed > 0) {
                            this.speed += this.acceleration;
                            if (this.time_sound_motor < 0) {
                                SoundPlay.sound(Main.ContentSound.motor_back, 1f-((float) sqrt(pow2(this.x_rend) + pow2(this.y_rend))/SoundConst));
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
        move_xy_transport_bot();
    }
    private void motor_bot_base(int g,byte behavior){
        this.time_sound_motor -=1;

        if (this.trigger_drive == 1 && this.crite_life == 0) {
            switch (behavior) {
                case 1:{
                    if (this.speed > this.min_speed) {
                        this.speed -= this.acceleration;
                        if (this.time_sound_motor < 0) {
                            SoundPlay.sound(Main.ContentSound.motor, 1f-((float) sqrt(pow2(this.x_rend) + pow2((float)this.y_rend))/SoundConst));
                            this.time_sound_motor = this.time_max_sound_motor;
                        }
                    }
                }
                case 2:{
                    if (g > distance_target && this.speed < this.max_speed) {
                        this.speed += this.acceleration;
                        if (this.time_sound_motor < 0) {
                            SoundPlay.sound(Main.ContentSound.motor_back, 1f-((float) sqrt(pow2(this.x_rend) + pow2((float)this.y_rend))/SoundConst));
                            this.time_sound_motor = this.time_max_sound_motor;
                        }
                    } else if(this.speed > this.min_speed){
                        this.speed -= this.acceleration;
                        if (this.time_sound_motor < 0) {
                            SoundPlay.sound(Main.ContentSound.motor, 1f-((float) sqrt(pow2(this.x_rend) + pow2((float)this.y_rend))/SoundConst));
                            this.time_sound_motor = this.time_max_sound_motor;
                        }
                    }
                }
                case 3:{
                    if (g > distance_target && this.speed < this.max_speed) {
                        this.speed += this.acceleration;
                        if (this.time_sound_motor < 0) {
                            SoundPlay.sound(Main.ContentSound.motor_back, 1f-((float) sqrt(pow2(this.x_rend) + pow2((float)this.y_rend))/SoundConst));
                            this.time_sound_motor = this.time_max_sound_motor;
                        }
                    } else if (g > distance_target_2 && this.speed > this.min_speed) {
                        this.speed -= this.acceleration;
                        if (this.time_sound_motor < 0) {
                            SoundPlay.sound(Main.ContentSound.motor, 1f-((float) sqrt(pow2(this.x_rend) + pow2((float)this.y_rend))/SoundConst));
                            this.time_sound_motor = this.time_max_sound_motor;
                        }

                    } else {
                        if (this.speed < 0) {
                            this.speed -= this.acceleration;
                            if (this.time_sound_motor < 0) {
                                SoundPlay.sound(Main.ContentSound.motor, 1f-((float) sqrt(pow2(this.x_rend) + pow2((float)this.y_rend))/SoundConst));
                                this.time_sound_motor = this.time_max_sound_motor;
                            }
                        } else if (this.speed > 0) {
                            this.speed += this.acceleration;
                            if (this.time_sound_motor < 0) {
                                SoundPlay.sound(Main.ContentSound.motor_back, 1f-((float) sqrt(pow2(this.x_rend) + pow2((float)this.y_rend))/SoundConst));
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
    private void rotation_bot(float g) {

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
        if (abs(g-rotation_corpus)<20 && this.crite_life == 0){
            this.trigger_drive = 1;
        }
        else{
            this.trigger_drive = 0;
        }
    }
    private void motor_bot_base() {
        this.time_sound_motor -= 1;
        if (this.trigger_drive == 1 && this.crite_life == 0) {
            if (this.speed < this.max_speed) {
                this.speed += this.acceleration;
                if (this.time_sound_motor < 0) {
                    SoundPlay.sound(Main.ContentSound.motor, 1f-((float) sqrt(pow2(this.x_rend) + pow2((float)this.y_rend))/SoundConst));
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

    private void bypass_build(ArrayList<Building> obj_building, ArrayList<Transport> obj_tr, float g, int i3, int i2) {
        if(obj_building.size()!= 0&& this.enemyList.size()!=0) {
            if (ai_sost == 0) {

                if (null != findIntersection(this.tower_x, this.tower_y, obj_tr.get(i2).tower_x, obj_tr.get(i2).tower_y)) {
                    path.clear();
                    float[] xy = Method.tower_xy_2(this.x, this.y, this.ai_x_const, this.ai_y_const, 0, 0, -this.rotation_corpus);
                    Ai.pathAIAStar(this.allyList.get(i3), obj_tr.get(i2), xy[0], xy[1]);
                    trigger_fire = false;
                } else {
                    path.clear();
                    trigger_fire = true;
                }
            }if(path.size() > 0) {
                float []xy = Method.tower_xy_2(this.x,this.y,this.ai_x_const,this.ai_y_const,0,0,-this.rotation_corpus);
                this.g = (float) sqrt(pow2((xy[0] - BlockList2D.get(path.get(0)[1]).get(path.get(0)[0]).x_center)) + pow2(xy[1] - BlockList2D.get(path.get(0)[1]).get(path.get(0)[0]).y_center));
                float gr = (float) ((atan2(xy[1] - BlockList2D.get(path.get(0)[1]).get(path.get(0)[0]).y_center,xy[0] - BlockList2D.get(path.get(0)[1]).get(path.get(0)[0]).x_center)/3.1415926535*180)-90);
                rotation_bot(gr);
                motor_bot_base();
                if(this.g< 70){
                    path.remove(0);
                }
            }
            else{
                float rad = (float) sqrt(pow2((x - obj_tr.get(i2).x)) + pow2(y - obj_tr.get(i2).y));
                rotation_bot(g);
                motor_bot_base(rad, this.behavior);
            }
        }
    }
    private void bypass_build_not_tower(ArrayList<Building> obj_building, ArrayList<Transport> obj_tr, float g, int i3, int i2) {
        if(obj_building.size()!= 0) {
            if (ai_sost == 0) {
                if (null != findIntersection(this.tower_x, this.tower_y, obj_tr.get(i2).tower_x, obj_tr.get(i2).tower_y)) {
                    path.clear();
                    float[] xy = Method.tower_xy_2(this.x, this.y, this.ai_x_const, this.ai_y_const, 0, 0, -this.rotation_corpus);
                    Ai.pathAIAStar(this.allyList.get(i3), obj_tr.get(i2), xy[0], xy[1]);
                    trigger_fire = false;
                } else {
                    path.clear();
                    trigger_fire = true;
                }
            }
            if(path.size() > 0) {
                float []xy = Method.tower_xy_2(this.x,this.y,this.ai_x_const,this.ai_y_const,0,0,-this.rotation_corpus);
                this.g = (float) sqrt(pow2((xy[0] - BlockList2D.get(path.get(0)[1]).get(path.get(0)[0]).x_center)) + pow2(xy[1] - BlockList2D.get(path.get(0)[1]).get(path.get(0)[0]).y_center));
                float gr = (float) ((atan2(xy[1] - BlockList2D.get(path.get(0)[1]).get(path.get(0)[0]).y_center,xy[0] - BlockList2D.get(path.get(0)[1]).get(path.get(0)[0]).x_center)/3.1415926535*180)-90);
                rotation_bot(gr);
                motor_bot_base();
                if(this.g< 70){
                    path.remove(0);
                }
            }
            else{
                float rad = (float) sqrt(pow2((x - obj_tr.get(i2).x)) + pow2(y - obj_tr.get(i2).y));
                rotation_bot(g);
                motor_bot_base(rad,this.behavior);
            }
        }
    }
    protected float[] findIntersection(float x0, float y0, float dx, float dy) {
        //float xy_r = (float) atan2(y0-dy, x0-dx);
        float x = x0/width_block;
        float y = y0/height_block;
        dx = dx/width_block;
        dy = dy/height_block;
        float xy_r = (float)(atan2(y-dy, x-dx));
        float speed_x = (float) cos(xy_r)/2;
        float speed_y = (float) sin(xy_r)/2;
//        System.out.println(x+" g "+y);
//        System.out.println(dx+" h "+dy);
//        System.out.println(xy_r);
        if (y > dy) {
            if (x > dx) {
                while (x > dx && y > dy) {
                    x -= speed_x;
                    y -= speed_y;
                    //System.out.println(x+" "+y+" "+speed_x+" "+speed_y+" "+dx+" "+dy+" "+" "+xy_r+" w");
                    //double[]xy = Main.rc.get(0).render_obj(x0,y0);
//                    System.out.println(x+" z "+y);
//                    System.out.println(dx+" vo "+dy);
                    if (BlockList2D.get((int)y).get((int)x).passability) {
                        return new float[]{x, y};
                    }

                }
            } else if(x < dx){
                while (x < dx && y > dy) {
                    x -= speed_x;
                    y -= speed_y;
                    //System.out.println(x+" "+y+" "+speed_x+" "+speed_y+" "+dx+" "+dy+" "+" "+xy_r+" s");
                    //double[]xy = Main.rc.get(0).render_obj(x0,y0);
                    if (BlockList2D.get((int)y).get((int)x).passability) {
                        return new float[]{x, y};
                    }
                }
            }
            else{
                while (y > dy) {
                    y -= speed_y;
                    //System.out.println(x+" "+y+" "+speed_x+" "+speed_y+" "+dx+" "+dy+" "+" "+xy_r+" s");
                    //double[]xy = Main.rc.get(0).render_obj(x0,y0);
                    if (BlockList2D.get((int)y).get((int)x).passability) {
                        return new float[]{x, y};
                    }
                }
            }
        } else if(y < dy){
            if (x > dx) {
                while (x > dx && y < dy) {
                    x -= speed_x;
                    y -= speed_y;
                    //System.out.println(x+" "+y+" "+speed_x+" "+speed_y+" "+dx+" "+dy+" "+" "+xy_r+" x");
                    //double[]xy = Main.rc.get(0).render_obj(x0,y0);
                    if (BlockList2D.get((int)y).get((int)x).passability) {
                        return new float[]{x, y};
                    }
                }
            } else if (x < dx){
                while (x < dx && y < dy) {
                    x -= speed_x;
                    y -= speed_y;

                    // System.out.println(x+" "+y+" "+speed_x+" "+speed_y+" "+dx+" "+dy+" "+" "+xy_r+" z");
                    //double[]xy = Main.rc.get(0).render_obj(x0,y0);
                    if (BlockList2D.get((int)y).get((int)x).passability) {
                        return new float[]{x, y};
                    }
                }
            }
            else {
                while (y < dy) {
                    y -= speed_y;
                    //System.out.println(x+" "+y+" "+speed_x+" "+speed_y+" "+dx+" "+dy+" "+" "+xy_r+" s");
                    //double[]xy = Main.rc.get(0).render_obj(x0,y0);
                    if (BlockList2D.get((int)y).get((int)x).passability) {
                        return new float[]{x, y};
                    }
                }
            }
        }
        else {
            if (x > dx) {
                while (x > dx) {
                    x -= speed_x;
                    //System.out.println(x+" "+y+" "+speed_x+" "+speed_y+" "+dx+" "+dy+" "+" "+xy_r+" x");
                    //double[]xy = Main.rc.get(0).render_obj(x0,y0);
                    if (BlockList2D.get((int)y).get((int)x).passability) {
                        return new float[]{x, y};
                    }
                }
            } else if (x < dx){
                while (x < dx) {
                    x -= speed_x;


                    // System.out.println(x+" "+y+" "+speed_x+" "+speed_y+" "+dx+" "+dy+" "+" "+xy_r+" z");
                    //double[]xy = Main.rc.get(0).render_obj(x0,y0);
                    if (BlockList2D.get((int)y).get((int)x).passability) {
                        return new float[]{x, y};
                    }
                }
            }
        }
        return null;

    }



    private void speed_balance(){
        if(this.speed<this.acceleration && this.speed>-this.acceleration){
            this.speed = 0;
        }
    }
    protected void helicopter_ii(ArrayList<Transport>obj_search, int i3){
        if (this.enemyList.size()!= 0) {
            int[]sp = Method.detection_near_transport_xy_def(this.allyList.get(i3), obj_search);
            g = (float) (atan2(this.y - obj_search.get(sp[0]).y, this.x - obj_search.get(sp[0]).x) / 3.1415926535 * 180);
            g -= 90;
            rotation_bot(g);
            motor_bot_base(sp[1], this.behavior);
            speed_balance();
        }

    }
    private float[] less_hp_bot(ArrayList<Transport>bot, ArrayList<Transport>obj_player, ArrayList<Transport>obj_support, int i){
        int i2;
        if (this.hp > this.max_hp / 3 && this.medic_help == 0) {
            i2 = Method.detection_near_transport_i(bot.get(i), obj_player);
            g = (float) (atan2(this.y - obj_player.get(i2).y, this.x - obj_player.get(i2).x) / 3.1415926535f * 180f);
            g -= 90;
            return new float[]{g,1,i2};

        } else{
            this.behavior_buffer = this.behavior;
            this.behavior = 3;
            this.medic_help = 1;
            if (this.hp >= this.max_hp - 20) {
                this.medic_help = 0;
                this.behavior = this.behavior_buffer;
            }
            int ind = NULL;
            int radius = NULL;
            for (int i3 = 0; i3 < obj_support.size(); i3++) {
                if(this.x != obj_support.get(i3).x && obj_support.get(i3).y != this.y && Objects.equals(obj_support.get(i3).teg_unit, "support")) {
                    double g = sqrt(pow2((this.x - obj_support.get(i3).x)) + pow2(this.y - obj_support.get(i3).y));
                    if (radius == NULL || radius > g) {
                        ind = i3;
                        radius = (int) g;

                    }
                }
            }
            if(ind != NULL){
                g = (float) (atan2(this.y - obj_support.get(ind).y, this.x - obj_support.get(ind).x) / 3.1415926535 * 180);
                g -= 90;
                return new float[]{g,2,ind};
            }
            i2 = Method.detection_near_transport_i(bot.get(i), obj_player);
            g = (float) (atan2(this.y - obj_player.get(i2).y, this.x - obj_player.get(i2).x) / 3.1415926535 * 180);
            g -= 90;
            return new float[]{g,1,ind};
        }

    }
    protected void corpus_corpus(ArrayList<Transport>obj_2){
        boolean z;
        for(int i = 0;i<obj_2.size();i++) {
            z = rectCollision((int)this.x,(int)this.y,(int)this.corpus_width,(int)this.corpus_height,this.rotation_corpus,(int)obj_2.get(i).x,
                    (int)obj_2.get(i).y,(int)obj_2.get(i).corpus_width,(int)obj_2.get(i).corpus_height,obj_2.get(i).rotation_corpus);

            if (z & obj_2.get(i).priority_paint == this.priority_paint) {
                SoundPlay.sound(Main.ContentSound.hit,1-((float) sqrt(pow2(this.x_rend) + pow2((float)this.y_rend))/SoundConst));
                MethodCollision(obj_2.get(i));
                physicCollision(obj_2.get(i));

            }
        }
    }
    protected void corpus_corpus_def_xy(ArrayList<Transport>obj_2){
        boolean z;
        for (Transport transport : obj_2) {
            if (transport.x != this.x && transport.y != this.y) {
                z = rectCollision((int) this.x, (int) this.y, (int) this.corpus_width, (int) this.corpus_height, this.rotation_corpus, (int) transport.x,
                        (int) transport.y, (int) transport.corpus_width, (int) transport.corpus_height, transport.rotation_corpus);

                if (z && transport.priority_paint == this.priority_paint) {
                    SoundPlay.sound(Main.ContentSound.hit, 1f - ((float) sqrt(pow2(this.x_rend) + pow2(this.y_rend)) / SoundConst));
                    MethodCollision(transport);
                    physicCollision(transport);

                }
            }
        }
    }
    private void physicCollision(Transport transport){
        float x = this.x+corpus_width_2;
        float y = this.y+corpus_height_2;
        float[]xy;
        float v = 4;
        float x_2 = transport.x+ transport.corpus_width_2;
        float y_2 = transport.y+ transport.corpus_height_2;
        xy = Method.tower_xy(x,y,0,0,-corpus_height_2,-rotation_corpus);
        float x_1_2 = xy[0];
        float y_1_2 = xy[1];
        xy = Method.tower_xy(x_2,y_2,0,0,-transport.corpus_height_2,-transport.rotation_corpus);
        float x_2_2 = xy[0];
        float y_2_2 = xy[1];
        if(sqrt(pow2(x_1_2 - x_2_2) + pow2(y_1_2 - y_2_2))<(transport.corpus_width_2+corpus_width_2)*1.4){
            xy = Method.tower_xy_2(x_2,y_2,0,0,-transport.corpus_height_3, transport.corpus_width_3,-transport.rotation_corpus);
            float x_2_2_1 = xy[0];
            float y_2_2_1 = xy[1];
            xy = Method.tower_xy_2(x_2,y_2,0,0,-transport.corpus_height_3,-transport.corpus_width_3,-transport.rotation_corpus);
            float x_2_2_2 = xy[0];
            float y_2_2_2 = xy[1];
            xy = Method.tower_xy_2(x,y,0,0,-corpus_height_3,corpus_width_3,-rotation_corpus);
            float x_1_2_1 = xy[0];
            float y_1_2_1 = xy[1];
            xy = Method.tower_xy_2(x,y,0,0,-corpus_height_3,-corpus_width_3,-rotation_corpus);
            float x_1_2_2 = xy[0];
            float y_1_2_2 = xy[1];
            if(sqrt(pow2(x_2_2_1 - x_1_2) + pow2(y_2_2_1 - y_1_2))<(transport.corpus_width_2+corpus_width_2)/1.5) {
                transport.rotation_corpus += (abs(transport.speed) + 1) * v;
            }
            if(sqrt(pow2(x_2_2_2 - x_1_2) + pow2(y_2_2_2 - y_1_2))<(transport.corpus_width_2+corpus_width_2)/1.5) {
                transport.rotation_corpus -= (abs(transport.speed) + 1) * v;
            }
            if(sqrt(pow2(x_1_2_1 - x_2_2) + pow2(y_1_2_1 - y_2_2))<(transport.corpus_width_2+corpus_width_2)/1.5) {
                this.rotation_corpus += (abs(transport.speed) + 1) * v;
            }
            if(sqrt(pow2(x_1_2_2 - x_2_2) + pow2(y_1_2_2 - y_2_2))<(transport.corpus_width_2+corpus_width_2)/1.5) {
                this.rotation_corpus -= (abs(transport.speed) + 1) * v;
            }
            return;
        }
        xy = Method.tower_xy(x,y,0,0,corpus_height_2,-rotation_corpus);
        float x_1_1 = xy[0];
        float y_1_1 = xy[1];
        xy = Method.tower_xy(x_2,y_2,0,0, transport.corpus_height_2,-transport.rotation_corpus);
        float x_2_1 = xy[0];
        float y_2_1 = xy[1];
        if(sqrt(pow2(x_1_1 - x_2_1) + pow2(y_1_1 - y_2_1))<(transport.corpus_width_2+corpus_width_2)*1.2){
            xy = Method.tower_xy_2(x_2,y_2,0f,0f, transport.corpus_height_3, transport.corpus_width_3,-transport.rotation_corpus);
            float x_2_1_1 = xy[0];
            float y_2_1_1 = xy[1];
            xy = Method.tower_xy_2(x_2,y_2,0,0, transport.corpus_height_3,-transport.corpus_width_3,-transport.rotation_corpus);
            float x_2_1_2 = xy[0];
            float y_2_1_2 = xy[1];
            xy = Method.tower_xy_2(x,y,0,0,corpus_height_3,corpus_width_3,-rotation_corpus);
            float x_1_1_1 = xy[0];
            float y_1_1_1 = xy[1];
            xy = Method.tower_xy_2(x,y,0,0,corpus_height_3,-corpus_width_3,-rotation_corpus);
            float x_1_1_2 = xy[0];
            float y_1_1_2 = xy[1];
            if(sqrt(pow2(x_2_1_1 - x_1_1) + pow2(y_2_1_1 - y_1_1))<(transport.corpus_width_2+corpus_width_2)/1.2) {
                transport.rotation_corpus -= (abs(transport.speed) + 1) * v;
            }
            if(sqrt(pow2(x_2_1_2 - x_1_1) + pow2(y_2_1_2 - y_1_1))<(transport.corpus_width_2+corpus_width_2)/1.2) {
                transport.rotation_corpus += (abs(transport.speed) + 1) * v;
            }
            if(sqrt(pow2(x_1_1_1 - x_2_1) + pow2(y_1_1_1 - y_2_1))<(transport.corpus_width_2+corpus_width_2)/1.2) {
                this.rotation_corpus -= (abs(transport.speed) + 1) * v;
            }
            if(sqrt(pow2(x_1_1_2 - x_2_1) + pow2(y_1_1_2 - y_2_1))<(transport.corpus_width_2+corpus_width_2)/1.2) {
                this.rotation_corpus += (abs(transport.speed) + 1) * v;
            }
            return;
        }
        if(sqrt(pow2(x_1_1 - x_2_2) + pow2(y_1_1 - y_2_2))<(transport.corpus_width_2+corpus_width_2)*1.2){
            xy = Method.tower_xy_2(x_2,y_2,0,0,-transport.corpus_height_3, transport.corpus_width_3,-transport.rotation_corpus);
            float x_2_2_1 = xy[0];
            float y_2_2_1 = xy[1];
            xy = Method.tower_xy_2(x_2,y_2,0,0,-transport.corpus_height_3,-transport.corpus_width_3,-transport.rotation_corpus);
            float x_2_2_2 = xy[0];
            float y_2_2_2 = xy[1];
            xy = Method.tower_xy_2(x,y,0,0,corpus_height_3,corpus_width_3,-rotation_corpus);
            float x_1_1_1 = xy[0];
            float y_1_1_1 = xy[1];
            xy = Method.tower_xy_2(x,y,0,0,corpus_height_3,-corpus_width_3,-rotation_corpus);
            float x_1_1_2 = xy[0];
            float y_1_1_2 = xy[1];
            if(sqrt(pow2(x_2_2_1 - x_1_1) + pow2(y_2_2_1 - y_1_1))<(transport.corpus_width_2+corpus_width_2)/1.2) {
                transport.rotation_corpus -= (abs(transport.speed) + 1) * v;
            }
            if(sqrt(pow2(x_2_2_2 - x_1_1) + pow2(y_2_2_2 - y_1_1))<(transport.corpus_width_2+corpus_width_2)/1.2) {
                transport.rotation_corpus += (abs(transport.speed) + 1) * v;
            }
            if(sqrt(pow2(x_1_1_1 - x_2_2) + pow2(y_1_1_1 - y_2_2))<(transport.corpus_width_2+corpus_width_2)/1.2) {
                this.rotation_corpus -= (abs(transport.speed) + 1) * v;
            }
            if(sqrt(pow2(x_1_1_2 - x_2_2) + pow2(y_1_1_2 - y_2_2))<(transport.corpus_width_2+corpus_width_2)/1.2) {
                this.rotation_corpus += (abs(transport.speed) + 1) * v;
            }
        }




    }
    private void damage_temperature(){
        if(this.t > 25){
            this.hp -= this.t/2;
            this.t -=2;
        }
        else if(this.t<-25){
            this.hp += this.t/2;
            this.t +=2;
        }
    }
    protected void transportDeletePlayer(int i, ArrayList<Transport>obj){
        if(this.hp>0)return;
        Main.DebrisList.add(new DebrisTransport(this.x,this.y,this.rotation_corpus,this.speed,this.rotation_inert,
                this.speed_inert, this.corpus_img,this.corpus_width,this.corpus_height,this.type_unit));
        eventDead();
        obj.remove(i);
        EnumerationList = true;
    }
    protected void transportDeleteBot(int i, ArrayList<Transport>obj){
        if(this.hp>0)return;
        if(this.crite_life == 1){
            Main.DebrisList.add(new DebrisTransport(this.x,this.y,this.rotation_corpus,this.speed,this.rotation_inert,this.speed_inert,
                    this.corpus_img,this.corpus_width,this.corpus_height,this.type_unit));
            eventDead();
            obj.remove(i);
            EnumerationList = true;
            return;
        }
        this.crite_life = 1;
        this.hp = this.max_hp/2;

    }
    protected void debrisDelete(int i, ArrayList<Transport>obj){
        if(this.hp<0){
            obj.remove(i);
            EnumerationList = true;
        }
    }
    private void eventDead(){
        for(int i = 0;i<4;i++){
        Main.BangList.add(new Bang(this.x+corpus_width_2 + -20+rand.rand(40),
                this.y+corpus_height_2 +-20+rand.rand(40),5));}
        for(int i = 0;i<5;i++){
            Main.FlameSpawnList.add(new FlameSpawn(this.x + -5+rand.rand(50),this.y + -5+rand.rand(50)));}
        SoundPlay.sound(Main.ContentSound.kill,1-((float) sqrt(pow2(this.x_rend) + pow2(this.y_rend))/SoundConst));
    }
    private boolean rectCollision(int x1, int y1, int width, int height, double rotation,
                                  int x2, int y2, int width2, int height2, double rotation_2){

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
        //Rectangle intersection = area1.getBounds();
        //System.out.println("Прямоугольники пересекаются. Результат: " + intersection);
        return !area1.isEmpty();
    }
    private boolean rect_1_collision(int x1,int y1,int width,int height,double rotation,Area area2){

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
//    protected void build_corpus(ArrayList<Building> building){
//        for (Building value : building) {
//            for (int j = 0; j < value.area_list.size(); j++) {
//                boolean z = rect_1_collision((int) this.x, (int) this.y, (int) this.corpus_width, (int) this.corpus_height, this.rotation_corpus,
//                        value.area_list.get(j));
//                if (z) {
//                    if (this.speed > 2 || this.speed < -2) {
//                        SoundPlay.sound(Main.ContentSound.get(0).break_wooden, 1-((float) sqrt(pow2(this.x_rend) + pow2(this.y_rend))/SoundConst));
//                    }
//                    metod_1(value.xy_area_list.get(j));
//                }
//            }
//        }
//    }
    private static boolean z = false;
    private static int render_x_max,render_x_min,render_y_max,render_y_min;
    protected void build_corpus(int i){
        render_x_max = (int)((x+BorderDetected)/Main.width_block);
        render_x_min = (int)(((x-BorderDetected)/Main.width_block));
        if(render_x_min <0){render_x_min =0;}
        if(render_x_max >RC.block_i_x_max){render_x_max = RC.block_i_x_max;}
        render_y_max = (int)((y+BorderDetected)/Main.height_block);
        render_y_min = (int)((y-BorderDetected)/Main.height_block);
        if(render_y_min <0){render_y_min = 0;}
        if(render_y_max >RC.block_i_y_max){render_y_max = RC.block_i_y_max;}

        for (int iy = render_y_min; iy < render_y_max; iy++) {
            for (int ix = render_x_min; ix < render_x_max; ix++) {
                if (BlockList2D.get(iy).get(ix).passability) {
                    z = rectCollision((int) this.x, (int) this.y, (int) this.corpus_width, (int) this.corpus_height, this.rotation_corpus, BlockList2D.get(iy).get(ix).x, BlockList2D.get(iy).get(ix).y,
                            width_block, height_block, 0);
                    if (z) {
                        if (this.speed > 2 || this.speed < -2) {
                            SoundPlay.sound(Main.ContentSound.break_wooden, 1 - ((float) sqrt(pow2(this.x_rend) + pow2(this.y_rend)) / SoundConst));
                        }
                        MethodCollision(BlockList2D.get(iy).get(ix).x, BlockList2D.get(iy).get(ix).y);
                    }
                }
                else{
                    BlockList2D.get(iy).get(ix).objMap.Collision.collision(allyList.get(i),ix,iy);
                }
            }
        }
    }
    private void MethodCollision(Transport transport){
        if(this.x< transport.x) {
            this.x -= 2;
            transport.x += 2;
            this.speed_inert += transport.speed*0.5;
            transport.speed_inert += this.speed*0.5;
            this.speed *= -0.8;
            transport.speed *= -0.8;
            this.rotation_inert = transport.rotation_corpus;
            transport.rotation_inert = this.rotation_corpus;
        }
        else if(this.x> transport.x) {
            this.x += 2;
            transport.x -= 2;
            this.speed_inert += transport.speed*0.5;
            transport.speed_inert += this.speed*0.5;
            this.speed *= -0.5;
            transport.speed *= -0.7;
            this.rotation_inert = transport.rotation_corpus;
            transport.rotation_inert = this.rotation_corpus;
        }
        if(this.y< transport.y) {
            this.y -= 2;
            transport.y += 2;
        }
        else if(this.y> transport.y) {
            this.y += 2;
            transport.y -= 2;
        }
    }
    private void MethodCollision(int x, int y){
        if(this.x<x) {
            this.x -= 2;
            this.speed *= -0.8;
            this.speed_inert *= -0.8;
        }
        else if(this.x>x) {
            this.x += 2;
            this.speed *= -0.8;
            this.speed_inert *= -0.8;
        }
        if(this.y<y) {
            this.y -= 2;
        }
        else if(this.y>y) {
            this.y += 2;
        }
    }
    protected void move_debris(){
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
    protected void inertia_xy() {
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
    protected void hill_bot(ArrayList<Transport>obj){
        for (Transport transport : obj) {
            if (sqrt(pow2(this.x - transport.x) + pow2(this.y - transport.y)) < 230 && transport.max_hp > transport.hp) {
                transport.hp += this.hill;
                if(transport.hp >= transport.max_hp -20 && transport.crite_life == 1){
                    transport.crite_life = 0;
                }
            }
        }
    }
    protected void bypass_hiller(int i) {
        if(this.allyList.size() > 1) {
            int[] sp = Method.detection_near_transport_i_def(this.allyList, i,this.allyList);
            g = (float) (atan2(this.y - this.allyList.get(sp[0]).y, this.x - this.allyList.get(sp[0]).x) / 3.1415926535 * 180);
            g -= 90;
            bypass_build_not_tower(BuildingList, allyList,g,i,sp[0]);
        }
        speed_balance();
    }
    protected void spawn_soldat(ArrayList<Soldat>soldat){
        this.time_spawn_soldat -= 1;
        if(this.time_spawn_soldat <= 0){
            int z = rand.rand(2);
            this.time_spawn_soldat = this.time_spawn_soldat_max;
            switch(z){
                case 0:{
                    soldat.add(new SoldatBull(this.x,this.y, EnemyList));
                    break;
                }
                case 1:{
                    soldat.add(new SoldatFlame(this.x,this.y, EnemyList));
                    break;
                }
                //case 3->{soldat.add(new soldat_(this.x,this.y));}
            }
        }
    }
    public void HPSynchronization(){
        this.green_len = (float) this.hp /this.max_hp * Option.size_x_indicator;
    }
    public void all_action(int i){
        damage_temperature();
        inertia_xy();
    }
    public void all_action_client(int i){
        HPSynchronization();

    }
    public void all_action_client_1(int i){
        HPSynchronization();
    }
    public void all_action_client_2(int i){
        HPSynchronization();
    }
    public void tower_action_client(int i,int iTower,float x,float y,float rotation,boolean sost_fire_bot){

    }
    public void tower_action_client(int i,int iTower,float x,float y,float rotation,boolean sost_fire_bot,boolean sost_fire_bot_2){

    }
    public void tower_action_client_1(int i,int iTower,float x,float y,float rotation,boolean sost_fire_bot){

    }
    public void tower_action_client_2(int i,int iTower,float x,float y,float rotation,boolean sost_fire_bot){

    }
    public void tower_action(int i,int iTower,float x,float y,float rotation,boolean sost_fire_bot){
        this.update();

    }
    public void tower_action(int i,int iTower,float x,float y,float rotation,boolean sost_atack,boolean sost_fire_bot,float x_fire,float y_fire){
        this.update();

    }

    public void update(){

    }
    protected void center_render(){
        float[]xy = Main.RC.render_objZoom(this.x,this.y);
        this.x_rend = (int)xy[0];
        this.y_rend = (int)xy[1];
        xy = Main.RC.render_objZoom(this.tower_x,this.tower_y);
        this.x_tower_rend = (int)xy[0];
        this.y_tower_rend = (int)xy[1];

    }
    protected void center_corpus_render(){
        float[]xy = Main.RC.render_objZoom(this.x,this.y);
        this.x_rend = (int)xy[0];
        this.y_rend = (int)xy[1];

    }
    public void center_render_tower(){
        float[]xy = Main.RC.render_objZoom(this.tower_x,this.tower_y);
        this.x_tower_rend = (int)xy[0];
        this.y_tower_rend = (int)xy[1];

    }
}

