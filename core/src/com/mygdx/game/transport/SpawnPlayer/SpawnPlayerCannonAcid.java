package com.mygdx.game.transport.SpawnPlayer;

import Content.Transport.Transport.PlayerCannonAcid;
import com.mygdx.game.main.Main;

public class SpawnPlayerCannonAcid extends PlayerSpawnData{
    public void SpawnPlayer(boolean host){
        Main.PlayerList.add(new PlayerCannonAcid(200,200,Main.PlayerList,host));
    }
}
