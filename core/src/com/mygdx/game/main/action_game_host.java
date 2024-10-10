package com.mygdx.game.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.game.particle.acid;
import com.mygdx.game.particle.flame_spawn;

import java.awt.*;

import static com.mygdx.game.main.Main.*;

public class action_game_host {
    private int i;
    private static int timer = 0;
    public action_game_host() {
        rc.get(0).metod();
        if(Main.player_obj.size()==0){
            if(Main.press_w){
                Main.rc.get(0).y += 10;
            }
            if(Main.press_s){
                Main.rc.get(0).y -= 10;
            }
            if(Main.press_a){
                Main.rc.get(0).x -= 10;
            }
            if(Main.press_d){
                Main.rc.get(0).x += 10;
            }
            try {
                if(timer <= 0) {

                    if (left_mouse) {
                        Main.flame_spawn.add(new flame_spawn(mouse_x / Main.zoom + Main.rc.get(0).x2, mouse_y / Main.zoom + Main.rc.get(0).y2));
                        timer = 60;


                    }
                    if (right_mouse) {
                        //main.Main.bang_obj.add(new particle.bang(mouse_x,mouse_y,new Color(236,124,38),12));
                        Main.liquid_obj.add(new acid(mouse_x / Main.zoom + Main.rc.get(0).x2, mouse_y / Main.zoom + Main.rc.get(0).y2));
                        //main.Main.liquid_obj.add(new particle.acid(mouse_x/1.23,mouse_y/1.23));
                        //main.Main.liquid_obj.add(new particle.acid(mouse_x/1.23,mouse_y/1.23));
                        //main.Main.liquid_obj.add(new particle.acid(mouse_x/1.23,mouse_y/1.23));

                    }
                }
                else{timer-= 1;}
            }
            catch(Exception es){

            }

        }
        //boolean[]mouse_e = new metod.mouse_control().mouse_event();
        //Main.player_obj.get(1).all_action_client(Main.left_mouse_client, Main.right_mouse_client, Main.mouse_x_client,
                //Main.mouse_y_client, Main.press_w_client, Main.press_a_client, Main.press_s_client, Main.press_d_client);
        Gdx.gl.glClearColor(0, 0 ,0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        render.setProjectionMatrix(camera.combined);
        batch.begin();
        render.begin(ShapeRenderer.ShapeType.Filled);
        for (i= 0; i< matrix.size(); i++){
            for (int i2= 0; i2< matrix.get(i).size(); i2++) {
                matrix.get(i).get(i2).all_action();
            }
        }
        batch.end();
        for (i= 0; i< Main.liquid_obj.size(); i++){
            Main.liquid_obj.get(i).all_action(i);}
        for (i = 0; i< Main.flame_static_obj.size(); i++){
            Main.flame_static_obj.get(i).all_action(i);}
        for (i = 0; i< Main.flame_obj.size(); i++){
            Main.flame_obj.get(i).all_action(i);}
        for (i = 0; i< Main.flame_particle_obj.size(); i++){
            Main.flame_particle_obj.get(i).all_action(i);}
        for (i = 0; i< Main.bull_obj.size(); i++){
            if(Main.bull_obj.get(i).height == 1) {
                Main.bull_obj.get(i).all_action(i);
            }
        }
        render.end();
        batch.begin();
        render.begin(ShapeRenderer.ShapeType.Filled);
        for (i = 0; i< Main.soldat_obj.size(); i++){
            Main.soldat_obj.get(i).all_action(i);}
        for (i= 0; i< Main.flame_spawn.size(); i++){
            Main.flame_spawn.get(i).all_action(i);
        }
        for (i = 0; i < Main.player_obj.size(); i++) {
            Main.player_obj.get(i).all_action(i);
        }
        for (i= 0; i< Main.debris.size(); i++){
            Main.debris.get(i).all_action(i);
        }
        for (i = 0; i< Main.build.size(); i++){
            Main.build.get(i).all_action(i);}
        for (i = 0; i < Main.enemy_obj.size(); i++) {
            Main.enemy_obj.get(i).all_action(i);
        }
        render.end();

        batch.draw(new Texture("badlogic.jpg"),100,100,100,100);
        //batch.end();
        render.begin(ShapeRenderer.ShapeType.Filled);

        for (i = 0; i< Main.bull_obj.size(); i++){
            if(Main.bull_obj.get(i).height == 2) {
                Main.bull_obj.get(i).all_action(i);
            }
        }
        for (i= 0; i< block_air.size(); i++){
            block_air.get(i).all_action();
        }
        for (i= 0; i< Main.bang_obj.size(); i++){
            Main.bang_obj.get(i).all_action(i);}
        for (i= 0; i< player_obj.size(); i++){
            player_obj.get(i).update();
        }
        for (i= 0; i< enemy_obj.size(); i++){
            enemy_obj.get(i).update();
        }
        for (i = 0; i< Main.build.size(); i++){
            Main.build.get(i).xy_light_render.clear();
        }
        render.end();
        batch.end();
    }





}
