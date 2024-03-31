package bull;
import main.Main;
import metod.rand;

import java.awt.*;

public class bull_flame extends Bull {
    public bull_flame(double x, double y, double rotation, double damage,double t_damage, double penetration, byte type_team, byte height){
        this.x = x;
        this.y = y;
        this.r = 236;this.g = 124;this.b = 38;
        //this.color = new Color((int)this.r,(int)this.g,(int)this.b);
        this.rotation = rotation;
        this.type_team = type_team;
        this.height = height;
        this.damage = damage;
        this.penetration = penetration;
        this.t_damage = t_damage;

        this.size = rand.rand(8,16);
        this.speed = -5;
        this.time = rand.rand(65,80);

    }
    public void all_action(int i){
        super.bull_move();
        super.color_fire();
        super.corpus_bull_temperature(Main.enemy_obj);
        super.corpus_bull_temperature(Main.player_obj);
        super.corpus_bull(Main.helicopter_obj);
        super.corpus_bull(Main.debris);
        super.soldat_bull(Main.soldat_obj);
        super.bull_build_flame(Main.build,Main.bull_obj,i);
        super.bull_clear_time_flame(i);
        super.clear(i);
        //metod.interface_panel.panel_information(main.Main.bull_obj.size(),600,600,24,Color.YELLOW,main.display.paint_2);
        //metod.interface_panel.panel_information(this.rotation,500,600,24,Color.YELLOW,main.display.paint_1);
    }
    public void update(Graphics2D g){
        g.setColor(this.color);
        center_render();
        g.fillOval(this.x_rend,this.y_rend,this.size_render,this.size_render);
    }
}
