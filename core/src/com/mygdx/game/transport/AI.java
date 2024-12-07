package com.mygdx.game.transport;
import com.mygdx.game.main.Main;

public class AI {
    // Координаты ИИ
    private static int x, y;
    private static byte c;

    // Метод для обновления позиции ИИ
    public void path_AI(Transport ai,Transport target,double x_ai,double y_ai){
        c = 1;
        int[] target_xy = block_detected_2(target);
        if(ai.path.size() != 0) {
            for (int i = 0; i < Main.BuildingList.size(); i++) {
                if(null != ai.findIntersection(Main.BlockList2D.get(ai.path.get(ai.path.size()-1)[1]).get(ai.path.get(ai.path.size()-1)[0]).x_center,
                        Main.BlockList2D.get(ai.path.get(ai.path.size()-1)[1]).get(ai.path.get(ai.path.size()-1)[0]).y_center, target_xy[0], target_xy[1],
                        Main.BuildingList.get(i).x,Main.BuildingList.get(i).y,Main.BuildingList.get(i).x+Main.BuildingList.get(i).width,
                        Main.BuildingList.get(i).y+Main.BuildingList.get(i).height)){
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
            while (y != target_xy[1] && x != target_xy[0]) {
                if (Main.BlockList2D.get(y + 1).get(x).passability && y < target_xy[1]) {
                    if (x < target_xy[0]) {
                        while (Main.BlockList2D.get(y + 1).get(x).passability) {
                            x += 1;
                            if (!Main.BlockList2D.get(y + 1).get(x).passability) {
                                ai.path.add(new int[]{x, y});
                                y += 1;
                                ai.path.add(new int[]{x, y});
                                break;
                            }
                            ai.path.add(new int[]{x, y});
                        }


                        ai.path.add(new int[]{x, y});
                    } else {
                        while (Main.BlockList2D.get(y + 1).get(x).passability) {
                            x -= 1;
                            if (!Main.BlockList2D.get(y + 1).get(x).passability) {
                                ai.path.add(new int[]{x, y});
                                y += 1;
                                ai.path.add(new int[]{x, y});
                                break;
                            }
                            ai.path.add(new int[]{x, y});
                        }

                        ai.path.add(new int[]{x, y});

                    }
                }
                else if (Main.BlockList2D.get(y - 1).get(x).passability && y > target_xy[1]) {
                    if (x < target_xy[0]) {
                        while (Main.BlockList2D.get(y - 1).get(x).passability) {
                            x += 1;
                            if (!Main.BlockList2D.get(y - 1).get(x).passability) {
                                ai.path.add(new int[]{x, y});
                                y -= 1;
                                ai.path.add(new int[]{x, y});
                                break;
                            }
                            ai.path.add(new int[]{x, y});
                        }


                        ai.path.add(new int[]{x, y});
                    } else {
                        while (Main.BlockList2D.get(y - 1).get(x).passability) {
                            x -= 1;
                            if (!Main.BlockList2D.get(y - 1).get(x).passability) {
                                ai.path.add(new int[]{x, y});
                                y -= 1;
                                ai.path.add(new int[]{x, y});
                                break;
                            }
                            ai.path.add(new int[]{x, y});
                        }


                        ai.path.add(new int[]{x, y});
                    }
                }
                if (Main.BlockList2D.get(y).get(x + 1).passability && x < target_xy[0]) {
                    if (y < target_xy[1]) {
                        while (Main.BlockList2D.get(y).get(x + 1).passability) {
                            y += 1;
                            if (!Main.BlockList2D.get(y).get(x+1).passability) {
                                ai.path.add(new int[]{x, y});
                                x+= 1;
                                ai.path.add(new int[]{x, y});
                                break;
                            }
                            ai.path.add(new int[]{x, y});
                        }


                        ai.path.add(new int[]{x, y});
                    } else {
                        while (Main.BlockList2D.get(y).get(x + 1).passability) {
                            y -= 1;
                            if (!Main.BlockList2D.get(y).get(x+1).passability) {
                                ai.path.add(new int[]{x, y});
                                x+= 1;
                                ai.path.add(new int[]{x, y});
                                break;
                            }
                            ai.path.add(new int[]{x, y});
                        }

                        ai.path.add(new int[]{x, y});
                    }
                }
                else if (Main.BlockList2D.get(y).get(x - 1).passability && x > target_xy[0]) {
                    if (y < target_xy[1]) {
                        while (Main.BlockList2D.get(y).get(x - 1).passability) {
                            y += 1;
                            if (!Main.BlockList2D.get(y).get(x-1).passability) {
                                ai.path.add(new int[]{x, y});
                                x-= 1;
                                ai.path.add(new int[]{x, y});
                                break;
                            }
                            ai.path.add(new int[]{x, y});
                        }


                        ai.path.add(new int[]{x, y});
                    } else {
                        while (Main.BlockList2D.get(y).get(x - 1).passability) {
                            y -= 1;
                            if (!Main.BlockList2D.get(y).get(x-1).passability) {
                                ai.path.add(new int[]{x, y});
                                x-= 1;
                                ai.path.add(new int[]{x, y});
                                break;
                            }
                            ai.path.add(new int[]{x, y});
                        }


                        ai.path.add(new int[]{x, y});
                    }
                }
//                if (!Main.BlockList2D.get(y).get(x + 1).passability && x < target_xy[0] &
//                            !Main.BlockList2D.get(y + 1).get(x).passability && y < target_xy[1]) {
//                        y += 1;
//                        x += 1;
//                        ai.path.add(new int[]{x, y});
//                    } else if (!Main.BlockList2D.get(y).get(x - 1).passability && x < target_xy[0] &
//                            !Main.BlockList2D.get(y + 1).get(x).passability && y < target_xy[1]) {
//                        y += 1;
//                        x -= 1;
//                        ai.path.add(new int[]{x, y});
//                    } else if (!Main.BlockList2D.get(y).get(x - 1).passability && x < target_xy[0] &
//                            !Main.BlockList2D.get(y - 1).get(x).passability && y < target_xy[1]) {
//                        y -= 1;
//                        x -= 1;
//                        ai.path.add(new int[]{x, y});
//                    } else if (!Main.BlockList2D.get(y).get(x + 1).passability && x < target_xy[0] &
//                            !Main.BlockList2D.get(y - 1).get(x).passability && y < target_xy[1]) {
//                        y -= 1;
//                        x += 1;
//                        ai.path.add(new int[]{x, y});}
                else{
                    if (!Main.BlockList2D.get(y).get(x + 1).passability && x < target_xy[0]) {
                        x += 1;
                        ai.path.add(new int[]{x, y});

                    }else if (!Main.BlockList2D.get(y).get(x - 1).passability && x > target_xy[0]) {
                        x -= 1;
                        ai.path.add(new int[]{x, y});
                    }
                    if (!Main.BlockList2D.get(y + 1).get(x).passability && y < target_xy[1]) {
                        y += 1;
                        ai.path.add(new int[]{x, y});
                    }else if (!Main.BlockList2D.get(y - 1).get(x).passability && y > target_xy[1]) {
                        y -= 1;
                        ai.path.add(new int[]{x, y});
                    }
                }
            }
            for(int i = 0;i<ai.path.size();i++){
                Main.BlockList2D.get(ai.path.get(i)[1]).get(ai.path.get(i)[0]).render_block = Main.UpdateBlockReg.Update4;
            }
        }
    }
    public int[] block_detected_2(Transport tr){
        int i = (int)(tr.tower_y/Main.height_block)-1;
        int i2 = (int)(tr.tower_x/Main.width_block)-1;
        Main.BlockList2D.get(i).get(i2).render_block = Main.UpdateBlockReg.Update3;
        if(!Main.BlockList2D.get(i).get(i2).passability) {
            return new int[]{i2, i};
        }
        else{
            if(Main.BlockList2D.get(i+1).get(i2).passability){
                return new int[]{i2, i-1};
            }
            else if(Main.BlockList2D.get(i-1).get(i2).passability){
                return new int[]{i2, i+1};
            }
            else if(Main.BlockList2D.get(i).get(i2+1).passability){
                return new int[]{i2-1, i};
            }
            else if(Main.BlockList2D.get(i).get(i2-1).passability){
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
}
