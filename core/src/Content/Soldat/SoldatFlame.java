package Content.Soldat;
import com.mygdx.game.main.Main;
import com.mygdx.game.soldat.Soldat;

public class SoldatFlame extends Soldat {
    public SoldatFlame(float x, float y){
        this.name = "bull";
        this.x = x;
        this.y = y;
        this.speed = 3;
        this.speed_rotation = 5;
        this.width = 22;
        this.height = 22;
        this.soldat_image = Main.ContentImage.soldat_1;
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
        super.collision_soldat(Main.SoldatList,i);
        super.collision_build(Main.BuildingList);
        super.collision_transport(Main.EnemyList);
        super.hustle(Main.PlayerList);
        super.clear(Main.SoldatList,i);
    }
    public void all_action_client(int i) {
        super.all_action(i);
        super.hustle(Main.PlayerList);
        super.clear(Main.SoldatList,i);
    }
}
