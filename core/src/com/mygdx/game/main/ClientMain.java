package com.mygdx.game.main;
import Content.Build.BigBuildingWood1;
import Content.Build.Home1;
import Content.Bull.*;
import Content.Particle.*;
import Content.Transport.Transport.*;
import com.esotericsoftware.kryonet.Client;

import java.io.IOException;
import java.util.ArrayList;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.mygdx.game.build.BuildPacket;
import com.mygdx.game.build.BuildType;
import com.mygdx.game.method.Sound;
import Content.Soldat.SoldatBull;
import Content.Soldat.SoldatFlame;
import Content.Soldat.SoldatPacket;
import com.mygdx.game.transport.*;

import static Content.Bull.BullRegister.PacketBull;
import static com.mygdx.game.build.BuildRegister.PacketBuilding;
import static com.mygdx.game.main.Main.*;
import static com.mygdx.game.method.Keyboard.ZoomConstTransport;
import static Content.Soldat.SoldatRegister.PacketSoldat;
import static com.mygdx.game.transport.TransportRegister.*;


public class ClientMain extends Listener{
    public static Client Client;
    static int udpPort = 27950, tcpPort = 27950;
    public static String IP;
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
        Client.getKryo().register(Sound.class);
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
        //c.sendUDP(packetMessage);
        if(p instanceof PackerServer) {
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
                ZoomConstTransport();
            }
            PacketEnemy = ((PackerServer) p).enemy;
            if (PacketEnemy.size() == EnemyList.size()) {
                for (int i = 0; i < PacketEnemy.size(); i++) {
                    enemy_data(i);
                }
            } else {
                EnemyList.clear();
                for (int i = 0; i < PacketEnemy.size(); i++) {
                    System.out.println(PacketEnemy.get(i).name);
                    enemy_create(i);
                    enemy_data(i);
                }
                ZoomConstTransport();
            }


            PacketBuilding = ((PackerServer) p).building;
            if (PacketBuilding.size() != BuildingList.size()) {
                BuildingList.clear();
                for (int i = 0; i < PacketBuilding.size(); i++) {
                    Building_create(i,PacketBuilding.get(i).x,PacketBuilding.get(i).y);
                }
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
                }
                ZoomConstTransport();
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
                        SoldatList.add(new SoldatFlame(0, 0));
                    } else if (soldatPacket.name.equals("bull")) {
                        SoldatList.add(new SoldatBull(0, 0));
                    }
                }
                ZoomConstTransport();
            }
            PacketBull = ((PackerServer) p).bull;
            i = BullList.size();
            for (int i2 = 0; i2 < PacketBull.size(); i2++) {
                switch (PacketBull.get(i2).type) {
                    case 1:
                        BullList.add(new BullFlame(PacketBull.get(i2).x, PacketBull.get(i2).y,
                            PacketBull.get(i2).rotation, 0.0, 0.0, 0, PacketBull.get(i2).team, PacketBull.get(i2).height));
                        break;
                    case 2:
                        BullList.add(new BullFragment(PacketBull.get(i2).x, PacketBull.get(i2).y,
                            0.0, 0.0, PacketBull.get(i2).height));break;

                    case 3:
                        BullList.add(new BullMortar(PacketBull.get(i2).x, PacketBull.get(i2).y,
                                PacketBull.get(i2).rotation, 0.0, 0, 0, 0, PacketBull.get(i2).team
                                , PacketBull.get(i2).height));break;
                    case 4:
                        BullList.add(new BullAcid(PacketBull.get(i2).x, PacketBull.get(i2).y,
                                PacketBull.get(i2).rotation, 0.0, 0.0, PacketBull.get(i2).team
                                , PacketBull.get(i2).height));break;
                    case 5:
                        BullList.add(new BullTank(PacketBull.get(i2).x, PacketBull.get(i2).y,
                                PacketBull.get(i2).rotation, 0.0, 0.0, PacketBull.get(i2).team
                                , PacketBull.get(i2).height));break;
                }
                try {
                    bull_data(i2);
                }catch (IndexOutOfBoundsException e){
                    e.printStackTrace();
                }
            }
        }
    }
    private void bull_data(int i){
        BullList.get(this.i).x = PacketBull.get(i).x;
        BullList.get(this.i).y = PacketBull.get(i).y;
        BullList.get(this.i).rotation = PacketBull.get(i).rotation;
        BullList.get(this.i).time = PacketBull.get(i).time;
        BullList.get(this.i).height = PacketBull.get(i).height;
        BullList.get(this.i).type = PacketBull.get(i).type;
        BullList.get(this.i).type_team = PacketBull.get(i).team;
        this.i += 1;
    }

    private void player_data(int i) {
        PlayerList.get(i).type_unit = PacketPlayer.get(i).name;
        PlayerList.get(i).x = PacketPlayer.get(i).x;
        PlayerList.get(i).y = PacketPlayer.get(i).y;
        PlayerList.get(i).rotation_corpus = PacketPlayer.get(i).rotation_corpus;
        PlayerList.get(i).rotation_tower = PacketPlayer.get(i).rotation_tower;
        PlayerList.get(i).reload = PacketPlayer.get(i).reload;
        PlayerList.get(i).hp = PacketPlayer.get(i).hp;
        PlayerList.get(i).team = PacketPlayer.get(i).team;
        PlayerList.get(i).speed = PacketPlayer.get(i).speed;
        PlayerList.get(i).host = PacketPlayer.get(i).host;
        if(PacketPlayer.get(i).host) {
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
    private void soldat_data(int i){
        SoldatList.get(i).name = PacketSoldat.get(i).name;
        SoldatList.get(i).x = PacketSoldat.get(i).x;
        SoldatList.get(i).y = PacketSoldat.get(i).y;
        SoldatList.get(i).rotation = PacketSoldat.get(i).rotation;
        SoldatList.get(i).team = PacketSoldat.get(i).team;
    }
    public void BuildData(int i){
        BuildingList.get(i).name = PacketBuilding.get(i).name;
        BuildingList.get(i).x = PacketBuilding.get(i).x;
        BuildingList.get(i).y = PacketBuilding.get(i).y;
        BuildingList.get(i).rotation = PacketBuilding.get(i).rotation;
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
                    BuildingList.add(new BigBuildingWood1(x, y, 0));
                    break;
                case Home1:
                    BuildingList.add(new Home1(x, y, 0));
                    break;
            }
        }
    }
    public void debris_create(int i, double x, double y, double rotation){
//        System.out.println(packet_enemy.size());
//        System.out.println(packet_enemy.get(i).name);
//        System.out.println(packet_enemy.get(i).x);
//        System.out.println(i);
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
        }
    }

}

