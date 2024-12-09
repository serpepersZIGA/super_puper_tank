package Content.Particle;
import com.mygdx.game.main.Main;
import com.mygdx.game.method.rand;
import com.mygdx.game.particle.Particle;

import static Data.DataColor.*;


public class Blood extends Particle {
    public Blood(float x, float y){
        this.x = x;
        this.y = y;
        this.size = 16+rand.rand(8);
        this.speed_x = 0;
        this.speed_y = 0;
        r = BloodR;g = BloodG;b = BloodB;
        this.interval_rise_size = 0.02f;
    }
    public void all_action(int i){
        super.liquid_physic(i,Main.LiquidList);
        super.size_rise_delete(Main.LiquidList,i);
        center_render();
        Main.Render.setColor(r,g,b,1);
        Main.Render.circle((this.x_rend),(this.y_rend),(int)(size*Main.Zoom),(int)(size*Main.Zoom));
    }

}
