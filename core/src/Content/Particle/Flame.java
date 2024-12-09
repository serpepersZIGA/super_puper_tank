package Content.Particle;

import com.mygdx.game.method.rand;

import com.mygdx.game.main.Main;
import com.mygdx.game.particle.Particle;

import static Data.DataColor.*;

public class Flame extends Particle {
    public Flame(float x, float y){
        this.x = x;
        this.y = y;
        this.size = 14+rand.rand(10);
        this.size_render = (int)(size*Main.Zoom);
        this.time_delete = 60;
        this.time_spawn_max = 60;
        this.time_spawn = this.time_spawn_max;
        super.size_update();
        int z =  rand.rand(3);
        switch(z){
            case 0:{r = FlameR;g = FlameG;b = FlameB;break;}
            case 1:{r = FlameR2;g = FlameG2;b = FlameB2;break;}
            case 2:{r = FlameR3;g = FlameG3;b = FlameB3;break;}
        }

    }
    public void all_action(int i){
        //super.flame_physic(i, Main.flame_obj);
        create_flame_particle(Main.FlameParticleList);
        this.update();
        timer(i, Main.FlameList);

    }
    public void update(){
        center_render();
        Main.Render.setColor(r,g,b,1);
        Main.Render.circle(this.x_rend,this.y_rend,size_render,size_render);
    }

}
