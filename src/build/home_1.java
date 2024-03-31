package build;

import main.Main;
import metod.render_metod;

public class home_1 extends Build {
    private String build_image;
    public home_1(int x,int y,double rotation){
        this.x = x;
        this.y = y;
        this.rotation = rotation;
        this.build_image = "src/image/build/home_1.png";
        this.width = 100;
        this.height = 50;
        this.width_2 = this.width/2;
        this.height_2 = this.height/2;
        this.time_flame = 0;

    }
    public void all_action(int i) {
        super.all_action(i);
        super.flame_build(Main.flame_static_obj);

    }
    public void update(){
        center_render();
        render_metod.transorm_img(this.x_rend,this.y_rend,this.width,this.height,this.rotation,this.build_image);
    }
}
