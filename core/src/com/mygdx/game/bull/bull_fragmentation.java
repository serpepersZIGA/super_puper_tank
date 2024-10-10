package com.mygdx.game.bull;

import com.mygdx.game.main.Main;

public class bull_fragmentation extends Bull {
    public bull_fragmentation(double x, double y, double rotation, double damage, double penetration, double damage_fragment, double penetration_fragment, byte type_time, byte height){
        this.x = x;this.y = y;
        this.damage = damage;this.penetration = penetration;
        this.speed = 6;
        this.size = 14;
        this.size_render = (int)(size*Main.zoom);
        this.time = 120;
        this.rotation = rotation;
        this.damage_fragment = damage_fragment;
        this.penetration_fragment = penetration_fragment;
        this.amount_fragment = 15;

        this.type_team = type_time;
        this.height = height;
        this.r = (float)1/255*236;
        this.g = (float)1/255*124;
        this.b = (float)1/255*38;
        this.color = new float[]{r,g,b};
    }
    public void all_action(int i){
        super.bull_move();
        super.corpus_bull_mortar(Main.enemy_obj);
        super.corpus_bull_mortar(Main.player_obj);
        super.corpus_bull_mortar(Main.debris);
        super.bull_build_fragment(Main.build,Main.bull_obj);
        super.soldat_bull(Main.soldat_obj);
        super.fragments_create();
        this.update();
        super.clear(i);
    }
    public void update(){
        center_render();
        Main.render.setColor(r,g,b,1);
        Main.render.circle((this.x_rend),(this.y_rend),this.size_render,this.size_render);
    }

}
