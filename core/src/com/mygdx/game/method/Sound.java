package com.mygdx.game.method;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.io.File;

public class Sound {
    public static void sound(String str,float volume){
        try {
            if(volume>-80) {

                File audioFile = new File(str);
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
                Clip clip = AudioSystem.getClip();


                // Откройте аудиоклип и начните воспроизведение
                clip.open(audioStream);
                clip.start();

                // Регулировка громкости
                FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                gainControl.setValue(volume); // Установите желаемый уровень громкости в диапазоне -80.0 (тишина) до 6.0206 (макс.)
            }

        } catch (Exception e) {
            System.out.println("Error playing metod.sound: " + e.getMessage());
        }

    }
    public static void sound(File audioFile,float volume){
        try {
            if(volume>-80) {

                AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
                Clip clip = AudioSystem.getClip();


                // Откройте аудиоклип и начните воспроизведение
                clip.open(audioStream);
                clip.start();

                // Регулировка громкости
                FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                gainControl.setValue(volume); // Установите желаемый уровень громкости в диапазоне -80.0 (тишина) до 6.0206 (макс.)
            }

        } catch (Exception e) {
            System.out.println("Error playing metod.sound: " + e.getMessage());
        }

    }
}

