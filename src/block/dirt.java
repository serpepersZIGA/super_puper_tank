package block;

import metod.rand;
import metod.render_metod;

import java.awt.*;

public class dirt extends Block {
    public dirt(int x,int y){
        this.x = x;
        this.y = y;
        int z = rand.rand(1,3);
        this.width = 52;
        this.height = 52;

        switch (z) {
            case 1->{this.img = render_metod.image_incilization("src/image/other/dirt.png");}
            case 2->{this.img = render_metod.image_incilization("src/image/other/dirt_3.png");}
            case 3->{this.img = render_metod.image_incilization("src/image/other/dirt_4.png");}
            //case 4->{this.img = metod.render_metod.image_incilization("src/image/other/block.dirt.png");}
        }
    }
    public void all_action(Graphics2D g){
        super.update(g);
    }
}
