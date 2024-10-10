package com.mygdx.game.metod;

import javax.swing.*;
import java.awt.*;

public class interface_panel {
    public static void panel_information(int e, int x, int y, int size, Color c,JLabel panel){
        panel.setText(String.valueOf(e));
        panel.setForeground(c);
        panel.setBounds(x,y,0,0);
        panel.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,size));

    }
    public static void panel_information(double e, int x, int y, int size, Color c,JLabel panel){
        panel.setText(String.valueOf(e));
        panel.setForeground(c);
        panel.setBounds(x,y,100,100);
        panel.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,size));

    }
    public static void panel_information(byte e, int x, int y, int size, Color c,JLabel panel){
        panel.setText(String.valueOf(e));
        panel.setForeground(c);
        panel.setBounds(x,y,100,100);
        panel.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,size));}
    public static void panel_information(String e, int x, int y, int size, Color c,JLabel panel){
        panel.setText(e);
        panel.setForeground(c);
        panel.setBounds(x,y,100,100);
        panel.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,size));

    }

}
