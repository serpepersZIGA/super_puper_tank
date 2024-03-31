package metod;

import main.Game;
import main.Main;

import javax.imageio.ImageIO;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;


public class render_metod {
    public static BufferedImage image_incilization(String img){
        try {
            return ImageIO.read(new File(img));
        } catch (Exception e) {
            e.printStackTrace();
        }
        BufferedImage image = null;
        return image;
    }

    public static void transorm_img(int x,int y,double width,double height,double rotation,String image){
        AffineTransform at = new AffineTransform();
        at.translate(x* Game.zoom,y* Game.zoom);

        //image = (BufferedImage) image.getScaledInstance((int)(width*game.zoom),(int)(height*game.zoom), Image.SCALE_SMOOTH);
        //image = new BufferedImage((int)(width*game.zoom),(int)(height*game.zoom), BufferedImage.TYPE_INT_ARGB);
        try {


            BufferedImage img = image_incilization(image);
            at.rotate(Math.toRadians(rotation), img.getWidth() * Game.zoom / 2.0, img.getHeight() * Game.zoom / 2.0);
            Main.size_render.add(new double[]{width, height});
            Main.transforms.add(at);
            Main.images.add(img);
        }catch (NullPointerException e){e.printStackTrace();}
        // отображаем изображение так, чтобы центр совпадал с заданной точкой
        //g.dispose();
    }
    public static void transorm_img(int x,int y,double width,double height,String image){
        AffineTransform at = new AffineTransform();
        BufferedImage img = image_incilization(image);
        at.translate(x * Game.zoom, y * Game.zoom);
        Main.size_render.add(new double[]{width * Game.zoom, height * Game.zoom});
        Main.transforms.add(at);
        Main.images.add(img);

    }
    public static void transorm_img(int x,int y,double width,double height,double rotation,String image,double x_const,double y_const){
        AffineTransform at = new AffineTransform();
        at.translate(x * Game.zoom, y * Game.zoom);
        BufferedImage img = image_incilization(image);
        at.rotate(Math.toRadians(rotation), width * Game.zoom / 2.0 - x_const, height * Game.zoom / 2.0 - y_const);
        Main.size_render.add(new double[]{width, height});
        Main.transforms.add(at);
        Main.images.add(img);
    }
}
