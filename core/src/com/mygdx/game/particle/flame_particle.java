package com.mygdx.game.particle;

import com.mygdx.game.metod.rand;

import java.awt.*;

import com.mygdx.game.main.Main;
public class flame_particle extends particle {
    public flame_particle(double x,double y){
        this.x = x;
        this.y = y;
        this.time_delete = rand.rand(25,45);
        this.size = rand.rand(5,10);
        this.size_render = (int)(size*Main.zoom);
        this.speed_x = rand.rand(-6,6);
        this.speed_y = rand.rand(-6,6);
        this.r = (float)1/255*236;this.g = (float)1/255*124;this.b = (float)1/255*38;
    }
    public void all_action(int i){
        super.color_fire();
        super.move_particle();
        this.update();
        super.timer(i, Main.flame_particle_obj);
    }
    public void update(){
        center_render();
        //System.out.println(r);
        Main.render.setColor(r,g,b,0.4f);
        Main.render.circle(this.x_rend,this.y_rend,size_render,size_render);
    }
}
