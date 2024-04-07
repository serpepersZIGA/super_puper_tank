package bull;

import build.Build;
import main.Game;
import main.Main;
import main.display;
import metod.move;
import metod.rand;
import particle.acid;
import particle.bang;
import particle.flame_spawn;
import soldat.Soldat;
import transport.Transport;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.util.ArrayList;

import static java.lang.StrictMath.pow;
import static java.lang.StrictMath.sqrt;
import static main.display.correct_int;

public abstract class Bull implements Serializable {
    public double x,y,speed,damage,penetration,rotation,r,g,b,speed_x,speed_y,damage_fragment,penetration_fragment,t_damage;
    public int time,size,amount_fragment,x_rend,y_rend,size_render;
    public byte type_team,height,clear_sost;
    public Color color;
    public void bull_move(){
        //metod.interface_panel.panel_information(this.y,600,600,24,Color.YELLOW,main.display.paint_2);
        this.x += move.move_sin(this.speed,this.rotation);
        this.y += move.move_cos(this.speed,this.rotation);
    }
    public void bull_move_xy(){
        this.x += this.speed_x;
        this.y += this.speed_y;
    }
    public void fragments_create(int i){
        this.time -= 1;
        if(this.time<=0){
            for(int i3 = 0;i3<this.amount_fragment;i3++){
            Main.bull_obj.add(new bull_fragment(this.x,this.y,this.damage_fragment,this.penetration_fragment,this.type_team));}
            Main.bang_obj.add(new bang(this.x/correct_int,this.y/correct_int,this.color,4));
            this.clear_sost = 1;}

    }
    public void color_fire(){
        this.r-= 1.5;
        this.g -=1.5;
        this.b -= 1;
        if(this.b< 0){this.b = 0;}
        if(this.g< 0){this.g = 0;}
        if(this.r< 0){this.r = 0;}
        this.color = new Color((int)r,(int)g,(int)b);
    }
    public void spawn_flame(){
        if(1 == rand.rand(1,16)){
            //main.Main.particle.flame_obj.add(new particle.flame(this.x/1.23,this.y/1.23));
            Main.flame_spawn.add(new flame_spawn(this.x,this.y));

        }
    }
    public void center_render(){
        double[]xy = Main.rc.get(0).render_obj(this.x,this.y);
        this.x_rend = (int)(xy[0]* Game.zoom);
        this.y_rend = (int)(xy[1]* Game.zoom);
        this.size_render = Main.rc.get(0).render_size(this.size);
    }
    public void spawn_acid(){
        if(1 == rand.rand(1,4)){
            Main.liquid_obj.add(new acid(this.x/correct_int,this.y/correct_int));
        }
    }
    public void clear(int i){
        if(this.clear_sost == 1){
            Main.bull_obj.remove(i);
        }
    }
    public void all_action(int i){

    }
    public void update(Graphics2D g){

    }
    public void bull_clear_display(){
        if(this.x<0){this.clear_sost = 1;return;}
        else if(this.x> display.width){this.clear_sost = 1;return;}
        if(this.y<0){this.clear_sost = 1;return;}
        else if(this.y>display.height){this.clear_sost = 1;return;}
    }
    public void bull_clear_time(){
        this.time -= 1;

        if(this.time<=0){this.clear_sost = 1;}
    }
    public void bull_clear_time_flame(int i){
        this.time -= 1;
        if(this.time<=0){
            spawn_flame();
            this.clear_sost = 1;}
    }
    public void bull_clear_time_acid(int i){
        this.time -= 1;
        if(this.time<=0){
            spawn_acid();
            this.clear_sost = 1;}
    }
    public void bull_build(ArrayList<Build>build, ArrayList<Bull>bull, int i2){
        for(int i=0;i<build.size();i++) {
            if(height == 1) {
            boolean z = rect_bull(build.get(i).x,build.get(i).y,build.get(i).width,build.get(i).height,
                    (int) this.x,(int)this.y,this.size,build.get(i).rotation);
            if(z) {
                this.clear_sost = 1;
            }
            }
        }
    }
    public void bull_build_flame(ArrayList<Build>build, ArrayList<Bull>bull, int i2){
        for(int i=0;i<build.size();i++) {
            if(height == 1) {
                boolean z = rect_bull(build.get(i).x, build.get(i).y, build.get(i).width, build.get(i).height,
                        (int) this.x, (int) this.y, this.size, build.get(i).rotation);
                if (z) {
                    build.get(i).time_flame += 10;
                    this.clear_sost = 1;
                }
            }
        }
    }
    public void bull_build_fragment(ArrayList<Build>build, ArrayList<Bull>bull){
        for(int i=0;i<build.size();i++) {
            if(height == 1) {
                boolean z = rect_bull(build.get(i).x, build.get(i).y, build.get(i).width, build.get(i).height,
                        (int) this.x, (int) this.y, this.size, build.get(i).rotation);
                if (z) {
                    for (int i3 = 0; i3 < this.amount_fragment; i3++) {
                        Main.bull_obj.add(new bull_fragment(this.x, this.y, this.damage_fragment, this.penetration_fragment, this.type_team));
                    }
                    Main.bang_obj.add(new bang(this.x / correct_int, this.y / correct_int, this.color, 4));
                    this.clear_sost = 1;
                }
            }
        }
    }
    public void bull_build_acid(ArrayList<Build>build,ArrayList<Bull>bull){
        for(int i=0;i<build.size();i++) {
            if(height == 1) {
                boolean z = rect_bull(build.get(i).x, build.get(i).y, build.get(i).width, build.get(i).height,
                        (int) this.x, (int) this.y, this.size, build.get(i).rotation);
                if (z) {

                    spawn_acid();
                    this.clear_sost = 1;
                }
            }
        }
    }

