package com.mygdx.game.main;

import com.mygdx.game.particle.acid;
import com.mygdx.game.particle.flame_spawn;

import java.awt.*;


public class action_game_client implements Runnable{
    int i;
    @Override
    public void run() {

        try {
            Main.rc.get(0).metod();}
        catch (Exception ignored){}
        if(Main.player_obj.size()==0){
            if(Main.press_w){
                Main.rc.get(0).y -= 10;
            }
            if(Main.press_s){
                Main.rc.get(0).y += 10;
            }
            if(Main.press_a){
                Main.rc.get(0).x -= 10;
            }
            if(Main.press_d){
                Main.rc.get(0).x += 10;
            }
            try {

                boolean left_mouse = true;
                boolean right_mouse = true;
                if (left_mouse) {
                    double mouse_x = (((MouseInfo.getPointerInfo().getLocation().getX() - 18))*1.23)/ Main.zoom + Main.rc.get(0).x2;
                    double mouse_y = (((MouseInfo.getPointerInfo().getLocation().getY() - 45)*1.23))/ Main.zoom + Main.rc.get(0).y2;
                    //main.Main.bang_obj.add(new particle.bang(mouse_x,mouse_y,new Color(236,124,38),12));
                    Main.flame_spawn.add(new flame_spawn(mouse_x,mouse_y));

                }
                if (right_mouse) {
                    double mouse_x = (((MouseInfo.getPointerInfo().getLocation().getX() - 18))*1.23)/ Main.zoom + Main.rc.get(0).x2;
                    double mouse_y = (((MouseInfo.getPointerInfo().getLocation().getY() - 45)*1.23))/ Main.zoom + Main.rc.get(0).y2;
                    //main.Main.bang_obj.add(new particle.bang(mouse_x,mouse_y,new Color(236,124,38),12));
                    Main.liquid_obj.add(new acid(mouse_x/1.23,mouse_y/1.23));
                    //main.Main.liquid_obj.add(new particle.acid(mouse_x/1.23,mouse_y/1.23));
                    //main.Main.liquid_obj.add(new particle.acid(mouse_x/1.23,mouse_y/1.23));
                    //main.Main.liquid_obj.add(new particle.acid(mouse_x/1.23,mouse_y/1.23));

                }
            }
            catch(Exception es){

            }

        }
        //boolean[]mouse_e = new metod.mouse_control().mouse_event();
//        for (i= 0; i< main.Main.debris.size(); i++){
//            main.Main.debris.get(i).all_action(i);
//        }
        //main.Main.metod.control_client.get(0).iteration_control_client();
        for (i= 0; i< Main.bang_obj.size(); i++){
            Main.bang_obj.get(i).all_action(i);}
        for (i= 0; i< Main.liquid_obj.size(); i++){
            Main.liquid_obj.get(i).all_action(i);}
//        for (i = 0; i< main.Main.build.build.size(); i++){
//            main.Main.build.build.get(i).all_action(i);}
//        for (i = 0; i< main.Main.bull_obj.size(); i++){
//            main.Main.bull_obj.get(i).all_action(i);}
        for (i = 0; i< Main.flame_static_obj.size(); i++){
            Main.flame_static_obj.get(i).all_action(i);}
        for (i = 0; i< Main.flame_obj.size(); i++){
            Main.flame_obj.get(i).all_action(i);}
//        for (i = 0; i< main.Main.soldat_obj.size(); i++){
//            main.Main.soldat_obj.get(i).all_action(i);}
        for (i = 0; i< Main.flame_particle_obj.size(); i++){
            Main.flame_particle_obj.get(i).all_action(i);}
        for (i= 0; i< Main.flame_spawn.size(); i++){
            Main.flame_spawn.get(i).all_action(i);
        }
        Main.player_obj.get(0).all_action_client_1();
        Main.player_obj.get(1).all_action_client_1();

//        for (i = 0; i < main.Main.player_obj.size(); i++) {
//            main.Main.player_obj.get(i).all_action(i);
//        }
//        for (i = 0; i < main.Main.enemy_obj.size(); i++) {
//            main.Main.enemy_obj.get(i).all_action(i);
//        }
    }
}
