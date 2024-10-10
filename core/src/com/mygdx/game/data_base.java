package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import java.awt.*;

public class data_base {
    public Texture tower_player,tower_player_auxiliart_1,tower_enemy,tower_enemy_auxiliart_1;
    public Texture corpus_player,corpus_track_remount_enemy,corpus_track_soldat_enemy,corpus_enemy;
    public Texture build_1,build_2;
    public Texture track_enemy_1lvl;
    public Texture dirt_1,dirt_2,dirt_3,dirt_4;
    public Texture soldat_1;
    public data_base(){
        corpus_track_soldat_enemy = new Texture("image/enemy/machine_enemy_1lvl.png");
        corpus_track_remount_enemy = new Texture("image/enemy/corpus_enemy_medic_1.png");
        tower_player = new Texture("image/player/tower_player_1.png");
        tower_enemy = new Texture("image/enemy/tower_enemy_1.png");
        corpus_player = new Texture("image/player/corpus_player_many_tower_1.png");
        corpus_enemy = new Texture("image/enemy/corpus_enemy_many_tower_1.png");
        build_1 = new Texture("image/build/home_3.png");
        build_2 = new Texture("image/build/big_build_wood_1.png");
        dirt_1 = new Texture("image/other/dirt.png");
        dirt_2 = new Texture("image/other/dirt_2.png");
        dirt_3 = new Texture("image/other/dirt_3.png");
        dirt_4 = new Texture("image/other/dirt_4.png");
        tower_player_auxiliart_1 = new Texture("image/player/tower_auxiliart_player_1.png");
        tower_enemy_auxiliart_1 = new Texture("image/enemy/tower_auxiliart_enemy_1.png");
        soldat_1 = new Texture("image/infantry/soldat_enemy.png");
        track_enemy_1lvl = new Texture("image/enemy/machine_enemy_1lvl.png");
    }
}
