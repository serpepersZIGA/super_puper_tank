package particle;

import main.Main;
import metod.rand;

import java.awt.*;

public class flame_particle extends particle {
    public flame_particle(double x,double y){
        this.x = x;
        this.y = y;
        this.time_delete = rand.rand(25,45);
        this.size = rand.rand(5,10);
        this.speed_x = rand.rand(-6,6);
        this.speed_y = rand.rand(-6,6);
        this.r = 236;this.g = 124;this.b = 38;
        this.color = new Color((int)this.r,(int)this.g,(int)this.b);
    }
    public void all_action(int i){
        super.color_fire();
        super.move_particle();
        super.timer(i, Main.flame_particle_obj);
    }
    public void update(Graphics2D g){
        center_render();
        g.setColor(this.color);
        g.fillOval((int)(this.x_rend/1.24),(int)(this.y_rend/1.24),this.size_render,this.size_render);
    }


}
