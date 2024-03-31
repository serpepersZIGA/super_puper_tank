package metod;

import build.Build;
import bull.Bull;
import soldat.Soldat;
import transport.Transport;

import java.io.Serializable;
import java.util.ArrayList;

public class buffer_server implements Serializable {
    public ArrayList<Transport>enemy_obj,player_obj,debris;
    public ArrayList<Soldat>soldat_obj;
    public ArrayList<Build>build;
    public ArrayList<Bull>bull_obj;
    public buffer_server(ArrayList<Transport>enemy_obj, ArrayList<build.Build>build, ArrayList<Bull>bull_obj, ArrayList<Soldat>soldat_obj
            , ArrayList<Transport>debris, ArrayList<Transport>player_obj){
        this.player_obj = player_obj;
        this.enemy_obj = enemy_obj;
        this.debris = debris;
        this.soldat_obj = soldat_obj;
        this.build = build;
        this.bull_obj = bull_obj;
    }
}
