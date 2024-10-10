package com.mygdx.game.block;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.main.Main;

import com.mygdx.game.metod.render_metod;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.util.*;

import static com.mygdx.game.main.Main.batch;
import static java.lang.StrictMath.pow;
import static java.lang.StrictMath.sqrt;
import static java.sql.Types.NULL;

public abstract class Block {
    public int x,y,x_rend,y_rend,x_center,y_center;
    public int width,height,transparency = -150,brightness,radius;
    public Texture img;
    public int width_zoom,height_zoom;
    public ArrayList<Integer> h = new ArrayList<>();
    private int i;
    private int h_total;
    public float fg;

    public ArrayList<float[]> rgbl = new ArrayList<>();
    private float[] rgb;
    public byte passability;
    private float r;
    private float g;
    private float b;
    //public Texture paint;
    public Pixmap pixmap;
    public void passability_detected(){
        passability = 1;
        for (i = 0;i<Main.build.size();i++){
            for (int j = 0;j<Main.build.get(i).xy_area_list.size();j++) {
                if (rect_collision(x, y, width, height, 0, Main.build.get(i).xy_area_list.get(j)[0],Main.build.get(i).xy_area_list.get(j)[1], Main.build.get(i).xy_area_list.get(j)[2], Main.build.get(i).xy_area_list.get(j)[3], Main.build.get(i).rotation)) {
                    passability = 2;
                    return;
                }
            }
        }


    }
    public void block_xy(){
        this.x_center = this.x +this.width/2;
        this.y_center = this.y +this.height/2;
    }
    public boolean rect_collision(int x1,int y1,int width,int height,double rotation,
                                  int x2,int y2,int width2,int height2,double rotation_2){

        Rectangle rect1 = new Rectangle(x1,y1,width,height); // Прямоугольник 1
        Rectangle rect2 = new Rectangle(x2,y2,width2,height2); // Прямоугольник 2

        // Создаем аффинное преобразование для поворота
        AffineTransform transform1 = new AffineTransform();
        transform1.rotate(Math.toRadians(rotation), rect1.getCenterX(), rect1.getCenterY());
        AffineTransform transform2 = new AffineTransform();
        transform2.rotate(Math.toRadians(rotation_2), rect2.getCenterX(), rect2.getCenterY());

        // Преобразование прямоугольников с учетом поворота
        Area area1 = new Area(rect1);
        area1.transform(transform1);
        Area area2 = new Area(rect2);
        area2.transform(transform2);

        // Вычисление пересечения двух преобразованных прямоугольников
        area1.intersect(area2);

        // Проверка наличия пересечения
        if (!area1.isEmpty()) {
            //Rectangle intersection = area1.getBounds();
            //System.out.println("Прямоугольники пересекаются. Результат: " + intersection);
            return true;

        }
        return false;
    }

    public void update(){
        center_render();
        render_metod.transorm_img(this.x_rend,this.y_rend,this.width_zoom,this.height_zoom,this.img);

        //g.drawImage(this.img,(int)((this.x_rend/correct_int)* Game_tank.zoom),(int)((this.y_rend/correct_int)* Game_tank.zoom),(int)(this.width* Game_tank.zoom),(int)(this.height* Game_tank.zoom),null);

    }
    public void all_action(){

    }
    public void update_air() {
        this.radius = NULL;
        for (i = 0; i < Main.flame_spawn.size(); i++) {
            int gh = (int) sqrt(pow(Main.flame_spawn.get(i).x_rend - this.x, 2) + pow(Main.flame_spawn.get(i).y_rend - this.y, 2));
            if(gh <300*Main.zoom) {rgbl.add(Main.flame_spawn.get(i).rgb);
                h.add(gh);
                if (this.radius == NULL || this.radius > gh) {
                    this.radius = gh;
                }
            }

        }

//        for (i = 0; i < Main.build.size(); i++) {
//            if(Main.build.get(i).time_flame > 0) {
//                int gh = (int) sqrt(pow(Main.build.get(i).x_rend - this.x, 2) + pow(Main.build.get(i).y_rend - this.y, 2));
//
//                if(gh <300*Main.zoom){rgbl.add(Main.build.get(i).rgb);
//                h.add(gh);
//                    if (this.radius == NULL || this.radius > gh) {
//                        this.radius = gh;
//                    }
//                }
//            }
//        }
        for (i = 0; i < Main.build.size(); i++) {
            if(Main.build.get(i).time_flame > 0) {
                for (int j = 0; j < Main.build.get(i).xy_light_render.size(); j++) {
                    int gh = (int) sqrt(pow(Main.build.get(i).xy_light_render.get(j)[0]*Main.zoom - this.x, 2) + pow(Main.build.get(i).xy_light_render.get(j)[1]*Main.zoom - this.y, 2));

                    if (gh < 300 * Main.zoom) {
                        rgbl.add(Main.build.get(i).rgb);
                        h.add(gh);
                        if (this.radius == NULL || this.radius > gh) {
                            this.radius = gh;
                        }
                    }
                }
            }
        }
        if(h.size() != 0) {
            brightness = transparency + (int) (this.radius / Main.zoom);
            for (i = 0;i<h.size();i++) {
//                r += rgbl.get(i)[0]-(rgbl.get(i)[0] / h.get(i));
//                g += rgbl.get(i)[1]-(rgbl.get(i)[1] / h.get(i));
//                b += rgbl.get(i)[2]-(rgbl.get(i)[2] / h.get(i));
                r += rgbl.get(i)[0]*(float) (150f / h.get(i)* Main.zoom);
                g += rgbl.get(i)[1]*(float)(150f / h.get(i)* Main.zoom);
                b += rgbl.get(i)[2]*(float)(150f / h.get(i)* Main.zoom);
                fg+=(float)radius/h.get(i);
            }
            //brightness = transparency + (int) (radius / Main.zoom);
            r /= fg;
            g /= fg;
            b /= fg;
            fg = 0;

            if (brightness < 0) {
                brightness = 0;
            } else if (brightness > 215) {
                brightness = 215;
            }
            Main.render.setColor(0,0,0,1-(1f/brightness)*40);
            Main.render.rect(x,y,width,height);
            if ((1f / brightness) * 20 < 0.09) {
                r = 0;g =0;b=0;
                rgbl.clear();
                h.clear();
                return;
            } else if ((1f / brightness) * 10 > 0.2) {
                Main.render.setColor(r,g,b, 0.3f);
                Main.render.rect(x, y, width, height);
            } else {
                Main.render.setColor(r,g,b, (1f / brightness)*20);
                Main.render.rect(x, y, width, height);
            }
            rgbl.clear();
            h.clear();
            r = 0;g =0;b=0;
        } else{
            Main.render.setColor(0, 0, 0,0.8f);
            Main.render.rect(x,y,width,height);

        }

    }
    public void center_render(){
        int[]xy = Main.rc.get(0).render_obj(this.x,this.y);
        this.x_rend = (int)(xy[0]* Main.zoom);
        this.y_rend = (int)(xy[1]* Main.zoom);
    }




}
