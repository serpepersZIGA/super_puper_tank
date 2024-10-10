package com.mygdx.game.metod;

import java.io.Serializable;

public class spisok_multiplayer_client implements Serializable {
    public boolean press_w,press_a,press_s,press_d,left_mouse,right_mouse;
    public double mx,my;
    public spisok_multiplayer_client(boolean press_w,boolean press_a,boolean press_s,boolean press_d,
                                     boolean left_mouse,boolean right_mouse,double mx,double my){
        this.press_w = press_w;
        this.press_a = press_a;
        this.press_s = press_s;
        this.press_d = press_d;
        this.left_mouse = left_mouse;
        this.right_mouse = right_mouse;
        this.mx = mx;
        this.my = my;
    }

}
