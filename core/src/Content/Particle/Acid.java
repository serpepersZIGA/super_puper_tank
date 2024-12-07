package Content.Particle;
import com.mygdx.game.main.Main;
import com.mygdx.game.method.rand;
import com.mygdx.game.particle.Particle;

import static Data.DataColor.*;


public class Acid extends Particle {
    public Acid(double x, double y){
        this.x = x;
        this.y = y;
        this.size = 16+rand.rand(8);
        //this.size_render = (int)(size*Main.zoom);
        this.speed_x = 0;
        this.speed_y = 0;
        r = AcidR; g = AcidG; b = AcidB;
        this.interval_rise_size = 0.02;


    }
    public void all_action(int i){
        super.liquid_physic(i,Main.LiquidList);
        super.size_update();
        center_render();
        Main.Render.setColor(r,g,b,0.1f);
        Main.Render.circle(this.x_rend,this.y_rend,(int)(size*Main.Zoom),(int)(size*Main.Zoom));
        size_rise_delete(Main.LiquidList,i);
    }

}
