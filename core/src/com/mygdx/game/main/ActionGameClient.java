package com.mygdx.game.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import Content.Particle.Acid;
import Content.Particle.FlameSpawn;

import static com.mygdx.game.main.Main.*;
import static com.mygdx.game.main.ClientMain.Client;


public class ActionGameClient extends com.mygdx.game.main.ActionGame {

    int i;
    private static int timer = 0;
    public void action() {
        Main.RC.metod();
        if(Main.PlayerList.size()==0){
            if(Main.PressW){
                Main.RC.y += 10;
            }
            if(Main.PressS){
                Main.RC.y -= 10;
            }
            if(Main.PressA){
                Main.RC.x -= 10;
            }
            if(Main.PressD){
                Main.RC.x += 10;
            }
            try {
                if(timer <= 0) {

                    if (LeftMouse) {
                        Main.FlameSpawnList.add(new FlameSpawn(MouseX / Main.Zoom + Main.RC.x2, MouseY / Main.Zoom + Main.RC.y2));
                        timer = 60;


                    }
                    if (RightMouse) {
                        //main.Main.bang_obj.add(new particle.bang(mouse_x,mouse_y,new Color(236,124,38),12));
                        Main.LiquidList.add(new Acid(MouseX / Main.Zoom + Main.RC.x2, MouseY / Main.Zoom + Main.RC.y2));
                        //main.Main.liquid_obj.add(new particle.acid(mouse_x/1.23,mouse_y/1.23));
                        //main.Main.liquid_obj.add(new particle.acid(mouse_x/1.23,mouse_y/1.23));
                        //main.Main.liquid_obj.add(new particle.acid(mouse_x/1.23,mouse_y/1.23));

                    }
                }
                else{timer-= 1;}
            }
            catch(Exception ignored){

            }

        }


//        for (i = 0; i < main.Main.player_obj.size(); i++) {
//            main.Main.player_obj.get(i).all_action(i);
//        }
//        for (i = 0; i < main.Main.enemy_obj.size(); i++) {
//            main.Main.enemy_obj.get(i).all_action(i);
//        }
        Gdx.gl.glClearColor(0, 0 ,0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Batch.begin();
        Render.begin(ShapeRenderer.ShapeType.Filled);
        Main.RC.render_block();
//        for (i= 0; i< matrix.size(); i++){
//            for (int i2= 0; i2< matrix.get(i).size(); i2++) {
//                matrix.get(i).get(i2).all_action();
//            }
//        }
        Batch.end();
        for (i= 0; i< Main.LiquidList.size(); i++){
            Main.LiquidList.get(i).all_action(i);}
        for (i = 0; i< Main.FlameStaticList.size(); i++){
            Main.FlameStaticList.get(i).all_action(i);}
        for (i = 0; i< Main.FlameList.size(); i++){
            Main.FlameList.get(i).all_action(i);}
        for (i = 0; i< Main.FlameParticleList.size(); i++){
            Main.FlameParticleList.get(i).all_action(i);}
        for (i = 0; i< Main.BullList.size(); i++){
            if(Main.BullList.get(i).height == 1) {
                Main.BullList.get(i).all_action_client(i);
            }
        }
        Render.end();
        Batch.begin();
        Render.begin(ShapeRenderer.ShapeType.Filled);
        for (i = 0; i< Main.SoldatList.size(); i++){
            Main.SoldatList.get(i).all_action_client(i);}
        for (i= 0; i< Main.FlameSpawnList.size(); i++){
            Main.FlameSpawnList.get(i).all_action(i);
        }
        for(i = 0; i< PlayerList.size(); i++) {
            if(PlayerList.get(i).host) {
                Main.PlayerList.get(i).all_action_client_2(i);
            }
            else {
                Main.PlayerList.get(i).all_action_client_1(i);
            }
        }

        for (i= 0; i< Main.DebrisList.size(); i++){
            Main.DebrisList.get(i).all_action_client(i);
        }
        for (i = 0; i < Main.EnemyList.size(); i++) {
            Main.EnemyList.get(i).all_action_client(i);
        }
        for (i = 0; i< Main.BuildingList.size(); i++){
            Main.BuildingList.get(i).all_action(i);}
        Render.end();

        Batch.draw(new Texture("badlogic.jpg"),100,100,100,100);
        //batch.end();
        Render.begin(ShapeRenderer.ShapeType.Filled);

        for (i = 0; i< Main.BullList.size(); i++){
            if(Main.BullList.get(i).height == 2) {
                Main.BullList.get(i).all_action_client(i);
            }
        }
        for (i= 0; i< PlayerList.size(); i++){
            PlayerList.get(i).update();
        }
        for (i= 0; i< EnemyList.size(); i++){
            EnemyList.get(i).update();
        }
        for (i= 0; i< AirList.size(); i++){
            for(int i2= 0; i2< AirList.get(i).size(); i2++) {
                AirList.get(i).get(i2).all_action();
            }
        }
        for (i= 0; i< Main.BangList.size(); i++){
            Main.BangList.get(i).all_action(i);}
        for (i = 0; i< Main.BuildingList.size(); i++){
            Main.BuildingList.get(i).xy_light_render.clear();
        }
        Render.end();
        Batch.end();
        client_packet();
    }
    public static void client_packet(){
        PacketClient.press_w = Main.PressW;
        PacketClient.press_a = Main.PressA;
        PacketClient.press_s = Main.PressS;
        PacketClient.press_d = Main.PressD;
        PacketClient.left_mouse = Main.LeftMouse;
        PacketClient.right_mouse = Main.RightMouse;
        PacketClient.mouse_x = Main.MouseX;
        PacketClient.mouse_y = Main.MouseY;
        Client.sendTCP(PacketClient);
        PacketClient.rot_tower.clear();

    }
}
