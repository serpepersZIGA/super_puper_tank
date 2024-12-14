package com.mygdx.game;

import com.mygdx.game.main.Main;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class GameStart {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();

		config.setWindowedMode(1920,1080-80);
//		if (Config.screenMode.equals("window")){
//			config.setWindowedMode(1536,864);
//		}else if (Config.screenMode.equals("fullScreen")){
//			config.setWindowedMode((int) screenSize.getWidth(), (int) screenSize.getHeight());
//		}
		config.setForegroundFPS(60);
		config.setTitle("Game");
		config.setWindowIcon("image/player/tower_player_1.png");
		new Lwjgl3Application(new Main(), config);

	}
}
