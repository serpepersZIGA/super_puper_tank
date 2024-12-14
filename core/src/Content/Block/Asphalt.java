package Content.Block;

import com.mygdx.game.block.Block;
import com.mygdx.game.block.Update1;
import com.mygdx.game.main.Main;
import com.mygdx.game.method.RenderMethod;

import static com.mygdx.game.main.Main.ContentImage;

public class Asphalt extends Block {
    public Asphalt(int x, int y){
        this.x = x;
        this.y = y;
        block_xy();
        this.render_block = new Update1();
    }
    public void all_action(){
        super.render();
    }
    public void render(int x,int y){
        RenderMethod.transorm_img(x, y, Main.width_block_zoom, Main.height_block_zoom, ContentImage.grass);
    }
}
