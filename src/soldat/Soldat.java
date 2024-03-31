package soldat;

import build.Build;
import bull.bull_flame;
import bull.bull_fragmentation;
import bull.bull_tank;
import main.Game;
import main.Main;
import metod.metod;
import metod.rand;
import metod.render_metod;
import particle.blood;
import transport.Transport;
import metod.move;

import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.util.ArrayList;

import static java.lang.StrictMath.*;

public abstract class Soldat implements Serializable {
    public double x,y,speed,rotation,speed_rotation,damage,penetration,t_damage,damage_fragmentation,penetration_fragmentation;
    public int width,height,width_2,height_2,size,time,time_max,x_rend,y_rend,width_render,height_render;
    public byte clear_sost,team;
    public String soldat_image;
    public void all_action(int i){
        this.update();

    }
    public void update(){
        center_render();
        render_metod.transorm_img(this.x_rend,this.y_rend,this.width,this.height,this.rotation-30,this.soldat_image);
    }
    public void move_soldat(ArrayList<Transport>player, int i, double g, double g_left, double g_right) {
            if ((sqrt(pow(this.x - player.get(i).x, 2) + pow(this.y - player.get(i).y, 2)) > 130) &&
                    g > g_left && g < g_right) {
                move_invert();
            }
            if ((sqrt(pow(this.x - player.get(i).x, 2) + pow(this.y - player.get(i).y, 2)) < 150) &&
                    g > g_left && g < g_right) {
                move();

            }else if (this.rotation < g) {
                this.rotation += this.speed_rotation;
            } else if (this.rotation > g) {
                this.rotation -= this.speed_rotation;
            }
    }
    public void clear(ArrayList<Soldat>obj,int i){
        if(this.clear_sost == 1){
            for(int i1 =0;i1<120;i1++){
            Main.liquid_obj.add(new blood(this.x/1.23,this.y/1.23));}
            obj.remove(i);
        }
    }
    public void fire_bull(double g,double g_left,double g_right){
        this.time -=1;
        if(this.time < 0 && g>g_left && g<g_right){
            this.time = this.time_max;
            Main.bull_obj.add(new bull_tank(this.x,this.y,-this.rotation,this.damage,this.penetration,this.team,(byte)1));
        }
    }
    public void fire_flame(double g,double g_left,double g_right){
        this.time -=1;
        if(this.time < 0 && g>g_left && g<g_right){
            this.time = this.time_max;
            Main.bull_obj.add(new bull_flame(this.x,this.y,-this.rotation+ rand.rand(-15,15),this.damage,this.t_damage,this.penetration,this.team,(byte)1));
            Main.bull_obj.add(new bull_flame(this.x,this.y,-this.rotation+ rand.rand(-15,15),this.damage,this.t_damage,this.penetration,this.team,(byte)1));
        }
    }
    public void fire_mortar(double g,double g_left,double g_right){
        this.time -=1;
        if(this.time < 0 && g>g_left && g<g_right){
            this.time = this.time_max;
            Main.bull_obj.add(new bull_fragmentation(this.x,this.y,-this.rotation,this.damage,this.penetration,this.damage_fragmentation,
                    this.penetration_fragmentation,this.team,(byte)1));
        }
    }
    public void ii_soldat(ArrayList<Build>obj_build, ArrayList<Transport>obj_player, int i3, int i2, double g, double g_left, double g_right){
        byte triger_rotation = 0;
        if(Main.build.size()!= 0) {
            int i = metod.detection_near_soldat_build(Main.soldat_obj, i3,obj_build);
            if (sqrt(pow(this.x - obj_build.get(i).x, 2) + pow(this.y - obj_build.get(i).y, 2)) < obj_build.get(i).width * 1.2) {
                if (obj_player.get(i2).y < obj_build.get(i).y && this.y > obj_build.get(i).y) {
                    //System.out.println(2);
                    move();
                    triger_rotation = 1;
                    if (obj_build.get(i).x + obj_build.get(i).width < this.x || obj_build.get(i).x > this.x) {
                        if (0 < this.rotation) {
                            this.rotation -= this.speed_rotation;
                        } else if (0 > this.rotation) {
                            this.rotation += this.speed_rotation;
                        }
                    } else if (this.x < obj_build.get(i).x + obj_build.get(i).width_2) {
                        if (-90 < this.rotation) {
                            this.rotation -= this.speed_rotation;
                        } else if (-90 > this.rotation) {
                            this.rotation += this.speed_rotation;
                        }
                    } else if (this.x > obj_build.get(i).x + obj_build.get(i).width_2) {
                        if (90 < this.rotation) {
                            this.rotation -= this.speed_rotation;
                        } else if (90 > this.rotation) {
                            this.rotation += this.speed_rotation;
                        }
                    }

                } else if (obj_player.get(i2).y > obj_build.get(i).y && this.y < obj_build.get(i).y) {
                    //System.out.println(3);
                    move();
                    triger_rotation = 1;
                    if (obj_build.get(i).x + obj_build.get(i).width < this.x || obj_build.get(i).x > this.x) {
                        if (-180 < this.rotation) {
                            this.rotation -= this.speed_rotation;
                        } else if (-180 > this.rotation) {
                            this.rotation += this.speed_rotation;
                        }
                    } else if (this.x < obj_build.get(i).x + obj_build.get(i).width_2) {
                        if (-90 < this.rotation) {
                            this.rotation -= this.speed_rotation;
                        } else if (-90 > this.rotation) {
                            this.rotation += this.speed_rotation;
                        }
                    } else if (this.x > obj_build.get(i).x + obj_build.get(i).width_2) {
                        if (90 < this.rotation) {
                            this.rotation -= this.speed_rotation;
                        } else if (90 > this.rotation) {
                            this.rotation += this.speed_rotation;
                        }
                    }
                }
            }
        }
        else{
            triger_rotation = 0;
        }
        if (triger_rotation == 0) {
            move_soldat(Main.player_obj, i2, g, g_left, g_right);
        }

    }
    public void move_soldat_ii_bull(int i){
        if(Main.player_obj.size() != 0) {
            int i2 = metod.detection_near_soldat_transport(Main.soldat_obj, i, Main.player_obj);
            double g = atan2(this.y-Main.player_obj.get(i2).y, this.x-Main.player_obj.get(i2).x)/ 3.1415926535 * 180;
            g -=90;
            double g_left = this.rotation - 10;
            double g_right = this.rotation + 10;
            fire_bull(g, g_left, g_right);
            if(g>20 && this.rotation<-160){
                g= -271;
                if (this.rotation <= g){this.rotation = 89;}
            }
            else if(g<-160 && this.rotation>20){
                g= 91;
                if (this.rotation >= g){this.rotation = -269;}
            }
            ii_soldat(Main.build, Main.player_obj, i, i2, g, g_left, g_right);
        }
    }
    public void hustle(ArrayList<Transport>transport){
        for(int i = 0;i<transport.size();i++){
            boolean z = rect_bull((int)transport.get(i).x,(int)transport.get(i).y,(int)transport.get(i).corpus_width,(int)transport.get(i).corpus_height,
                    (int)this.x,(int)this.y,this.size,transport.get(i).rotation_corpus);
            if(z){
                this.clear_sost = 1;
            }
        }
    }
    public void center_render(){
        double[]xy = Main.rc.get(0).render_obj(this.x,this.y);
        this.x_rend = (int)(xy[0]* Game.zoom);
        this.y_rend = (int)(xy[1]* Game.zoom);
    }
    public void collision_transport(ArrayList<Transport>transport){
        for(int i = 0;i<transport.size();i++){
            boolean z = rect_bull((int)transport.get(i).x,(int)transport.get(i).y,(int)transport.get(i).corpus_width,(int)transport.get(i).corpus_height,
                    (int)this.x,(int)this.y,this.size,transport.get(i).rotation_corpus);
            if(z){
                metod_1(transport.get(i).x,transport.get(i).y);
            }
        }
    }
    public void collision_build(ArrayList<Build>build){
        for(int i = 0;i<build.size();i++){
            boolean z = rect_bull(build.get(i).x,build.get(i).y,build.get(i).width,build.get(i).height,
                    (int)this.x,(int)this.y,this.size,0);
            if(z){
                metod_1(build.get(i).x,build.get(i).y);
            }
        }
    }
    public void collision_soldat(ArrayList<Soldat>soldat,int i2){
        for(int i = 0;i<soldat.size();i++) {
            if (sqrt(pow(this.x -soldat.get(i).x,2)+pow(this.y -soldat.get(i).y,2))<this.size+soldat.get(i).size&&
            i2 != i){
                metod_1(soldat.get(i).x,soldat.get(i).y);
            }
        }
    }
    public void metod_1(double x,double y){
        if(x>this.x){
            this.x -= 2;
        }
        else if(x<this.x){
            this.x += 2;
        }
        if(y>this.y){
            this.y -= 2;
        }
        else if(y<this.y){
            this.y += 2;
        }
    }
    public boolean rect_bull(int x1,int y1,int width,int height,int x,int y,int size,double rotation){
        Rectangle2D rect1 = new Rectangle2D.Double(x1,y1,width,height);
        AffineTransform transform1 = new AffineTransform();
        transform1.rotate(Math.toRadians(-rotation), rect1.getCenterX(), rect1.getCenterY());
        Area area1 = new Area(rect1);
        area1.transform(transform1);

        Ellipse2D circle = new Ellipse2D.Double(x,y,size,size);

        if (area1.intersects(circle.getBounds2D())) {
            return true;
        }
        return false;
    }
    public void move_soldat_ii_flame(int i){
        if(Main.player_obj.size() != 0) {
            int i2 = metod.detection_near_soldat_transport(Main.soldat_obj, i, Main.player_obj);
            double g = atan2(this.y-Main.player_obj.get(i2).y, this.x-Main.player_obj.get(i2).x)/ 3.1415926535 * 180;
            g -=90;
            double g_left = this.rotation - 10;
            double g_right = this.rotation + 10;
            fire_flame(g, g_left, g_right);
            if(g>0 && this.rotation<-180){
                g= -271;
                if (this.rotation <= g){this.rotation = 90;}
            }
            else if(g<-180 && this.rotation>0){
                g= 91;
                if (this.rotation >= g){this.rotation = -270;}
            }
            ii_soldat(Main.build, Main.player_obj, i, i2, g, g_left, g_right);
        }
    }
    public void move_soldat_ii_mortar(int i){
        if(Main.player_obj.size() != 0) {
            int i2 = metod.detection_near_soldat_transport(Main.soldat_obj, i, Main.player_obj);
            double g = atan2(this.y-Main.player_obj.get(i2).y, this.x-Main.player_obj.get(i2).x)/ 3.1415926535 * 180;
            g -=90;
            double g_left = this.rotation - 10;
            double g_right = this.rotation + 10;
            fire_mortar(g, g_left, g_right);
            if(g>0 && this.rotation<-180){
                g= -271;
                if (this.rotation <= g){this.rotation = 90;}
            }
            else if(g<-180 && this.rotation>0){
                g= 91;
                if (this.rotation >= g){this.rotation = -270;}
            }
            ii_soldat(Main.build, Main.player_obj, i, i2, g, g_left, g_right);
        }
    }
    public void move(){
        this.x += move.move_sin(this.speed,-this.rotation);
        this.y += move.move_cos(this.speed, -this.rotation);
    }
    public void move_invert(){
        this.x -= move.move_sin(this.speed, -this.rotation);
        this.y -= move.move_cos(this.speed, -this.rotation);
    }
}
