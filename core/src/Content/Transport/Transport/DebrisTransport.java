package Content.Transport.Transport;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.method.RenderMethod;
import com.mygdx.game.main.Main;
import com.mygdx.game.transport.Transport;
import com.mygdx.game.transport.UnitType;

import static com.mygdx.game.main.Main.ContentImage;

public class DebrisTransport extends Transport {

    public DebrisTransport(float x, float y, float rotation, float speed, float inert_rotation,
                           float inert_speed, Sprite corpus, float width, float height, UnitType type){

        this.type_unit = type;
        this.x = x;
        this.y = y;
        this.corpus_width = width;
        this.corpus_height = height;
        this.rotation_corpus = rotation;
        this.speed = speed;
        this.speed_inert = inert_speed;
        this.rotation_inert = inert_rotation;
        this.corpus_img = corpus;
        this.spisok = Main.DebrisList;
        this.max_hp = 10000;
        this.armor = 5;
        this.teg_unit = "debris";
        if(this.corpus_img == null){
            this.corpus_img = ContentImage.corpus_enemy;
            this.corpus_width = 50;
            this.corpus_height = 129;
        }
        data();
        this.height = 1;
        this.team = -1;
        this.acceleration = 0.1f;
    }
    public void all_action(int i){
        super.all_action(i);
        super.corpus_corpus(Main.PlayerList);
        super.corpus_corpus(Main.EnemyList);
        super.corpus_corpus_def_xy(Main.DebrisList,(byte)1);
        build_corpus(Main.BuildingList);
        move_debris();
        center_render();
        RenderMethod.transorm_img(this.x_rend,this.y_rend,this.corpus_width_zoom,this.corpus_height_zoom,this.rotation_corpus,this.corpus_img,const_x_corpus,const_y_corpus);
        debris_delete(i, Main.DebrisList);
    }
    public void all_action_client(int i){;
        center_render();
        RenderMethod.transorm_img(this.x_rend,this.y_rend,this.corpus_width_zoom,this.corpus_height_zoom,this.rotation_corpus,this.corpus_img,const_x_corpus,const_y_corpus);
    }

}
