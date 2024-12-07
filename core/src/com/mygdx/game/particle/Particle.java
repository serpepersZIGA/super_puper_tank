package com.mygdx.game.particle;

import Content.Particle.Flame;
import Content.Particle.FlameParticle;
import com.mygdx.game.main.Main;
import com.mygdx.game.method.Method;
import com.mygdx.game.method.Sound;
import com.mygdx.game.method.move;
import com.mygdx.game.method.rand;

import java.util.ArrayList;

import static java.lang.StrictMath.pow;
import static java.lang.StrictMath.sqrt;
import static java.sql.Types.NULL;

public abstract class Particle {
    public double x,y,size,size_2,speed_x,speed_y,interval_rise_size,size_3;
    protected float r;
    protected float g;
    protected float b;
    public static float rad;
    protected int time_delete;
    public static int brightness = 200;
    private int sound_time;
    private static final int sound_time_max = 300;
    public static final float r_m =(float)(3.0/255);
    public static final float g_m =(float)(3.0/255);
    public static final float b_m =(float)(1.0/255);
    protected int time_spawn,time_spawn_max;
    public int x_rend,y_rend,size_render;
    public float[]rgb;

    protected void timer(int i, ArrayList<Particle>obj){
        this.time_delete -= 1;
        if(this.time_delete <= 0){
            obj.remove(i);
        }
    }
    protected void spawn_flame(){
        this.time_spawn -= 1;
        if(this.time_spawn <0){
            this.time_spawn = time_spawn_max;
            Main.FlameList.add(new Flame((int)((this.x+ -20+rand.rand(40))),(int)((this.y+-20+rand.rand(40)))));
        }
    }
    protected void sound_play(){
        sound_time +=1;
        if(sound_time_max == sound_time) {
            double[]xy = Main.RC.render_obj(this.x,this.y);
            rad = (float) (sqrt(pow(xy[0],2)+pow(xy[1],2))/-60);
            sound_time = 0;
            if(rad>-80) {
                Sound.sound(Main.SA.get(0).flame_sound,rad);
            }
        }
    }
    protected final void grass_delete(){
        int ix = (int) (x/Main.width_block)-1;
        int iy = (int) (y/Main.height_block)-1;
        if(ix >0 & iy >0){Main.BlockList2D.get(iy).get(ix).render_block = Main.UpdateBlockReg.Update2;}
    }
    protected void size_update(){
        this.size_2 = this.size/2;
        this.size_3 = this.size_2-2;
    }
    protected void liquid_physic(int i,ArrayList<Particle>liquid_obj){
         int[]rg= Method.detection_near_particle_xy_def(liquid_obj,i,liquid_obj);
         if(rg[0]!=NULL) {
             double RotationXY = (-rg[1] - 90)*3.1415926535 /180;
             if (rg[0] < this.size_3) {
                 liquid_obj.get(rg[2]).x += move.move_sin2(3, RotationXY);
                 liquid_obj.get(rg[2]).y += move.move_cos2(3, RotationXY);
                 this.x -= move.move_sin2(3, RotationXY);
                 this.y -= move.move_cos2(3, RotationXY);
                 return;
             } else if (rg[0] < this.size && rg[0] > this.size_2) {
                 this.x += move.move_sin2(2, RotationXY);
                 this.y += move.move_cos2(2, RotationXY);
                 return;
             }
         }
    }
    protected void flame_physic(int i,ArrayList<Particle>flame_obj){
        int[]rg= Method.detection_near_particle_xy_def(flame_obj,i,flame_obj);
        if(rg[0]!=NULL) {
            double RotationXY = (-rg[1] - 90)*3.1415926535 /180;
            if (rg[0] < this.size_3) {
                this.x -= move.move_sin2(3, RotationXY);
                this.y -= move.move_cos2(3, RotationXY);
            } else if (rg[0] > this.size_2) {
                this.x += move.move_sin2(2, RotationXY);
                this.y += move.move_cos2(2, RotationXY);
            }
        }
    }
    protected void center_render(){
        double[]xy = Main.RC.render_obj(this.x,this.y);
        this.x_rend = (int)(xy[0]* Main.Zoom);
        this.y_rend = (int)(xy[1]* Main.Zoom);
    }
    protected void size_rise(){
        this.size += this.interval_rise_size;
        this.size_2 = this.size/2;
        this.size_3 = this.size_2/2;
    }
    protected void size_rise_delete(ArrayList<Particle>part, int i){

        this.size -= this.interval_rise_size;
        if(this.size < 4){
            part.remove(i);
        }
        this.size_2 = this.size/2;
        this.size_3 = this.size_2/2;
    }
    protected void color_fire(){
        this.r-= r_m;
        this.g -=g_m;
        this.b -= b_m;
        if(this.r< 0){this.r = 0;}
        if(this.g< 0){this.g = 0;}
        if(this.b< 0){this.b = 0;}
    }
    protected void move_particle(){
        this.x += this.speed_x;
        this.y += this.speed_y;
    }
    protected void create_flame_particle(ArrayList<Particle>obj){
        this.time_spawn -= 1;
        if(this.time_spawn <= 0){
            obj.add(new FlameParticle(this.x,this.y));
            this.time_spawn = this.time_spawn_max;
        }
    }
    public void all_action(int i){}
    public void update(){
    }





}
