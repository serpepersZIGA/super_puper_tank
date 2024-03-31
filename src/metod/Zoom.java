package metod;

import main.Game;
import main.display;

import javax.swing.*;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class Zoom extends JComponent implements MouseWheelListener {
    public Zoom(){
        display.display.addMouseWheelListener(this);
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        if(e.getScrollType() == MouseWheelEvent.WHEEL_UNIT_SCROLL && Game.zoom >0.4 && Game.zoom<2.1) {
            Game.zoom -= (.1 * e.getWheelRotation());
            if(Game.zoom > 2){
                Game.zoom = 2;
            }
            else if(Game.zoom < 0.5){
                Game.zoom = 0.5;
            }
        }
    }
}
