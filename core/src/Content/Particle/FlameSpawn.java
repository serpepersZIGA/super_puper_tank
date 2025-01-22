package Content.Particle;
import com.mygdx.game.block.Block;
import com.mygdx.game.method.rand;
import com.mygdx.game.main.Main;
import com.mygdx.game.particle.Particle;

import static Data.DataColor.RGBFlame;

public class FlameSpawn extends Particle {
    public FlameSpawn(float x, float y){
        this.x = x;
        this.y = y;
        this.time_delete = 400;
        grass_delete();
        rgb = RGBFlame;



    }
    public void all_action(int i){
        spawn_flame();
        sound_play();
        center_render();
        Block.LightingAir(x_rend,y_rend,rgb);
        timer(i,Main.FlameSpawnList);
    }
}