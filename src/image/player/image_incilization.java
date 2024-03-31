package image.player;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class image_incilization {
    public static BufferedImage image_incilization(String img){
        try {
            return ImageIO.read(new File(img));
        } catch (Exception e) {
            e.printStackTrace();
        }
        BufferedImage image = null;
        return image;
    }
}
