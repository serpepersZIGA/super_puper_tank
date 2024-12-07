package Content.Bull;

import com.mygdx.game.bull.Bull;

import java.util.ArrayList;

public class BullRegister {
    public static ArrayList<Bull>register_bull = new ArrayList<>();
    public static ArrayList<BullPacket> PacketBull = new ArrayList<>();
    public BullRegister() {
        register_bull.add(new BullFlame(0,0,0,0,0,0, (byte) 0, (byte) 0));
        register_bull.add(new BullAcid(0,0,0,0,0,(byte)0,(byte)0));
        register_bull.add(new BullTank(0,0,0,0,0,(byte)0,(byte)0));
        register_bull.add(new BullFragment(0,0,0,0,(byte)0));
        register_bull.add(new BullMortar(0,0,0,0,0,0,0,(byte)0,(byte)0));



    }

}
