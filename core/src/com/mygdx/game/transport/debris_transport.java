package com.mygdx.game.transport;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.metod.render_metod;
import com.mygdx.game.main.Main;
public class debris_transport extends Transport {

    public debris_transport(double x, double y, double rotation, double speed, double inert_rotation,
                            double inert_speed, Texture corpus, double width, double height){
        this.x = x;
        this.y = y;
        this.corpus_width = width;
        this.corpus_height = height;
        this.rotation_corpus = rotation;
        this.speed = speed;
        this.speed_inert = inert_speed;
        this.rotation_inert = inert_rotation;
        this.corpus_img = corpus;
        this.spisok = Main.debris;
        this.max_hp = 10000;
        this.armor = 5;
        this.teg_unit = "debris";
        data();
        this.height = 1;
        this.team = -1;
        this.acceleration = 0.1;
    }
    public void all_action(int i){
        super.all_action(i);
        super.corpus_corpus(Main.player_obj,(byte)1);
        super.corpus_corpus(Main.enemy_obj,(byte)1);
        super.corpus_corpus_def_xy(Main.debris,(byte)1);
        build_corpus(Main.build);
        move_debris();
        debris_delete(i, Main.debris);
        center_render();
        render_metod.transorm_img(this.x_rend,this.y_rend,this.width_corpus_zoom,this.height_corpus_zoom,this.rotation_corpus,this.corpus_img,const_x_corpus,const_y_corpus);


    }

}
