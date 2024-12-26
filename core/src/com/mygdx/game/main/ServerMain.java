package com.mygdx.game.main;
import Content.Bull.*;
import Content.Particle.*;
import Content.Transport.Transport.PlayerCannonFlame;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.mygdx.game.build.BuildPacket;
import com.mygdx.game.build.BuildType;
import com.mygdx.game.build.PacketBuildingServer;
import com.mygdx.game.method.SoundPlay;
import Content.Soldat.SoldatBull;
import Content.Soldat.SoldatFlame;
import Content.Soldat.SoldatPacket;
import com.mygdx.game.transport.DebrisPacket;
import Content.Transport.Transport.DebrisTransport;
import com.mygdx.game.transport.TransportPacket;
import com.mygdx.game.transport.UnitType;

import java.io.IOException;
import java.util.ArrayList;

import static com.mygdx.game.build.BuildRegister.PacketBuilding;
import static com.mygdx.game.main.Main.*;
import static com.mygdx.game.method.Keyboard.ZoomConstTransport;

public class ServerMain extends Listener {
    static Server Server;
    static final int udpPort = 27950, tcpPort = 27950;
    public static int nConnect = 0;

    public void create(){
        System.out.println("Создаем сервер");
        //Создаем сервер
        Server = new Server(10000000,10000000);

        //Регистрируем пакет класс
        Server.getKryo().register(PackerServer.class);
        Server.getKryo().register(Packet_client.class);
        Server.getKryo().register(TransportPacket.class);
        Server.getKryo().register(BullPacket.class);
        Server.getKryo().register(ArrayList.class);
        Server.getKryo().register(DebrisTransport.class);
        Server.getKryo().register(SoundPlay.class);
        Server.getKryo().register(SoldatPacket.class);
        Server.getKryo().register(DebrisPacket.class);
        Server.getKryo().register(SoldatFlame.class);
        Server.getKryo().register(SoldatBull.class);
        Server.getKryo().register(UnitType.class);
        Server.getKryo().register(Bang.class);
        Server.getKryo().register(FlameSpawn.class);
        Server.getKryo().register(Flame.class);
        Server.getKryo().register(FlameParticle.class);
        Server.getKryo().register(Acid.class);
        Server.getKryo().register(Blood.class);
        Server.getKryo().register(FlameStatic.class);
        Server.getKryo().register(BuildPacket.class);
        Server.getKryo().register(BuildType.class);

        Server.getKryo().register(BullFlame.class);
        Server.getKryo().register(BullFragment.class);
        Server.getKryo().register(BullAcid.class);
        Server.getKryo().register(BullTank.class);
        Server.getKryo().register(BullMortar.class);
        Server.getKryo().register(PacketBuildingServer.class);

        //Регистрируем порт
        try {
            Server.bind(tcpPort, udpPort);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //Запускаем сервер
        Server.start();

        Server.addListener(Main.serverMain);

    }
    public void connected(Connection c){
        System.out.println("На сервер подключился "+c.getRemoteAddressTCP().getHostString());
        int i2 = Main.PlayerList.size();
        nConnect+= 1;
        Main.PlayerList.add(new PlayerCannonFlame(200,200,Main.PlayerList,false));
        PlayerList.get(i2).nConnect = nConnect;

        for (int i = 0;i<Main.BuildingList.size();i++){
            PacketBuildServer(i);
        }
        Server.sendToAllTCP(PacketBuildingServer);
        ZoomConstTransport();
        KeyboardObj.zoom_const();

    }
    public void PacketBuildServer(int i){
        //PacketBuildingServer packB = new PacketBuildingServer();
        PacketBuildingServer.BuildPack.add(new BuildPacket());
        PacketBuildingServer.BuildPack.get(i).name = BuildingList.get(i).name;
        PacketBuildingServer.BuildPack.get(i).x = BuildingList.get(i).x;
        PacketBuildingServer.BuildPack.get(i).y = BuildingList.get(i).y;
    }

    //Используется когда клиент отправляет пакет серверу
    public void received(Connection c, Object p){
        if(p instanceof Packet_client) {
//            Main.PressWClient = ((Packet_client) p).press_w;
//            Main.PressAClient = ((Packet_client) p).press_a;
//            Main.PressSClient = ((Packet_client) p).press_s;
//            Main.PressDClient = ((Packet_client) p).press_d;
//            Main.MouseXClient = ((Packet_client) p).mouse_x;
//            Main.MouseYClient = ((Packet_client) p).mouse_y;
//            Main.RightMouseClient = ((Packet_client) p).right_mouse;
//            Main.LeftMouseClient = ((Packet_client) p).left_mouse;
            Clients.add((Packet_client) p);
//            for(int i2 = 0;i2<PlayerList.size();i2++) {
//                if (!PlayerList.get(i2).host) {
//                    Main.PlayerList.get(i2).rotation_tower = ((Packet_client) p).rotation_tower_client;
//
//                    if (Main.PlayerList.get(i2).tower_obj.size() < ((Packet_client) p).rot_tower.size()) {
//                        for (int i = 0; i < Main.PlayerList.get(i2).tower_obj.size(); i++) {
//                            Main.PlayerList.get(i2).tower_obj.get(i).rotation_tower = ((Packet_client) p).rot_tower.get(i);
//                        }
//                    } else {
//                        for (int i = 0; i < ((Packet_client) p).rot_tower.size(); i++) {
//                            Main.PlayerList.get(i2).tower_obj.get(i).rotation_tower = ((Packet_client) p).rot_tower.get(i);
//                        }
//
//                    }
//                }
//            }
        }
    }

    //Используется когда клиент покидает сервер.
    public void disconnected(Connection c){
        System.out.println("Клиент покинул сервер!");
    }
}
