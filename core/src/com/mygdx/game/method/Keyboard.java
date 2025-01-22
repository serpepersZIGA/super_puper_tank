package com.mygdx.game.method;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.mygdx.game.block.Block;
import com.mygdx.game.build.Building;
import com.mygdx.game.bull.Bull;
import com.mygdx.game.main.Main;
import com.mygdx.game.menu.button.Button;
import com.mygdx.game.particle.Particle;
import com.mygdx.game.soldat.Soldat;
import com.mygdx.game.transport.Transport;

import static com.mygdx.game.block.Block.lighting;
import static com.mygdx.game.main.Main.*;

public class Keyboard extends InputAdapter{
    public static boolean PressW,PressA,PressS,PressD,PressUP,PressDown;
    public static boolean LeftMouse, RightMouse,LeftMouseClick, RightMouseClick;
    public static int MouseX,MouseY;
    private static float ZoomMax,ZoomMin;
    public static void ZoomMaxMin(){
        ZoomMax = 2;
        ZoomMin = (float) (0.4);
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.W) {
            PressW = true;
        }
        if (keycode ==Input.Keys.S ) {
            PressS = true;
        }
        if (keycode ==Input.Keys.A) {
            PressA = true;
        }
        if (keycode ==Input.Keys.D) {
            PressD = true;
        }
        if (keycode == Input.Keys.UP) {
            Button.YList += 4;
            PressUP = true;
        }
        if (keycode == Input.Keys.DOWN) {
            Button.YList -= 4;
            PressDown = true;
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode ==Input.Keys.W) {
            PressW = false;
        }
        if (keycode ==Input.Keys.S) {
            PressS = false;
        }
        if (keycode ==Input.Keys.A) {
            PressA = false;
        }
        if (keycode ==Input.Keys.D) {
            PressD = false;
        }
        if (keycode == Input.Keys.UP) {
            PressUP = false;
        }
        if (keycode == Input.Keys.DOWN) {
            PressDown = false;
        }
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if(button == Input.Buttons.LEFT) {
            LeftMouse = true;
        }
        if(button == Input.Buttons.RIGHT) {
           RightMouse = true;
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if(button == Input.Buttons.LEFT) {
            LeftMouse = false;
            LeftMouseClick = true;
        }
        if(button == Input.Buttons.RIGHT) {
            RightMouse = false;
            RightMouseClick = true;
        }
        return false;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        MouseX = screenX;
        MouseY = (screenY-Main.screenHeight)*-1;
        return false;
    }


    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        MouseX = screenX;
        MouseY = (screenY-Main.screenHeight)*-1;
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        MouseX = screenX;
        MouseY = (screenY-Main.screenHeight)*-1;
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        if(amountY > 0) {
            if (Main.Zoom >ZoomMin) {
                Main.Zoom -= 0.1;
                zoom_const();
            }
        } else {
            if (Main.Zoom < ZoomMax) {
                Main.Zoom += 0.1;
                zoom_const();
            }
        }

