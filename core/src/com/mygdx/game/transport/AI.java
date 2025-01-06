package com.mygdx.game.transport;
import com.mygdx.game.block.UpdateRegister;
import com.mygdx.game.main.Main;
import com.mygdx.game.soldat.Soldat;

import static com.mygdx.game.main.Main.BlockList2D;
import static com.mygdx.game.main.Main.Render;

public class AI {
    // Координаты ИИ
    private static int x, y;
    private static byte c;

    // Метод для обновления позиции ИИ
    public void path_AI(Transport ai,Transport target,float x_ai,float y_ai){

        int[] target_xy = block_detected_2(target);
            int[] ai_xy = block_detected_3(x_ai, y_ai);
            x = ai_xy[0];
            y = ai_xy[1];
            while (y != target_xy[1] || x != target_xy[0]) {
                if (Main.BlockList2D.get(y + 1).get(x).passability && y < target_xy[1]) {
                    if (x < target_xy[0]) {
                        while (Main.BlockList2D.get(y + 1).get(x).passability) {
                            x += 1;
                            if (!Main.BlockList2D.get(y + 1).get(x).passability) {
                                ai.path.add(new int[]{x, y});
                                y += 1;
                                ai.path.add(new int[]{x, y});
                                y += 1;
                                ai.path.add(new int[]{x, y});
                                break;
                            }
                            ai.path.add(new int[]{x, y});
                        }


                    } else {
                        while (Main.BlockList2D.get(y + 1).get(x).passability) {
                            x -= 1;
                            if (!Main.BlockList2D.get(y + 1).get(x).passability) {
                                ai.path.add(new int[]{x, y});
                                y += 1;
                                ai.path.add(new int[]{x, y});
                                y += 1;
                                ai.path.add(new int[]{x, y});
                                break;

                            }
                            ai.path.add(new int[]{x, y});
                        }


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
                                y -= 1;
                                ai.path.add(new int[]{x, y});
                                break;
                            }
                            ai.path.add(new int[]{x, y});
                        }


                    } else {
                        while (Main.BlockList2D.get(y - 1).get(x).passability) {
                            x -= 1;
                            if (!Main.BlockList2D.get(y - 1).get(x).passability) {
                                ai.path.add(new int[]{x, y});
                                y -= 1;
                                ai.path.add(new int[]{x, y});
                                y -= 1;
                                ai.path.add(new int[]{x, y});
                                break;
                            }
                            ai.path.add(new int[]{x, y});
                        }

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
                                x+= 1;
                                ai.path.add(new int[]{x, y});
                                break;
                            }
                            ai.path.add(new int[]{x, y});
                        }

                    } else {
                        while (Main.BlockList2D.get(y).get(x + 1).passability) {
                            y -= 1;
                            if (!Main.BlockList2D.get(y).get(x+1).passability) {
                                ai.path.add(new int[]{x, y});
                                x+= 1;
                                ai.path.add(new int[]{x, y});
                                x+= 1;
                                ai.path.add(new int[]{x, y});
                                break;
                            }
                            ai.path.add(new int[]{x, y});
                        }
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
                                x-= 1;
                                ai.path.add(new int[]{x, y});
                                break;
                            }
                            ai.path.add(new int[]{x, y});
                        }


                    } else {
                        while (Main.BlockList2D.get(y).get(x - 1).passability) {
                            y -= 1;
                            if (!Main.BlockList2D.get(y).get(x-1).passability) {
                                ai.path.add(new int[]{x, y});
                                x-= 1;
                                ai.path.add(new int[]{x, y});
                                x-= 1;
                                ai.path.add(new int[]{x, y});
                                break;
                            }
                            ai.path.add(new int[]{x, y});
                        }

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
//            for(int i = 0;i<ai.path.size();i++){
//                Main.BlockList2D.get(ai.path.get(i)[1]).get(ai.path.get(i)[0]).render_block = UpdateRegister.Update3;
//            }
    }
    public void path_AISoldat(Soldat ai, Transport target, float x_ai, float y_ai){

        int[] target_xy = block_detected_2Soldat(target);
        int[] ai_xy = block_detected_3(x_ai, y_ai);
        x = ai_xy[0];
        y = ai_xy[1];
        while (y != target_xy[1] || x != target_xy[0]) {
            if (Main.BlockList2D.get(y + 1).get(x).passability && y < target_xy[1]) {
                if (x < target_xy[0]) {
                    while (Main.BlockList2D.get(y + 1).get(x).passability) {
                        x += 1;
                        if (!Main.BlockList2D.get(y + 1).get(x).passability) {
                            ai.path.add(new int[]{x, y});
                            y += 1;
                            ai.path.add(new int[]{x, y});
                            y += 1;
                            ai.path.add(new int[]{x, y});
                            break;
                        }
                        ai.path.add(new int[]{x, y});
                    }


                } else {
                    while (Main.BlockList2D.get(y + 1).get(x).passability) {
                        x -= 1;
                        if (!Main.BlockList2D.get(y + 1).get(x).passability) {
                            ai.path.add(new int[]{x, y});
                            y += 1;
                            ai.path.add(new int[]{x, y});
                            y += 1;
                            ai.path.add(new int[]{x, y});
                            break;

                        }
                        ai.path.add(new int[]{x, y});
                    }


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
                            y -= 1;
                            ai.path.add(new int[]{x, y});
                            break;
                        }
                        ai.path.add(new int[]{x, y});
                    }


                } else {
                    while (Main.BlockList2D.get(y - 1).get(x).passability) {
                        x -= 1;
                        if (!Main.BlockList2D.get(y - 1).get(x).passability) {
                            ai.path.add(new int[]{x, y});
                            y -= 1;
                            ai.path.add(new int[]{x, y});
                            y -= 1;
                            ai.path.add(new int[]{x, y});
                            break;
                        }
                        ai.path.add(new int[]{x, y});
                    }

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
                            x+= 1;
                            ai.path.add(new int[]{x, y});
                            break;
                        }
                        ai.path.add(new int[]{x, y});
                    }

                } else {
                    while (Main.BlockList2D.get(y).get(x + 1).passability) {
                        y -= 1;
                        if (!Main.BlockList2D.get(y).get(x+1).passability) {
                            ai.path.add(new int[]{x, y});
                            x+= 1;
                            ai.path.add(new int[]{x, y});
                            x+= 1;
                            ai.path.add(new int[]{x, y});
                            break;
                        }
                        ai.path.add(new int[]{x, y});
                    }
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
                            x-= 1;
                            ai.path.add(new int[]{x, y});
                            break;
                        }
                        ai.path.add(new int[]{x, y});
                    }


                } else {
                    while (Main.BlockList2D.get(y).get(x - 1).passability) {
                        y -= 1;
                        if (!Main.BlockList2D.get(y).get(x-1).passability) {
                            ai.path.add(new int[]{x, y});
                            x-= 1;
                            ai.path.add(new int[]{x, y});
                            x-= 1;
                            ai.path.add(new int[]{x, y});
                            break;
                        }
                        ai.path.add(new int[]{x, y});
                    }

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
//            for(int i = 0;i<ai.path.size();i++){
//                Main.BlockList2D.get(ai.path.get(i)[1]).get(ai.path.get(i)[0]).render_block = UpdateRegister.Update3;
//            }
    }
    public int[] block_detected_2(Transport tr){
        int i = (int)(tr.tower_y/Main.height_block)-1;
        int i2 = (int)(tr.tower_x/Main.width_block)-1;
        System.out.println(i+"  "+i2);
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
