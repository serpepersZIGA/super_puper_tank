package com.mygdx.game.main;
import Content.Particle.*;
import Content.Transport.Transport.*;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.game.Map.MapScan;
import com.mygdx.game.block.Block;
import Content.Block.BlockMap;
import Content.Block.Air;
import com.mygdx.game.block.UpdateRegister;
import com.mygdx.game.build.Building;
import com.mygdx.game.bull.Bull;
import Data.DataImage;
import com.mygdx.game.menu.InputWindow;
import com.mygdx.game.menu.MapAllLoad;
import com.mygdx.game.menu.button.*;
import com.mygdx.game.method.Keyboard;
import com.mygdx.game.method.Option;
import com.mygdx.game.method.RenderCenter;
import com.mygdx.game.particle.*;
import com.mygdx.game.soldat.Soldat;
import Content.Soldat.SoldatRegister;
import Data.DataSound;
import com.mygdx.game.transport.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;


public class Main extends ApplicationAdapter {
	public static ArrayList<Transport> PlayerList = new ArrayList<>();
	public static ArrayList<Transport> EnemyList = new ArrayList<>();
	public static ArrayList<Building> BuildingList = new ArrayList<>();
	public static ArrayList<Bull> BullList = new ArrayList<>();
	public static LinkedList<Particle> FlameStaticList = new LinkedList<>();
	public static ArrayList<Button>ButtonList = new ArrayList<>();
	public static LinkedList<Particle> FlameList = new LinkedList<>();
	public static ArrayList<Soldat> SoldatList = new ArrayList<>();
	public static LinkedList<Particle> BangList = new LinkedList<>();
	public static LinkedList<Particle> FlameParticleList = new LinkedList<>();
	public static ArrayList<Particle> LiquidList = new ArrayList<>();
	public static LinkedList<Particle> FlameSpawnList = new LinkedList<>();
	public static ArrayList<Transport> HelicopterList = new ArrayList<>();
	public static ArrayList<Transport> DebrisList = new ArrayList<>();

	public static ArrayList<DataSound> ContentSound = new ArrayList<>();
	public static ArrayList<ArrayList<Block>> AirList = new ArrayList<>();
	public static ArrayList<ArrayList<Block>> BlockList2D = new ArrayList<>();

	public static RenderCenter RC;
	//public static ArrayList<double[]> size_render = new ArrayList<>();
	public static DataImage ContentImage;
	public static SpriteBatch Batch;
	public static Keyboard KeyboardObj;
	public static boolean PressWClient, PressSClient, PressAClient, PressDClient;
	public static int screenWidth;
	public static int screenHeight;
	public static boolean LeftMouseClient, RightMouseClient;
	public static double MouseXClient, MouseYClient;
	private static int i;
	public static float Zoom = 1;
	public static ShapeRenderer Render;
	public static UpdateRegister UpdateBlockReg;
	public static AI Ai;
	public static boolean EnumerationList;
	public static ActionGame ActionGame;
	public static boolean GameStart;
	public static boolean GameHost;
	public static TransportRegister TransportRegister;
	public static Content.Soldat.SoldatRegister SoldatRegister;
	public static int width_2,height_2,x_block,y_block,width_block= 50,height_block =50,width_block_air= 15,height_block_air =15,quantity_width,quantity_height;
	public int[][]xy;
	public static int width_block_zoom= 50,height_block_zoom =50,width_block_render= 63,height_block_render =63;
	public static float radius_air_max = 150,radius_air_max_zoom;
	public static ServerMain serverMain;
	public static ClientMain Main_client;
	public static Option Option;
	public static PackerServer PacketServer;
	public static Packet_client PacketClient;
	public static int TickBlock,TickBlockMax = 600;
	public static BitmapFont font,font2;
	public static byte ConfigMenu;
	public static InputWindow InputWindow;
	public static int xMaxAir ;
	public static int yMaxAir ;
	public static Texture textureBuffer;
	public static int IDClient;
	public static  ArrayList<Packet_client> Clients = new ArrayList<>();


	public Main(){
	}



