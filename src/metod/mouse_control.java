package metod;

import main.display;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class mouse_control extends MouseAdapter {
    private boolean left_mouse,right_mouse;
    public mouse_control(){
        display.display.addMouseListener(this);
        //this.right_mouse = right_mouse;
        //this.left_mouse = left_mouse;

    }
    @Override
    public void mousePressed (MouseEvent e){
        if (SwingUtilities.isLeftMouseButton (e))
        {
            this.left_mouse = true;
        }
        else if (SwingUtilities.isRightMouseButton (e))
        {
            this.right_mouse = true;
        }
        //metod.interface_panel.panel_information(String.valueOf(this.left_mouse),600,600,24, Color.YELLOW,main.display.paint_2);
        //metod.interface_panel.panel_information(String.valueOf(this.right_mouse),500,600,24, Color.YELLOW,main.display.paint_1);
    }
    @Override
    public void mouseReleased (MouseEvent e){
        if (SwingUtilities.isLeftMouseButton (e))
        {
            this.left_mouse = false;
        }
        else if (SwingUtilities.isRightMouseButton (e))
        {
            this.right_mouse = false;
        }
        //metod.interface_panel.panel_information(String.valueOf(this.left_mouse),600,600,24, Color.YELLOW,main.display.paint_2);
        //metod.interface_panel.panel_information(String.valueOf(this.right_mouse),500,600,24, Color.YELLOW,main.display.paint_1);
    }
    public boolean[] mouse_event(){
        //metod.interface_panel.panel_information(String.valueOf(this.left_mouse),600,600,24, Color.YELLOW,main.display.paint_2);
        //metod.interface_panel.panel_information(String.valueOf(this.right_mouse),500,600,24, Color.YELLOW,main.display.paint_1);
        return new boolean[]{this.left_mouse,this.right_mouse};
    }
}
