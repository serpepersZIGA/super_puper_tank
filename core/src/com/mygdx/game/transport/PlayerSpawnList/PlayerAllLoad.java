package com.mygdx.game.transport.PlayerSpawnList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.mygdx.game.main.Main;
import com.mygdx.game.menu.button.ButtonTank.TankChoice;
import com.mygdx.game.menu.button.MapLoad;

public class PlayerAllLoad {
    public static void PlayerCount(){
        FileHandle[] files = Gdx.files.internal("PlayerAllSpawnList").list();
        int x = 1200;
        int y = 200;
        for (FileHandle file: files) {
            System.out.println(file.readString());
            Main.ButtonList.add(new TankChoice(x,y,260,140,file.readString()));
            y+= 140;
        }
    }
}
