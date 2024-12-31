package com.mygdx.game.menu.button.ButtonTank;

import Content.Transport.Transport.PlayerCannonMortar;
import com.mygdx.game.MapFunction.MapScan;
import com.mygdx.game.main.Main;
import com.mygdx.game.menu.button.Button;
import com.mygdx.game.menu.button.Maps;
import com.mygdx.game.transport.SpawnPlayer.SpawnPlayerCannonFlame;
import com.mygdx.game.transport.SpawnPlayer.SpawnPlayerCannonMortar;

import static com.mygdx.game.main.Main.KeyboardObj;
import static com.mygdx.game.main.Main.PlayerList;
import static com.mygdx.game.transport.SpawnPlayer.PlayerSpawnListData.*;

public class TankChoice extends Button {
    public TankChoice(int x, int y, int width, int height,String TankName) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        //this.txt = txt;
        DataRect();
        this.txt = TankName;
        this.TankName = TankName;
        this.ConfigMenu = 1;
        XTXT -= 40;
        TypeFont = true;

    }

    @Override
    public void render(int i) {
        super.render(i);
        XYDetectedButtonRect();
        ActionButton1();
        ActionButton();
        RenderButtonRect();
    }

    protected void ActionButton() {
        if (condition) {
            switch(TankName){
                case "PlayerCannonMortar":{
                    Main.SpawnPlayer = PlayerSpawnCannonMortar;
                    break;
                }
                case "PlayerCannonFlame":{
                    Main.SpawnPlayer = PlayerSpawnCannonFlame;
                    break;
                }
                case "PlayerCannonBull":{
                    Main.SpawnPlayer = PlayerSpawnCannonBull;
                    break;
                }
                case "PlayerCannonAcid":{
                    Main.SpawnPlayer = PlayerSpawnCannonAcid;
                    break;
                }
            }
            KeyboardObj.zoom_const();

            condition = false;
        }
    }
}
