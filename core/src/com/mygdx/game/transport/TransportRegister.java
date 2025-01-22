package com.mygdx.game.transport;

import Content.Transport.Transport.*;
import com.mygdx.game.main.Main;

import java.util.ArrayList;

public class TransportRegister {
    public static ArrayList<TransportPacket> PacketPlayer = new ArrayList<>();
    public static ArrayList<TransportPacket> PacketEnemy = new ArrayList<>();
    public static ArrayList<DebrisPacket> PacketDebris = new ArrayList<>();
    public static Transport PlayerCannonFlame, PlayerCannonMortar, PlayerCannonBullTank, PlayerCannonAcid,
            PanzerMortarT1, PanzerFlameT1, PanzerAcidT1, PanzerT1, TrackRemountT1, TrackSoldatT1, Helicopter_t1;
    public TransportRegister() {
        PlayerCannonFlame = new PlayerCannonFlame(0,0, Main.PlayerList,false);
        PlayerCannonMortar = new PlayerCannonMortar(0,0, Main.PlayerList,false);
        PlayerCannonBullTank = new PlayerCannonBullTank(0,0, Main.PlayerList,false);
        PlayerCannonAcid = new PlayerCannonAcid(0,0, Main.PlayerList,false);

        PanzerMortarT1 = new PanzerMortarT1(0,0, Main.EnemyList);
        PanzerFlameT1 = new PanzerFlameT1(0,0, Main.EnemyList);
        PanzerAcidT1 = new PanzerAcidT1(0,0, Main.EnemyList);
        PanzerT1 = new PanzerT1(0,0, Main.EnemyList);

        TrackRemountT1 = new TrackRemountT1(0,0, Main.EnemyList);
        TrackSoldatT1 = new TrackSoldatT1(0,0, Main.EnemyList);
        Helicopter_t1 = new HelicopterT1(0,0, Main.EnemyList);


    }
}
