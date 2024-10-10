package com.mygdx.game.metod;

import java.io.Serializable;

public class spisok_multiplayer implements Serializable {
    private Object[]po;

    public spisok_multiplayer(Object[]po) {
        this.po = po;
        //main.Main.enemy_obj = (ArrayList<transport.Transport>) this.po[0];
        //main.Main.player_obj = (ArrayList<transport.Transport>) this.po[1];
        //main.Main.build.build = (ArrayList<build.build>) this.po[2];
        //main.Main.bull_obj = (ArrayList<bull>) this.po[3];
    }

    public Object[] getPo() {
        return po;
    }

}
