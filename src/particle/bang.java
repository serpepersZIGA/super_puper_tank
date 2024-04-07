package particle;

import main.Game;
import main.Main;

import java.awt.*;

import static main.display.correct_int;

public class bang extends particle {
    public bang(double x, double y, Color color,double size){
        this.x = x;
        this.y = y;
        this.color = color;
        this.size = size;
        this.interval_rise_size = 12;
        this.time_delete = 30;
    }
    public void all_action(int i) {
        super.size_rise();
        super.timer(i, Main.bang_obj);
    }
    public void update(Graphics2D g){
        g.setColor(color);
        //this.x -= this.size/2;
        //this.y -= this.size/2;
        center_render();
        //this.x += this.size/2;
        //this.y += this.size/2;
        g.drawOval((int)(((this.x_rend-(this.size_2* Game.zoom))/correct_int)),(int)(((this.y_rend-(this.size_2* Game.zoom))/correct_int)),this.size_render,this.size_render);
    }
}
