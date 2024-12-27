package com.mygdx.game.soldat;

import com.badlogic.gdx.graphics.g2d.Sprite;
import Content.Bull.BullFlame;
import Content.Bull.BullMortar;
import Content.Bull.BullPacket;
import com.mygdx.game.main.Main;
import com.mygdx.game.build.Building;
import Content.Bull.BullTank;
import com.mygdx.game.method.RenderMethod;
import com.mygdx.game.method.Method;
import com.mygdx.game.method.rand;
import Content.Particle.Blood;
import com.mygdx.game.transport.Transport;
import com.mygdx.game.method.move;

import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.util.ArrayList;

import static Content.Bull.BullRegister.PacketBull;
import static com.mygdx.game.main.Main.*;
import static java.lang.StrictMath.*;

public abstract class Soldat implements Serializable {
    public String name;
    public float x,y,speed,rotation,speed_rotation,damage,penetration,t_damage,damage_fragmentation,penetration_fragmentation,range_see = 650;
    public int width,height,width_2,height_2,size,time,time_max,x_rend,y_rend,width_render,height_render;
    public byte clear_sost,team,trigger_attack;
    public Sprite soldat_image;
    public void data(){
        this.width_render = (int)(width*ZoomWindowX);
        this.height_render = (int)(height*ZoomWindowY);
        this.width_2 = width/2;
        this.height_2 = height/2;
    }

