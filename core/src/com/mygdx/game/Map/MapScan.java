package com.mygdx.game.Map;

import Content.Build.BigBuildingWood1;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.mygdx.game.block.UpdateRegister;
import com.mygdx.game.main.Main;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.Objects;

import static java.nio.file.Files.readAllLines;

public class MapScan {
    public static void MapInput(String Map) {
        Main.BuildingList.clear();
        BlockDelete();
        int conf = 0;
        String TxT = "";
        boolean confS = false;
        boolean confAsphalt = false;
        boolean confAsphaltX = false;
        boolean confAsphaltY = false;

//        try {
//            TxT = String.valueOf(readAllLines(Paths.get(Map), StandardCharsets.UTF_8));
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
        FileHandle file = Gdx.files.internal(Map);
        TxT = file.readString();
        String TotalTxT = "";
        String Build = "";
        String X = "";
        String Y = "";
        String Z = "";
        int x = 0;
        int y = 0;
        int z = 0;


        for (int i = 0; i < TxT.length(); i++) {
            char c = TxT.charAt(i);
            if (c == '/' || c == ',' || c == ' ') {
                TotalTxT = "";
            } else if (c == ':' & !confS) {
                if (conf == 0) {
                    Build = TotalTxT;
                    TotalTxT = "";
                } else if (conf == 1) {
                    X = TotalTxT;
                    TotalTxT = "";
                    x = Integer.parseInt(X);
                } else if (conf == 2) {
                    Y = TotalTxT;
                    TotalTxT = "";
                    y = Integer.parseInt(Y);
                }
                conf += 1;
            } else if (c == '^') {
                confS = true;
            } else if (c == '>' & !confS) {
                confAsphalt = true;
            } else if (c == 'X' & !confS & confAsphalt) {
                confAsphaltX = true;
            } else if (c == 'Y' & !confS & confAsphalt) {
                confAsphaltY = true;
            } else if (c == ';' & !confS) {
                if (!confAsphalt) {
                    MapSpawn(Build, x, y);
                } else {
                    if (confAsphaltX) {
                        Z = TotalTxT;
                        z = Integer.parseInt(Z);
                        MapSpawnBlock(Build, x, y, z, 1);
                        confAsphaltX = false;
                    } else if (confAsphaltY) {
                        Z = TotalTxT;
                        z = Integer.parseInt(Z);
                        MapSpawnBlock(Build, x, y, z, 2);
                        confAsphaltY = false;
                    } else {
                        MapSpawnBlock(Build, x, y, z, 0);
                    }
                    confAsphalt = false;
                    Z = "";
                    z = 0;
                }
                TotalTxT = "";
                x = 0;
                y = 0;
                Build = "";
                conf = 0;

            } else if (c == ';' & confS) {
                confS = false;

            } else if (!confS) {
                TotalTxT = TotalTxT + c;
            }
        }

    }

    public static String MapName(String Map) {
        String TxT;
        String name = "";
        boolean conf = false;
        try {
            TxT = String.valueOf(readAllLines(Paths.get(Map), StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String TotalTxT = "";
        for (int i = 0; i < TxT.length(); i++) {
            char c = TxT.charAt(i);
            if (c == '/') {
                TotalTxT = "";
            } else if (c == '^') {
                conf = true;
            } else if (c == ';' & conf) {
                name = TotalTxT;
                conf = false;

            } else if (conf) {
                TotalTxT = TotalTxT + c;
            }
        }
        return name;
    }

    private static void BlockDelete() {
        for (int i = 0; i < Main.BlockList2D.size(); i++) {
            for (int i2 = 0; i2 < Main.BlockList2D.get(i).size(); i2++) {
                Main.BlockList2D.get(i).get(i2).render_block = UpdateRegister.GrassUpdate;
            }
        }
    }

    private static void MapSpawn(String Build, int x, int y) {
        if (Objects.equals(Build, "BigBuildingWood1")) {
            Main.BuildingList.add(new BigBuildingWood1(x*Main.width_block, y*Main.height_block));
        } else if (Objects.equals(Build, "Asphalt")) {
            AsphaltSpawn(x, y);
        }
    }

    private static void MapSpawnBlock(String Build, int x, int y, int z, int conf) {
        if (Objects.equals(Build, "Asphalt") & conf == 0) {
            AsphaltSpawn(x, y);
        } else if (Objects.equals(Build, "Asphalt") & conf == 1) {
            for (int i = 0; i < z; i++) {
                AsphaltSpawn(x + i, y);
            }
        } else if (Objects.equals(Build, "Asphalt") & conf == 2) {
            for (int i = 0; i < z; i++) {
                AsphaltSpawn(x, y + i);
            }
        }
    }

    public static void AsphaltSpawn(int x, int y) {
        Main.BlockList2D.get(y).get(x).render_block = UpdateRegister.UpdateAsphalt1;
    }
}
