package com.mygdx.game.main;
import Content.Build.BigBuildingWood1;
import Content.Build.Home1;
import Content.Bull.*;
import Content.Particle.*;
import Content.Transport.Transport.*;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.esotericsoftware.kryonet.Client;

import java.io.IOException;
import java.util.ArrayList;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.mygdx.game.block.Block;
import com.mygdx.game.build.BuildPacket;
import com.mygdx.game.build.BuildType;
import com.mygdx.game.build.PacketBuildingServer;
import com.mygdx.game.method.CycleTimeDay;
import com.mygdx.game.object_map.ObjectLoad;
import com.mygdx.game.method.SoundPlay;
import Content.Soldat.SoldatBull;
import Content.Soldat.SoldatFlame;
import Content.Soldat.SoldatPacket;
import com.mygdx.game.object_map.MapObject;
import com.mygdx.game.object_map.ObjectMapAssets;
import com.mygdx.game.object_map.PacketMapObject;
import com.mygdx.game.object_map.VoidObject;
import com.mygdx.game.object_map.component_collision_system.CollisionVoid;
import com.mygdx.game.transport.*;
import com.mygdx.game.transport.SpawnPlayer.*;

import static Content.Bull.BullRegister.PacketBull;
import static com.mygdx.game.build.BuildRegister.PacketBuilding;
import static com.mygdx.game.main.Main.*;
import static Content.Soldat.SoldatRegister.PacketSoldat;
import static com.mygdx.game.object_map.MapObject.PacketMapObjects;
import static com.mygdx.game.transport.TransportRegister.*;


