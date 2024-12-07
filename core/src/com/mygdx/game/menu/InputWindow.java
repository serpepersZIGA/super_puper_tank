package com.mygdx.game.menu;

import com.mygdx.game.main.ClientMain;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class InputWindow{
    public static JButton button;
    public static JFrame frame;
    public InputWindow() throws IOException {
        // Создаем новое окно
        frame = new JFrame("Input Window");
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.setSize(500, 320);

        // Создаем текстовое поле
        // Создаем кнопку для сохранения текста
        button = new JButton("Save");
        //frame.add(button);

        // Добавляем текстовое поле и кнопку в окно
        File BufferIP = new File("Buffer/bufferIP.txt");
        FileInputStream inputStream = new FileInputStream(BufferIP);
        byte [] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            ClientMain.IP = new String(buffer, 0, bytesRead);
        }
        JTextField textField = new JTextField(ClientMain.IP,40);
        inputStream.close();
        frame.getContentPane().add(textField, "North");
        frame.getContentPane().add(button, "South");

        // Создаем переменную для хранения текста

        // Добавляем обработчик событий для кнопки
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ClientMain.IP = textField.getText();
                File BufferFile = new File("Buffer/bufferIP.txt");
                FileOutputStream outputStream;
                try {
                    outputStream = new FileOutputStream(BufferFile);
                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
                byte [] buffer = ClientMain.IP.getBytes();
                try {
                    outputStream.write(buffer);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                try {
                    outputStream.close();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                //"127.0.0.1"

            }
        });

        // Делаем окно видимым
        frame.setVisible(true);
    }
}
