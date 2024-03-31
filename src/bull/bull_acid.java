package bull;

import bull.Bull;
import main.Main;
import metod.rand;

import java.awt.*;

public class bull_acid extends Bull {
    public bull_acid(double x, double y, double rotation, double damage, double penetration, byte type_team, byte height){
        this.x = x;
        this.y = y;
        this.rotation = rotation;
        this.damage = damage;
        this.penetration = penetration;
        this.type_team = type_team;
        this.height = height;
        this.color = new Color(51, 179, 51);
        this.size = rand.rand(8,16);
        this.speed = -5;
        this.time = rand.rand(65,80);

    }
    public void all_action(int i){
        super.bull_move();
        super.corpus_bull(Main.enemy_obj);
        super.corpus_bull(Main.player_obj);
        super.corpus_bull(Main.helicopter_obj);
        super.corpus_bull(Main.debris);
        super.bull_build(Main.build,Main.bull_obj,i);
        super.bull_build_acid(Main.build,Main.bull_obj);
        super.soldat_bull(Main.soldat_obj);
        super.bull_clear_time_acid(i);
        super.clear(i);
    }
    public void update(Graphics2D g){
        g.setColor(this.color);
        center_render();
        g.fillOval(this.x_rend,this.y_rend,this.size_render,this.size_render);
    }
}