    public void corpus_bull_mortar(ArrayList<Transport> obj_2){
        boolean z;
        for(int i2 = 0;i2<obj_2.size();i2++) {

            z = rect_bull((int)obj_2.get(i2).x, (int)obj_2.get(i2).y,(int)obj_2.get(i2).corpus_width,(int)obj_2.get(i2).corpus_height,(int)this.x,(int)this.y,
                    this.size,-obj_2.get(i2).rotation_corpus);
            if (z && this.type_team != obj_2.get(i2).team) {
                obj_2.get(i2).time_trigger_bull_bot = obj_2.get(i2).time_trigger_bull;
                armor_damage(obj_2,i2);
                metod_mortar();
                return;
            }
        }
    }
    public void corpus_bull(ArrayList<Transport> obj_2){
        boolean z;
        for(int i2 = 0;i2<obj_2.size();i2++) {

            z = rect_bull((int)obj_2.get(i2).x, (int)obj_2.get(i2).y,(int)obj_2.get(i2).corpus_width,(int)obj_2.get(i2).corpus_height,(int)this.x,(int)this.y,
                    this.size,-obj_2.get(i2).rotation_corpus);
            if (z && this.type_team != obj_2.get(i2).team) {
                obj_2.get(i2).time_trigger_bull_bot = obj_2.get(i2).time_trigger_bull;
                armor_damage(obj_2,i2);
                this.clear_sost = 1;
                return;
            }
        }
    }
    public void corpus_bull_temperature(ArrayList<Transport> obj_2){
        boolean z;
        for(int i2 = 0;i2<obj_2.size();i2++) {

            z = rect_bull((int)obj_2.get(i2).x, (int)obj_2.get(i2).y,(int)(obj_2.get(i2).corpus_width/correct_int),(int)(obj_2.get(i2).corpus_height/correct_int),(int)this.x,(int)this.y,
                    this.size,-obj_2.get(i2).rotation_corpus);
            if (z && this.type_team != obj_2.get(i2).team) {
                obj_2.get(i2).time_trigger_bull_bot = obj_2.get(i2).time_trigger_bull;
                armor_damage(obj_2,i2);
                metod_temperature(obj_2,i2);
                return;
            }
        }
    }
    public void armor_damage(ArrayList<Transport>tr, int i){
        tr.get(i).hp -=this.damage-((this.damage/100*(tr.get(i).armor-this.penetration)));
    }
    public void metod_temperature(ArrayList<Transport>obj, int i2){
         obj.get(i2).t += this.t_damage;
         this.clear_sost = 1;
    }
    public void metod_mortar(){
        for(int i3 = 0;i3<this.amount_fragment;i3++){
            Main.bull_obj.add(new bull_fragment(this.x,this.y,this.damage_fragment,this.penetration_fragment,this.type_team));}
        Main.bang_obj.add(new bang(this.x/correct_int,this.y/correct_int,this.color,4));
        this.clear_sost = 1;

    }
    public void soldat_bull(ArrayList<Soldat>soldat){
        for(int i = 0;i<soldat.size();i++) {
            boolean z = circle_bull((int)this.x,(int)this.y,this.size,(int)soldat.get(i).x,(int)soldat.get(i).y,soldat.get(i).size);
            if(z && this.type_team !=soldat.get(i).team){
                soldat.get(i).clear_sost = 1;
                this.clear_sost = 1;
            }
        }
    }
    public boolean circle_bull(int x1,int y1,int size1,int x,int y,int size){
        if(sqrt(pow(x-x1,2)+pow(y-y1,2))<size+size1){
            return true;
        }
        return false;
    }

    public boolean rect_bull(int x1,int y1,int width,int height,int x,int y,int size,double rotation){
        Rectangle2D rect1 = new Rectangle2D.Double(x1,y1,width,height);
        AffineTransform transform1 = new AffineTransform();
        transform1.rotate(Math.toRadians(-rotation), rect1.getCenterX(), rect1.getCenterY());
        Area area1 = new Area(rect1);
        area1.transform(transform1);

        Ellipse2D circle = new Ellipse2D.Double(x,y,size,size);

        if (area1.intersects(circle.getBounds2D())) {
            return true;
        }
        return false;
    }
    //public void
    //public

}
