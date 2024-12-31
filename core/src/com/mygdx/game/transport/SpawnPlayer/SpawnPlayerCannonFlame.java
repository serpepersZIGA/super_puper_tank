package com.mygdx.game.transport.SpawnPlayer;

import Content.Transport.Transport.PlayerCannonFlame;
import com.mygdx.game.main.Main;

public class SpawnPlayerCannonFlame extends PlayerSpawnData{
    public void SpawnPlayer(boolean host){
        Main.PlayerList.add(new PlayerCannonFlame(200,200,Main.PlayerList,host));
    }
}
