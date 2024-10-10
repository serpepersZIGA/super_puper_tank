package com.mygdx.game.metod;

import com.mygdx.game.main.Main;

public class render_center {
    public double x,y,x2,y2;
    public double width_2 = (double) Main.screenWidth/2,height_2 = (double) Main.screenHeight/2;
    public render_center(double x,double y){
        this.x = x;
        this.y = y;
    }
    public void metod(){
        this.x2 = this.x -this.width_2/ Main.zoom;
        this.y2 = this.y -this.height_2/ Main.zoom;
    }
    public double[] render_obj(double x_obj,double y_obj){
        x_obj -= this.x2;
        y_obj -= this.y2;
        return new double[]{x_obj,y_obj};
    }
    public int[] render_obj(int x_obj,int y_obj){
        x_obj -= (int)this.x2;
        y_obj -= (int)this.y2;
        return new int[]{x_obj,y_obj};
    }

}
