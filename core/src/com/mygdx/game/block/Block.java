package com.mygdx.game.block;

import Content.Particle.FlameSpawn;
import com.mygdx.game.build.Building;
import com.mygdx.game.main.Main;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.util.*;

import static com.mygdx.game.block.UpdateRegister.VoidUpdate;
import static com.mygdx.game.main.Main.FlameSpawnList;
import static com.mygdx.game.method.CycleTimeDay.*;
import static com.mygdx.game.method.pow2.pow2;
import static java.lang.StrictMath.sqrt;
import static java.sql.Types.NULL;

public abstract class Block {
    public int x,y;
    public int x_rend,y_rend,x_center,y_center;
    public int radius;

    public UpdateBlock render_block;
    public ArrayList<Integer> h = new ArrayList<>();
    private int i;
    private ArrayList<float[]> rgbl = new ArrayList<>();
    public boolean passability;
    private float r;
    private float g;
    private float b;
    private float rad;
    public int iBuilding;
    public static final float lighting = 400;
    public static float lighting_zoom = 400,lighting_zoom_2 = 200;
    public static void passability_detected() {
        for (int i = 0; i < Main.BuildingList.size(); i++) {
            for (int j = 0; j < Main.BuildingList.get(i).ConstructBuilding.length; j++) {
                for (int j2 = 0; j2 < Main.BuildingList.get(i).ConstructBuilding[j].length; j2++) {
                    Main.BlockList2D.get(j+Main.BuildingList.get(i).yMatrix).get(j2+Main.BuildingList.get(i).xMatrix).passability =
                            Main.BuildingList.get(i).ConstructBuilding[j][j2];
                    if(Main.BuildingList.get(i).ConstructBuilding[j][j2]) {
                        Main.BlockList2D.get(j+Main.BuildingList.get(i).yMatrix).get(j2+Main.BuildingList.get(i).xMatrix).render_block = VoidUpdate;
                        Main.BlockList2D.get(j+Main.BuildingList.get(i).yMatrix).get(j2+Main.BuildingList.get(i).xMatrix).iBuilding = i;

                    }
                }
            }
        }
    }
    protected final void block_xy(){
        this.x_center = this.x +Main.width_block/2;
        this.y_center = this.y +Main.height_block/2;
    }
    private boolean rect_collision(int x1,int y1,int width,int height,
                                  int x2,int y2,int width2,int height2,double rotation_2){

        Rectangle rect1 = new Rectangle(x1,y1,width,height); // Прямоугольник 1
        Rectangle rect2 = new Rectangle(x2,y2,width2,height2); // Прямоугольник 2

        // Создаем аффинное преобразование для поворота
        AffineTransform transform2 = new AffineTransform();
        transform2.rotate(Math.toRadians(rotation_2), rect2.getCenterX(), rect2.getCenterY());

        // Преобразование прямоугольников с учетом поворота
        Area area1 = new Area(rect1);
        Area area2 = new Area(rect2);
        area2.transform(transform2);

        // Вычисление пересечения двух преобразованных прямоугольников
        area1.intersect(area2);

        // Проверка наличия пересечения
        //Rectangle intersection = area1.getBounds();
        //System.out.println("Прямоугольники пересекаются. Результат: " + intersection);
        return !area1.isEmpty();
    }

    public void update(){
        int[]xy = Main.RC.render_objZoom(this.x,this.y);
        render_block.render(xy[0],xy[1]);
    }
    public void updateTick(int ix,int iy){
        int[]xy = Main.RC.render_objZoom(this.x,this.y);
        render_block.renderTick(xy[0],xy[1],ix,iy);
    }
    public void render(){
    }
    public void all_action(){

    }
    protected final void UpdateAir(){
        if(h.size() != 0) {
            for (i = 0;i<h.size();i++) {
                r += rgbl.get(i)[0] * Main.radius_air_max_zoom / h.get(i);
                g += rgbl.get(i)[1] * Main.radius_air_max_zoom / h.get(i);
                b += rgbl.get(i)[2] * Main.radius_air_max_zoom / h.get(i);
            }
            rad = ((float) radius/lighting_zoom);
            Main.Render.setColor(r/rad,g/rad,b/rad,lightFlame);
            Main.Render.rect(x,y,Main.width_block_air,Main.height_block_air);
            rgbl.clear();
            h.clear();
            r = 0;g =0;b=0;
        } else{
            Main.Render.setColor(0, 0, 0,lightTotal);
            Main.Render.rect(x,y,Main.width_block_air,Main.height_block_air);
        }
    }
    public static void LightingAir(int xZOOM, int yZOOM, float[] RGB){
        int x_min = (int) ((xZOOM-lighting_zoom)/Main.width_block_air);
        int x_max = (int) ((xZOOM+lighting_zoom)/Main.width_block_air);
        int y_min = (int) ((yZOOM-lighting_zoom)/Main.height_block_air);
        int y_max = (int) ((yZOOM+lighting_zoom)/Main.height_block_air);
        if(x_min>Main.xMaxAir){
            return;
        }
        if(x_max<0){
            return;
        }
        if(y_min>Main.yMaxAir){
            return;
        }
        if(y_max<0){
            return;
        }


        if(x_min<0){
            x_min = 0;
        }
        if (x_max>Main.xMaxAir) {
            x_max = Main.xMaxAir;
        }


        if(y_min<0){
            y_min = 0;
        }
        if (y_max>Main.yMaxAir) {
            y_max = Main.yMaxAir;
        }
        for (int i = y_min; i < y_max; i++) {
                for (int i2 = x_min; i2 < x_max; i2++) {
                    int gh = (int) sqrt(pow2(xZOOM - Main.AirList.get(i).get(i2).x) + pow2(yZOOM - Main.AirList.get(i).get(i2).y));
                    if (gh < lighting_zoom) {
                        Main.AirList.get(i).get(i2).rgbl.add(RGB);
                        Main.AirList.get(i).get(i2).h.add(gh);
                        if (Main.AirList.get(i).get(i2).radius == NULL || Main.AirList.get(i).get(i2).radius > gh) {
                            Main.AirList.get(i).get(i2).radius = gh;
                        }
                    }

                }
            }
    }




}
