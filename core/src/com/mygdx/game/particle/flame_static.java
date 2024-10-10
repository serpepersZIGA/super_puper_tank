package com.mygdx.game.particle;
import com.mygdx.game.main.Main;
import com.mygdx.game.metod.rand;


public class flame_static extends particle {
    public flame_static(double x, double y){
        this.x = x;
        this.y = y;
        this.size = rand.rand(14,24);
        this.size_render = (int)(size*Main.zoom);
        this.size_2 = this.size/2;
        this.time_delete = 60;
        this.time_spawn_max = 60;
        this.time_spawn = this.time_spawn_max;
        byte z = (byte)rand.rand(1,3);
        switch(z){
            case 1:{this.r = (float)1/255*236;this.g = (float)1/255*124;this.b = (float)1/255*38;}
            case 2:{this.r = (float)1/255*220;this.g = (float)1/255*120;this.b = (float)1/255*30;}
            case 3:{this.r = (float)1/255*250;this.g = (float)1/255*140;this.b = (float)1/255*43;}
        }
    }
    public void all_action(int i){
        super.create_flame_particle(Main.flame_particle_obj);
        this.update();
        super.timer(i, Main.flame_static_obj);

    }
    public void update(){
        center_render();
        Main.render.setColor(r,g,b,1);
        Main.render.circle(this.x_rend,this.y_rend,size_render,size_render);
    }

}
