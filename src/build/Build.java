package build;

import main.Main;
import metod.rand;
import particle.flame_static;
import particle.particle;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class Build implements Serializable {
    public double rotation;
    public int width,height,x,y,time_flame,width_2,height_2,x_rend,y_rend,width_render,height_render,brightness_max = 240,brightness;
    ;
    //public byte flame_sost;
    public void all_action(int i){
        this.update();
    }
    public void update(){
    }
    public void center_render(){
        double[]xy = Main.rc.get(0).render_obj(this.x,this.y);
        this.x_rend = (int)xy[0];
        this.y_rend = (int)xy[1];
    }


    public void flame_build(ArrayList<particle>part){
        if(this.time_flame>0){
            this.brightness = brightness_max;
            if(rand.rand(1,5)== 1){
                int z = rand.rand(1,4);
                this.time_flame -= 1;
                switch (z){
                    case 1->{part.add(new flame_static(this.x/1.23+rand.rand(-this.width_2,this.width_2),this.y/1.23+this.height_2));}
                    case 2->{part.add(new flame_static(this.x/1.23+rand.rand(-this.width_2,this.width_2),this.y/1.23-this.height_2));}
                    case 3->{part.add(new flame_static(this.x/1.23+this.width_2,this.y/1.23+rand.rand(-this.height_2,this.height_2)));}
                    case 4->{part.add(new flame_static(this.x/1.23-this.width_2/2,this.y/1.23+rand.rand(-this.height_2,this.height_2)));}
                }
            }
        }
        else{
            this.brightness = 0;
        }
    }

}