public class ClientMain extends Listener{
    public static Client Client;
    static int udpPort = 27950, tcpPort = 27950;
    public static String IP = "127.0.0.1";
    public static int IDClient;
    private int i;
    public void create() {
        System.out.println("Подключаемся к серверу");
        Client = new Client(10000000,10000000);

        //Регистрируем пакет
        Client.getKryo().register(PackerServer.class);
        Client.getKryo().register(Packet_client.class);
        Client.getKryo().register(TransportPacket.class);
        Client.getKryo().register(BullPacket.class);
        Client.getKryo().register(ArrayList.class);
        Client.getKryo().register(DebrisTransport.class);
        Client.getKryo().register(SoundPlay.class);
        Client.getKryo().register(SoldatPacket.class);
        Client.getKryo().register(DebrisPacket.class);
        Client.getKryo().register(SoldatFlame.class);
        Client.getKryo().register(SoldatBull.class);
        Client.getKryo().register(UnitType.class);
        Client.getKryo().register(Bang.class);
        Client.getKryo().register(FlameSpawn.class);
        Client.getKryo().register(Flame.class);
        Client.getKryo().register(FlameParticle.class);
        Client.getKryo().register(Acid.class);
        Client.getKryo().register(Blood.class);
        Client.getKryo().register(FlameStatic.class);
        Client.getKryo().register(BuildPacket.class);
        Client.getKryo().register(BuildType.class);
        Client.getKryo().register(BullFlame.class);
        Client.getKryo().register(BullFragment.class);
        Client.getKryo().register(BullAcid.class);
        Client.getKryo().register(BullTank.class);
        Client.getKryo().register(BullMortar.class);
        Client.getKryo().register(PacketBuildingServer.class);
        Client.getKryo().register(PlayerSpawnData.class);
        Client.getKryo().register(SpawnPlayerCannonFlame.class);
        Client.getKryo().register(SpawnPlayerCannonAcid.class);
        Client.getKryo().register(SpawnPlayerCannonMortar.class);
        Client.getKryo().register(SpawnPlayerCannonBull.class);
        Client.getKryo().register(SpawnPlayerVoid.class);

        Client.getKryo().register(PacketMapObject.class);
        Client.getKryo().register(ObjectMapAssets.class);

        //Запускаем клиент
        Client.start();
        //Клиент начинает подключатся к серверу

        //Клиент подключается к серверу
        try {
            Client.connect(5000, IP, tcpPort, udpPort);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Client.addListener(Main.Main_client);
    }

    @Override
    public void received(Connection c, Object p) {
        Main.IDClient = c.getID();
        if(p instanceof PackerServer) {
            CycleTimeDay.lightTotal = ((PackerServer) p).TotalLight;
            PacketPlayer = ((PackerServer) p).player;
            if (PacketPlayer.size() == PlayerList.size()) {
                for (i = 0; i < PacketPlayer.size(); i++) {
                    player_data(i);
                }
            } else {
                PlayerList.clear();
                for (i = 0; i < PacketPlayer.size(); i++) {
                    switch (PacketPlayer.get(i).name) {
                        case PlayerFlameT1:
                            PlayerList.add(new PlayerCannonFlame(0, 0, PlayerList, PacketPlayer.get(i).host));
                            break;
                        case PlayerMortarT1:
                            PlayerList.add(new PlayerCannonMortar(0, 0, PlayerList, PacketPlayer.get(i).host));
                            break;
                        case PlayerT1:
                            PlayerList.add(new PlayerCannonBullTank(0, 0, PlayerList, PacketPlayer.get(i).host));
                            break;
                        case PlayerAcidT1:
                            PlayerList.add(new PlayerCannonAcid(0, 0, PlayerList, PacketPlayer.get(i).host));
                            break;
                    }
                    player_data(i);
                }
                KeyboardObj.zoom_const();
            }
            PacketEnemy = ((PackerServer) p).enemy;
            if (PacketEnemy.size() == EnemyList.size()) {
                for (int i = 0; i < PacketEnemy.size(); i++) {
                    enemy_data(i);
                }
            } else {
                EnemyList.clear();
                for (int i = 0; i < PacketEnemy.size(); i++) {
                    enemy_create(i);
                    enemy_data(i);
                }
                KeyboardObj.zoom_const();
            }
            PacketDebris = ((PackerServer) p).debris;
            if (PacketDebris.size() == DebrisList.size()) {
                for (int i = 0; i < DebrisList.size(); i++) {
                    debris_data(i);
                }
            } else {
                DebrisList.clear();
                for (int i = 0; i < PacketDebris.size(); i++) {
                    debris_create(i, PacketDebris.get(i).x, PacketDebris.get(i).y, PacketDebris.get(i).rotation);
                    debris_data(i);
                }
                KeyboardObj.zoom_const();
            }


            PacketMapObjects = ((PackerServer) p).mapObject;
            for (int i = 0; i < PacketMapObjects.size(); i++) {
                BlockList2D.get(PacketMapObjects.get(i).iy).get(PacketMapObjects.get(i).ix).objMap
                        = new VoidObject();
                KeyboardObj.zoom_const();
            }




            PacketSoldat = ((PackerServer) p).soldat;
            if (PacketSoldat.size() == SoldatList.size()) {
                for (int i = 0; i < PacketSoldat.size(); i++) {
                    soldat_data(i);
                }
            } else {
                SoldatList.clear();
                for (SoldatPacket soldatPacket : PacketSoldat) {
                    if (soldatPacket.name.equals("flame")) {
                        SoldatList.add(new SoldatFlame(0, 0,EnemyList));
                    } else if (soldatPacket.name.equals("bull")) {
                        SoldatList.add(new SoldatBull(0, 0,EnemyList));
                    }
                }
                KeyboardObj.zoom_const();
            }
            PacketBull = ((PackerServer) p).bull;
            i = BulletList.size();
            for (int i2 = 0; i2 < PacketBull.size(); i2++) {
                switch (PacketBull.get(i2).type) {
                    case 1:
                        BulletList.add(new BullFlame(PacketBull.get(i2).x, PacketBull.get(i2).y,
                            PacketBull.get(i2).rotation, 0.0F, 0.0F, 0, PacketBull.get(i2).team, PacketBull.get(i2).height));
                        break;
                    case 2:
                        BulletList.add(new BullFragment(PacketBull.get(i2).x, PacketBull.get(i2).y,
                                0.0F, 0.0f, PacketBull.get(i2).height));break;

                    case 3:
                        BulletList.add(new BullMortar(PacketBull.get(i2).x, PacketBull.get(i2).y,
                                PacketBull.get(i2).rotation, 0.0f, 0f, 0f, 0f, PacketBull.get(i2).team
                                , PacketBull.get(i2).height));break;
                    case 4:
                        BulletList.add(new BullAcid(PacketBull.get(i2).x, PacketBull.get(i2).y,
                                PacketBull.get(i2).rotation, 0.0f, 0.0f, PacketBull.get(i2).team
                                , PacketBull.get(i2).height));break;
                    case 5:
                        BulletList.add(new BullTank(PacketBull.get(i2).x, PacketBull.get(i2).y,
                                PacketBull.get(i2).rotation, 0.0f, 0.0f, PacketBull.get(i2).team
                                , PacketBull.get(i2).height));break;
                }
                try {
                    bull_data(i2);
                }catch (IndexOutOfBoundsException e){
                    e.printStackTrace();
                }
            }
        }
        else if(p instanceof PacketBuildingServer){
            PacketBuilding = ((PacketBuildingServer) p).BuildPack;
            BuildingList.clear();
            for (int i = 0; i < PacketBuilding.size(); i++) {
                Building_create(i,PacketBuilding.get(i).x-width_block,PacketBuilding.get(i).y-height_block);
            }
            ArrayList<ArrayList<PacketMapObject>>objMapList;
            objMapList = ((PacketBuildingServer) p).ObjectMapPack;
            for (int iy = 0;iy< objMapList.size();iy++) {
                for (int ix = 0; ix < objMapList.get(iy).size(); ix++) {
                    object_create(ix,iy, objMapList.get(iy).get(ix).objectAssets, objMapList.get(iy).get(ix).x,
                    objMapList.get(iy).get(ix).y,objMapList.get(iy).get(ix).width, objMapList.get(iy).get(ix).height,
                    objMapList.get(iy).get(ix).lighting,objMapList.get(iy).get(ix).distance_lighting);
                }
            }
            Block.passability_detected();

        }
    }
    public void object_create(int ix, int iy, ObjectMapAssets assets,int x,int y,int width,int height,
        boolean light,float distance_lighting) {
        if(width != 0) {
        Sprite asset = ObjectLoad.ImageLoad(assets);
        BlockList2D.get(iy).get(ix).objMap = new MapObject(x-ix* width_block, y-iy* height_block, asset, width, height, 0, ix, iy, new CollisionVoid()
                , light, distance_lighting, assets);
        }
        else{
            BlockList2D.get(iy).get(ix).objMap = new VoidObject();
        }
    }
    private void bull_data(int i){
        BulletList.get(this.i).x = PacketBull.get(i).x;
        BulletList.get(this.i).y = PacketBull.get(i).y;
        BulletList.get(this.i).rotation = PacketBull.get(i).rotation;
        BulletList.get(this.i).time = PacketBull.get(i).time;
        BulletList.get(this.i).height = PacketBull.get(i).height;
        BulletList.get(this.i).type = PacketBull.get(i).type;
        BulletList.get(this.i).type_team = PacketBull.get(i).team;
        this.i += 1;
    }

    private void player_data(int i) {
        PlayerList.get(i).type_unit = PacketPlayer.get(i).name;
        PlayerList.get(i).x = PacketPlayer.get(i).x;
        PlayerList.get(i).y = PacketPlayer.get(i).y;
        PlayerList.get(i).rotation_corpus = PacketPlayer.get(i).rotation_corpus;
        PlayerList.get(i).reload = PacketPlayer.get(i).reload;
        PlayerList.get(i).hp = PacketPlayer.get(i).hp;
        PlayerList.get(i).team = PacketPlayer.get(i).team;
        PlayerList.get(i).speed = PacketPlayer.get(i).speed;
        PlayerList.get(i).host = PacketPlayer.get(i).host;
        PlayerList.get(i).nConnect = PacketPlayer.get(i).IDClient;
        if(PacketPlayer.get(i).IDClient!=IDClient || PacketPlayer.get(i).host) {
            PlayerList.get(i).rotation_tower = PacketPlayer.get(i).rotation_tower;
            for (int i2 = 0; i2< PacketPlayer.get(i).rotation_tower_2.size(); i2++) {
                PlayerList.get(i).tower_obj.get(i2).rotation_tower = PacketPlayer.get(i).rotation_tower_2.get(i2);
            }
        }
    }
    private void enemy_data(int i){
        EnemyList.get(i).type_unit = PacketEnemy.get(i).name;
        EnemyList.get(i).x = PacketEnemy.get(i).x;
        EnemyList.get(i).y = PacketEnemy.get(i).y;
        EnemyList.get(i).rotation_corpus = PacketEnemy.get(i).rotation_corpus;
        EnemyList.get(i).crite_life = PacketEnemy.get(i).crite_life;
        EnemyList.get(i).rotation_tower = PacketEnemy.get(i).rotation_tower;
        EnemyList.get(i).reload = PacketEnemy.get(i).reload;
        EnemyList.get(i).hp = PacketEnemy.get(i).hp;
        EnemyList.get(i).team = PacketEnemy.get(i).team;
        EnemyList.get(i).speed = PacketEnemy.get(i).speed;
        for (int i2 = 0; i2< PacketEnemy.get(i).rotation_tower_2.size(); i2++) {
            EnemyList.get(i).tower_obj.get(i2).rotation_tower = PacketEnemy.get(i).rotation_tower_2.get(i2);
        }
    }
    private void debris_data(int i){
        DebrisList.get(i).type_unit = PacketDebris.get(i).name;
        DebrisList.get(i).x = PacketDebris.get(i).x;
        DebrisList.get(i).y = PacketDebris.get(i).y;
        DebrisList.get(i).rotation_corpus = PacketDebris.get(i).rotation;
    }
    private void soldat_data(int i) {
        SoldatList.get(i).name = PacketSoldat.get(i).name;
        SoldatList.get(i).x = PacketSoldat.get(i).x;
        SoldatList.get(i).y = PacketSoldat.get(i).y;
        SoldatList.get(i).rotation = PacketSoldat.get(i).rotation;
        SoldatList.get(i).team = PacketSoldat.get(i).team;
    }
    private void enemy_create(int i){
        switch (PacketEnemy.get(i).name) {
            case PanzerFlameT1:
                EnemyList.add(new PanzerFlameT1(0, 0, EnemyList));
                break;
            case PanzerMortarT1:
                EnemyList.add(new PanzerMortarT1(0, 0, EnemyList));
                break;
            case PanzerT1:
                EnemyList.add(new PanzerT1(0, 0, EnemyList));
                break;
            case PanzerAcidT1:
                EnemyList.add(new PanzerAcidT1(0, 0, EnemyList));
                break;
            case TrackRemountT1:
                EnemyList.add(new TrackRemountT1(0, 0, EnemyList));
                break;
            case TrackSoldatT1:
                EnemyList.add(new TrackSoldatT1(0, 0, EnemyList));
                break;
        }
    }
    public void Building_create(int i,int x,int y){
        if(PacketBuilding.get(i).name != null) {
            switch (PacketBuilding.get(i).name) {
                case BigBuildingWood1:
                    BuildingList.add(new BigBuildingWood1(x, y));
                    break;
                case Home1:
                    BuildingList.add(new Home1(x, y));
                    break;
            }
        }
    }
    public void debris_create(int i, float x, float y, float rotation){
        switch (PacketDebris.get(i).name) {
            case PanzerFlameT1:
                DebrisList.add(new DebrisTransport(x, y,rotation,0, 0,0, PanzerFlameT1.corpus_img,
                        PanzerFlameT1.corpus_width, PanzerFlameT1.corpus_height, UnitType.PanzerFlameT1));
                break;
            case PanzerMortarT1:
                DebrisList.add(new DebrisTransport(x, y,rotation,0, 0,0, PanzerMortarT1.corpus_img,
                        PanzerMortarT1.corpus_width, PanzerMortarT1.corpus_height, UnitType.PanzerMortarT1));
                break;
            case PanzerT1:
                DebrisList.add(new DebrisTransport(x, y,rotation,0, 0,0, PanzerT1.corpus_img,
                        PanzerT1.corpus_width, PanzerT1.corpus_height, UnitType.PanzerT1));
                break;
            case PanzerAcidT1:
                DebrisList.add(new DebrisTransport(x, y,rotation,0, 0,0, PanzerAcidT1.corpus_img,
                        PanzerAcidT1.corpus_width, PanzerAcidT1.corpus_height, UnitType.PanzerAcidT1));
                break;
            case TrackRemountT1:
                DebrisList.add(new DebrisTransport(x, y,rotation,0, 0,0, TrackRemountT1.corpus_img,
                        TrackRemountT1.corpus_width, TrackRemountT1.corpus_height, UnitType.TrackRemountT1));
                break;
            case TrackSoldatT1:
                DebrisList.add(new DebrisTransport(x, y,rotation,0, 0,0, TrackSoldatT1.corpus_img,
                        TrackSoldatT1.corpus_width, TrackSoldatT1.corpus_height, UnitType.TrackSoldatT1));
                break;


            case PlayerFlameT1:
                DebrisList.add(new DebrisTransport(x, y,rotation,0, 0,0, PlayerCannonFlame.corpus_img,
                        PlayerCannonFlame.corpus_width, PlayerCannonFlame.corpus_height, UnitType.PlayerFlameT1));
                break;
            case PlayerT1:
                DebrisList.add(new DebrisTransport(x, y,rotation,0, 0,0, PlayerCannonBullTank.corpus_img,
                        PlayerCannonBullTank.corpus_width, PlayerCannonBullTank.corpus_height, UnitType.PlayerT1));
                break;
            case PlayerAcidT1:
                DebrisList.add(new DebrisTransport(x, y,rotation,0, 0,0, PlayerCannonAcid.corpus_img,
                        PlayerCannonAcid.corpus_width, PlayerCannonAcid.corpus_height, UnitType.PlayerAcidT1));
                break;
            case PlayerMortarT1:
                DebrisList.add(new DebrisTransport(x, y,rotation,0, 0,0, PlayerCannonMortar.corpus_img,
                        PlayerCannonMortar.corpus_width, PlayerCannonMortar.corpus_height, UnitType.PlayerMortarT1));
                break;
            case HelicopterT1:
                DebrisList.add(new DebrisTransport(x, y,rotation,0, 0,0, Helicopter_t1.corpus_img,
                        Helicopter_t1.corpus_width, Helicopter_t1.corpus_height, UnitType.HelicopterT1));
                break;
        }
    }

}

