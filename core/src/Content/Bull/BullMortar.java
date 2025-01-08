package Content.Bull;

import com.mygdx.game.bull.Bull;
import com.mygdx.game.main.Main;

public class BullMortar extends Bull {
    public BullMortar(float x, float y, float rotation, float damage, float penetration, float damage_fragment, float penetration_fragment, byte type_time, byte height){
        this.x = x;this.y = y;
        this.damage = damage;this.penetration = penetration;
        this.speed = 6;
        this.size = 14;
        this.size_render = (int)(size*Main.Zoom);
        this.time = 120;
        this.rotation = rotation;
        this.damage_fragment = damage_fragment;
        this.penetration_fragment = penetration_fragment;
        this.amount_fragment = 15;

        this.type_team = type_time;
        this.height = height;
        this.r = (float)1/255*236;
        this.g = (float)1/255*124;
        this.b = (float)1/255*38;
        type = 3;
        speed_save();
    }
    public void all_action(int i){
        super.bull_move_xy();
        super.corpus_bull_mortar(Main.EnemyList);
        super.corpus_bull_mortar(Main.PlayerList);
        super.corpus_bull_mortar(Main.DebrisList);
        super.BullBuildMortar();
        super.soldat_bull(Main.SoldatList);
        super.fragments_create();
        this.update();
        super.clear(i);
    }
    public void all_action_client(int i){
        //super.all_action_client(i);
        super.bull_move_xy();
        super.corpus_bull_mortar(Main.EnemyList);
        super.corpus_bull_mortar(Main.PlayerList);
        super.corpus_bull_mortar(Main.DebrisList);
        super.BullBuildMortar();
        super.soldat_bull(Main.SoldatList);
        super.fragments_create_client();
        this.update();
        super.clear(i);
    }
    public void update(){
        center_render();
        Main.Render.setColor(r,g,b,1);
        Main.Render.circle((this.x_rend),(this.y_rend),this.size_render,this.size);
    }

}
