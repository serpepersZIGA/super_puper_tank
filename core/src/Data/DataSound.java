package Data;

import java.io.File;

public class DataSound {
    public File acid_attack,break_wooden,cannon,flame_attack,flame_sound,hit,hit_not_penetration,kill,machinegun,motor,
            motor_helicopter,motor_back;
    public DataSound(){
        this.acid_attack = new File("sound/acid_attack.wav");
        this.break_wooden = new File("sound/break_wooden.wav");
        this.cannon = new File("sound/cannon.wav");
        this.flame_attack = new File("sound/flame_attack.wav");
        this.flame_sound = new File("sound/flame_sound.wav");
        this.hit = new File("sound/hit.wav");
        this.hit_not_penetration = new File("src/sound/hit_not_penetration.wav");
        this.kill = new File("sound/kill.wav");
        this.machinegun = new File("sound/machinegun.wav");
        this.motor = new File("sound/motor.wav");
        this.motor_helicopter = new File("sound/motor_helicopter.wav");
        this.motor_back = new File("sound/motor_back.wav");


    }
}
