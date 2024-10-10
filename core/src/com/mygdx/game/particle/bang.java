package com.mygdx.game.particle;

import com.mygdx.game.main.Main;
import com.mygdx.game.metod.rand;

import java.awt.*;


public class bang extends particle {
    public bang(double x, double y,float[] color,double size){
        this.x = x;
        this.y = y;
        this.color = color;
        this.size = size;
        this.size_render = (int)(size*Main.zoom);
        this.interval_rise_size = 12;
        this.time_delete = 30;
        this.r = (float)1/255*rand.rand(160,230);
        this.g = (float)1/255*rand.rand(90,150);
        this.b = (float)1/255*rand.rand(20,23);
    }
    public void all_action(int i) {
        super.size_rise();
        super.timer(i, Main.bang_obj);
        center_render();
        Main.render.setColor(r,g,b,(float)0.4);
        Main.render.circle(this.x_rend,this.y_rend,(int)(size* Main.zoom),(int)(size* Main.zoom));
    }
}
