package bull;

import bull.Bull;
import main.Main;

import java.awt.*;

public class bull_fragmentation extends Bull {
    public bull_fragmentation(double x, double y, double rotation, double damage, double penetration, double damage_fragment, double penetration_fragment, byte type_time, byte height){
        this.x = x;this.y = y;
        this.damage = damage;this.penetration = penetration;
        this.speed = 6;
        this.size = 14;
        this.time = 120;
        this.rotation = rotation;
        this.damage_fragment = damage_fragment;
        this.penetration_fragment = penetration_fragment;
        this.amount_fragment = 15;

        this.type_team = type_time;
        this.height = height;
        this.color = new Color(236,124,38);
    }
    public void all_action(int i){
        super.bull_move();
        super.corpus_bull_mortar(Main.enemy_obj);
        super.corpus_bull_mortar(Main.player_obj);
        super.corpus_bull(Main.debris);
        super.bull_build_fragment(Main.build,Main.bull_obj);
        super.soldat_bull(Main.soldat_obj);
        super.fragments_create(i);
        super.clear(i);
    }
    public void update(Graphics2D g){
        g.setColor(this.color);
        center_render();
        g.fillOval(this.x_rend,this.y_rend,this.size_render,this.size_render);
    }

}
