package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GComponent extends JComponent{
    //Наследование от JComponent - базовый класс для рисованных компонентов Swing

    int x, y, bounds = 64;
    boolean move_up, move_left;
    int speed = 3;

    Image image;
    //Объект изображения - здесь будет храниться загруженная картинка мяча

    public GComponent() {

        image = new ImageIcon("ball.png").getImage();
        //ImageIcon("ball.png") - создает иконку из файла ball.png в корне проекта
        //.getImage() - получает объект Image для рисования


        setDoubleBuffered(true);
        //Включение двойной буферизации:
        //Все рисуется сначала в памяти, потом переносится на экран
        //Убирает мерцание при анимации

        Timer timer = new Timer(10, new ActionListener() {
            //Создание таймера для анимации:
            //Timer(10, ...) - срабатывает каждые 10 миллисекунд (≈100 FPS)
            //ActionListener - объект, который будет вызываться по таймеру
            @Override
            public void actionPerformed(ActionEvent e) {
                //Метод, вызываемый таймером каждый раз (каждые 10 мс)
                if (x < 0) {
                    move_left = false;
                }
                if (x > getWidth() - bounds) {
                    //getWidth() - ширина компонента
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

                repaint();
                //Запрос на перерисовку компонента
                //Вызывает paintComponent() для обновления изображения
            }
        });

        timer.start();
        //Запуск таймера (до этого он был создан, но не запущен)
    }

    @Override
    protected void paintComponent(Graphics g) {
        //Вызывается Swing'ом при необходимости отрисовать компонент
        //Graphics g - "кисть" для рисования

        Graphics2D g2d = (Graphics2D) g;
        //Приведение к Graphics2D - более мощный API для 2D графики

        super.paintComponent(g2d);
        //Очистка фона - вызывает метод родительского класса
        //Стирает предыдущий кадр

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        //Rendering hint (подсказка рендерингу) — это настройка, которая говорит системе:
        //КАК рисовать (качественно или быстро)
        //ЧЕМ рисовать (какие алгоритмы использовать)
        //КАКИЕ компромиссы делать между качеством и скоростью

        //Включение сглаживания (anti-aliasing)
        //Убирает "лесенку" на диагональных линиях и краях
        //RenderingHints — это настройки качества рендеринга (отрисовки) в Java 2D.
        //RenderingHints.KEY_ANTIALIASING — ключ "включение сглаживания"
        //RenderingHints.VALUE_ANTIALIAS_ON — значение "включить"
        //Линии сглажены за счет полупрозрачных пикселей

        g2d.drawImage(image,x,y,null);
        //Рисование мяча в текущих координатах (x, y)
        //ImageObserver — механизм асинхронной загрузки изображений в Java
        //null - наблюдатель за загрузкой изображения (не нужен)
        //Параметр null означает: "Рисуй сейчас, не жди полной загрузки. Если изображение еще не загружено
        // — покажи что есть."

        g2d.dispose();
        //Освобождение ресурсов - хороший тон, но не обязательно (Java сама очистит)

        Toolkit.getDefaultToolkit().sync();
        //Синхронизация с дисплеем (особенно важно для Linux)
        //Гарантирует, что все графические операции завершены
    }
}