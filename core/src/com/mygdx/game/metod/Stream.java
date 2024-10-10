package com.mygdx.game.metod;

import com.mygdx.game.build.Build;
import com.mygdx.game.bull.Bull;
import com.mygdx.game.transport.Transport;
import com.mygdx.game.main.Main;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;

public class Stream{
    public static void getInputStream(){
        try {
            ObjectInputStream input = new ObjectInputStream(Main.socket.getInputStream());
            spisok_multiplayer spisok_multiplayer = (spisok_multiplayer) input.readObject();
            Object[] list = spisok_multiplayer.getPo();
            Main.enemy_obj = (ArrayList<Transport>) list[0];
            Main.player_obj = (ArrayList<Transport>) list[1];
            Main.build = (ArrayList<Build>) list[2];
            Main.bull_obj = (ArrayList<Bull>) list[3];

        } catch (StreamCorruptedException e){
            e.printStackTrace();
        } catch (IOException | ClassNotFoundException es) {
            es.printStackTrace();
        }
    }
    public static void getOutputStream(){
        try {
            ObjectOutputStream output = new ObjectOutputStream(Main.socket.getOutputStream());
            //File file = new File("src/buffer.data");
            //FileOutputStream fos = new FileOutputStream(file);
            output.writeObject(new spisok_multiplayer(new Object[]{Main.enemy_obj, Main.player_obj, Main.build, Main.bull_obj, Main.soldat_obj, Main.debris}));

        } catch (IOException | ConcurrentModificationException ex) {
            ex.printStackTrace();
        }
    }
    public static void getInputStream_client(){
        try {
            ObjectInputStream input = new ObjectInputStream(Main.socket.getInputStream());
            spisok_multiplayer_client spisok_multiplayer = (spisok_multiplayer_client) input.readObject();
            Main.press_w_client = spisok_multiplayer.press_w;
            Main.press_a_client = spisok_multiplayer.press_a;
            Main.press_s_client = spisok_multiplayer.press_s;
            Main.press_d_client = spisok_multiplayer.press_d;
            Main.left_mouse_client = spisok_multiplayer.left_mouse;
            Main.right_mouse_client = spisok_multiplayer.right_mouse;
            Main.mouse_x_client = spisok_multiplayer.mx;
            Main.mouse_y_client = spisok_multiplayer.my;

        } catch (IOException | ClassNotFoundException es) {
            es.printStackTrace();
        }
    }
    public static void getOutputStream_client(){
        try {
            ObjectOutputStream output = new ObjectOutputStream(Main.socket.getOutputStream());
            //File file = new File("src/buffer.data");
            //FileOutputStream fos = new FileOutputStream(file);
            output.writeObject(new spisok_multiplayer_client(Main.press_w, Main.press_a, Main.press_s, Main.press_d,
                    Main.control_client.get(0).left_mouse,Main.control_client.get(0).right_mouse,Main.control_client.get(0).mouse_x
                    ,Main.control_client.get(0).mouse_y));

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
