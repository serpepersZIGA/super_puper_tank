package com.mygdx.game.bull;

import com.mygdx.game.metod.rand;

import com.mygdx.game.main.Main;

public class bull_fragment extends Bull {
    public int x2,y2;

    public bull_fragment(double x, double y, double damage, double penetration, byte type_team){
        this.x = x;
        this.y = y;
        this.type_team = type_team;
        this.damage = damage;
        this.penetration = penetration;
        this.speed_x = rand.rand(-8.0,8.0);
        this.speed_y = rand.rand(-8.0,8.0);
        this.x2 = rand.rand(3,8);
        this.y2 = rand.rand(3,8);
        this.size = this.x2;
        this.size_render = (int)(size*Main.zoom);
        this.r = (float)1/255*236;
        this.g = (float)1/255*124;
        this.b = (float)1/255*38;
        this.time = rand.rand(50,130);
    }
    public void all_action(int i){
        super.bull_move_xy();
        super.corpus_bull(Main.enemy_obj);
        super.corpus_bull(Main.player_obj);
        super.bull_clear_time();
        super.bull_build(Main.build,Main.bull_obj,i);
        super.soldat_bull(Main.soldat_obj);
        this.update();
        super.clear(i);
    }
    public void update(){
        center_render();
        Main.render.setColor(r,g,b,1);
        Main.render.rect(this.x_rend,this.y_rend,(int)(this.x2* Main.zoom),(int)(this.y2* Main.zoom));
    }
}
