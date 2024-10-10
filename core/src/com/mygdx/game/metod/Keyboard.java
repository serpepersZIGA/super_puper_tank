package com.mygdx.game.metod;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.mygdx.game.block.Block;
import com.mygdx.game.build.Build;
import com.mygdx.game.bull.Bull;
import com.mygdx.game.main.Main;
import com.mygdx.game.particle.particle;
import com.mygdx.game.soldat.Soldat;
import com.mygdx.game.transport.Transport;

public class Keyboard extends InputAdapter{

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.W) {
            Main.press_w = true;
        }
        if (keycode ==Input.Keys.S ) {
            Main.press_s = true;
        }
        if (keycode ==Input.Keys.A) {
            Main.press_a = true;
        }
        if (keycode ==Input.Keys.D) {
            Main.press_d = true;
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode ==Input.Keys.W) {
            Main.press_w = false;
        }
        if (keycode ==Input.Keys.S) {
            Main.press_s = false;
        }
        if (keycode ==Input.Keys.A) {
            Main.press_a = false;
        }
        if (keycode ==Input.Keys.D) {
            Main.press_d = false;
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
            Main.left_mouse = true;
        }
        if(button == Input.Buttons.RIGHT) {
            Main.right_mouse = true;
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if(button == Input.Buttons.LEFT) {
            Main.left_mouse = false;
        }
        if(button == Input.Buttons.RIGHT) {
            Main.right_mouse = false;
        }
        return false;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        Main.mouse_x = screenX;
        Main.mouse_y = (screenY-Main.screenHeight)*-1;
        return false;
    }


    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        Main.mouse_x = screenX;
        Main.mouse_y = (screenY-Main.screenHeight)*-1;
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        Main.mouse_x = screenX;
        Main.mouse_y = (screenY-Main.screenHeight)*-1;
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        if(amountY > 0) {
            if (Main.zoom >0.7) {
                Main.zoom -= 0.1;
                zoom_const();
            }
        } else {
            if (Main.zoom < 2) {
                Main.zoom += 0.1;
                zoom_const();
            }
        }

        return false;
    }
    public void zoom_const(){
        for(particle particle : Main.flame_particle_obj){
            particle.size_render = (int)(particle.size* Main.zoom);
        }
        for(int i = 0;i<Main.matrix.size();i++) {
            for (Block block : Main.matrix.get(i)) {
                block.width_zoom = (int) (block.width * Main.zoom);
                block.height_zoom = (int) (block.height * Main.zoom);
            }
        }
        for(Build build : Main.build){
            build.width_render = (int)(build.width* Main.zoom);
            build.height_render = (int)(build.height* Main.zoom);
        }
        for(particle particle : Main.flame_obj){
           particle.size_render = (int)(particle.size* Main.zoom);
        }
        for(particle particle : Main.flame_static_obj){
            particle.size_render = (int)(particle.size* Main.zoom);
        }
        for(particle particle : Main.liquid_obj){
            particle.size_render = (int)(particle.size* Main.zoom);
        }
        for(particle particle : Main.bang_obj){
            particle.size_render = (int)(particle.size* Main.zoom);
        }
        for(particle particle : Main.flame_particle_obj){
            particle.size_render = (int)(particle.size* Main.zoom);
        }
        for(particle particle : Main.bang_obj){
            particle.size_render = (int)(particle.size* Main.zoom);
        }
        for(Bull bull : Main.bull_obj){
            bull.size_render = (int)(bull.size* Main.zoom);
        }
        for(Transport tr : Main.player_obj){
            tr.width_corpus_zoom = (int)(tr.corpus_width*Main.zoom);
            tr.height_corpus_zoom = (int)(tr.corpus_height*Main.zoom);
            tr.width_tower_zoom = (int)(tr.tower_width*Main.zoom);
            tr.height_tower_zoom = (int)(tr.tower_height*Main.zoom);
            tr.const_x_corpus = (int)(tr.corpus_width_2*Main.zoom);
            tr.const_y_corpus = (int)(tr.corpus_height_2*Main.zoom);
            tr.const_x_tower = (int)(tr.const_tower_x*Main.zoom);
            tr.const_y_tower = (int)(tr.const_tower_y*Main.zoom);

        }
        for(Transport tr : Main.enemy_obj){
            tr.width_corpus_zoom = (int)(tr.corpus_width*Main.zoom);
            tr.height_corpus_zoom = (int)(tr.corpus_height*Main.zoom);
            tr.width_tower_zoom = (int)(tr.tower_width*Main.zoom);
            tr.height_tower_zoom = (int)(tr.tower_height*Main.zoom);
            tr.const_x_corpus = (int)(tr.corpus_width_2*Main.zoom);
            tr.const_y_corpus = (int)(tr.corpus_height_2*Main.zoom);
            tr.const_x_tower = (int)(tr.const_tower_x*Main.zoom);
            tr.const_y_tower = (int)(tr.const_tower_y*Main.zoom);
        }
        for(Transport tr : Main.debris){
            tr.width_corpus_zoom = (int)(tr.corpus_width*Main.zoom);
            tr.height_corpus_zoom = (int)(tr.corpus_height*Main.zoom);
            tr.const_x_corpus = (int)(tr.corpus_width_2*Main.zoom);
            tr.const_y_corpus = (int)(tr.corpus_height_2*Main.zoom);

        }
        for(Soldat sl : Main.soldat_obj){
            sl.width_render = (int)(sl.width*Main.zoom);
            sl.height_render = (int)(sl.height*Main.zoom);

        }
    }
}