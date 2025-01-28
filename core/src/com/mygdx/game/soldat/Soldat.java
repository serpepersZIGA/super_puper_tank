package com.mygdx.game.soldat;

import com.badlogic.gdx.graphics.g2d.Sprite;
import Content.Bull.BullFlame;
import Content.Bull.BullMortar;
import Content.Bull.BullPacket;
import com.mygdx.game.main.Main;
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
import static com.mygdx.game.method.pow2.pow2;
import static com.mygdx.game.transport.Transport.ai_sost;
import static java.lang.StrictMath.*;

public abstract class Soldat implements Serializable {
    public String name;
    public float x,y,speed,rotation,speed_rotation = 5,damage,penetration,t_damage,damage_fragmentation,penetration_fragmentation,range_see = 1400;
    public int width,height,width_2,height_2,size,time,time_max,x_rend,y_rend,width_render,height_render;
    public byte clear_sost,team,trigger_attack;
    public ArrayList<int[]>path = new ArrayList<>();
    protected float g;
    public Sprite soldat_image;
    public ArrayList<Transport>allyList,enemyList;
    public float SpeedCollision;
    public void data(){
        if(allyList == EnemyList){
            enemyList = PlayerList;
        }
        else {
            enemyList = EnemyList;
        }
        this.width_render = (int)(width*Zoom);
        this.height_render = (int)(height*Zoom);
        this.width_2 = width/2;
        this.height_2 = height/2;
        SpeedCollision = speed*4;
    }

