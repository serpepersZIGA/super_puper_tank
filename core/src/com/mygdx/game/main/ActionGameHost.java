package com.mygdx.game.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import Content.Bull.BullFragment;
import Content.Particle.Acid;
import Content.Particle.FlameSpawn;
import Content.Soldat.SoldatPacket;
import com.mygdx.game.build.BuildPacket;
import com.mygdx.game.method.CycleTimeDay;
import com.mygdx.game.method.Keyboard;
import com.mygdx.game.transport.DebrisPacket;
import com.mygdx.game.transport.Transport;
import com.mygdx.game.transport.TransportPacket;

import static Content.Bull.BullRegister.PacketBull;
import static com.mygdx.game.build.BuildRegister.PacketBuilding;
import static com.mygdx.game.main.Main.*;
import static com.mygdx.game.main.ServerMain.Server;
import static Content.Soldat.SoldatRegister.PacketSoldat;
import static com.mygdx.game.transport.TransportRegister.*;

public class ActionGameHost extends com.mygdx.game.main.ActionGame {
    private int i;
    private static int timer = 0;
    public void action() {
        RC.metod();
        if(Main.PlayerList.size()==0){
            if(Keyboard.PressW){
                Main.RC.y += 10;
            }
            if(Keyboard.PressS){
                Main.RC.y -= 10;
            }
            if(Keyboard.PressA){
                Main.RC.x -= 10;
            }
            if(Keyboard.PressD){
                Main.RC.x += 10;
            }
            try {
                if(timer <= 0) {

                    if (Keyboard.LeftMouse) {
                        Main.FlameSpawnList.add(new FlameSpawn(Keyboard.MouseX / Zoom + RC.x2,Keyboard.MouseY / Zoom + RC.y2));
                        timer = 30;
                        Main.BullList.add(new BullFragment(Keyboard.MouseX / Zoom + RC.x2,Keyboard.MouseY / Zoom + RC.y2,40,20,(byte)1));
                        Main.BullList.add(new BullFragment(Keyboard.MouseX / Zoom + RC.x2,Keyboard.MouseY / Zoom + RC.y2,40,20,(byte)1));
                        Main.BullList.add(new BullFragment(Keyboard.MouseX / Zoom + RC.x2,Keyboard.MouseY / Zoom + RC.y2,40,20,(byte)1));
                        Main.BullList.add(new BullFragment(Keyboard.MouseX / Zoom + RC.x2,Keyboard.MouseY / Zoom + RC.y2,40,20,(byte)1));
                        Main.BullList.add(new BullFragment(Keyboard.MouseX / Zoom + RC.x2,Keyboard.MouseY / Zoom + RC.y2,40,20,(byte)1));
                        Main.BullList.add(new BullFragment(Keyboard.MouseX / Zoom + RC.x2,Keyboard.MouseY / Zoom + RC.y2,40,20,(byte)1));
                        Main.BullList.add(new BullFragment(Keyboard.MouseX / Zoom + RC.x2,Keyboard.MouseY / Zoom + RC.y2,40,20,(byte)1));
                        Main.BullList.add(new BullFragment(Keyboard.MouseX / Zoom + RC.x2,Keyboard.MouseY / Zoom + RC.y2,40,20,(byte)1));
                        Main.BullList.add(new BullFragment(Keyboard.MouseX / Zoom + RC.x2,Keyboard.MouseY / Zoom + RC.y2,40,20,(byte)1));


                    }
                    if (Keyboard.RightMouse) {
                        //main.Main.bang_obj.add(new particle.bang(mouse_x,mouse_y,new Color(236,124,38),12));
                        Main.LiquidList.add(new Acid(Keyboard.MouseX / Zoom + RC.x2,Keyboard.MouseY / Zoom + RC.y2));
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
        //boolean[]mouse_e = new metod.mouse_control().mouse_event();
        //Main.player_obj.get(1).all_action_client(Main.left_mouse_client, Main.right_mouse_client, Main.mouse_x_client,
                //Main.mouse_y_client, Main.press_w_client, Main.press_a_client, Main.press_s_client, Main.press_d_client);
        if(Transport.ai_sost != 0){Transport.ai_sost-=1;}
        Batch.begin();
        Render.begin(ShapeRenderer.ShapeType.Filled);
        Main.RC.render_block();
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
                Main.BullList.get(i).all_action(i);
            }
        }
        Render.end();
        Batch.begin();
        Render.begin(ShapeRenderer.ShapeType.Filled);
        for (i = 0; i< Main.SoldatList.size(); i++){
            packet_enemy_soldat();
            Main.SoldatList.get(i).all_action(i);
        }
        for (i= 0; i< Main.FlameSpawnList.size(); i++){
            Main.FlameSpawnList.get(i).all_action(i);
        }
        for (i= 0; i< Main.DebrisList.size(); i++){
            packet_debris_server();
            Main.DebrisList.get(i).all_action(i);
        }
        for (i = 0; i < Main.EnemyList.size(); i++) {
            packet_enemy_server();
            Main.EnemyList.get(i).all_action(i);
        }
        for(i = 0; i< PlayerList.size(); i++) {
            packet_player_server();
            if(PlayerList.get(i).host) {
                Main.PlayerList.get(i).all_action(i);
            }
            else {
                Main.PlayerList.get(i).all_action_client(i);
            }
        }
        for (i = 0; i< Main.BuildingList.size(); i++){
            Main.BuildingList.get(i).all_action(i);}
        Batch.draw(textureBuffer,-20,1,1,1);
        Render.end();

        Render.begin(ShapeRenderer.ShapeType.Filled);

        for (i = 0; i< Main.BullList.size(); i++){
            if(Main.BullList.get(i).height == 2) {
                Main.BullList.get(i).all_action(i);
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
        server_packet();
        if(Transport.ai_sost == 0){Transport.ai_sost=400;}
        CycleDayNight.WorkTime();
    }
    private void server_packet() {
        if(EnumerationList){
            PacketEnemy.clear();
            for (i = 0; i < Main.EnemyList.size(); i++) {
                packet_enemy_server();
            }
            PacketPlayer.clear();
            for (i = 0; i < Main.PlayerList.size(); i++) {
                packet_player_server();
            }
            EnumerationList = false;
        }
        PacketServer.debris = PacketDebris;
        PacketServer.soldat = PacketSoldat;
        PacketServer.player = PacketPlayer;
        PacketServer.enemy = PacketEnemy;
        PacketServer.bull = PacketBull;
        PacketServer.building = PacketBuilding;
        PacketServer.TotalLight = CycleTimeDay.lightTotal;

        Server.sendToAllUDP(PacketServer);
        PacketPlayer.clear();
        PacketEnemy.clear();
        PacketBull.clear();
        PacketSoldat.clear();
        PacketDebris.clear();
        PacketBuilding.clear();
    }
    private void packet_player_server(){
        PacketPlayer.add(new TransportPacket());
        PacketPlayer.get(i).name = PlayerList.get(i).type_unit;
        PacketPlayer.get(i).x = PlayerList.get(i).x;
        PacketPlayer.get(i).y = PlayerList.get(i).y;
        PacketPlayer.get(i).rotation_corpus = PlayerList.get(i).rotation_corpus;
        PacketPlayer.get(i).rotation_tower = PlayerList.get(i).rotation_tower;
        PacketPlayer.get(i).reload = PlayerList.get(i).reload;
        PacketPlayer.get(i).hp = PlayerList.get(i).hp;
        PacketPlayer.get(i).team = PlayerList.get(i).team;
        PacketPlayer.get(i).speed = PlayerList.get(i).speed;
        PacketPlayer.get(i).host = PlayerList.get(i).host;
        PacketPlayer.get(i).IDClient = PlayerList.get(i).nConnect;

        for (int i2 = 0; i2< PlayerList.get(i).tower_obj.size(); i2++) {
            PacketPlayer.get(i).rotation_tower_2.add(PlayerList.get(i).tower_obj.get(i2).rotation_tower);
        }

    }
    private void packet_enemy_server(){
        PacketEnemy.add(new TransportPacket());
        PacketEnemy.get(i).name = EnemyList.get(i).type_unit;
        PacketEnemy.get(i).x = EnemyList.get(i).x;
        PacketEnemy.get(i).y = EnemyList.get(i).y;
        PacketEnemy.get(i).crite_life = EnemyList.get(i).crite_life;
        PacketEnemy.get(i).rotation_corpus = EnemyList.get(i).rotation_corpus;
        PacketEnemy.get(i).rotation_tower = EnemyList.get(i).rotation_tower;
        PacketEnemy.get(i).reload = EnemyList.get(i).reload;
        PacketEnemy.get(i).hp = EnemyList.get(i).hp;
        PacketEnemy.get(i).team = EnemyList.get(i).team;
        PacketEnemy.get(i).speed = EnemyList.get(i).speed;
        for (int i2 = 0; i2< EnemyList.get(i).tower_obj.size(); i2++) {
            PacketEnemy.get(i).rotation_tower_2.add(EnemyList.get(i).tower_obj.get(i2).rotation_tower);
        }
    }
    private void packet_debris_server(){
        PacketDebris.add(new DebrisPacket());
        PacketDebris.get(i).name = DebrisList.get(i).type_unit;
        PacketDebris.get(i).x = DebrisList.get(i).x;
        PacketDebris.get(i).y = DebrisList.get(i).y;
        PacketDebris.get(i).rotation = DebrisList.get(i).rotation_corpus;
    }
    private void packet_enemy_soldat(){
        PacketSoldat.add(new SoldatPacket());
        PacketSoldat.get(i).name = SoldatList.get(i).name;
        PacketSoldat.get(i).x = SoldatList.get(i).x;
        PacketSoldat.get(i).y = SoldatList.get(i).y;
        PacketSoldat.get(i).rotation = SoldatList.get(i).rotation;
        PacketSoldat.get(i).team = SoldatList.get(i).team;
    }
    public void PacketBuildServer(int i){
        PacketBuilding.add(new BuildPacket());
        PacketBuilding.get(i).name = BuildingList.get(i).name;
        PacketBuilding.get(i).x = BuildingList.get(i).x;
        PacketBuilding.get(i).y = BuildingList.get(i).y;
    }
}
