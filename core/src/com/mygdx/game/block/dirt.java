package com.mygdx.game.block;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.main.Main;
import com.mygdx.game.metod.rand;
import com.mygdx.game.metod.render_metod;

import java.awt.*;

public class dirt extends Block {
    public dirt(int x,int y){
        this.x = x;
        this.y = y;
        int z = rand.rand(1,3);
        this.width = 62;
        this.height = 62;
        block_xy();



//        switch (z) {
//            case 1:this.img = Main.content_base.dirt_1;
//            case 2:this.img = Main.content_base.dirt_2;
//            case 3:this.img = Main.content_base.dirt_3;
//        }
        if(z== 1){this.img = Main.content_base.dirt_1;}
        else if(z== 2){this.img = Main.content_base.dirt_2;}
        else if(z== 3){this.img = Main.content_base.dirt_3;}
    }
    public void all_action(){
        super.update();
    }
}
