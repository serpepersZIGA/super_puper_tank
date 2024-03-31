package main;

import block.Block;
import build.Build;
import bull.Bull;
import metod.Zoom;
import metod.mouse_control;
import metod.render_center;
import particle.particle;
import particle.flame_spawn;
import soldat.Soldat;
import sound.sound_archive;
import transport.Transport;
import metod.control_client;

import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static ArrayList<Transport>player_obj = new ArrayList<>();
    public static ArrayList<Transport> enemy_obj = new ArrayList<>();
    public static ArrayList<Build>build = new ArrayList<>();
    public static ArrayList<Bull>bull_obj = new ArrayList<>();
    public static ArrayList<particle> flame_static_obj = new ArrayList<>();
    public static ArrayList<particle> flame_obj = new ArrayList<>();

    public static ArrayList<Soldat>soldat_obj = new ArrayList<>();
    public static ArrayList<particle>bang_obj = new ArrayList<>();
    public static ArrayList<particle>flame_particle_obj = new ArrayList<>();
    public static ArrayList<particle>liquid_obj = new ArrayList<>();
    public static ArrayList<flame_spawn>flame_spawn = new ArrayList<>();
    public static List<BufferedImage>images = new ArrayList<>();
    public static ArrayList<Transport>helicopter_obj = new ArrayList<>();
    public static List<AffineTransform>transforms = new ArrayList<>();
    public static List<mouse_control>m_control = new ArrayList<>();
    public static ArrayList<Transport>debris = new ArrayList<>();

    public static ArrayList<sound_archive>sa = new ArrayList<>();
    public static ArrayList<Block>block_air = new ArrayList<>();

    public static List<render_center>rc = new ArrayList<>();
    public static  ArrayList<Transport>tower_obj = new ArrayList<>();
    public static ArrayList<double[]> size_render = new ArrayList<>();
    public static ArrayList<control_client>control_client = new ArrayList<>();
    public static ArrayList<Block>block = new ArrayList<>();
    public static ArrayList<Zoom>zoom = new ArrayList<>();
    public static void main(String[] args) throws IOException {
        display.display_create();
    }
}