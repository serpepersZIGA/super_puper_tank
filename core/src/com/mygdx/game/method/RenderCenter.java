package com.mygdx.game.method;

import com.mygdx.game.block.UpdateRegister;
import com.mygdx.game.main.Main;

public class RenderCenter {
    public float x,y,x2,y2;
    public int ixCam,iyCam,ixCamBuff,iyCamBuff;
    public float width_2 = Main.screenWidth/2f,height_2 = Main.screenHeight/2f,width_2_zoom = Main.screenWidth/2f
            ,height_2_zoom = Main.screenHeight/2f;
    public float WidthRender = Main.screenWidth, HeightRender = Main.screenHeight;
    public int render_x_max,render_x_min,render_y_max,render_y_min;
    public int block_i_x_max,block_i_y_max;
    public float WidthRenderZoom,HeightRenderZoom;
    public RenderCenter(float x, float y){
        this.x = x;
        this.y = y;
    }
    public void const_xy_block(){
        block_i_x_max = Main.BlockList2D.get(0).size()-2;
        block_i_y_max = Main.BlockList2D.size()-2;
    }
    public void metod(){
        this.x2 = this.x -this.width_2_zoom;
        this.y2 = this.y -this.height_2_zoom;
        ixCamBuff = (int) (x2/Main.width_block);
        iyCamBuff = (int) (y2/Main.height_block);
    }
    public float[] WindowSynchronization(float x_obj, float y_obj){
        x_obj -= this.x2;
        y_obj -= this.y2;
        return new float[]{x_obj,y_obj};
    }
    public int[] WindowSynchronization(int x_obj, int y_obj){
        x_obj -= (int)this.x2;
        y_obj -= (int)this.y2;
        return new int[]{x_obj,y_obj};
    }
    public int[] render_objZoom(int x_obj,int y_obj){
        x_obj -= this.x2;
        y_obj -= this.y2;
        return new int[]{(int) (x_obj*Main.Zoom), (int) (y_obj*Main.Zoom)};
    }
    public float[] render_objZoom(float x_obj,float y_obj){
        x_obj -= this.x2;
        y_obj -= this.y2;
        return new float[]{x_obj*Main.Zoom,y_obj*Main.Zoom};
    }
    public void render_block(){
        if(ixCam!= ixCamBuff||iyCam!= iyCamBuff) {
            CameraMapConf();
            ixCam = ixCamBuff+1;
            iyCam = iyCamBuff+1;
        }

        if (Main.TickBlock < Main.TickBlockMax) {
            for (int iy = render_y_min; iy < render_y_max; iy++) {
                for (int ix = render_x_min; ix < render_x_max; ix++) {
                    Main.BlockList2D.get(iy).get(ix).update();
                }
            }
        } else {
            for (int iy = render_y_min; iy < render_y_max; iy++) {
                for (int ix = render_x_min; ix < render_x_max; ix++) {
                    Main.BlockList2D.get(iy).get(ix).update();
                    if (Main.BlockList2D.get(iy).get(ix).render_block == UpdateRegister.DirtUpdate) {
                        if (rand.rand(20) == 1) {
                            Main.BlockList2D.get(iy).get(ix).render_block = UpdateRegister.GrassUpdate;
                        }
                    }
                }
            }
            Main.TickBlock = 0;

        }

    }
    private void CameraMapConf(){
        Main.TickBlock +=1;
        render_x_max = (int)((x2+ WidthRenderZoom)/Main.width_block);
        render_x_min = (int)(((x2- WidthRenderZoom)/Main.width_block));
        if(render_x_min <0){render_x_min =0;}
        if(render_x_max >block_i_x_max){render_x_max = block_i_x_max;}
        render_y_max = (int)((y2+ HeightRenderZoom)/Main.height_block);
        render_y_min = (int)((y2- HeightRenderZoom)/Main.height_block);
        if(render_y_min <0){render_y_min = 0;}
        if(render_y_max >block_i_y_max){render_y_max = block_i_y_max;}
    }
    public void BuildingIteration(){
        for (int i = 0; i< Main.BuildingList.size(); i++){
            if((render_x_max>Main.BuildingList.get(i).RightTopPointX &render_x_min<Main.BuildingList.get(i).RightTopPointX &
            render_y_max>Main.BuildingList.get(i).RightTopPointY &render_y_min<Main.BuildingList.get(i).RightTopPointY)||
            (render_x_max>Main.BuildingList.get(i).xMatrix &render_x_min<Main.BuildingList.get(i).xMatrix &
            render_y_max>Main.BuildingList.get(i).yMatrix &render_y_min<Main.BuildingList.get(i).yMatrix)
            ) {
                Main.BuildingList.get(i).all_action(i);
                Main.BuildingList.get(i).xy_light_render.clear();
            }

        }
    }

}
