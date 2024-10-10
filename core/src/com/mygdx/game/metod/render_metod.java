package com.mygdx.game.metod;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.main.Main;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

import static com.mygdx.game.main.Main.batch;
import static com.mygdx.game.main.Main.sprites;
import static java.lang.Math.cos;
import static java.lang.Math.sin;


public class render_metod {
    public static void transorm_img(int x, int y, double width, double height, double rotation, Texture image){
        //image = new Texture("badlogic.jpg");
        Sprite sprite = new Sprite(image);
        sprite.setPosition(x,y);
        sprite.setSize((float) width,(float)height);
        sprite.setRotation((float) rotation);
        sprite.draw(Main.batch);
    }
    public static void transorm_img(int x,int y,double width,double height,Texture image){
        //image = new Texture("badlogic.jpg");
        Sprite sprite = new Sprite(image);
        sprite.setPosition(x,y);
        sprite.setSize((float) width,(float)height);
        sprite.draw(Main.batch);


    }
    public static void transorm_img(int x,int y,double width,double height,double rotation,Texture image,double x_const,double y_const){
        //image = new Texture("badlogic.jpg");

        Sprite sprite = new Sprite(image);
        sprite.setPosition((float)x, (float) y);
        sprite.setOrigin((float)x_const,(float)y_const);
        sprite.setSize((float) width,(float)height);
        sprite.setRotation((float) rotation);
        sprite.draw(Main.batch);
    }

    //public static void
}
