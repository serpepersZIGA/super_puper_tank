package com.mygdx.game.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.mygdx.game.main.ClientMain;

import javax.swing.*;
import java.awt.*;

public class InputWindow{
    public static JButton Button;
    public static JFrame frame;
    public InputWindow(){
        // Создаем новое окно
        frame = new JFrame("Input Window");
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.setSize(500, 320);

        // Создаем текстовое поле
        // Создаем кнопку для сохранения текста
        Button = new JButton("Save");
        //frame.add(button);

        // Добавляем текстовое поле и кнопку в окно
        FileHandle handle = Gdx.files.local("bufferIP.txt");
        ClientMain.IP = handle.readString();
        JTextField textField = new JTextField(ClientMain.IP,40);

        frame.getContentPane().add(textField, "North");
        frame.getContentPane().add(Button, "South");

        // Создаем переменную для хранения текста

        // Добавляем обработчик событий для кнопки
        Button.addActionListener(e -> {
            ClientMain.IP = textField.getText();
            FileHandle file = Gdx.files.local("bufferIP.txt");
            file.writeString(ClientMain.IP, false);
            //"127.0.0.1"

        });

        // Делаем окно видимым
        frame.setVisible(true);
    }
}
