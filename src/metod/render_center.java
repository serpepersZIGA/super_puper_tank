package metod;

import main.Game;
import main.display;

public class render_center {
    public double x,y,x2,y2;
    public double width_2 = (double) display.width/2,height_2 = (double)display.height/2;
    public render_center(double x,double y){
        this.x = x;
        this.y = y;
    }
    public void metod(){
        this.x2 = this.x -this.width_2/ Game.zoom;
        this.y2 = this.y -this.height_2/ Game.zoom;
    }
    public double[] render_obj(double x_obj,double y_obj){
        x_obj -= this.x2;
        y_obj -= this.y2;
        return new double[]{x_obj,y_obj};
    }
    public int render_size(double size){
        return (int)(size* Game.zoom);
    }
    public int render_size(int size){
        return (int)(size* Game.zoom);
    }

}
