package particle;

import main.Game;
import main.Main;
import metod.metod;
import metod.move;

import java.awt.*;
import java.util.ArrayList;

import static java.sql.Types.NULL;
import static main.display.correct_int;

public abstract class particle {
    public double x,y,size,size_2,speed_x,speed_y,r,g,b,interval_rise_size,size_3,v;
    public int time_delete, time_spawn,time_spawn_max,x_rend,y_rend,size_render;
    public Color color;

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
        double[]xy = Main.rc.get(0).render_obj(this.x*correct_int,this.y*correct_int);
        this.x_rend = (int)(xy[0]* Game.zoom);
        this.y_rend = (int)(xy[1]* Game.zoom);
        this.size_render = Main.rc.get(0).render_size(this.size);
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
        this.r-= 2.2;
        this.g -=2.2;
        this.b -= 1.2;
        if(this.b< 0){this.b = 0;}
        if(this.g< 0){this.g = 0;}
        if(this.r< 0){this.r = 0;}
        this.color = new Color((int)r,(int)g,(int)b);
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
    public void update(Graphics2D g){
    }





}
