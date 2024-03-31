package main;

import block.*;
import metod.Zoom;
import particle.*;
import sound.sound_archive;
import transport.*;
import metod.render_center;
import metod.mouse_control;
import metod.control_client;
import build.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


public class Game extends JComponent implements ActionListener,KeyListener,MouseListener,MouseWheelListener {
    public Timer time;
    public static boolean press_w,press_s,press_a,press_d;
    public static boolean left_mouse,right_mouse;
    public static boolean press_w_client,press_s_client,press_a_client,press_d_client;
    public static boolean left_mouse_client,right_mouse_client;
    public static double mouse_x_client,mouse_y_client;
    public static byte game_sost = 0;
    public int i,i_images;
    public static double zoom = 1;
    public static Graphics2D g2;
    public static ServerSocket Serversocket;
    public static Socket socket;
    public static File file = new File("src/buffer.data");
    public int width_2,height_2,x_block,y_block,width_block= 50,height_block =50,width_block_air= 20,height_block_air =20;
    public static ArrayList<Object> list = new ArrayList<>();
    //public block_iteration block_air_it = new block_iteration(main.Main.block_air);
    //public block_iteration block_it = new block_iteration(main.Main.block);
    public Game() throws IOException {
        time = new Timer(15,this);
        Main.sa.add(new sound_archive());
        Main.rc.add(new render_center(700,0));

//        if(game_sost == 0){
//            try {
//                String ip = InetAddress.getLocalHost().getHostAddress();
//                Serversocket = new ServerSocket(2451,5000);
//                socket = Serversocket.accept();
//                System.out.println(ip);
//                //DataOutputStream outToClient = new DataOutputStream(socket.getOutputStream());
//                //DataInputStream inFromClient = new DataInputStream(socket.getInputStream());
//                //ObjectOutputStream objectOutputStream = new ObjectOutputStream(outToClient);
//
//                spawn_object();
//
//            } catch (IOException ex) {
//                ex.printStackTrace();
//            }
//        }
//        else if(game_sost == 1){
//            //String ip = InetAddress.getLocalHost().getHostAddress();
//            socket = new Socket("127.0.0.1", 2451);
//            //ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
//        }
        Main.m_control.add(new mouse_control());
        Main.control_client.add(new control_client());
        Main.zoom.add(new Zoom());
        this.addKeyListener(this);
        //this.addMouseWheelListener(this);
        spawn_object();
        //input = new ObjectInputStream(main.Game.socket.getInputStream());
        //output = new ObjectOutputStream(main.Game.socket.getOutputStream());

        //main.display.main.display.addMouseWheelListener(this);
        setFocusable(true);
        field(5000,5000);

        time.start();
    }
    private void spawn_object(){
        Main.player_obj.add(new player_cannon_flame(400,600,Main.player_obj));
        Main.player_obj.add(new player_cannon_flame(600,600,Main.player_obj));
        Main.build.add(new home_1(400,400,0));
        Main.build.add(new home_1(600,400,0));
        Main.build.add(new home_1(800,400,0));
        Main.build.add(new home_1(1000,400,0));
        Main.build.add(new home_1(1170,400,0));
        Main.flame_spawn.add(new flame_spawn(500,500));
    }
    public void field(int width_field,int height_field){
        int quantity_width = width_field/this.width_block;
        int quantity_height = height_field/this.height_block;
        this.width_2 = this.width_block/2;
        this.height_2 = this.height_block/2;
        this.width_block*=1.24;
        this.height_block*=1.24;

        this.x_block = this.width_2;
        this.y_block = -1000;
        for(int i = quantity_height;i>0;i--){
            this.y_block += this.height_block;
            this.x_block = -1000;

            for(int i2 = quantity_width;i2>0;i2--){
                this.x_block += this.width_block;
                Main.block.add(new dirt(this.x_block,this.y_block));

            }
        }
        quantity_width = (int)(display.width*1.24/this.width_block_air);
        quantity_height = (int)(display.height*1.24/this.height_block_air);
        this.y_block = -20;
        for(int i = quantity_height+1;i>0;i--){
            this.y_block += this.height_block_air;
            this.x_block = -20;

            for(int i2 = quantity_width+1;i2>0;i2--){
                this.x_block += this.width_block_air;
                Main.block_air.add(new air(this.x_block,this.y_block));

            }
        }


    }
    public void paintComponent(Graphics g){
            super.paintComponent(g);
            //g.setColor(Color.gray);
            //g.fillRect(0,0,main.display.width,main.display.height);
            g2 = (Graphics2D) g;
            for (i = 0; i < Main.block.size(); i++) {
                Main.block.get(i).update(g2);
            }
            //action_block(block_it,g2);
            for (i = 0; i < Main.flame_static_obj.size(); i++) {
                Main.flame_static_obj.get(i).update(g2);
            }
            for (i = 0; i < Main.flame_obj.size(); i++) {
                Main.flame_obj.get(i).update(g2);
            }
            for (particle acid : Main.liquid_obj) {
                acid.update(g2);
            }
            for (i = 0; i < Main.bang_obj.size(); i++) {
                Main.bang_obj.get(i).update(g2);
            }
            for (i = 0; i < Main.flame_particle_obj.size(); i++) {
                Main.flame_particle_obj.get(i).update(g2);
            }
                for (int i = 0; i < Main.images.size(); i++) {
                    AffineTransform transform = Main.transforms.get(i);
                    BufferedImage image = Main.images.get(i);
                    double[] zoom = Main.size_render.get(i);
                    //transform.scale(game.zoom, game.zoom);
                    g2.setTransform(transform);
                    g2.drawImage(image, 0, 0, (int) (zoom[0] * Game.zoom), (int) (zoom[1] * Game.zoom), null);
                    //g2.dispose();
                }
            Main.images.clear();
            Main.size_render.clear();
            Main.transforms.clear();

            AffineTransform at = new AffineTransform();
            g2.setTransform(at);
            for (i = 0; i < Main.bull_obj.size(); i++) {
                Main.bull_obj.get(i).update(g2);
            }
            for (i = 0; i < Main.player_obj.size(); i++) {
                Main.player_obj.get(i).update_indicator(g2);
            }
            for (i = 0; i < Main.enemy_obj.size(); i++) {
                Main.enemy_obj.get(i).update_indicator_2(g2);
            }
            for (i = 0; i < Main.helicopter_obj.size(); i++) {
                Main.helicopter_obj.get(i).update_indicator(g2);
            }

            for (i = 0; i < Main.player_obj.size(); i++) {
                Main.player_obj.get(i).indicator_reload(g2);
            }
            for (i = 0; i < Main.enemy_obj.size(); i++) {
                if (Main.enemy_obj.get(i).priority_paint == 0) {
                    Main.enemy_obj.get(i).indicator_reload(g2);
                }
            }
            for (i = 0; i < Main.enemy_obj.size(); i++) {
                if (Main.enemy_obj.get(i).priority_paint == 1) {
                    Main.enemy_obj.get(i).indicator_reload(g2);
                }
            }
            for (i = 0; i < Main.helicopter_obj.size(); i++) {
                Main.helicopter_obj.get(i).indicator_reload(g2);
            }
            //action_block(block_air_it,g2);
            for (i = 0; i < Main.block_air.size(); i++) {
                Main.block_air.get(i).update_air(g2);
            }
        //metod.interface_panel.panel_information(main.Main.tower_obj.size(),400,400,24,Color.red,main.display.paint_1);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
//        if(game_sost == 0) {
//            //metod.interface_panel.panel_information(main.Main.build.size(),400,200,24,Color.YELLOW,main.display.paint_1);
//            metod.Stream.getOutputStream();
//            Thread thread = new Thread(new main.action_game_host());
//            thread.start();
//            repaint();
//            metod.Stream.getInputStream_client();
//            //metod.Stream.getInputStream();
//            //metod.Stream.getOutputStream();
//        }
//        if(game_sost == 1) {
//            //metod.interface_panel.panel_information(main.Main.build.size(),400,200,24,Color.YELLOW,main.display.paint_1);
//            metod.Stream.getInputStream();
//            Thread thread = new Thread(new main.action_game_client());
//            thread.start();
//            repaint();
//            metod.Stream.getOutputStream_client();
//            //metod.Stream.getInputStream();
//            //metod.Stream.getOutputStream();
//        }
        Thread thread = new Thread(new action_game_host());
        thread.start();
        repaint();


    }
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

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

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W) {
            Game.press_w = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_S) {
            Game.press_s = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_A) {
            Game.press_a = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_D) {
            Game.press_d = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W) {
            Game.press_w = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_S) {
            Game.press_s = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_A) {
            Game.press_a = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_D) {
            Game.press_d = false;
        }
    }
}

