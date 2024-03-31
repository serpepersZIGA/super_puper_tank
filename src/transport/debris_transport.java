package transport;

import main.Main;
import metod.render_metod;

public class debris_transport extends Transport {

    public debris_transport(double x, double y, double rotation, double speed, double inert_rotation,
                            double inert_speed,String corpus,double width,double height){
        this.x = x;
        this.y = y;
        this.rotation_corpus = rotation;
        this.speed = speed;
        this.speed_inert = inert_speed;
        this.rotation_inert = inert_rotation;
        this.corpus_image = corpus;
        this.spisok = Main.debris;
        this.max_hp = 10000;
        this.armor = 5;
        data();
        this.height = 1;
        this.team = -1;
        this.acceleration = 0.1;
    }
    public void all_action(int i){
        super.all_action(i);
        corpus_corpus(Main.player_obj,(byte)1);
        corpus_corpus(Main.enemy_obj,(byte)1);
        corpus_corpus_def_xy(Main.debris,(byte)1);
        build_corpus(Main.build);
        move_debris();
        debris_delete(i, Main.debris);


    }
    public void update(){
        center_render();
        render_metod.transorm_img(this.x_rend,this.y_rend,this.corpus_width,this.corpus_height,this.rotation_corpus,this.corpus_image);
    }

}
