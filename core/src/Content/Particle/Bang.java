package Content.Particle;

import com.mygdx.game.main.Main;
import com.mygdx.game.method.rand;
import com.mygdx.game.particle.Particle;


public class Bang extends Particle {
    public Bang(double x, double y, double size){
        this.x = x;
        this.y = y;
        this.size = size;
        this.size_render = (int)(size*Main.Zoom);
        this.interval_rise_size = 12;
        this.time_delete = 30;
        this.r = (float)1/255*(160+rand.rand(70));
        this.g = (float)1/255*(90+rand.rand(60));
        this.b = (float)1/255*(20+rand.rand(3));
    }
    public void all_action(int i) {
        size_rise();
        center_render();
        Main.Render.setColor(r,g,b,(float)0.4);
        Main.Render.circle(this.x_rend,this.y_rend,(int)(size* Main.Zoom),(int)(size* Main.Zoom));
        timer(i, Main.BangList);
    }

}
