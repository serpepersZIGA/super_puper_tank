package com.mygdx.game.menu;

import Content.Block.Asphalt;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.mygdx.game.block.UpdateAsphalt1;
import com.mygdx.game.main.Main;
import com.mygdx.game.menu.button.MapLoad;

import java.awt.*;
import java.io.File;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Objects;

public class MapAllLoad{
    public static void MapCount(){
        FileHandle[] files = Gdx.files.internal("maps").list();


        System.out.println(Arrays.toString(files));
        int x = 1200;
        int y = 200;
        System.out.println(files.length);
        for (FileHandle file: files) {
            System.out.println(file.path());
            Main.ButtonList.add(new MapLoad(x,y,260,140,file.path()));
            y+= 140;
        }


    }
    public static void AsphaltSpawn(int x,int y){
        Main.BlockList2D.get(y).get(x).render_block = new UpdateAsphalt1();
    }
}
