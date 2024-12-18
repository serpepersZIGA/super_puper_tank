package com.mygdx.game.bull;
import Content.Bull.BullFragment;
import com.mygdx.game.main.Main;

import com.mygdx.game.method.move;
import com.mygdx.game.method.rand;
import Content.Particle.Acid;
import Content.Particle.FlameSpawn;
import Content.Particle.Bang;
import com.mygdx.game.soldat.Soldat;
import com.mygdx.game.transport.Transport;

import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.util.ArrayList;

import static com.mygdx.game.main.Main.*;
import static com.mygdx.game.main.Main.BlockList2D;
import static com.mygdx.game.method.pow2.pow2;
import static java.lang.StrictMath.sqrt;

public abstract class Bull implements Serializable {
    public float x,y,speed,damage,penetration,rotation,speed_x,speed_y,damage_fragment,penetration_fragment,t_damage;
    public int time,size,amount_fragment,x_rend,y_rend,size_render;
    public float r,g,b,r_wane = (float)(1/255*1.5),g_wane= (float)(1/255*1.5),b_wane= (float)(1/255);
    private boolean z;
    private int i;
    public byte type_team,height,clear_sost;
    public byte type;
    protected final void speed_save(){
        this.speed_x = move.move_sin(this.speed,this.rotation);
        this.speed_y = move.move_cos(this.speed,this.rotation);
    }
    protected final void bull_move_xy(){
        this.x -= this.speed_x;
        this.y -= this.speed_y;
    }
    protected final void fragments_create(){
        this.time -= 1;
        if(this.time<=0){
            Main.BangList.add(new Bang(this.x,this.y,10));
            for(int i3 = 0;i3<this.amount_fragment;i3++){
            Main.BullList.add(new BullFragment(this.x,this.y,this.damage_fragment,this.penetration_fragment,this.type_team));}
            this.clear_sost = 1;}

    }
    protected final void fragments_create_client(){
        this.time -= 1;
        if(this.time<=0){
            Main.BangList.add(new Bang(this.x,this.y,10));
            this.clear_sost = 1;}

    }
    protected final void color_fire(){
        this.r-= r_wane;
        this.g -=g_wane;
        this.b -= b_wane;
        if(this.b< 0){this.b = 0;return;}
        if(this.g< 0){this.g = 0;return;}
        if(this.r< 0){this.r = 0;return;}
    }
    protected final void spawn_flame(){
        if(1 == rand.rand(16)){
            Main.FlameSpawnList.add(new FlameSpawn(this.x,this.y));

        }
    }
    protected final void center_render(){
        double[]xy = Main.RC.render_obj(this.x,this.y);
        this.x_rend = (int)(xy[0]* Main.Zoom);
        this.y_rend = (int)(xy[1]* Main.Zoom);
    }
    protected final void spawn_acid(){
        if(1 == rand.rand(3)){
            Main.LiquidList.add(new Acid(this.x,this.y));
        }
    }
    protected final void clear(int i){
        if(this.clear_sost == 1){
            Main.BullList.remove(i);
        }
    }
    public void all_action(int i){
        this.update();

    }
    public void all_action_client(int i){
    }
    public void update(){

    }
    protected final void bull_clear_time(){
        this.time -= 1;

        if(this.time<=0){this.clear_sost = 1;}
    }
    protected final void bull_clear_time_flame(){
        this.time -= 1;
        if(this.time<=0){
            spawn_flame();
            this.clear_sost = 1;}
    }
    protected final void bull_clear_time_acid(){
        this.time -= 1;
        if(this.time<=0){
            spawn_acid();
            this.clear_sost = 1;}
    }
    protected void BullBuild(){
        int yM = (int) (y/height_block)-1;
        int xM = (int) (x/width_block)-1;
        if(xM>0&yM>0&xM< xMaxAir&yM<yMaxAir) {
            if (BlockList2D.get(yM).get(xM).passability & height == 1) {
                this.clear_sost = 1;
            }
        }
    }
    protected void BullBuildFlame(){
        int yM = (int) (y/height_block)-1;
        int xM = (int) (x/width_block)-1;
        if(xM>0&yM>0&xM< xMaxAir&yM<yMaxAir) {
            if (BlockList2D.get(yM).get(xM).passability & height == 1) {
                this.clear_sost = 1;
                BuildingList.get(BlockList2D.get(yM).get(xM).iBuilding).time_flame += 10;

            }
        }
    }
    protected void BullBuildMortar(){
        int yM = (int) (y/height_block)-1;
        int xM = (int) (x/width_block)-1;
        if(xM>0&yM>0&xM< xMaxAir&yM<yMaxAir) {
            if (BlockList2D.get(yM).get(xM).passability & height == 1) {
                this.clear_sost = 1;
                Main.BangList.add(new Bang(this.x, this.y, 4));
                for (int l = 0; l < 20; l++) {
                    Main.BullList.add(new BullFragment(this.x, this.y, damage_fragment, penetration_fragment, type_team));
                }
            }
        }
    }
    protected void BullBuildAcid(){
        int yM = (int) (y/height_block)-1;
        int xM = (int) (x/width_block)-1;
        if(xM>0&yM>0&xM< xMaxAir&yM<yMaxAir) {
            if (BlockList2D.get(yM).get(xM).passability & height == 1) {
                this.clear_sost = 1;
                spawn_acid();

            }
        }
    }
    protected final boolean rect_bull(Area area,int x,int y,int size){

        Ellipse2D circle = new Ellipse2D.Double(x,y,size,size);
        return area.intersects(circle.getBounds2D());
    }

