package com.mygdx.game.MapFunction;

import Content.Build.BigBuildingWood1;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.mygdx.game.block.UpdateRegister;
import com.mygdx.game.main.Main;

import java.io.*;
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
        boolean StructConf = false;
        boolean confAsphalt = false;
        boolean confAsphaltX = false;
        boolean confAsphaltY = false;
        StringBuilder result = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(Map))) {
            result.append(br.readLine());

        } catch (IOException e) {
            e.printStackTrace();
            MapBaseAdd.AddMap();
            try {
                BufferedReader br = new BufferedReader(new FileReader(Map));
                result.append(br.readLine());
            } catch (IOException ignored) {
            }
        }
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
            if (c == '\n' || c == ',' || c == ' ') {
                TotalTxT = "";
            }
            else if (c == ':' & !confS) {
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
                    MapSpawn(Build, x, y,StructConf);
                    StructConf = false;
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

            } else if (Objects.equals(TotalTxT, "str{")) {
                StructConf = true;
                TotalTxT = "";
                TotalTxT = TotalTxT + c;
            } else if (c == '}'& StructConf) {
                Build = TotalTxT;
                //StructConf = false;
            } else if (!confS) {
                TotalTxT = TotalTxT + c;
                System.out.println(TotalTxT);
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
            if (c == '\n') {
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

    private static void MapSpawn(String Build, int x, int y,boolean Structure) {
        if(Structure){
            SpawnStructure("Map/Structure/"+Build+".str",x, y);
        }
        else {
            switch (Build) {
                case "BigBuildingWood1": {
                    Main.BuildingList.add(new BigBuildingWood1(x * Main.width_block, y * Main.height_block));
                }
                break;
                case "Asphalt": {
                    AsphaltSpawn(x, y);
                }
                break;
            }
        }
//        if (Objects.equals(Build, "BigBuildingWood1")) {
//            Main.BuildingList.add(new BigBuildingWood1(x*Main.width_block, y*Main.height_block));
//        } else if (Objects.equals(Build, "Asphalt")) {
//            AsphaltSpawn(x, y);
//        } else if (Objects.equals(Build, "Structure{street1}")) {
//            SpawnStructure("Structure/street1.str",x, y);}
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
    private static void SpawnStructure(String Struct, int xStr, int yStr) {
        int conf = 0;
        String TxT = "";
        boolean confAsphalt = false;
        boolean confAsphaltX = false;
        boolean confAsphaltY = false;
        StringBuilder result = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(Struct))) {
            result.append(br.readLine());

        } catch (IOException e) {
            e.printStackTrace();
            MapBaseAdd.AddMap();
            try {
                BufferedReader br = new BufferedReader(new FileReader(Struct));
                result.append(br.readLine());
            } catch (IOException ignored) {
            }
        }
        FileHandle file = Gdx.files.internal(Struct);
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
            if (c == '\n' || c == ',' || c == ' '|| c == '/') {
                TotalTxT = "";
            }
            else if (c == ':') {
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
            }  else if (c == '>') {
                confAsphalt = true;
            } else if (c == 'X' & confAsphalt) {
                confAsphaltX = true;
            } else if (c == 'Y' & confAsphalt) {
                confAsphaltY = true;
            } else if (c == ';') {
                if (!confAsphalt) {
                    MapSpawn(Build, xStr + x, yStr +y,false);
                } else {
                    if (confAsphaltX) {
                        Z = TotalTxT;
                        z = Integer.parseInt(Z);
                        MapSpawnBlock(Build, xStr+ x, yStr+y, z, 1);
                        confAsphaltX = false;
                    } else if (confAsphaltY) {
                        Z = TotalTxT;
                        z = Integer.parseInt(Z);
                        MapSpawnBlock(Build, xStr+ x, yStr+y, z, 2);
                        confAsphaltY = false;
                    } else {
                        MapSpawnBlock(Build, xStr+ x, yStr+y, z, 0);
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

            } else{
                TotalTxT = TotalTxT + c;
            }
        }

    }
}
