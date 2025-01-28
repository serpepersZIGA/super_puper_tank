package com.mygdx.game.main;
import Content.Bull.*;
import Content.Particle.*;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.mygdx.game.build.BuildPacket;
import com.mygdx.game.build.BuildType;
import com.mygdx.game.build.PacketBuildingServer;
import com.mygdx.game.method.CycleTimeDay;
import com.mygdx.game.method.SoundPlay;
import Content.Soldat.SoldatBull;
import Content.Soldat.SoldatFlame;
import com.mygdx.game.soldat.SoldatPacket;
import com.mygdx.game.object_map.ObjectMapAssets;
import com.mygdx.game.object_map.PacketMapObject;
import com.mygdx.game.transport.DebrisPacket;
import Content.Transport.Transport.DebrisTransport;
import com.mygdx.game.transport.SpawnPlayer.*;
import com.mygdx.game.transport.TransportPacket;
import com.mygdx.game.transport.UnitType;

import java.io.IOException;
import java.util.ArrayList;

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
        Server.getKryo().register(PlayerSpawnData.class);
        Server.getKryo().register(SpawnPlayerCannonFlame.class);
        Server.getKryo().register(SpawnPlayerCannonAcid.class);
        Server.getKryo().register(SpawnPlayerCannonMortar.class);
        Server.getKryo().register(SpawnPlayerCannonBull.class);
        Server.getKryo().register(SpawnPlayerVoid.class);

        Server.getKryo().register(PacketMapObject.class);
        Server.getKryo().register(ObjectMapAssets.class);

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
        System.out.println("На сервер подключился " + c.getRemoteAddressTCP().getHostString());

        for (int i = 0;i<Main.BuildingList.size();i++){
            PacketBuildServer(i);
        }
        for (int iy = 0; iy< BlockList2D.size(); iy++){
            PacketBuildingServer.ObjectMapPack.add(new ArrayList<>());
            for (int ix = 0; ix< BlockList2D.get(iy).size(); ix++){
                PacketObjectMapServer(ix,iy,PacketBuildingServer.ObjectMapPack.get(iy));
            }
        }
        PacketBuildingServer.FlameLight = CycleTimeDay.lightFlame;
        Server.sendToAllTCP(PacketBuildingServer);
        ZoomConstTransport();
        KeyboardObj.zoom_const();

    }
    public void PacketObjectMapServer(int ix,int iy,ArrayList<PacketMapObject>YMap){
        YMap.add(new PacketMapObject());
        YMap.get(ix).x = BlockList2D.get(iy).get(ix).objMap.x;
        YMap.get(ix).y = BlockList2D.get(iy).get(ix).objMap.y;
        YMap.get(ix).width = BlockList2D.get(iy).get(ix).objMap.width;
        YMap.get(ix).height = BlockList2D.get(iy).get(ix).objMap.height;
        YMap.get(ix).ix = ix;
        YMap.get(ix).iy = iy;
        YMap.get(ix).lighting = BlockList2D.get(iy).get(ix).objMap.lighting;
        YMap.get(ix).distance_lighting = BlockList2D.get(iy).get(ix).objMap.distance_lighting;
        YMap.get(ix).objectAssets = BlockList2D.get(iy).get(ix).objMap.assets;
    }
    public void PacketBuildServer(int i){
        PacketBuildingServer.BuildPack.add(new BuildPacket());
        PacketBuildingServer.BuildPack.get(i).name = BuildingList.get(i).name;
        PacketBuildingServer.BuildPack.get(i).x = BuildingList.get(i).x;
        PacketBuildingServer.BuildPack.get(i).y = BuildingList.get(i).y;
    }

    //Используется когда клиент отправляет пакет серверу
    public void received(Connection c, Object p){
        if(p instanceof Packet_client) {
            Clients.add((Packet_client) p);
        }
        else if(p instanceof PlayerSpawnData){
            nConnect += 1;
            if(!p.equals(new SpawnPlayerVoid())) {
                int i2 = Main.PlayerList.size();
                ((PlayerSpawnData) p).SpawnPlayer(false);
                PlayerList.get(i2).nConnect = nConnect;
            }
        }
    }

    //Используется когда клиент покидает сервер.
    public void disconnected(Connection c){System.out.println("Клиент покинул сервер!");}
}