	public static void spawn_object(){
		PlayerList.add(new PlayerCannonFlame(200,200, PlayerList,true));
//		BuildingList.add(new BigBuildingWood1(500,600,0));
//		BuildingList.add(new BigBuildingWood1(500,1200,0));
//		BuildingList.add(new BigBuildingWood1(1200,1200,0));
//		BuildingList.add(new BigBuildingWood1(1200,600,0));
		MapScan.MapInput("maps/MapBase.mapt");
		MapAllLoad.MapCount();
		EnemyList.add(new PanzerFlameT1(2200,2000,Main.EnemyList));
		EnemyList.add(new TrackRemountT1(2200,2100,Main.EnemyList));
		LiquidList.add(new Acid(200,200));
		LiquidList.add(new Blood(200,200));
		FlameSpawnList.add(new FlameSpawn(200,200));
		FlameStaticList.add(new FlameStatic(200,200));
		BangList.add(new Bang(200,200,10));
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
			BlockList2D.add(new ArrayList<Block>());
			y_block += height_block;
			x_block = 0;

			for(int i2 = 0;i2<quantity_width;i2++){
				x_block += width_block;
				BlockList2D.get(i).add(new BlockMap(x_block,y_block));


			}
		}
		quantity_width = (int)(screenWidth/width_block_air);
		quantity_height = (int)(screenHeight/height_block_air);
		y_block = -height_block_air;
		for(int i = 0; i<quantity_height+1;i++){
			AirList.add(new ArrayList<Block>());
			y_block += height_block_air;
			x_block = -width_block_air;
			for(int i2 = 0; i2<quantity_width+1;i2++){
				x_block += width_block_air;
				AirList.get(i).add(new Air(x_block,y_block));

			}
		}

		LiquidList.add(new Acid(200,200));
		LiquidList.add(new Blood(200,200));
		FlameSpawnList.add(new FlameSpawn(200,200));
		FlameStaticList.add(new FlameStatic(200,200));
		BangList.add(new Bang(200,200,10));
	}

	@Override
	public void create () {
		textureBuffer = new Texture("image/infantry/soldat_enemy.png");
		ContentImage = new DataImage();
		ContentSound.add(new DataSound());
		screenWidth = Gdx.graphics.getWidth();
		screenHeight = Gdx.graphics.getHeight();
		Render = new ShapeRenderer();
		RC = new RenderCenter(0,0);
		Batch = new SpriteBatch();
		font = TXTFont(64,"font/Base/BaseFont4.ttf");
		font2 = TXTFont(16,"font/Base/BaseFont.ttf");
		InputWindow = new InputWindow();

		KeyboardObj = new Keyboard();
		Gdx.input.setInputProcessor(KeyboardObj);
		field(10000, 10000);
		spawn_object();
		Option = new Option();
		KeyboardObj.zoom_const();
		Ai = new AI();
		TransportRegister = new TransportRegister();
		SoldatRegister = new SoldatRegister();
		ButtonList.add(new Play(100,600,400,120,"PLAY",(byte)0));
		ButtonList.add(new PlayHost(100,800,400,120,"HOST",(byte)1));
		ButtonList.add(new PlayClient(100,600,400,120,"CONNECT",(byte)1));
		ButtonList.add(new Cancel(100,400,400,120,"CANCEL",(byte)1));
		ButtonList.add(new Maps(100,400,400,120,"MAPS",(byte)0));
		ButtonList.add(new Cancel(100,400,400,120,"CANCEL",(byte)3));
		ActionGame = new ActionMenu();
		RC.const_xy_block();
		xMaxAir = Main.AirList.get(0).size();
		yMaxAir = Main.AirList.size();





	}
	public static BitmapFont TXTFont(int size,String fontPath){
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(fontPath));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.characters = FreeTypeFontGenerator.DEFAULT_CHARS;
		parameter.size = size;
		BitmapFont font = generator.generateFont(parameter); // font size 64 pixels
		generator.dispose();
		return font;
	}
	@Override
	public void render () {
		ActionGame.action();
	}

	//Используется когда клиент отправляет пакет серверу

	//Используется когда клиент покидает сервер.

	@Override
	public void dispose () {
		Batch.dispose();
		Render.dispose();
		font.dispose();
		ContentImage.dispose();
		if(ServerMain.Server != null) {
			try {
				ServerMain.Server.dispose();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		else if(ClientMain.Client != null) {
			try {
				ClientMain.Client.dispose();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}
}