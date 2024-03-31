package sound;

import java.io.File;

public class sound_archive {
    public File acid_attack,break_wooden,cannon,flame_attack,flame_sound,hit,hit_not_penetration,kill,machinegun,motor,
            motor_helicopter,motor_back;
    public sound_archive(){
        this.acid_attack = new File("src/sound/acid_attack.wav");
        this.break_wooden = new File("src/sound/break_wooden.wav");
        this.cannon = new File("src/sound/cannon.wav");
        this.flame_attack = new File("src/sound/flame_attack.wav");
        this.flame_sound = new File("src/sound/flame_sound.wav");
        this.hit = new File("src/sound/hit.wav");
        this.hit_not_penetration = new File("src/sound/hit_not_penetration.wav");
        this.kill = new File("src/sound/kill.wav");
        this.machinegun = new File("src/sound/machinegun.wav");
        this.motor = new File("src/sound/motor.wav");
        this.motor_helicopter = new File("src/sound/motor_helicopter.wav");
        this.motor_back = new File("src/sound/motor_back.wav");


    }
}
