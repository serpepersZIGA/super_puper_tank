package com.mygdx.game.transport.SpawnPlayer;

import java.util.ArrayList;

public class PlayerSpawnListData {
    public static PlayerSpawnData PlayerSpawnCannonFlame,PlayerSpawnCannonMortar,PlayerSpawnCannonAcid,PlayerSpawnCannonBull;
    public PlayerSpawnListData(){
        PlayerSpawnCannonFlame = new SpawnPlayerCannonFlame();
        PlayerSpawnCannonMortar = new SpawnPlayerCannonMortar();
        PlayerSpawnCannonAcid = new SpawnPlayerCannonAcid();
        PlayerSpawnCannonBull = new SpawnPlayerCannonBull();
    }
}
