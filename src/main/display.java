package main;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class display {
    public static JFrame display;
    public static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    public static int width = (int) (screenSize.getWidth());
    public static int height = (int) (screenSize.getHeight());
    public static Game data;
    public static JLabel paint_1,paint_2,get_mouse;
    public static JPanel panel;
    public static double correct_int = 1.25;
    public static void display_create() throws IOException {
        panel = new JPanel();
        paint_1 = new JLabel();
        paint_2 = new JLabel();
        get_mouse = new JLabel();
        display = new JFrame();
        display.setSize(width,height);
        System.out.println(display.getSize());
        display.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        data = new Game();
        display.add(paint_1);
        display.add(paint_2);
        display.add(get_mouse);
        display.add(data);
        display.setVisible(true);

    }

}
