package org.example;



import javax.swing.*;
import java.awt.*;

public class GraphDemo extends JFrame {
    //Главное окно программы - наследуется от JFrame

    public GraphDemo() {
        super("game");
        //super("game") - вызывает конструктор JFrame с заголовком "game"

        setSize(800, 800);
        //Устанавливает размер окна 800x800 пикселей
        setLocationRelativeTo(null);
        //Центрирует окно на экране
        //null означает "относительно центра экрана"
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        //Действие при закрытии окна:
        //EXIT_ON_CLOSE - завершить программу полностью


        //createBufferStrategy(2);
        //createBufferStrategy(2) — это продвинутая техника для высокопроизводительной анимации и игр в Swing
        //Это механизм тройной или двойной буферизации на уровне AWT/ОС, а не Swing. Используется для:
        //Игр с высокой частотой кадров
        //Плавной анимации
        //Минимизации мерцания

        setLayout(new BorderLayout());
        //Установка менеджера компоновки:
        //BorderLayout делит окно на 5 зон (север, юг, запад, восток, центр)
        //По умолчанию компоненты добавляются в центр

        add(new GComponent());
        //Добавление нашего компонента с мячом в окно
        //Создается объект GComponent и помещается в центр окна
        setVisible(true);
        //Делает окно видимым (всегда вызывается последним!)
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(
                //Почему не просто new GraphDemo()?
                //SwingUtilities.invokeLater() гарантирует, что GUI создается в EDT (Event Dispatch Thread)
                //Это специальный поток Swing для всех GUI операций
                //Без этого могут быть гонки данных и ошибки

                new Runnable() {
                    public void run() {
                        new GraphDemo();
                    }
                });
    }

    //Что происходит:
    //Программа запускается в основном потоке
    //invokeLater ставит задачу в очередь EDT
    //EDT выполняет run() метод
    //Создается объект GraphDemo (окно)
    //Запускается таймер в GComponent
    //Начинается анимация

}