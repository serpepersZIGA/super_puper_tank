package com.mygdx.game.particle;

import com.mygdx.game.main.Main;
import com.mygdx.game.metod.metod;
import com.mygdx.game.metod.move;

import java.util.ArrayList;

import static java.sql.Types.NULL;

public abstract class particle {
    public double x,y,size,size_2,speed_x,speed_y,interval_rise_size,size_3,v;
    public float r,g,b,r_m =(float)(3.0/255),g_m =(float)(3.0/255),b_m =(float)(1.0/255);
    public int time_delete, time_spawn,time_spawn_max,x_rend,y_rend,size_render;
    public float[] color;

    public void timer(int i, ArrayList<particle>obj){
        this.time_delete -= 1;
        if(this.time_delete <= 0){
            obj.remove(i);
        }
    }
    public void size_update(){
        this.size_2 = this.size/2;
        this.size_3 = this.size_2/2;
    }
    public void liquid_physic(int i,ArrayList<particle>liquid_obj){
         int[]rg= metod.detection_near_particle_xy_def(liquid_obj,i,liquid_obj);
         if(rg[0]!=NULL) {
             if (rg[0] < this.size_3) {
                 liquid_obj.get(rg[2]).x += move.move_sin(3, -rg[1] - 90);
                 liquid_obj.get(rg[2]).y += move.move_cos(3, -rg[1] - 90);
                 this.x -= move.move_sin(3, -rg[1] - 90);
                 this.y -= move.move_cos(3, -rg[1] - 90);
             } else if (rg[0] < this.size && rg[0] > this.size_2) {
                 this.x += move.move_sin(2, -rg[1] - 90);
                 this.y += move.move_cos(2, -rg[1] - 90);
             }
         }
    }
    public void flame_physic(int i,ArrayList<particle>flame_obj){
        int[]rg= metod.detection_near_particle_xy_def(flame_obj,i,flame_obj);
        if(rg[0]!=NULL) {
            if (rg[0] < this.size_3) {
                this.x -= move.move_sin(3, -rg[1] - 90);
                this.y -= move.move_cos(3, -rg[1] - 90);
            } else if (rg[0] > this.size_2) {
                this.x += move.move_sin(2, -rg[1] - 90);
                this.y += move.move_cos(2, -rg[1] - 90);
            }
        }
    }
    public void center_render(){
        double[]xy = Main.rc.get(0).render_obj(this.x,this.y);
        this.x_rend = (int)(xy[0]* Main.zoom);
        this.y_rend = (int)(xy[1]* Main.zoom);
    }
    public void size_rise(){
        this.size += this.interval_rise_size;
        this.size_2 = this.size/2;
        this.size_3 = this.size_2/2;
    }
    public void size_rise_delete(ArrayList<particle>part,int i){

        this.size += this.interval_rise_size;
        if(this.size < 4){
            part.remove(i);
        }
        this.size_2 = this.size/2;
        this.size_3 = this.size_2/2;
    }
    public void color_fire(){
        this.r-= r_m;
        this.g -=g_m;
        this.b -= b_m;
        if(this.r< 0){this.r = 0;}
        if(this.g< 0){this.g = 0;}
        if(this.b< 0){this.b = 0;}
    }
    public void move_particle(){
        this.x += this.speed_x;
        this.y += this.speed_y;
    }
    public void create_flame_particle(ArrayList<particle>obj){
        this.time_spawn -= 1;
        if(this.time_spawn <= 0){
            obj.add(new flame_particle(this.x,this.y));
            this.time_spawn = this.time_spawn_max;
        }
    }
    public void all_action(int i){}
    public void update(){
    }





}
