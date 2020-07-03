package com.mine.chat.client;

import com.mine.chat.network.TCPConnection;
import com.mine.chat.network.TCPConnectionListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;


public class Client extends JFrame implements ActionListener, TCPConnectionListener {

    private static final String IP = "localhost";
    private static final int PORT = 40000;
    private static final int WIDTH = 600;
    private static final int HEIGHT = 400;


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Client();
            }
        });
    }

    private final JTextArea log = new JTextArea();
    private final JTextField fldNickname = new JPasswordField();
    private final JTextField fldInput = new JPasswordField();

    private TCPConnection connection;

    private Client() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setAlwaysOnTop(true);
        log.setEditable(false);
        log.setLineWrap(true);
        fldInput.addActionListener(this);
        add(log, BorderLayout.CENTER);
        add(fldInput, BorderLayout.SOUTH);
        add(fldNickname, BorderLayout.NORTH);

        setVisible(true);
        try {
            connection = new TCPConnection(this, IP, PORT);
        } catch (IOException e) {
            printMessage("Connection Exception: " + e);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String message = fldInput.getText();
        if (message.equals("")) return;
        fldInput.setText(null);
        connection.sendString(fldNickname.getText() + ": " + message);
    }

    @Override
    public void onConnectionReady(TCPConnection tcpConnection) {
        printMessage("Connection is Ready");
    }

    @Override
    public void onReceiveString(TCPConnection tcpConnection, String value) {
        printMessage(value);
    }

    @Override
    public void onDisconnect(TCPConnection tcpConnection) {
        printMessage("Connection Close");
    }

    @Override
    public void onException(TCPConnection tcpConnection, Exception e) {
        printMessage("Connection Exception: " + e);
    }

    private synchronized void printMessage(String message) {
        SwingUtilities.invokeLater(() -> {
            log.append(message + "\n");
            log.setCaretPosition(log.getDocument().getLength());
        });
    }
}
