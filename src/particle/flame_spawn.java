package particle;

import main.Game;
import main.Main;
import metod.rand;
import metod.sound;

import java.util.ArrayList;

import static java.lang.StrictMath.pow;
import static java.lang.StrictMath.sqrt;
import static main.display.correct_int;

public class flame_spawn {
    public int time,time_max,time_delete,time_sound,brightness;
    public double x,y,g,x_rend,y_rend;
    public flame_spawn(double x, double y){
        this.x = x;
        this.y = y;
        this.time_max = 5;
        this.time_delete = 400;
        this.time = this.time_max;
        this.time_sound = 60;
        this.brightness = 240;

    }
    public void all_action(int i){
        spawn_flame();
        clear_obj(i,Main.flame_spawn);
        sound_play();
        center_render();
    }
    public void spawn_flame(){
        this.time -= 1;
        if(this.time <0){
            this.time = this.time_max;
            Main.flame_obj.add(new flame((int)((this.x+ rand.rand(-20,20))/correct_int),(int)((this.y+rand.rand(-20,20))/correct_int)));
        }
    }
    public void clear_obj(int i, ArrayList<flame_spawn>obj){
        this.time_delete -= 1;
        if(this.time_delete < 0){
            obj.remove(i);
        }
    }
    public void sound_play(){
        if(rand.rand(0,300)== 1) {
            double[]xy = Main.rc.get(0).render_obj(this.x,this.y);
            g = sqrt(pow(xy[0],2)+pow(xy[1],2))/-60;
            if(g>-80) {
                sound.sound(Main.sa.get(0).flame_sound, (float) (g));
            }
        }
    }
    public void center_render(){
        double[]xy = Main.rc.get(0).render_obj(this.x,this.y);
        this.x_rend = (int)(xy[0]/correct_int* Game.zoom);
        this.y_rend = (int)(xy[1]/correct_int* Game.zoom);
    }
}