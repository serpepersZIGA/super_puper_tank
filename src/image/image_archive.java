package image;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class image_archive {
    public BufferedImage img;
    public image_archive(){

    }
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
