package by.bsuir.server.panel;

import by.bsuir.server.start.Server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static java.awt.Color.white;

public class ServerPanel extends JFrame {
    private JTextArea serverArea;
    private JButton cleanButton;

    public ServerPanel(String wind) {
        super(wind);
        setSize(500, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        serverArea = new JTextArea(40, 15);
        serverArea.setFont(new Font("TimesRoman", Font.ITALIC, 16));
        serverArea.setSize(448, 590);
        serverArea.setLocation(26, 19);
        serverArea.setLineWrap(true);//перенести слово на след строку
        serverArea.setWrapStyleWord(true);//перенести слово целиком
        serverArea.setEditable(false); //запретить редактирование
        add(serverArea);
        cleanButton = new JButton("Очистить");
        cleanButton.setSize(124, 36);
        cleanButton.setBackground(white);
        cleanButton.setLocation(41, 617);
        add(cleanButton);
        cleanButton.addActionListener(new ButtonCleanListener());
    }

    public class ButtonCleanListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            if(e.getSource()==cleanButton) {
                serverArea.setText(null);
                addMessage("Количество подключённых клиентов:" + Server.countOfClients + "\n");
                if(Server.countOfClients == 0) {
                    addMessage("Ожидается соединение ...\n");
                }
            }
        }
    }

    public void addMessage(String str){
        serverArea.append(str);
    }
}

