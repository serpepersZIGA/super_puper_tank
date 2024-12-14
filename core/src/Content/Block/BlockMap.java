package Content.Block;

import com.mygdx.game.block.Block;
import com.mygdx.game.block.UpdateRegister;

public class BlockMap extends Block {
    public BlockMap(int x, int y){
        this.x = x;
        this.y = y;
        block_xy();
        this.render_block = UpdateRegister.GrassUpdate;
    }
    public void all_action(){
        super.update();
    }
}
