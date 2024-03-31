package bull;

import bull.Bull;
import main.Main;

import java.awt.*;

public class bull_tank extends Bull {
    public bull_tank(double x,double y,double rotation,double damage,double penetration,byte type_time,byte height){
        this.x = x;
        this.y = y;
        this.rotation = rotation;
        this.type_team = type_time;
        this.height = height;

        this.size = 10;
        this.damage = damage;
        this.penetration = penetration;
        this.speed = -12;
        this.color = Color.yellow;
        this.time = 220;
    }
    public void all_action(int i){
        super.bull_move();
        //super.bull_clear_display();
        super.bull_clear_time();
        super.corpus_bull(Main.enemy_obj);
        super.corpus_bull(Main.player_obj);
        super.corpus_bull(Main.helicopter_obj);
        super.corpus_bull(Main.debris);
        super.bull_build(Main.build,Main.bull_obj,i);
        super.soldat_bull(Main.soldat_obj);
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
