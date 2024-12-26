package Content.Build;

import com.mygdx.game.build.UpdateBuilding;
import com.mygdx.game.method.RenderMethod;

import static com.mygdx.game.main.Main.ContentImage;

public class UpdateBigBuildWood1 extends UpdateBuilding {
    public void render(int x,int y,int width,int height){
        RenderMethod.transorm_img(x,y,width,height,ContentImage.big_build_wood_1);
    }
}
