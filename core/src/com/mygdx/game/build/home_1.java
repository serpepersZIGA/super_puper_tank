package com.mygdx.game.build;
import com.mygdx.game.main.Main;

import com.mygdx.game.metod.render_metod;

public class home_1 extends Build {
    public home_1(int x,int y,double rotation){
        this.x = x;
        this.y = y;
        this.rotation = rotation;
        this.build_image = Main.content_base.build_1;
        this.width = 100;
        this.height = 50;
        this.width_2 = this.width/2;
        this.height_2 = this.height/2;
        this.time_flame = 0;
        super.data();
        create_rect(this.x,this.y,width,height);

    }
    public void all_action(int i) {
        super.all_action(i);
        super.flame_build(Main.flame_static_obj);

    }
    public void update(){
        center_render();
        render_metod.transorm_img(this.x_rend,this.y_rend,this.width_render,this.height_render,this.rotation,this.build_image);
    }
}
