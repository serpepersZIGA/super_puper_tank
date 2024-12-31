package com.mygdx.game.menu.button.ButtonTank;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class ListTankPlayerAdd {
    public static void AddListTank(){
        new File("PlayerAllSpawnList").mkdirs();
//        File PlayerCannonAcidFile = new File("PlayerAllSpawnList/PlayerCannonAcid");
//        File PlayerCannonBullFile = new File("PlayerAllSpawnList/PlayerCannonBull");
//        File PlayerCannonMortarFile = new File("PlayerAllSpawnList/PlayerCannonMortar");
//        File PlayerCannonFlameFile = new File("PlayerAllSpawnList/PlayerCannonFlame");
        ArrayList<File>FileTank = new ArrayList<>();
        FileTank.add(new File("PlayerAllSpawnList/PlayerCannonAcid"));
        FileTank.add(new File("PlayerAllSpawnList/PlayerCannonBull"));
        FileTank.add(new File("PlayerAllSpawnList/PlayerCannonMortar"));
        FileTank.add(new File("PlayerAllSpawnList/PlayerCannonFlame"));
        for (File file : FileTank){
            try {
                PrintWriter out = new PrintWriter(file.getPath());
                out.println(file.getName());
                out.close();
            } catch (IOException ignored) {
            }
        }
    }
}
