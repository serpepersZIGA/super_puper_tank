package com.mygdx.game.method;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.main.Main;


public class RenderMethod {
    public static void transorm_img(int x, int y, double width, double height, double rotation, Sprite sprite){
        //image = new Texture("badlogic.jpg");
        sprite.setPosition(x,y);
        sprite.setSize((float) width,(float)height);
        sprite.setRotation((float) rotation);
        sprite.draw(Main.Batch);
    }
    public static void transorm_img(int x,int y,double width,double height,Sprite sprite){
        sprite.setPosition(x,y);
        sprite.setSize((float) width,(float)height);
        sprite.draw(Main.Batch);


    }
    public static void transorm_img(int x,int y,double width,double height,double rotation,Sprite sprite,double x_const,double y_const){
        //image = new Texture("badlogic.jpg");
        sprite.setPosition((float)x, (float) y);
        sprite.setOrigin((float)x_const,(float)y_const);
        sprite.setSize((float) width,(float)height);
        sprite.setRotation((float) rotation);
        sprite.draw(Main.Batch);
    }

    //public static void
}
