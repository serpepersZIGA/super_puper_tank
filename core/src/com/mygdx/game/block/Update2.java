package com.mygdx.game.block;

import com.mygdx.game.main.Main;
import com.mygdx.game.method.RenderMethod;

import static com.mygdx.game.main.Main.ContentImage;

public class Update2 extends UpdateBlock {
    public void render(int x,int y){
        RenderMethod.transorm_img(x, y, Main.width_block_zoom, Main.height_block_zoom, ContentImage.dirt_2);
    }
}
