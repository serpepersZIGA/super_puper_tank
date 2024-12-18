package Content.Build;
import com.mygdx.game.build.BuildType;
import com.mygdx.game.build.Building;
import com.mygdx.game.main.Main;

import com.mygdx.game.method.RenderMethod;

public class Home1 extends Building {
    public Home1(int x, int y, float rotation){
        name = BuildType.Home1;
        this.x = x;
        this.y = y;
        this.rotation = rotation;
        this.build_image = Main.ContentImage.build_1;
        this.width = 100;
        this.height = 50;
        this.width_2 = this.width/2;
        this.height_2 = this.height/2;
        this.time_flame = 0;
        super.Data();

    }
    public void all_action(int i) {
        super.all_action(i);
        super.flame_build(Main.FlameStaticList);

    }
    public void update(){
        center_render();
        RenderMethod.transorm_img(this.x_rend,this.y_rend,this.width_render,this.height_render,this.rotation,this.build_image);
    }
}
