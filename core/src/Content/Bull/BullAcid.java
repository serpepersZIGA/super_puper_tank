package Content.Bull;

import com.mygdx.game.bull.Bull;
import com.mygdx.game.method.rand;
import com.mygdx.game.main.Main;

public class BullAcid extends Bull {
    public BullAcid(double x, double y, double rotation, double damage, double penetration, byte type_team, byte height){
        this.x = x;
        this.y = y;
        this.rotation = rotation;
        this.damage = damage;
        this.penetration = penetration;
        this.type_team = type_team;
        this.height = height;
        r = (float) 1 /255*51;g =  (float) 1 /255*179; b = (float) 1 /255*51;
        this.size = 8+rand.rand(8);
        this.size_render = (int)(size*Main.Zoom);
        this.speed = -5;
        this.time = 65+rand.rand(15);
        type = 4;
        speed_save();

    }
    public void all_action(int i){
        super.bull_move_xy();
        super.corpus_bull(Main.EnemyList);
        super.corpus_bull(Main.PlayerList);
        super.corpus_bull(Main.DebrisList);
        super.bull_build_acid(Main.BuildingList);
        super.soldat_bull(Main.SoldatList);
        super.bull_clear_time_acid();
        this.update();
        super.clear(i);
    }

    public void all_action_client(int i){
        super.bull_move_xy();
        super.corpus_bull(Main.EnemyList);
        super.corpus_bull(Main.PlayerList);
        super.corpus_bull(Main.HelicopterList);
        super.corpus_bull(Main.DebrisList);
        super.bull_build_acid(Main.BuildingList);
        super.soldat_bull(Main.SoldatList);
        super.bull_clear_time_acid();
        this.update();
        super.clear(i);
    }

    public void update(){
        center_render();
        Main.Render.setColor(r,g,b,1);
        Main.Render.circle(this.x_rend,this.y_rend,this.size_render,this.size_render);
    }
}
