package soldat;

import main.Main;

public class soldat_bull extends Soldat{
    public soldat_bull(double x,double y){
        this.x = x;
        this.y = y;
        this.speed = 3;
        this.speed_rotation = 5;
        this.soldat_image = "src/image/infantry/soldat_enemy.png";
        this.width_2 = this.width/2;
        this.height_2 = this.height/2;
        this.rotation = 0;
        this.size = 10;
        this.time_max = 10;
        this.team = 2;
        this.damage = 18;
        this.penetration = 2;
    }
    public void all_action(int i) {
        super.all_action(i);
        super.move_soldat_ii_bull(i);
        super.collision_soldat(Main.soldat_obj,i);
        super.collision_build(Main.build);
        super.collision_transport(Main.enemy_obj);
        super.hustle(Main.player_obj);
        super.clear(Main.soldat_obj,i);
    }
}
