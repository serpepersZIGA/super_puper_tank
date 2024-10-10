package com.mygdx.game.particle;

import com.mygdx.game.metod.rand;

import com.mygdx.game.main.Main;

public class flame extends particle {
    public flame(double x, double y){
        this.x = x;
        this.y = y;
        this.size = rand.rand(14,24);
        this.size_render = (int)(size*Main.zoom);
        this.time_delete = 60;
        this.time_spawn_max = 60;
        this.time_spawn = this.time_spawn_max;
        super.size_update();
        int z =  rand.rand(1,3);
        switch(z){
            case 1:{r = (float)1/255*236;g = (float)1/255*124;b = (float)1/255*38;}
            case 2:{r = (float)1/255*220;g = (float)1/255*120;b = (float)1/255*30;}
            case 3:{r = (float)1/255*250;g = (float)1/255*140;b = (float)1/255*43;}
        }

        this.color = new float[]{r,g,b};
    }
    public void all_action(int i){
        //super.flame_physic(i, Main.flame_obj);
        super.create_flame_particle(Main.flame_particle_obj);
        this.update();
        super.timer(i, Main.flame_obj);

    }
    public void update(){
        center_render();
        Main.render.setColor(r,g,b,1);
        Main.render.circle(this.x_rend,this.y_rend,size_render,size_render);
    }

}
