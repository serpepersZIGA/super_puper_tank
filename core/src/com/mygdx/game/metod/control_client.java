package com.mygdx.game.metod;
import com.mygdx.game.main.Main;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class control_client implements KeyListener {
    public boolean press_w,press_a,press_s,press_d,left_mouse,right_mouse;
    public double mouse_x,mouse_y;
    public control_client(){

    }
    public void iteration_control_client(){
        this.mouse_x = (((MouseInfo.getPointerInfo().getLocation().getX() - 18))*1.23)/ Main.zoom + Main.rc.get(0).x2;
        this.mouse_y = (((MouseInfo.getPointerInfo().getLocation().getY() - 45)*1.23))/ Main.zoom + Main.rc.get(0).y2;
        this.left_mouse = true;
        this.right_mouse = true;
    }
//    public Object[]iteration_control(){
//        boolean[] mouse_e = main.Main.m_control.get(0).mouse_event();
//        return new Object[]{mouse_e[0],mouse_e[1],this.press_w,this.press_a,this.press_s,this.press_d};
//    }
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W) {
            this.press_w = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_S) {
            this.press_s = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_A) {
            this.press_a = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_D) {
            this.press_d = true;
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W) {
            this.press_w = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_S) {
            this.press_s = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_A) {
            this.press_a = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_D) {
            this.press_d = false;
        }
    }
}
