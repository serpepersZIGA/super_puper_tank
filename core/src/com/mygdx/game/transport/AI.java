package com.mygdx.game.transport;
import com.mygdx.game.block.UpdateRegister;
import com.mygdx.game.main.Main;
import com.mygdx.game.soldat.Soldat;

import java.util.ArrayList;

import static com.mygdx.game.method.pow2.pow2;
import static java.lang.Math.abs;
import static java.lang.Math.sqrt;

public class AI {
    // Координаты ИИ
    private static int x, y,xTotal,yTotal;
    private static byte c;
    private static int TargetLine,TargetLineMin,TargetLineTotal;
    private static ArrayList<int[]> OpenBlockList = new ArrayList<>();
    private static ArrayList<int[]> CloseBlockList = new ArrayList<>();
    private static int[]MinPathTotal;
    private static boolean conf;

    // Метод для обновления позиции ИИ
    public void pathAIAStar(Transport ai,Transport target,float x_ai,float y_ai){

        int[] target_xy = block_detected_2(target);
            int[] ai_xy = block_detected_3(x_ai, y_ai);
            x = ai_xy[0];
            y = ai_xy[1];
            TargetLineMin = -1;
            ai.path.add(new int[]{x, y});
            conf = false;
            while (y != target_xy[1] || x != target_xy[0]) {
                xTotal = x+1;
                if (!Main.BlockList2D.get(y).get(xTotal).passability) {
                    TargetLine = (int) sqrt(pow2(target_xy[0]-x+1)+pow2(target_xy[1]-y));
                    for (int i = 0;i<CloseBlockList.size();i++) {
                        if(xTotal== CloseBlockList.get(i)[0]&y== CloseBlockList.get(i)[1]){
                            conf = true;
                            break;
                        }
                    }
                    if(!conf) {
                        OpenBlockList.add(new int[]{xTotal, y, TargetLine});
                    }
                    conf = false;
                }
                xTotal = x-1;
                if (!Main.BlockList2D.get(y).get(xTotal).passability) {
                    TargetLine = (int) sqrt(pow2(target_xy[0]-x-1)+pow2(target_xy[1]-y));
                    for (int i = 0;i<CloseBlockList.size();i++) {
                        if(xTotal== CloseBlockList.get(i)[0]&y== CloseBlockList.get(i)[1]){
                            conf = true;
                            break;
                        }
                    }
                    if(!conf) {
                        OpenBlockList.add(new int[]{xTotal, y, TargetLine});
                    }
                    conf = false;
                }
                yTotal = y+1;
                if (!Main.BlockList2D.get(yTotal).get(x).passability) {
                    TargetLine = (int) sqrt(pow2(target_xy[0]-x)+pow2(target_xy[1]-y+1));
                    for (int i = 0;i<CloseBlockList.size();i++) {
                        if(x== CloseBlockList.get(i)[0]&yTotal== CloseBlockList.get(i)[1]){
                            conf = true;
                            break;
                        }
                    }
                    if(!conf) {
                        OpenBlockList.add(new int[]{x, yTotal, TargetLine});
                    }
                    conf = false;
                }
                yTotal = y-1;
                if (!Main.BlockList2D.get(yTotal).get(x).passability) {
                    TargetLine = (int) sqrt(pow2(target_xy[0]-x)+pow2(target_xy[1]-y-1));
                    for (int i = 0;i<CloseBlockList.size();i++) {
                        if(x== CloseBlockList.get(i)[0]&yTotal== CloseBlockList.get(i)[1]){
                            conf = true;
                            break;
                        }
                    }
                    if(!conf) {
                        OpenBlockList.add(new int[]{x, yTotal, TargetLine});
                    }
                    conf = false;
                }
                for(int i = 0;i<OpenBlockList.size();i++){
                    if(TargetLineMin == -1||OpenBlockList.get(i)[2]>TargetLineMin){
                        TargetLineMin = OpenBlockList.get(i)[2];
                        x = OpenBlockList.get(i)[0];
                        y = OpenBlockList.get(i)[1];
                        MinPathTotal = new int[]{OpenBlockList.get(i)[0],OpenBlockList.get(i)[1]};
                    }
                }
                //TargetLineTotal += 10;
                TargetLineMin = -1;
                CloseBlockList.add(new int[]{MinPathTotal[0],MinPathTotal[1]});
                ai.path.add(new int[]{MinPathTotal[0],MinPathTotal[1]});
                OpenBlockList.clear();

            }
        CloseBlockList.clear();
    }
    public void pathAISoldat(Soldat ai, Transport target, float x_ai, float y_ai){

        int[] target_xy = block_detected_2Soldat(target);
        int[] ai_xy = block_detected_3(x_ai, y_ai);
        x = ai_xy[0];
        y = ai_xy[1];
        TargetLineMin = -1;
        ai.path.add(new int[]{x, y});
        conf = false;
        while (y != target_xy[1] || x != target_xy[0]) {
            xTotal = x+1;
            if (!Main.BlockList2D.get(y).get(xTotal).passability) {
                TargetLine = (int) sqrt(pow2(target_xy[0]-x+1)+pow2(target_xy[1]-y));
                for (int i = 0;i<CloseBlockList.size();i++) {
                    if(xTotal== CloseBlockList.get(i)[0]&y== CloseBlockList.get(i)[1]){
                        conf = true;
                        break;
                    }
                }
                if(!conf) {
                    OpenBlockList.add(new int[]{xTotal, y, TargetLine});
                }
                conf = false;
            }
            xTotal = x-1;
            if (!Main.BlockList2D.get(y).get(xTotal).passability) {
                TargetLine = (int) sqrt(pow2(target_xy[0]-x-1)+pow2(target_xy[1]-y));
                for (int i = 0;i<CloseBlockList.size();i++) {
                    if(xTotal== CloseBlockList.get(i)[0]&y== CloseBlockList.get(i)[1]){
                        conf = true;
                        break;
                    }
                }
                if(!conf) {
                    OpenBlockList.add(new int[]{xTotal, y, TargetLine});
                }
                conf = false;
            }
            yTotal = y+1;
            if (!Main.BlockList2D.get(yTotal).get(x).passability) {
                TargetLine = (int) sqrt(pow2(target_xy[0]-x)+pow2(target_xy[1]-y+1));
                for (int i = 0;i<CloseBlockList.size();i++) {
                    if(x== CloseBlockList.get(i)[0]&yTotal== CloseBlockList.get(i)[1]){
                        conf = true;
                        break;
                    }
                }
                if(!conf) {
                    OpenBlockList.add(new int[]{x, yTotal, TargetLine});
                }
                conf = false;
            }
            yTotal = y-1;
            if (!Main.BlockList2D.get(yTotal).get(x).passability) {
                TargetLine = (int) sqrt(pow2(target_xy[0]-x)+pow2(target_xy[1]-y-1));
                for (int i = 0;i<CloseBlockList.size();i++) {
                    if(x== CloseBlockList.get(i)[0]&yTotal== CloseBlockList.get(i)[1]){
                        conf = true;
                        break;
                    }
                }
                if(!conf) {
                    OpenBlockList.add(new int[]{x, yTotal, TargetLine});
                }
                conf = false;
            }
            for(int i = 0;i<OpenBlockList.size();i++){
                if(TargetLineMin == -1||OpenBlockList.get(i)[2]>TargetLineMin){
                    TargetLineMin = OpenBlockList.get(i)[2];
                    x = OpenBlockList.get(i)[0];
                    y = OpenBlockList.get(i)[1];
                    MinPathTotal = new int[]{OpenBlockList.get(i)[0],OpenBlockList.get(i)[1]};
                }
            }
            //TargetLineTotal += 10;
            TargetLineMin = -1;
            CloseBlockList.add(new int[]{MinPathTotal[0],MinPathTotal[1]});
            ai.path.add(new int[]{MinPathTotal[0],MinPathTotal[1]});
            OpenBlockList.clear();

        }
        CloseBlockList.clear();
    }
    public int[] block_detected_2(Transport tr){
        int i = (int)(tr.tower_y/Main.height_block)-1;
        int i2 = (int)(tr.tower_x/Main.width_block)-1;
        //Main.BlockList2D.get(i).get(i2).render_block = UpdateRegister.Update3;
        if(!Main.BlockList2D.get(i).get(i2).passability) {
            return new int[]{i2, i};
        }
        else{
            if(!Main.BlockList2D.get(i+1).get(i2).passability){
                return new int[]{i2, i-1};
            }
            else if(!Main.BlockList2D.get(i-1).get(i2).passability){
                return new int[]{i2, i+1};
            }
            else if(!Main.BlockList2D.get(i).get(i2+1).passability){
                return new int[]{i2-1, i};
            }
            else if(!Main.BlockList2D.get(i).get(i2-1).passability){
                return new int[]{i2+1, i};
            }
        }
        return new int[]{i2, i};
    }
    public int[] block_detected_3(double x,double y){
        int i = (int)(y/Main.height_block)-1;
        int i2 = (int)(x/Main.width_block)-1;
        if(!Main.BlockList2D.get(i).get(i2).passability) {
            return new int[]{i2, i};
        }
        else{
            if(!Main.BlockList2D.get(i+1).get(i2).passability){
                return new int[]{i2, i+1};
            }
            else if(!Main.BlockList2D.get(i-1).get(i2).passability){
                return new int[]{i2, i-1};
            }
            else if(!Main.BlockList2D.get(i).get(i2+1).passability){
                return new int[]{i2+1, i};
            }
            else if(!Main.BlockList2D.get(i).get(i2-1).passability){
                return new int[]{i2-1, i};
            }
        }
        return new int[]{i2, i};
    }
    public int[] block_detected_2Soldat(Transport tr){
        int i = (int)(tr.tower_y/Main.height_block)-1;
        int i2 = (int)(tr.tower_x/Main.width_block)-1;
        try {
            if (!Main.BlockList2D.get(i).get(i2).passability) {
                return new int[]{i2, i};
            } else {
                if (!Main.BlockList2D.get(i + 1).get(i2).passability) {
                    return new int[]{i2, i - 1};
                } else if (!Main.BlockList2D.get(i - 1).get(i2).passability) {
                    return new int[]{i2, i + 1};
                } else if (!Main.BlockList2D.get(i).get(i2 + 1).passability) {
                    return new int[]{i2 - 1, i};
                } else if (!Main.BlockList2D.get(i).get(i2 - 1).passability) {
                    return new int[]{i2 + 1, i};
                }
            }
            return new int[]{i2, i};
        }
        catch (IndexOutOfBoundsException e){
            return new int[]{2, 2};
        }
    }
}
