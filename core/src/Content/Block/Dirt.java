package Content.Block;

import com.mygdx.game.block.Block;
import com.mygdx.game.block.Update1;
public class Dirt extends Block {
    public Dirt(int x, int y){
        this.x = x;
        this.y = y;
        block_xy();
        this.render_block = new Update1();
    }
    public void all_action(){
        super.update();
    }
}
