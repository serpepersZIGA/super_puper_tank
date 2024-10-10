package com.mygdx.game.block;

import com.mygdx.game.main.Main;

import java.awt.*;

public class air extends Block {
    public air(int x,int y){
        this.width = Main.width_block_air;this.height = Main.height_block_air;
        this.x = x;
        this.y = y;
    }
    public void all_action(){
        super.update_air();
    }

}
