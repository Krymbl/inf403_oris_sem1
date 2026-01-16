package org.example.server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class GComponent extends JComponent{

    int x, y, bounds = 64;
    boolean move_up, move_left;
    int speed = 3;

    int bYS = 300, bYC = 300;
    //bYS - Y-координата СЕРВЕРНОЙ ракетки (левая)
    //bYC - Y-координата КЛИЕНТСКОЙ ракетки (правая, полученная от клиента)

    Image image;

    private Socket clientSocket;

    public GComponent(Socket clientSocket) {

        this.clientSocket = clientSocket;

        image = new ImageIcon("ball.png").getImage();

        setDoubleBuffered(true);

        addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {

            }

            @Override
            public void mouseMoved(MouseEvent e) {
                bYS = e.getY();
                //При движении мыши обновляем позицию серверной ракетки
                //e.getY() - получаем Y-координату курсора мыши
            }
        });

        Timer timer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (x <= 20 && y + bounds > bYS && y < bYS + 200) {
                    move_left = false;
                    System.out.println("Удар об левую ракетку");
                }

                if (x + bounds >= (getWidth() - 20) && y + bounds > bYC && y < bYC + 200) {
                    move_left = true;
                    System.out.println("Удар об правую ракетку");
                }

                if (x < 0) {
                    move_left = false;
                }
                if (x > getWidth() - bounds) {
                    move_left = true;
                }

                if (y < 0) {
                    move_up = false;
                }
                if (y > getHeight() - bounds) {
                    move_up = true;
                }

                if (move_left) {
                    x -= speed;
                } else {
                    x += speed;
                }

                if (move_up) {
                    y -= speed;
                } else {
                    y += speed;
                }

                try {
                    DataOutputStream dos = new DataOutputStream(clientSocket.getOutputStream());
                    //Создаем поток вывода для отправки данных клиенту
                    dos.writeInt(bYS);
                    dos.writeInt(x);
                    dos.writeInt(y);
                    //Отправляем клиенту:
                    //bYS - позицию СЕРВЕРНОЙ ракетки (которой управляем мышью)
                    //x - X-координату мяча
                    //y - Y-координату мяча
                    DataInputStream dis = new DataInputStream(clientSocket.getInputStream());
                    //Создаем поток ввода для получения данных от клиента
                    bYC = dis.readInt();
                    //Получаем от клиента позицию КЛИЕНТСКОЙ ракетки
                    //Клиент управляет своей ракеткой и отправляет ее позицию
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                repaint();
            }
        });

        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        super.paintComponent(g2d);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.drawImage(image,x,y,null);

        g2d.fillRect(10, bYS, 10, 200);
        //Рисование СЕРВЕРНОЙ ракетки (левая, управляется мышью на сервере)
        //10 - X-координата (10 пикселей от левого края)
        //bYS - Y-координата (получена от мыши)
        //10 - ширина ракетки
        //200 - высота ракетки
        g2d.fillRect(this.getWidth() - 20, bYC, 10, 200);
        //Рисование КЛИЕНТСКОЙ ракетки (правая, получена от клиента)
        g2d.dispose();

        Toolkit.getDefaultToolkit().sync();
    }
}