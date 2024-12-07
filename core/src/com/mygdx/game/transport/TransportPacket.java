package com.mygdx.game.transport;

import java.util.ArrayList;

public class TransportPacket {
    public double x,y,rotation_corpus,rotation_tower,reload;
    public UnitType name;
    public byte team,crite_life;
    public int hp;
    public double speed;
    public boolean host;
    public ArrayList<Double>rotation_tower_2 = new ArrayList<>();
}