    public void all_action(int i){
        this.update();

    }
    public void update(){
        center_render();
        RenderMethod.transorm_img(this.x_rend,this.y_rend,this.width_render,this.height_render,this.rotation+180,this.soldat_image);
    }
    public void move_soldat(float h, float g, float g_left, float g_right) {
            if (h> 130 && g > g_left && g < g_right) {
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
    public void move_soldatPath(float g, double g_left, double g_right) {
        if (g > g_left && g < g_right) {
            move_invert();
        }
    }
    public void clear(ArrayList<Soldat>obj,int i){
        if(this.clear_sost == 1){
            for(int i1 =0;i1<12;i1++){
            Main.LiquidList.add(new Blood(this.x+i1, this.y));}
            obj.remove(i);
        }
    }
    public void fire_bull(float g){
        this.time -=1;
        if(this.time < 0 && abs(g-rotation)<20){
            this.time = this.time_max;
            Main.BulletList.add(new BullTank(this.x,this.y,-this.rotation+180,this.damage,this.penetration,this.team,(byte)1));
            PacketBull.add(new BullPacket());
            int i1 = PacketBull.size()-1;
            int i2 = BulletList.size()-1;
            bull_packets(i1,i2);
        }
    }
    public void fire_flame(float g){
        this.time -=1;
        if(this.time < 0 && abs(g-rotation)<20){
            this.time = this.time_max;
            Main.BulletList.add(new BullFlame(this.x,  this.y,-this.rotation+ -15+rand.rand(30)+180,  this.damage,this.t_damage,this.penetration,this.team,(byte)1));
            Main.BulletList.add(new BullFlame(this.x, this.y, -this.rotation+ -15+rand.rand(30)+180,  this.damage,this.t_damage,this.penetration,this.team,(byte)1));
            PacketBull.add(new BullPacket());
            PacketBull.add(new BullPacket());
            int i1 = PacketBull.size()-2;
            int i2 = BulletList.size()-2;
            bull_packets(i1,i2);
            bull_packets(i1+1,i2+1);
        }
    }
    public void fire_mortar(float g){
        this.time -=1;
        if(this.time < 0 && abs(g-rotation)<20){
            this.time = this.time_max;
            ///int i1 = packet_bull.size();
            //int i2 = bull_obj.size();
            Main.BulletList.add(new BullMortar(this.x,  this.y,-this.rotation+180,this.damage,this.penetration,this.damage_fragmentation,
                    this.penetration_fragmentation,this.team,(byte)1));
            PacketBull.add(new BullPacket());
            int i1 = PacketBull.size()-1;
            int i2 = BulletList.size()-1;
            bull_packets(i1,i2);
        }
    }
    public void bull_packets(int i1,int i2){
        PacketBull.get(i1).x = this.x;
        PacketBull.get(i1).y = this.y;
        PacketBull.get(i1).rotation = BulletList.get(i2).rotation;
        PacketBull.get(i1).time = BulletList.get(i2).time;
        PacketBull.get(i1).speed = BulletList.get(i2).speed;
        PacketBull.get(i1).height = BulletList.get(i2).height;
        PacketBull.get(i1).type = BulletList.get(i2).type;
        PacketBull.get(i1).team = this.team;
    }
    public void ii_soldat(float g, int iAi, int iEnemy){
        if (ai_sost == 0) {
            if (null != findIntersection(x,y, enemyList.get(iEnemy).tower_x, enemyList.get(iEnemy).tower_y)) {
                path.clear();
                Ai.pathAISoldat(SoldatList.get(iAi), enemyList.get(iEnemy), x, y);
            } else {
                path.clear();
            }
        }
        if(path.size() > 0) {
            float radius = (float) sqrt(pow2((x - BlockList2D.get(path.get(0)[1]).get(path.get(0)[0]).x_center)) + pow2(y - BlockList2D.get(path.get(0)[1]).get(path.get(0)[0]).y_center));
            float gr = (float) ((atan2(y - BlockList2D.get(path.get(0)[1]).get(path.get(0)[0]).y_center,x - BlockList2D.get(path.get(0)[1]).get(path.get(0)[0]).x_center)/3.1415926535*180)-90);
            SoldatRotatePath(gr);
            if(radius< 70){
                path.remove(0);
            }
        }
        else {
            float radius = (float) sqrt(pow2((x - enemyList.get(iEnemy).tower_x)) + pow2(y - enemyList.get(iEnemy).tower_y));
            SoldatRotate(g);
            SoldatMove(g,radius);
        }

    }
    public void SoldatRotatePath(float TargetRotate){
        if (abs(TargetRotate-rotation)<5){
            move_invert();
        }
        else if (TargetRotate > this.rotation) {
            this.rotation += this.speed_rotation;
        } else if (TargetRotate < this.rotation) {
            this.rotation -= this.speed_rotation;
        }
    }
    protected float[] findIntersection(float x0, float y0, float dx, float dy) {
        float x = x0/width_block;
        float y = y0/height_block;
        dx = dx/width_block;
        dy = dy/height_block;
        float xy_r = (float)(atan2(y-dy, x-dx));
        float speed_x = (float) cos(xy_r)/2;
        float speed_y = (float) sin(xy_r)/2;
//        System.out.println(x+" g "+y);
//        System.out.println(dx+" h "+dy);
//        System.out.println(xy_r);
        if (y > dy) {
            if (x > dx) {
                while (x > dx && y > dy) {
                    x -= speed_x;
                    y -= speed_y;
                    //System.out.println(x+" "+y+" "+speed_x+" "+speed_y+" "+dx+" "+dy+" "+" "+xy_r+" w");
                    //double[]xy = Main.rc.get(0).render_obj(x0,y0);
//                    System.out.println(x+" z "+y);
//                    System.out.println(dx+" vo "+dy);
                    if (BlockList2D.get((int)y).get((int)x).passability) {
                        return new float[]{x, y};
                    }

                }
            } else if(x < dx){
                while (x < dx && y > dy) {
                    x -= speed_x;
                    y -= speed_y;
                    //System.out.println(x+" "+y+" "+speed_x+" "+speed_y+" "+dx+" "+dy+" "+" "+xy_r+" s");
                    //double[]xy = Main.rc.get(0).render_obj(x0,y0);
                    if (BlockList2D.get((int)y).get((int)x).passability) {
                        return new float[]{x, y};
                    }
                }
            }
            else{
                while (y > dy) {
                    y -= speed_y;
                    //System.out.println(x+" "+y+" "+speed_x+" "+speed_y+" "+dx+" "+dy+" "+" "+xy_r+" s");
                    //double[]xy = Main.rc.get(0).render_obj(x0,y0);
                    if (BlockList2D.get((int)y).get((int)x).passability) {
                        return new float[]{x, y};
                    }
                }
            }
        } else if(y < dy){
            if (x > dx) {
                while (x > dx && y < dy) {
                    x -= speed_x;
                    y -= speed_y;
                    //System.out.println(x+" "+y+" "+speed_x+" "+speed_y+" "+dx+" "+dy+" "+" "+xy_r+" x");
                    //double[]xy = Main.rc.get(0).render_obj(x0,y0);
                    if (BlockList2D.get((int)y).get((int)x).passability) {
                        return new float[]{x, y};
                    }
                }
            } else if (x < dx){
                while (x < dx && y < dy) {
                    x -= speed_x;
                    y -= speed_y;

                    // System.out.println(x+" "+y+" "+speed_x+" "+speed_y+" "+dx+" "+dy+" "+" "+xy_r+" z");
                    //double[]xy = Main.rc.get(0).render_obj(x0,y0);
                    if (BlockList2D.get((int)y).get((int)x).passability) {
                        return new float[]{x, y};
                    }
                }
            }
            else {
                while (y < dy) {
                    y -= speed_y;
                    //System.out.println(x+" "+y+" "+speed_x+" "+speed_y+" "+dx+" "+dy+" "+" "+xy_r+" s");
                    //double[]xy = Main.rc.get(0).render_obj(x0,y0);
                    if (BlockList2D.get((int)y).get((int)x).passability) {
                        return new float[]{x, y};
                    }
                }
            }
        }
        else {
            if (x > dx) {
                while (x > dx) {
                    x -= speed_x;
                    //System.out.println(x+" "+y+" "+speed_x+" "+speed_y+" "+dx+" "+dy+" "+" "+xy_r+" x");
                    //double[]xy = Main.rc.get(0).render_obj(x0,y0);
                    if (BlockList2D.get((int)y).get((int)x).passability) {
                        return new float[]{x, y};
                    }
                }
            } else if (x < dx){
                while (x < dx) {
                    x -= speed_x;


                    // System.out.println(x+" "+y+" "+speed_x+" "+speed_y+" "+dx+" "+dy+" "+" "+xy_r+" z");
                    //double[]xy = Main.rc.get(0).render_obj(x0,y0);
                    if (BlockList2D.get((int)y).get((int)x).passability) {
                        return new float[]{x, y};
                    }
                }
            }
        }
        return null;

    }
    public void move_soldat_ii_bull(int i){
        if(enemyList.size() != 0) {
            int[] sp = Method.detection_near_soldat_transport_i_def(Main.SoldatList, i,enemyList);
            if (sp[1] < this.range_see) {
                float g = (float) (atan2(this.y - enemyList.get(sp[0]).y, this.x - enemyList.get(sp[0]).x) / 3.1415926535 * 180);
                g -= 90;
                fire_bull(g);
                ii_soldat(g, i,sp[0]);
            }
        }
    }
    public void SoldatRotate(float g){
        if (g > 20 && this.rotation < -180) {
            g = -272;
        }
        else if (g < -160 && this.rotation > 0) {
            g = 92;
        }
        if (this.rotation > 91) {
            this.rotation = -269;
        }
        else if (this.rotation < -271) {
            this.rotation = 89;
        }
        if (g > this.rotation) {
            this.rotation += this.speed_rotation;
        } else if (g < this.rotation) {
            this.rotation -= this.speed_rotation;
        }
    }
    public void SoldatMove(float g,float radius){
        if(abs(g-rotation)<20 & radius>250){
            move_invert();
        }
        else if(abs(g-rotation)<20 & radius<200){
            move();
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
    public void collision_build(){
        int xBlock= (int) (x/ width_block)-1;
        int yBlock= (int) (y/ height_block)-1;
        if(BlockList2D.get(yBlock).get(xBlock).passability){
            if(x<BlockList2D.get(yBlock).get(xBlock).x_center){
                x-= SpeedCollision;
            }
            else{
                x+= SpeedCollision;
            }
            if(y<BlockList2D.get(yBlock).get(xBlock).y_center){
                y-= SpeedCollision;
            }
            else{
                y+= SpeedCollision;
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
        if(enemyList.size() != 0) {
            int[] sp = Method.detection_near_soldat_transport_i_def(Main.SoldatList, i,enemyList);
            if (sp[1] < this.range_see) {
                float g = (float) (atan2(this.y - enemyList.get(sp[0]).y, this.x - enemyList.get(sp[0]).x) / 3.1415926535 * 180);
                g -= 90;
                fire_flame(g);
                ii_soldat(g, i,sp[0]);
            }
        }
    }
    public void move_soldat_ii_mortar(int i){
        if(enemyList.size() != 0) {
            int[] sp = Method.detection_near_soldat_transport_i_def(Main.SoldatList, i,enemyList);
            if (sp[1] < this.range_see) {
                float g = (float) (atan2(this.y - enemyList.get(sp[0]).y, this.x - enemyList.get(sp[0]).x) / 3.1415926535 * 180);
                g -= 90;
                fire_mortar(g);
                ii_soldat(g, i,sp[0]);
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
