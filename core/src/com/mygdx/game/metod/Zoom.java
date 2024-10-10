package com.mygdx.game.metod;

import com.mygdx.game.main.Main;

import javax.swing.*;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class Zoom extends JComponent implements MouseWheelListener {
    public Zoom(){
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        if(e.getScrollType() == MouseWheelEvent.WHEEL_UNIT_SCROLL && Main.zoom >0.4 && Main.zoom<2.1) {
            Main.zoom -= (.1 * e.getWheelRotation());
            if(Main.zoom > 2){
                Main.zoom = 2;
            }
            else if(Main.zoom < 0.5){
                Main.zoom = 0.5;
            }
        }
    }
}