        return false;
    }
    public void zoom_const(){
        for(Particle particle : Main.FlameParticleList){
            particle.size_render = (int)(particle.size* Main.Zoom);
        }

        RC.WidthRenderZoom = RC.WidthRender /Main.Zoom;
        RC.HeightRenderZoom = RC.HeightRender /Main.Zoom;
        RC.WidthRenderZoom2 = RC.WidthRenderZoom /2;
        RC.HeightRenderZoom2 = RC.HeightRenderZoom /2;
        RC.width_2_zoom = RC.width_2/Main.Zoom;
        RC.height_2_zoom = RC.height_2/Main.Zoom;
        RC.CameraMapConf();
        RC.cam_x_width = (int) (RC.WidthRenderZoom/Main.width_block);
        RC.cam_y_height= (int) (RC.HeightRenderZoom/Main.height_block);

        Main.width_block_zoom = (int) (Main.width_block_render * Main.Zoom);
        Main.height_block_zoom = (int) (Main.height_block_render * Main.Zoom);
        radius_air_max_zoom = radius_air_max*Main.Zoom;
        Block.lighting_zoom = lighting*Main.Zoom;
        Block.lighting_zoom_2 = Block.lighting_zoom / 2;
        for(int iy = 0;iy< BlockList2D.size();iy++){
            for(int ix = 0;ix< BlockList2D.get(iy).size();ix++) {
                if(BlockList2D.get(iy).get(ix).objMap != null) {
                    BlockList2D.get(iy).get(ix).objMap.width_render = (int) (BlockList2D.get(iy).get(ix).objMap.width * Main.Zoom);
                    BlockList2D.get(iy).get(ix).objMap.height_render = (int) (BlockList2D.get(iy).get(ix).objMap.height * Main.Zoom);
                }
            }
        }
        for(Building building : Main.BuildingList){
            building.width_render = (int)(building.width* Main.Zoom);
            building.height_render = (int)(building.height* Main.Zoom);
        }
        for(Particle particle : Main.FlameList){
           particle.size_render = (int)(particle.size* Main.Zoom);
        }
        for(Particle particle : Main.FlameStaticList){
            particle.size_render = (int)(particle.size* Main.Zoom);
        }
        for(Particle particle : Main.LiquidList){
            particle.size_render = (int)(particle.size* Main.Zoom);
        }
        for(Particle particle : Main.BangList){
            particle.size_render = (int)(particle.size* Main.Zoom);
        }
        for(Particle particle : Main.FlameParticleList){
            particle.size_render = (int)(particle.size* Main.Zoom);
        }
        for(Particle particle : Main.BangList){
            particle.size_render = (int)(particle.size* Main.Zoom);
        }

        for(Bull bull : Main.BullList){
            bull.size_render = (int)(bull.size* Main.Zoom);
        }
        for(Transport tr : Main.PlayerList){
            tr.corpus_width_zoom = (int)(tr.corpus_width*Main.Zoom);
            tr.corpus_height_zoom = (int)(tr.corpus_height*Main.Zoom);
            tr.width_tower_zoom = (int)(tr.width_tower *Main.Zoom);
            tr.height_tower_zoom = (int)(tr.height_tower *Main.Zoom);
            tr.const_x_corpus = (int)(tr.corpus_width_2*Main.Zoom);
            tr.const_y_corpus = (int)(tr.corpus_height_2*Main.Zoom);
            tr.const_x_tower = (int)(tr.const_tower_x*Main.Zoom);
            tr.const_y_tower = (int)(tr.const_tower_y*Main.Zoom);
            for (Transport tower : tr.tower_obj){
                tower.width_tower_zoom = (int)(tower.width_tower *Main.Zoom);
                tower.height_tower_zoom = (int)(tower.height_tower *Main.Zoom);
                tower.const_x_tower = (int)(tower.const_tower_x*Main.Zoom);
                tower.const_y_tower = (int)(tower.const_tower_y*Main.Zoom);
            }

        }
        for(Transport tr : Main.EnemyList){
            tr.corpus_width_zoom = (int)(tr.corpus_width*Main.Zoom);
            tr.corpus_height_zoom = (int)(tr.corpus_height*Main.Zoom);
            tr.width_tower_zoom = (int)(tr.width_tower *Main.Zoom);
            tr.height_tower_zoom = (int)(tr.height_tower *Main.Zoom);
            tr.const_x_corpus = (int)(tr.corpus_width_2*Main.Zoom);
            tr.const_y_corpus = (int)(tr.corpus_height_2*Main.Zoom);
            tr.const_x_tower = (int)(tr.const_tower_x*Main.Zoom);
            tr.const_y_tower = (int)(tr.const_tower_y*Main.Zoom);
            for (Transport tower : tr.tower_obj){
                tower.width_tower_zoom = (int)(tower.width_tower *Main.Zoom);
                tower.height_tower_zoom = (int)(tower.height_tower *Main.Zoom);
                tower.const_x_tower = (int)(tower.const_tower_x*Main.Zoom);
                tower.const_y_tower = (int)(tower.const_tower_y*Main.Zoom);
            }

        }
        for(Transport tr : Main.DebrisList){
            tr.corpus_width_zoom = (int)(tr.corpus_width*Main.Zoom);
            tr.corpus_height_zoom = (int)(tr.corpus_height*Main.Zoom);
            tr.const_x_corpus = (int)(tr.corpus_width_2*Main.Zoom);
            tr.const_y_corpus = (int)(tr.corpus_height_2*Main.Zoom);

        }
        for(Soldat sl : Main.SoldatList){
            sl.width_render = (int)(sl.width*Main.Zoom);
            sl.height_render = (int)(sl.height*Main.Zoom);
        }
        Main.Option.size_y_indicator_zoom = (int) (Main.Option.size_y_indicator* Main.Zoom);
        Main.Option.size_x_indicator_zoom = (int) (Main.Option.size_x_indicator* Main.Zoom);

        Main.Option.const_hp_x_zoom = (int)(Main.Option.const_hp_x* Main.Zoom);
        Main.Option.const_hp_y_zoom= (int)(Main.Option.const_hp_y* Main.Zoom);
        Main.Option.const_reload_x_zoom = (int)(Main.Option.const_reload_x* Main.Zoom);
        Main.Option.const_reload_y_zoom = (int)(Main.Option.const_reload_y* Main.Zoom);
    }
    public static void ZoomConstTransport(){
        for(Transport tr : Main.PlayerList){
            tr.corpus_width_zoom = (int)(tr.corpus_width*Main.Zoom);
            tr.corpus_height_zoom = (int)(tr.corpus_height*Main.Zoom);
            tr.width_tower_zoom = (int)(tr.width_tower *Main.Zoom);
            tr.height_tower_zoom = (int)(tr.height_tower *Main.Zoom);
            tr.const_x_corpus = (int)(tr.corpus_width_2*Main.Zoom);
            tr.const_y_corpus = (int)(tr.corpus_height_2*Main.Zoom);
            tr.const_x_tower = (int)(tr.const_tower_x*Main.Zoom);
            tr.const_y_tower = (int)(tr.const_tower_y*Main.Zoom);
            for (Transport tower : tr.tower_obj){
                tower.width_tower_zoom = (int)(tower.width_tower *Main.Zoom);
                tower.height_tower_zoom = (int)(tower.height_tower *Main.Zoom);
                tower.const_x_tower = (int)(tower.const_tower_x*Main.Zoom);
                tower.const_y_tower = (int)(tower.const_tower_y*Main.Zoom);
            }


        }
        for(Transport tr : Main.EnemyList){
            tr.corpus_width_zoom = (int)(tr.corpus_width*Main.Zoom);
            tr.corpus_height_zoom = (int)(tr.corpus_height*Main.Zoom);
            tr.width_tower_zoom = (int)(tr.width_tower *Main.Zoom);
            tr.height_tower_zoom = (int)(tr.height_tower *Main.Zoom);
            tr.const_x_corpus = (int)(tr.corpus_width_2*Main.Zoom);
            tr.const_y_corpus = (int)(tr.corpus_height_2*Main.Zoom);
            tr.const_x_tower = (int)(tr.const_tower_x*Main.Zoom);
            tr.const_y_tower = (int)(tr.const_tower_y*Main.Zoom);
            for (Transport tower : tr.tower_obj){
                tower.width_tower_zoom = (int)(tower.width_tower *Main.Zoom);
                tower.height_tower_zoom = (int)(tower.height_tower *Main.Zoom);
                tower.const_x_tower = (int)(tower.const_tower_x*Main.Zoom);
                tower.const_y_tower = (int)(tower.const_tower_y*Main.Zoom);
            }

        }
        for(Transport tr : Main.DebrisList){
            tr.corpus_width_zoom = (int)(tr.corpus_width*Main.Zoom);
            tr.corpus_height_zoom = (int)(tr.corpus_height*Main.Zoom);
            tr.const_x_corpus = (int)(tr.corpus_width_2*Main.Zoom);
            tr.const_y_corpus = (int)(tr.corpus_height_2*Main.Zoom);

        }
    }
}