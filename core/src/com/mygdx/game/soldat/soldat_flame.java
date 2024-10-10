package com.mygdx.game.soldat;
import com.mygdx.game.main.Main;

public class soldat_flame extends Soldat{
    public soldat_flame(double x,double y){
        this.x = x;
        this.y = y;
        this.speed = 3;
        this.speed_rotation = 5;
        this.width = 22;
        this.height = 22;
        this.soldat_image = Main.content_base.soldat_1;
        this.rotation = 0;
        this.size = 10;
        this.time_max = 2;
        this.damage = 8;
        this.team = 2;
        this.penetration = 1;
        this.t_damage = 1;
        data();
    }
    public void all_action(int i) {
        super.all_action(i);
        super.move_soldat_ii_flame(i);
        super.collision_soldat(Main.soldat_obj,i);
        super.collision_build(Main.build);
        super.collision_transport(Main.enemy_obj);
        super.hustle(Main.player_obj);
        super.clear(Main.soldat_obj,i);
    }
}
