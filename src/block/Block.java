package block;

import java.awt.*;
import java.awt.image.BufferedImage;
import main.Main;
import main.Game;

import static java.lang.StrictMath.pow;
import static java.lang.StrictMath.sqrt;
import static java.sql.Types.NULL;

public abstract class Block {
    public int x,y,x_rend,y_rend;
    public int width,height,transparency = -150,brightness,radius;
    public BufferedImage img;
    private int i;

    public void update(Graphics2D g){
        center_render();
        g.drawImage(this.img,(int)((this.x_rend/1.24)* Game.zoom),(int)((this.y_rend/1.24)* Game.zoom),(int)(this.width* Game.zoom),(int)(this.height* Game.zoom),null);

    }
    public void all_action(Graphics2D g){

    }
    public void update_air(Graphics2D g) {
        this.radius = NULL;
        for (i = 0; i < Main.flame_spawn.size(); i++) {
            int gh = (int) sqrt(pow(Main.flame_spawn.get(i).x_rend * 1.24 - this.x, 2) + pow(Main.flame_spawn.get(i).y_rend * 1.24 - this.y, 2));
            if (this.radius == NULL || this.radius > gh) {
                this.radius = gh;

            }
        }

        for (i = 0; i < Main.build.size(); i++) {
            if(Main.build.get(i).time_flame > 0) {
                int gh = (int) sqrt(pow(Main.build.get(i).x_rend * Game.zoom - this.x, 2) + pow(Main.build.get(i).y_rend * Game.zoom - this.y, 2));
                if (this.radius == NULL || this.radius > gh) {
                    this.radius = gh;

                }
            }
        }
        if(this.radius != NULL) {
            brightness = transparency + (int) (this.radius / Game.zoom);
            if (brightness < 0) {
                brightness = 0;
            } else if (brightness > 215) {
                brightness = 215;
            }
            g.setColor(new Color(0, 0, 0, brightness));
            g.fillRect(this.x, this.y, this.width, this.height);
        } else{
            g.setColor(new Color(0, 0, 0,215));
            g.fillRect(this.x, this.y, this.width, this.height);
        }
    }
    public void center_render(){
        double[]xy = Main.rc.get(0).render_obj(this.x,this.y);
        this.x_rend = (int)(xy[0]);
        this.y_rend = (int)(xy[1]);
    }




}
