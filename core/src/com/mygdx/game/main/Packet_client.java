package com.mygdx.game.main;

import com.mygdx.game.soldat.Soldat;
import com.mygdx.game.transport.Transport;

import java.util.ArrayList;

public class Packet_client {
    public boolean press_w,press_a,press_s,press_d;
    public boolean right_mouse,left_mouse;
    public int mouse_x,mouse_y;
    public double rotation_tower_client;
    public ArrayList<Double>rot_tower = new ArrayList<>();
}
