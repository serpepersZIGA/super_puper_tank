package Content.Bull;

import com.mygdx.game.bull.Bullet;
import com.mygdx.game.method.rand;

public class BullOption extends Bullet {
    public BullOption(Bullet b){
        this.x = b.x;
        this.y = y;
        this.r = b.r;this.g = b.g;this.b = b.r;
        this.rotation = b.rotation;
        this.type_team = b.type_team;
        this.height = b.height;
        this.damage = b.damage;
        this.penetration = b.penetration;
        this.t_damage = b.t_damage;

        this.size = b.size;
        this.size_render =  b.size_render;
        this.speed = b.speed;
        this.time = 65+rand.rand(15);
        type = 1;
    }
}
