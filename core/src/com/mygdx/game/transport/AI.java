package com.mygdx.game.transport;

import com.mygdx.game.block.Block;
import com.mygdx.game.main.Main;
import com.mygdx.game.metod.rand;
import com.mygdx.game.particle.acid;
import com.mygdx.game.particle.flame_spawn;

import java.util.*;

public class AI {
    private static final int[][] DIRECTIONS = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
    // Координаты ИИ
    private int x, y;
    private int x_old,y_old,speed_x,speed_y,x_targ,y_targ;

    // Метод для обновления позиции ИИ
    public void updatePosition(int targetX, int targetY, ArrayList<Transport>tr) {
        // Логика обхода препятствий
        for (int i = 0;i<tr.size();i++) {

        }
        // Обновите координаты ИИ
        this.x = targetX;
        this.y = targetY;
    }
    public void bypass_AI(Transport ai){
        if(Main.matrix.get(y).get(x).passability == 2){
            x = x_old;
            y = y_old;
            x+= 1;
            if(Main.matrix.get(y).get(x).passability != 2) {

            }
            x = x_old;
            x-= 1;
            if(Main.matrix.get(y).get(x).passability != 2) {

            }

        }
    }
    public void path_AI(Transport ai,Transport target,double x_ai,double y_ai){
        int c = 1;
        int[] target_xy = block_detected_2(target);
        if(ai.path.size() != 0) {
            for (int i = 0; i < Main.build.size(); i++) {
                if(null != ai.findIntersection(Main.matrix.get(ai.path.get(ai.path.size()-1)[1]).get(ai.path.get(ai.path.size()-1)[0]).x_center,
                        Main.matrix.get(ai.path.get(ai.path.size()-1)[1]).get(ai.path.get(ai.path.size()-1)[0]).y_center, target_xy[0], target_xy[1],
                        Main.build.get(i).x,Main.build.get(i).y,Main.build.get(i).x+Main.build.get(i).width,
                        Main.build.get(i).y+Main.build.get(i).height)){
                    ai.path.clear();
                    c = 2;
                    break;
                }
            }

        }
        else{c = 2;}
        if(c == 2) {
            int[] ai_xy = block_detected_3(x_ai, y_ai);
            x = ai_xy[0];
            y = ai_xy[1];
            speed_x = 0;
            speed_y = 0;
            x_old = x;
            y_old = y;
            while (y != target_xy[1] && x != target_xy[0]) {
                if (Main.matrix.get(y + 1).get(x).passability == 2 && y < target_xy[1]) {
                    if (x < target_xy[0]) {
                        while (Main.matrix.get(y + 1).get(x).passability == 2) {
                            x += 1;
                            if (Main.matrix.get(y + 1).get(x).passability != 2) {
                                y += 1;
                                ai.path.add(new int[]{x, y});
                                break;
                            }
                            ai.path.add(new int[]{x, y});
                        }
                    } else {
                        while (Main.matrix.get(y + 1).get(x).passability == 2) {
                            x -= 1;
                            if (Main.matrix.get(y + 1).get(x).passability != 2) {
                                y += 1;
                                ai.path.add(new int[]{x, y});
                                break;
                            }
                            ai.path.add(new int[]{x, y});
                        }
                    }
                } else if (Main.matrix.get(y - 1).get(x).passability == 2 && y > target_xy[1]) {
                    if (x < target_xy[0]) {
                        while (Main.matrix.get(y - 1).get(x).passability == 2) {
                            x += 1;
                            if (Main.matrix.get(y - 1).get(x).passability != 2) {
                                y -= 1;
                                ai.path.add(new int[]{x, y});
                                break;
                            }
                            ai.path.add(new int[]{x, y});
                        }
                    } else {
                        while (Main.matrix.get(y - 1).get(x).passability == 2) {
                            x -= 1;
                            if (Main.matrix.get(y - 1).get(x).passability != 2) {
                                y -= 1;
                                ai.path.add(new int[]{x, y});
                                break;
                            }
                            ai.path.add(new int[]{x, y});
                        }
                    }
                } else if (Main.matrix.get(y).get(x + 1).passability == 2 && x < target_xy[0]) {
                    if (y < target_xy[1]) {
                        while (Main.matrix.get(y).get(x + 1).passability == 2) {
                            y += 1;
                            if (Main.matrix.get(y).get(x + 1).passability != 2) {
                                x += 1;
                                ai.path.add(new int[]{x, y});
                                break;
                            }
                            ai.path.add(new int[]{x, y});
                        }
                    } else {
                        while (Main.matrix.get(y).get(x + 1).passability == 2) {
                            y -= 1;
                            if (Main.matrix.get(y).get(x + 1).passability != 2) {
                                x += 1;
                                ai.path.add(new int[]{x, y});
                                break;
                            }
                            ai.path.add(new int[]{x, y});
                        }
                    }
                } else if (Main.matrix.get(y).get(x - 1).passability == 2 && x > target_xy[0]) {
                    if (y < target_xy[1]) {
                        while (Main.matrix.get(y).get(x - 1).passability == 2) {
                            y += 1;
                            if (Main.matrix.get(y).get(x - 1).passability != 2) {
                                x -= 1;
                                ai.path.add(new int[]{x, y});
                                break;
                            }
                            ai.path.add(new int[]{x, y});
                        }
                    } else {
                        while (Main.matrix.get(y).get(x - 1).passability == 2) {
                            y -= 1;
                            if (Main.matrix.get(y).get(x - 1).passability != 2) {
                                x -= 1;
                                ai.path.add(new int[]{x, y});
                                break;
                            }
                            ai.path.add(new int[]{x, y});
                        }
                    }
                } else if (Main.matrix.get(y).get(x + 1).passability != 2 && x < target_xy[0]) {
                    x += 1;
                    x_old = x;
                    ai.path.add(new int[]{x, y});

                } else if (Main.matrix.get(y).get(x - 1).passability != 2 && x > target_xy[0]) {
                    x -= 1;
                    x_old = x;
                    ai.path.add(new int[]{x, y});
                } else if (Main.matrix.get(y + 1).get(x).passability != 2 && y < target_xy[1]) {
                    y += 1;
                    y_old = y;
                    ai.path.add(new int[]{x, y});
                } else if (Main.matrix.get(y - 1).get(x).passability != 2 && y > target_xy[1]) {
                    y -= 1;
                    y_old = y;
                    ai.path.add(new int[]{x, y});
                }
            }
        }
    }
    public int[] block_detected(Transport tr){
        for(int i =0;i<Main.matrix.size();i++) {
            for (int i2 =0;i2<Main.matrix.get(i).size();i2++) {
                if(Main.matrix.get(i).get(i2).rect_collision((int) tr.tower_x, (int) tr.tower_y, (int) tr.corpus_width, (int) tr.corpus_height, tr.rotation_corpus, Main.matrix.get(i).get(i2).x, Main.matrix.get(i).get(i2).y,Main.matrix.get(i).get(i2).width,Main.matrix.get(i).get(i2).height, 0)){
                    if(Main.matrix.get(i).get(i2).passability != 2) {
                        return new int[]{i2, i};
                    }
                    else{
                        if(Main.matrix.get(i+1).get(i2).passability != 2){
                            return new int[]{i2, i+1};
                        }
                        else if(Main.matrix.get(i-1).get(i2).passability != 2){
                            return new int[]{i2, i-1};
                        }
                        else if(Main.matrix.get(i).get(i2+1).passability != 2){
                            return new int[]{i2+1, i};
                        }
                        else if(Main.matrix.get(i).get(i2-1).passability != 2){
                            return new int[]{i2-1, i};
                        }
                    }
                }

            }
        }
        return null;
    }
    public int[] block_detected_2(Transport tr){
        int i = (int)(tr.tower_y/Main.height_block);
        int i2 = (int)(tr.tower_x/Main.width_block);
        if(Main.matrix.get(i).get(i2).passability != 2) {
            return new int[]{i2, i};
        }
        else{
            if(Main.matrix.get(i+1).get(i2).passability != 2){
                return new int[]{i2, i+1};
            }
            else if(Main.matrix.get(i-1).get(i2).passability != 2){
                return new int[]{i2, i-1};
            }
            else if(Main.matrix.get(i).get(i2+1).passability != 2){
                return new int[]{i2+1, i};
            }
            else if(Main.matrix.get(i).get(i2-1).passability != 2){
                return new int[]{i2-1, i};
            }
        }
        return new int[]{i2, i};
    }
    public int[] block_detected_3(double x,double y){
        int i = (int)(y/Main.height_block)-1;
        int i2 = (int)(x/Main.width_block)-1;
        if(Main.matrix.get(i).get(i2).passability != 2) {
            return new int[]{i2, i};
        }
        else{
            if(Main.matrix.get(i+1).get(i2).passability != 2){
                return new int[]{i2, i+1};
            }
            else if(Main.matrix.get(i-1).get(i2).passability != 2){
                return new int[]{i2, i-1};
            }
            else if(Main.matrix.get(i).get(i2+1).passability != 2){
                return new int[]{i2+1, i};
            }
            else if(Main.matrix.get(i).get(i2-1).passability != 2){
                return new int[]{i2-1, i};
            }
        }
        return new int[]{i2, i};
    }
}
