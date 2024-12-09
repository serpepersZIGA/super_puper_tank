package Content.Particle;

import com.mygdx.game.method.rand;
import com.mygdx.game.main.Main;
import com.mygdx.game.particle.Particle;

import static Data.DataColor.*;

public class FlameParticle extends Particle {
    public FlameParticle(float x, float y){
        this.x = x;
        this.y = y;
        this.time_delete = 25+rand.rand(20);
        this.size = 5+rand.rand(5);
        this.size_render = (int)(size*Main.Zoom);
        this.speed_x = -6+rand.rand(12);
        this.speed_y = -6+rand.rand(12);
        this.r = FlameR;this.g = FlameG;this.b = FlameB;
    }
    public void all_action(int i){
        super.color_fire();
        super.move_particle();
        this.update();
        super.timer(i, Main.FlameParticleList);
    }
    public void update(){
        center_render();
        Main.Render.setColor(r,g,b,0.4f);
        Main.Render.circle(this.x_rend,this.y_rend,size_render,size_render);
    }
}
