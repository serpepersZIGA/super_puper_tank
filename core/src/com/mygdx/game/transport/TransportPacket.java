package com.mygdx.game.transport;

import java.util.ArrayList;

public class TransportPacket {
    public float x,y,rotation_corpus,rotation_tower,reload;
    public UnitType name;
    public byte team,crite_life;
    public int hp;
    public float speed;
    public boolean host;
    public int IDClient;
    public ArrayList<Float>rotation_tower_2 = new ArrayList<>();
}