    public void all_action(int i){
        this.update();

    }
    public void update(){
        center_render();
        RenderMethod.transorm_img(this.x_rend,this.y_rend,this.width_render,this.height_render,(float)this.rotation+180,this.soldat_image);
    }
    public void move_soldat(ArrayList<Transport>player, int i, double g, double g_left, double g_right) {
        double h = sqrt(pow(this.x - player.get(i).x, 2) + pow(this.y - player.get(i).y, 2));
            if ( h> 130 && g > g_left && g < g_right) {
                move_invert();
            }
            if (h < 150 && g > g_left && g < g_right) {
                move();

            }else if (this.rotation < g) {
                this.rotation += this.speed_rotation;
            } else if (this.rotation > g) {
                this.rotation -= this.speed_rotation;
            }
    }
    public void clear(ArrayList<Soldat>obj,int i){
        if(this.clear_sost == 1){
            for(int i1 =0;i1<12;i1++){
            Main.LiquidList.add(new Blood(this.x+i1, this.y));}
            obj.remove(i);
        }
    }
    public void fire_bull(double g,double g_left,double g_right){
        this.time -=1;
        if(this.time < 0 && g>g_left && g<g_right){
            this.time = this.time_max;
            Main.BullList.add(new BullTank(this.x,this.y,-this.rotation+180,this.damage,this.penetration,this.team,(byte)1));
            PacketBull.add(new BullPacket());
            int i1 = PacketBull.size()-1;
            int i2 = BullList.size()-1;
            bull_packets(i1,i2);
        }
    }
    public void fire_flame(double g,double g_left,double g_right){
        this.time -=1;
        if(this.time < 0 && g>g_left && g<g_right){
            this.time = this.time_max;
            Main.BullList.add(new BullFlame(this.x,  this.y,-this.rotation+ -15+rand.rand(30)+180,  this.damage,this.t_damage,this.penetration,this.team,(byte)1));
            Main.BullList.add(new BullFlame(this.x, this.y, -this.rotation+ -15+rand.rand(30)+180,  this.damage,this.t_damage,this.penetration,this.team,(byte)1));
            PacketBull.add(new BullPacket());
            PacketBull.add(new BullPacket());
            int i1 = PacketBull.size()-2;
            int i2 = BullList.size()-2;
            bull_packets(i1,i2);
            bull_packets(i1+1,i2+1);
        }
    }
    public void fire_mortar(double g,double g_left,double g_right){
        this.time -=1;
        if(this.time < 0 && g>g_left && g<g_right){
            this.time = this.time_max;
            ///int i1 = packet_bull.size();
            //int i2 = bull_obj.size();
            Main.BullList.add(new BullMortar(this.x,  this.y,-this.rotation+180,this.damage,this.penetration,this.damage_fragmentation,
                    this.penetration_fragmentation,this.team,(byte)1));
            PacketBull.add(new BullPacket());
            int i1 = PacketBull.size()-1;
            int i2 = BullList.size()-1;
            bull_packets(i1,i2);
        }
    }
    public void bull_packets(int i1,int i2){
        PacketBull.get(i1).x = (float) this.x;
        PacketBull.get(i1).y = (float) this.y;
        PacketBull.get(i1).rotation = BullList.get(i2).rotation;
        PacketBull.get(i1).time = BullList.get(i2).time;
        PacketBull.get(i1).speed = BullList.get(i2).speed;
        PacketBull.get(i1).height = BullList.get(i2).height;
        PacketBull.get(i1).type = BullList.get(i2).type;
        PacketBull.get(i1).team = this.team;
    }
    public void ii_soldat(ArrayList<Building> obj_building, ArrayList<Transport>obj_player, int i3, int i2, double g, double g_left, double g_right){
        byte triger_rotation = 0;
        if(Main.BuildingList.size()!= 0) {
            int i = Method.detection_near_soldat_build(Main.SoldatList, i3, obj_building);
            if (sqrt(pow(this.x - obj_building.get(i).x, 2) + pow(this.y - obj_building.get(i).y, 2)) < obj_building.get(i).width * 1.2) {
                if (obj_player.get(i2).y < obj_building.get(i).y && this.y > obj_building.get(i).y) {
                    //System.out.println(2);
                    move_invert();
                    triger_rotation = 1;
                    if (obj_building.get(i).x + obj_building.get(i).width < this.x || obj_building.get(i).x > this.x) {
                        if (0 < this.rotation) {
                            this.rotation -= this.speed_rotation;
                        } else if (0 > this.rotation) {
                            this.rotation += this.speed_rotation;
                        }
                    } else if (this.x < obj_building.get(i).x + obj_building.get(i).width_2) {
                        if (-90 < this.rotation) {
                            this.rotation -= this.speed_rotation;
                        } else if (-90 > this.rotation) {
                            this.rotation += this.speed_rotation;
                        }
                    } else if (this.x > obj_building.get(i).x + obj_building.get(i).width_2) {
                        if (90 < this.rotation) {
                            this.rotation -= this.speed_rotation;
                        } else if (90 > this.rotation) {
                            this.rotation += this.speed_rotation;
                        }
                    }

                } else if (obj_player.get(i2).y > obj_building.get(i).y && this.y < obj_building.get(i).y) {
                    //System.out.println(3);
                    move_invert();
                    triger_rotation = 1;
                    if (obj_building.get(i).x + obj_building.get(i).width < this.x || obj_building.get(i).x > this.x) {
                        if (-180 < this.rotation) {
                            this.rotation -= this.speed_rotation;
                        } else if (-180 > this.rotation) {
                            this.rotation += this.speed_rotation;
                        }
                    } else if (this.x < obj_building.get(i).x + obj_building.get(i).width_2) {
                        if (-90 < this.rotation) {
                            this.rotation -= this.speed_rotation;
                        } else if (-90 > this.rotation) {
                            this.rotation += this.speed_rotation;
                        }
                    } else if (this.x > obj_building.get(i).x + obj_building.get(i).width_2) {
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
            move_soldat(Main.PlayerList, i2, g, g_left, g_right);
        }
        if (triger_rotation == 0) {
            move_soldat(Main.PlayerList, i2, g, g_left, g_right);
        }

    }
    public void move_soldat_ii_bull(int i){
        if(Main.PlayerList.size() != 0) {
            int[] sp = Method.detection_near_soldat_transport_i_def(Main.SoldatList, i, Main.PlayerList);
            if (sp[1] < this.range_see) {
                int i2 = Method.detection_near_soldat_transport(Main.SoldatList, i, Main.PlayerList);
                double g = atan2(this.y - Main.PlayerList.get(i2).y, this.x - Main.PlayerList.get(i2).x) / 3.1415926535 * 180;
                g -= 90;
                double g_left = this.rotation - 10;
                double g_right = this.rotation + 10;
                fire_bull(g, g_left, g_right);
                if (g > 20 && this.rotation < -160) {
                    g = -271;
                    if (this.rotation <= g) {
                        this.rotation = 89;
                    }
                } else if (g < -160 && this.rotation > 20) {
                    g = 91;
                    if (this.rotation >= g) {
                        this.rotation = -269;
                    }
                }
                ii_soldat(Main.BuildingList, Main.PlayerList, i, i2, g, g_left, g_right);
            }
        }
    }
    public void hustle(ArrayList<Transport>transport){
        for (Transport value : transport) {
            boolean z = rect_bull((int) value.x, (int) value.y, (int) value.corpus_width, (int) value.corpus_height,
                    (int) this.x, (int) this.y, this.size, value.rotation_corpus);
            if (z) {
                this.clear_sost = 1;
            }
        }
    }
    public void center_render(){
        float[]xy = Main.RC.render_objZoom(this.x,this.y);
        this.x_rend = (int)xy[0];
        this.y_rend = (int)xy[1];
    }
    public void collision_transport(ArrayList<Transport>transport){
        for (Transport value : transport) {
            boolean z = rect_bull((int) value.x, (int) value.y, (int) value.corpus_width, (int) value.corpus_height,
                    (int) this.x, (int) this.y, this.size, value.rotation_corpus);
            if (z) {
                metod_1(value.x, value.y);
            }
        }
    }
    public void collision_build(ArrayList<Building> building){
        for (Building value : building) {
            boolean z = rect_bull(value.x, value.y, value.width, value.height,
                    (int) this.x, (int) this.y, this.size, 0);
            if (z) {
                metod_1(value.x, value.y);
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
    public void metod_1(float x,float y){
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
    public boolean rect_bull(int x1,int y1,int width,int height,int x,int y,int size,float rotation){
        Rectangle2D rect1 = new Rectangle2D.Double(x1,y1,width,height);
        AffineTransform transform1 = new AffineTransform();
        transform1.rotate(Math.toRadians(-rotation), rect1.getCenterX(), rect1.getCenterY());
        Area area1 = new Area(rect1);
        area1.transform(transform1);

        Ellipse2D circle = new Ellipse2D.Double(x,y,size,size);

        return area1.intersects(circle.getBounds2D());
    }
    public void move_soldat_ii_flame(int i){
        if(Main.PlayerList.size() != 0) {
            int[] sp = Method.detection_near_soldat_transport_i_def(Main.SoldatList, i, Main.PlayerList);
            if (sp[1] < this.range_see) {
                int i2 = Method.detection_near_soldat_transport(Main.SoldatList, i, Main.PlayerList);
                double g = atan2(this.y - Main.PlayerList.get(i2).y, this.x - Main.PlayerList.get(i2).x) / 3.1415926535 * 180;
                g -= 90;
                double g_left = this.rotation - 10;
                double g_right = this.rotation + 10;
                fire_flame(g, g_left, g_right);
                if (g > 0 && this.rotation < -180) {
                    g = -271;
                    if (this.rotation <= g) {
                        this.rotation = 90;
                    }
                } else if (g < -180 && this.rotation > 0) {
                    g = 91;
                    if (this.rotation >= g) {
                        this.rotation = -270;
                    }
                }
                ii_soldat(Main.BuildingList, Main.PlayerList, i, i2, g, g_left, g_right);
            }
        }
    }
    public void move_soldat_ii_mortar(int i){
        if(Main.PlayerList.size() != 0) {
            int[] sp = Method.detection_near_soldat_transport_i_def(Main.SoldatList, i, Main.PlayerList);
            if (sp[1] < this.range_see) {
                int i2 = Method.detection_near_soldat_transport(Main.SoldatList, i, Main.PlayerList);
                double g = atan2(this.y - Main.PlayerList.get(i2).y, this.x - Main.PlayerList.get(i2).x) / 3.1415926535 * 180;
                g -= 90;
                double g_left = this.rotation - 10;
                double g_right = this.rotation + 10;
                fire_mortar(g, g_left, g_right);
                if (g > 0 && this.rotation < -180) {
                    g = -271;
                    if (this.rotation <= g) {
                        this.rotation = 90;
                    }
                } else if (g < -180 && this.rotation > 0) {
                    g = 91;
                    if (this.rotation >= g) {
                        this.rotation = -270;
                    }
                }
                ii_soldat(Main.BuildingList, Main.PlayerList, i, i2, g, g_left, g_right);
            }
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
    public void all_action_client(int i){

    }
}