    protected final void corpus_bull_mortar(ArrayList<Transport> obj_2){
        for(i = 0;i<obj_2.size();i++) {

            z = rect_bull((int)obj_2.get(i).x, (int)obj_2.get(i).y,(int)obj_2.get(i).corpus_width,(int)obj_2.get(i).corpus_height,(int)this.x,(int)this.y,
                    this.size,-obj_2.get(i).rotation_corpus);
            if (z && this.type_team != obj_2.get(i).team) {
                obj_2.get(i).time_trigger_bull_bot = obj_2.get(i).time_trigger_bull;
                armor_damage(obj_2,i);
                metod_mortar();
                return;
            }
        }
    }
    protected final void corpus_bull(ArrayList<Transport> obj_2){
        for(i = 0;i<obj_2.size();i++) {
            z = rect_bull((int)obj_2.get(i).x, (int)obj_2.get(i).y,(int)obj_2.get(i).corpus_width,(int)obj_2.get(i).corpus_height,(int)this.x,(int)this.y,
                    this.size,-obj_2.get(i).rotation_corpus);
            if (z && this.type_team != obj_2.get(i).team) {
                obj_2.get(i).time_trigger_bull_bot = obj_2.get(i).time_trigger_bull;
                armor_damage(obj_2,i);
                this.clear_sost = 1;
                return;
            }
        }
    }
    protected final void corpus_bull_temperature(ArrayList<Transport> obj_2){
        for(i = 0;i<obj_2.size();i++) {
            z = rect_bull((int)obj_2.get(i).x, (int)obj_2.get(i).y,(int)(obj_2.get(i).corpus_width),(int)(obj_2.get(i).corpus_height),(int)this.x,(int)this.y,
                    this.size,-obj_2.get(i).rotation_corpus);
            if (z && this.type_team != obj_2.get(i).team) {
                obj_2.get(i).time_trigger_bull_bot = obj_2.get(i).time_trigger_bull;
                armor_damage(obj_2,i);
                metod_temperature(obj_2,i);
                return;
            }
        }
    }
    protected final void armor_damage(ArrayList<Transport>tr, int i){
        tr.get(i).hp -=this.damage-((this.damage/100*(tr.get(i).armor-this.penetration)));
    }
    protected final void metod_temperature(ArrayList<Transport>obj, int i2){
         obj.get(i2).t += this.t_damage;
         this.clear_sost = 1;
    }
    protected final void metod_mortar(){
        for(int i3 = 0;i3<this.amount_fragment;i3++){
            Main.BullList.add(new BullFragment(this.x,this.y,this.damage_fragment,this.penetration_fragment,this.type_team));}
        Main.BangList.add(new Bang(this.x,this.y,4));
        this.clear_sost = 1;

    }
    protected final void soldat_bull(ArrayList<Soldat>soldat){
        for (Soldat value : soldat) {
            if (circle_bull((int) this.x, (int) this.y, this.size, (int) value.x, (int) value.y, value.size)
                    && this.type_team != value.team) {
                metod_mortar();
                value.clear_sost = 1;
                this.clear_sost = 1;
            }
        }
    }
    private boolean circle_bull(int x1,int y1,int size1,int x,int y,int size){
        return sqrt(pow2(x - x1) + pow2(y - y1)) < size + size1;
    }

    protected final boolean rect_bull(int x1,int y1,int width,int height,int x,int y,int size,double rotation){
        Rectangle2D rect1 = new Rectangle2D.Double(x1,y1,width,height);
        AffineTransform transform1 = new AffineTransform();
        transform1.rotate(Math.toRadians(-rotation), rect1.getCenterX(), rect1.getCenterY());
        Area area1 = new Area(rect1);
        area1.transform(transform1);

        Ellipse2D circle = new Ellipse2D.Double(x,y,size,size);
        return area1.intersects(circle.getBounds2D());
    }
}
