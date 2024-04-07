package particle;

import main.Main;
import metod.rand;

import java.awt.*;

import static main.display.correct_int;

public class blood extends particle {
    public blood(double x,double y){
        this.x = x;
        this.y = y;
        this.size = rand.rand(16,24);
        this.speed_x = 0;
        this.speed_y = 0;
        this.color = new Color(150,30,20);
        this.interval_rise_size = -0.02;
    }
    public void all_action(int i){
        super.liquid_physic(i,Main.liquid_obj);
        super.size_rise_delete(Main.liquid_obj,i);
    }
    public void update(Graphics2D g){
        center_render();
        g.setColor(this.color);
        g.fillOval((int)(this.x_rend/correct_int),(int)(this.y_rend/correct_int),this.size_render,this.size_render);
    }

}
