package com.mygdx.game.method;

import com.mygdx.game.main.Main;

import static com.mygdx.game.main.Main.UpdateBlockReg;

public class RenderCenter {
    public double x,y,x2,y2;
    public double width_2 = (double) Main.screenWidth/2,height_2 = (double) Main.screenHeight/2;
    public double width_render = Main.screenWidth,height_render = Main.screenHeight;
    public int render_x_max,render_x_min,render_y_max,render_y_min;
    public int block_i_x_max,block_i_y_max;
    public RenderCenter(double x, double y){
        this.x = x;
        this.y = y;
    }
    public void const_xy_block(){
        block_i_x_max = Main.BlockList2D.get(0).size()-2;
        block_i_y_max = Main.BlockList2D.size()-2;
    }
    public void metod(){
        this.x2 = this.x -this.width_2/ Main.Zoom;
        this.y2 = this.y -this.height_2/ Main.Zoom;
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
    public void render_block(){
        Main.TickBlock +=1;
        render_x_max = (int)((x2+width_render/Main.Zoom)/Main.width_block);
        render_x_min = (int)(((x2-width_render/Main.Zoom)/Main.width_block));
        if(render_x_min <0){render_x_min =0;}
        if(render_x_max >block_i_x_max){render_x_max = block_i_x_max;}
        render_y_max = (int)((y2+height_render/Main.Zoom)/Main.height_block);
        render_y_min = (int)((y2-height_render/Main.Zoom)/Main.height_block);
        if(render_y_min <0){render_y_min = 0;}
        if(render_y_max >block_i_y_max){render_y_max = block_i_y_max;}
        if(Main.TickBlock != Main.TickBlockMax) {
            for (int iy = render_y_min; iy < render_y_max; iy++) {
                for (int ix = render_x_min; ix < render_x_max; ix++) {
                    Main.BlockList2D.get(iy).get(ix).update();
                }
            }
        }
        else{
            for (int iy = render_y_min; iy < render_y_max; iy++) {
                for (int ix = render_x_min; ix < render_x_max; ix++) {
                    Main.BlockList2D.get(iy).get(ix).update();
                    if(rand.rand(20) == 1) {
                        Main.BlockList2D.get(iy).get(ix).render_block = UpdateBlockReg.Update1;
                    }

                }
            }
            Main.TickBlock = 0;

        }

    }

}
