package particle;

import main.Main;
import metod.rand;

import java.awt.*;

import static main.display.correct_int;

public class flame extends particle {
    public flame(double x, double y){
        this.x = x;
        this.y = y;
        this.size = rand.rand(14,24);
        this.size_2 = this.size/2;
        this.time_delete = 60;
        this.time_spawn_max = 60;
        this.time_spawn = this.time_spawn_max;
        super.size_update();
        byte z = (byte) rand.rand(1,3);
        switch(z){
            case 1->{this.r = 236;this.g = 124;this.b = 38;}
            case 2->{this.r = 220;this.g = 120;this.b = 30;}
            case 3->{this.r = 250;this.g = 140;this.b = 43;}
        }

        this.color = new Color((int)this.r,(int)this.g,(int)this.b);
    }
    public void all_action(int i){
        super.flame_physic(i, Main.flame_obj);
        super.create_flame_particle(Main.flame_particle_obj);
        super.timer(i, Main.flame_obj);

    }
    public void update(Graphics2D g){
        center_render();
        g.setColor(this.color);
        g.fillOval((int)(this.x_rend/correct_int),(int)(this.y_rend/correct_int),this.size_render,this.size_render);
    }

}
