package com.mygdx.game.main;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.game.block.Block;
import com.mygdx.game.block.air;
import com.mygdx.game.block.dirt;
import com.mygdx.game.build.Build;
import com.mygdx.game.build.big_build_wood_1;
import com.mygdx.game.build.home_1;
import com.mygdx.game.bull.Bull;
import com.mygdx.game.data_base;
import com.mygdx.game.metod.Keyboard;
import com.mygdx.game.metod.Zoom;
import com.mygdx.game.metod.render_center;
import com.mygdx.game.particle.flame_spawn;
import com.mygdx.game.particle.particle;
import com.mygdx.game.soldat.Soldat;
import com.mygdx.game.sound_archive;
import com.mygdx.game.transport.*;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Main extends ApplicationAdapter{
	public static ArrayList<Transport> player_obj = new ArrayList<>();
	public static ArrayList<Transport> enemy_obj = new ArrayList<>();
	public static ArrayList<Build>build = new ArrayList<>();
	public static ArrayList<Bull>bull_obj = new ArrayList<>();
	public static ArrayList<particle> flame_static_obj = new ArrayList<>();
	public static ArrayList<particle> flame_obj = new ArrayList<>();
	public static ArrayList<Soldat>soldat_obj = new ArrayList<>();
	public static ArrayList<particle>bang_obj = new ArrayList<>();
	public static ArrayList<particle>flame_particle_obj = new ArrayList<>();
	public static ArrayList<particle>liquid_obj = new ArrayList<>();
	public static ArrayList<com.mygdx.game.particle.flame_spawn>flame_spawn = new ArrayList<>();
	public static ArrayList<Transport>helicopter_obj = new ArrayList<>();
	public static ArrayList<Transport>debris = new ArrayList<>();

	public static ArrayList<sound_archive>sa = new ArrayList<>();
	public static ArrayList<Block>block_air = new ArrayList<>();
	public static ArrayList<ArrayList<Block>>matrix = new ArrayList<>();

	public static List<render_center>rc = new ArrayList<>();
	public static  ArrayList<Transport>tower_obj = new ArrayList<>();
	public static ArrayList<double[]> size_render = new ArrayList<>();
	public static ArrayList<com.mygdx.game.metod.control_client>control_client = new ArrayList<>();
	public static ArrayList<Block>block = new ArrayList<>();
	public static ArrayList<Zoom> Zoom_list = new ArrayList<>();
	public static data_base content_base;
	public static ArrayList<Sprite>sprites = new ArrayList<>();
	public static SpriteBatch batch;
	public static Keyboard keyboard;
	public static boolean press_w,press_s,press_a,press_d;
	public static boolean left_mouse,right_mouse;
	public static boolean press_w_client,press_s_client,press_a_client,press_d_client;
	public static int screenWidth;
	public static int screenHeight;
	public static boolean left_mouse_client,right_mouse_client;
	public static double mouse_x_client,mouse_y_client;
	public static byte game_sost = 0;
	public int i,i_images;
	public static double zoom = 1;
	public static int mouse_x,mouse_y;
	public static ServerSocket Serversocket;
	public static ShapeRenderer render;
	public static OrthographicCamera camera;
	public static Socket socket;
	public static AI ai;

	//public Thread thread = new Thread(new action_game_host());
	//private static OrthographicCamera camera;
	//public static File file = new File("src/buffer.data");
	public static int width_2,height_2,x_block,y_block,width_block= 50,height_block =50,width_block_air= 10,height_block_air =10,quantity_width,quantity_height;
	public int[][]xy;
	Texture img;
	public Main(){
		//camera = new OrthographicCamera();
		//camera.setToOrtho(false,1000, 1000);
		Main.sa.add(new sound_archive());



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
		//this.addMouseWheelListener(this);
		//input = new ObjectInputStream(main.Game.socket.getInputStream());
		//output = new ObjectOutputStream(main.Game.socket.getOutputStream());

		//main.display.main.display.addMouseWheelListener(this);
		//setFocusable(true);
	}


	private void spawn_object(){
		Main.player_obj.add(new player_cannon_flame(400,600,Main.player_obj));
		//Main.player_obj.add(new player_cannon_flame(600,600,Main.player_obj));
		//Main.enemy_obj.add(new panzer_howitzer_mt1(1800,800,enemy_obj));
		//Main.enemy_obj.add(new panzer_mt1_flame(1500,800,enemy_obj));
		Main.build.add(new big_build_wood_1(1200,400,0));
		Main.enemy_obj.add(new panzer_mt1_flame(700,1000,Main.enemy_obj));
		//Main.enemy_obj.add(new track_remont_f1(400,600,Main.enemy_obj));
		//Main.enemy_obj.add(new track_soldat_t1(700,600,Main.enemy_obj));
		//Main.build.add(new home_1(600,400,0));
		//Main.build.add(new home_1(800,400,0));
		//Main.build.add(new home_1(1000,400,0));
		//Main.build.add(new home_1(1170,400,0));
		Main.flame_spawn.add(new flame_spawn(500,500));
	}
	public void field(int width_field,int height_field){
		quantity_width = width_field/width_block;
		quantity_height = height_field/height_block;
		xy = new int[quantity_width][quantity_height];
		width_2 = width_block/2;
		height_2 = height_block/2;
		width_block*=1.24;
		height_block*=1.24;

		x_block = width_2;
		y_block = 0;
		for(int i = 0;i<quantity_height;i++){
			matrix.add(new ArrayList<Block>());
			y_block += height_block;
			x_block = 0;

			for(int i2 = 0;i2<quantity_width;i2++){
				x_block += width_block;
				matrix.get(i).add(new dirt(x_block,y_block));


			}
		}
		quantity_width = (int)(screenWidth/width_block_air);
		quantity_height = (int)(screenHeight/height_block_air);
		y_block = -height_block_air;
		for(int i = quantity_height+1;i>0;i--){
			y_block += height_block_air;
			x_block = -width_block_air;

			for(int i2 = quantity_width+1;i2>0;i2--){
				x_block += width_block_air;
				Main.block_air.add(new air(x_block,y_block));

			}
		}
	}
	public void field_game(){
		for(Build build : Main.build) {
			for (int i = 0; i < quantity_height; i++) {
				for (int j = 0; j < quantity_width; j++) {
				}
			}
		}
	}
	public boolean rect_collision(int x1,int y1,int width,int height,double rotation,
								  int x2,int y2,int width2,int height2,double rotation_2){

		Rectangle rect1 = new Rectangle(x1,y1,width,height); // Прямоугольник 1
		Rectangle rect2 = new Rectangle(x2,y2,width2,height2); // Прямоугольник 2

		// Создаем аффинное преобразование для поворота
		AffineTransform transform1 = new AffineTransform();
		transform1.rotate(Math.toRadians(rotation), rect1.getCenterX(), rect1.getCenterY());
		AffineTransform transform2 = new AffineTransform();
		transform2.rotate(Math.toRadians(rotation_2), rect2.getCenterX(), rect2.getCenterY());

		// Преобразование прямоугольников с учетом поворота
		Area area1 = new Area(rect1);
		area1.transform(transform1);
		Area area2 = new Area(rect2);
		area2.transform(transform2);

		// Вычисление пересечения двух преобразованных прямоугольников
		area1.intersect(area2);

		// Проверка наличия пересечения
		if (!area1.isEmpty()) {
			//Rectangle intersection = area1.getBounds();
			//System.out.println("Прямоугольники пересекаются. Результат: " + intersection);
			return true;

		}
		return false;
	}

	@Override
	public void create () {
		camera = new OrthographicCamera();
		screenWidth = Gdx.graphics.getWidth();
		screenHeight = Gdx.graphics.getHeight();
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		render = new ShapeRenderer();
		Main.rc.add(new render_center(700,0));
		batch = new SpriteBatch();
		content_base = new data_base();
		keyboard = new Keyboard();
		Gdx.input.setInputProcessor(keyboard);
		//Gdx.input.setInputProcessor(keyboard);
		field(3000, 3000);
		spawn_object();
		for(i = 0;i<matrix.size();i++){
			for(int i2 = 0;i2<matrix.get(i).size();i2++){
				matrix.get(i).get(i2).passability_detected();

			}
		}
		keyboard.zoom_const();
		ai = new AI();

	}
	@Override
	public void render () {
		new action_game_host();
	}
	@Override
	public void dispose () {
		batch.dispose();
		render.dispose();
		//img.dispose();
	}
	public void resize(int width,int height){}
}