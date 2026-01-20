package org.example.swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SwingExample {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(()-> {
            //JFrame — это контейнер ВЕРХНЕГО УРОВНЯ
            JFrame frame = new JFrame("Пример");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800,800);

            frame.setLayout(new BorderLayout());

            JPanel jpanel = new JPanel();
            jpanel.setLayout(new FlowLayout(FlowLayout.LEFT));

            JLabel label = new JLabel("Как дела?");
            JTextField field = new JTextField(20);
            JCheckBox checkbox = new JCheckBox("Согласен на обработку");
            JButton button = new JButton("Отправить");
            JLabel label2 = new JLabel("Ваш ответ: ");

            JLabel label3 = new JLabel("Последние координаты мыши при клике: ");

            jpanel.add(label);
            jpanel.add(field);
            jpanel.add(checkbox);
            jpanel.add(button);
            jpanel.add(label2);

            JPanel panel2 = new JPanel();
            panel2.setLayout(new GridLayout(3,3));

            for (int i = 0; i < 9; i++) {
                panel2.add(new Button("" + i));
            }

            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new Thread(() -> {
                        try {
                            Thread.sleep(5000);
                            SwingUtilities.invokeLater(() -> {
                                label2.setText("Ваш ответ: " + field.getText());
                                field.setText("");
                            });

                        } catch (InterruptedException ex) {
                            throw new RuntimeException(ex);
                        }
                    }).start();
                }
            });

            // 3. Создаем SwingWorker
//            SwingWorker<Void, Void> worker = new SwingWorker<>() {
//                @Override
//                protected Void doInBackground() throws Exception {
//                    // Это выполняется в фоновом потоке
//                    Thread.sleep(5000); // Имитация работы
//                    return null;
//                }
//
//                @Override
//                protected void done() {
//                    // Это выполняется в EDT после завершения
//                    try {
//                        get(); // Проверяем на исключения
//                        label2.setText("Ваш ответ: " + field.getText());
//                        field.setText("");
//                    } catch (Exception ex) {
//                        label2.setText("Ошибка: " + ex.getMessage());
//                    } finally {
//                        button.setEnabled(true); // Разблокируем кнопку
//                    }
//                }
//            };
//
//            // 4. Запускаем
//                worker.execute();
//            });

            field.addKeyListener(new KeyListener() {
                @Override
                public void keyTyped(KeyEvent e) {}

                @Override
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_ENTER ) {
                        label2.setText("Ваш ответ: " + field.getText());
                        field.setText("");
                    }

                }

                @Override
                public void keyReleased(KeyEvent e) {}
            });

            frame.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    label3.setText("Последние координаты мыши при клике: (" + e.getX() + "," + e.getY() + ")" );
                }

                @Override
                public void mousePressed(MouseEvent e) {

                }

                @Override
                public void mouseReleased(MouseEvent e) {

                }

                @Override
                public void mouseEntered(MouseEvent e) {

                }

                @Override
                public void mouseExited(MouseEvent e) {

                }
            });

            frame.add(jpanel, BorderLayout.CENTER);
            frame.add(panel2, BorderLayout.EAST);
            frame.add(label3, BorderLayout.SOUTH);
            frame.setVisible(true);

        });

    }
}
