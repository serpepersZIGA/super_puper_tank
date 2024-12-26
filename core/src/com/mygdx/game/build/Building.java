package com.mygdx.game.build;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.block.Block;
import com.mygdx.game.main.Main;


import com.mygdx.game.method.rand;
import Content.Particle.FlameStatic;
import com.mygdx.game.particle.Particle;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;


public abstract class Building implements Serializable {
    public int width,height,x,y,time_flame,width_2,height_2,x_rend,y_rend,width_render,height_render,brightness_max = 240,brightness;
    public static float[]rgb = {(float)1/255 * 236, (float) 1/255 * 124, (float) 1/255 * 38};
    //public float[]rgb = {(float)1/255 * rand.rand(20,250), (float) 1/255 * rand.rand(20,250), (float) 1/255 * rand.rand(20,250)};
    public Sprite build_image;
    private int distance_light,density_light_x,density_light_y;
    public ArrayList<int[]>xy_light = new ArrayList<>();
    public ArrayList<int[]>xy_light_render = new ArrayList<>();
    public BuildType name;
    public boolean[][]ConstructBuilding;
    public int ConstructX,ConstructY,xMatrix,yMatrix;
    public UpdateBuilding RenderBuilding;



    protected void Data(){
        DataCollision();
        distance_light = 100;
        density_light_y=(int)((double)height/distance_light);
        density_light_x=(int)((double)width/distance_light);
        size_light();
    }
    private void DataCollision(){
        ConstructX = ConstructBuilding[0].length;
        ConstructY =ConstructBuilding.length;
        this.width = Main.width_block*ConstructX;
        this.height = Main.height_block*ConstructY;
        xMatrix = this.x/Main.width_block;
        yMatrix = this.y/Main.height_block;
        this.x = Main.BlockList2D.get(yMatrix).get(xMatrix).x;
        this.y = Main.BlockList2D.get(yMatrix).get(xMatrix).y;
        this.width_2 = this.width/2;
        this.height_2 = this.height/2;
    }
    private void size_light(){
        int x_light = x;
        int y_light;
        for(int i = 0;i<density_light_x;i++){
            x_light += distance_light;
            y_light = y-100;
            for(int j = 0;j<density_light_y;j++){
                y_light += distance_light;
                xy_light.add(new int[]{x_light,y_light});
                xy_light_render.add(Main.RC.render_obj(x_light,y_light));
            }
        }
    }
    public void all_action(int i){
        this.update();
    }
    public void iteration_light_build(){
        for (int[] ints : xy_light) {
            xy_light_render.add(Main.RC.render_obj(ints[0], ints[1]));
        }
    }
    public void update(){
    }
    public void center_render(){
        int[]xy = Main.RC.render_obj(this.x,this.y);
        this.x_rend = (int)(xy[0]*Main.Zoom);
        this.y_rend = (int)(xy[1]*Main.Zoom);
    }


    public void flame_build(LinkedList<Particle> part){
        if(this.time_flame>0){
            iteration_light_build();
            for (int[] ints : xy_light_render) {
                Block.LightingAir((int) (ints[0] * Main.Zoom), (int) (ints[1] * Main.Zoom), rgb);
            }
            this.brightness = brightness_max;
            if(rand.rand(4)== 1) {
                int z = rand.rand(4);
                this.time_flame -= 1;
                    switch (z) {
                        case 0:{
                            part.add(new FlameStatic(this.x + rand.rand(this.width), this.y + this.height));break;}
                        case 1:{
                            part.add(new FlameStatic(this.x + rand.rand(this.width), this.y));break;}
                        case 2:{
                            part.add(new FlameStatic(this.x + this.width, this.y + rand.rand(height)));break;}
                        case 3:{
                            part.add(new FlameStatic(this.x, this.y + rand.rand(height)));break;}
                    }
            }
        }
        else{
            this.brightness = 0;
        }
    }

}
