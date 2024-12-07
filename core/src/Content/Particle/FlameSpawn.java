package Content.Particle;
import com.mygdx.game.block.Block;
import com.mygdx.game.method.rand;
import com.mygdx.game.method.Sound;
import com.mygdx.game.main.Main;
import com.mygdx.game.particle.Particle;

import java.util.ArrayList;

import static Data.DataColor.RGBFlame;
import static com.mygdx.game.main.Main.BlockList2D;
import static java.lang.StrictMath.pow;
import static java.lang.StrictMath.sqrt;

public class FlameSpawn extends Particle {
    public FlameSpawn(double x, double y){
        this.x = x;
        this.y = y;
        time_spawn_max = 5;
        this.time_delete = 400;
        this.time_spawn = time_spawn_max;
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