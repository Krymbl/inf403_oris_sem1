package org.example.star;


// UDPServer.java
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class UDPStarServer {
    private static final int PORT = 50000;
    //Размер буфера для приема данных
    private static final int BUFFER_SIZE = 4096;
    // UDP-сокет для сетевой коммуникации
    private DatagramSocket socket;

    private List<ClientData> clients;

    private boolean running;
    //AtomicInteger для генерации уникальных ID клиентов.
    //volatile гарантирует видимость изменений между потоками
    private volatile AtomicInteger nextClientId = new AtomicInteger(1);// использовать только для инкремента
    private byte[] buffer = new byte[BUFFER_SIZE];

    public UDPStarServer() {
        clients = new ArrayList<>();
        try {
            socket = new DatagramSocket(PORT);
            System.out.println("UDP сервер запущен на порту " + PORT);
            running = true;
        } catch (SocketException e) {
            System.err.println("Не удалось запустить сервер: " + e.getMessage());
            System.exit(1);
        }
    }

    public void start() {
        System.out.println("Сервер работает... Нажмите Ctrl+C для остановки");

        while (running) {
            try {
                // Создаем пакет для получения данных от клиента
                //DatagramPacket - это контейнер для данных в UDP-протоколе.
                //в UDP данные отправляются отдельными пакетами (датаграммами).
                //buffer - массив байт, куда будут записаны полученные данные
                //buffer.length - максимальное количество байт, которые можно записать

                DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);

                // Ожидаем данные
                socket.receive(receivePacket);

                // Получаем информацию о клиенте
                InetAddress clientAddress = receivePacket.getAddress();
                int clientPort = receivePacket.getPort();

                // Обрабатываем сообщение
                processMessage(receivePacket);

            } catch (IOException e) {
                System.err.println("Ошибка ввода-вывода: " + e.getMessage());
            }
        }

        socket.close();
        System.out.println("Сервер остановлен");
    }

    private void processMessage(DatagramPacket receivePacket) throws IOException {
        // Получаем наш buffer
        byte[] data = receivePacket.getData();
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(data));
        // чтение первого байта - тип сообщения (команда)
        byte msgType = dis.readByte();

        switch (msgType) {
            case 0: { // сообщение hello (команда (0) | int - длина сообщения | сообщение (имя игрока)
                int length = dis.readInt();
                byte[] buf = new byte[length];
                dis.read(buf, 0, length);
                // Прочитай из потока dis в массив buf, начиная с позиции 0, ровно length байт
                String name = new String(buf, StandardCharsets.UTF_8);
                clients.add(new ClientData(nextClientId.getAndIncrement(), name, receivePacket.getAddress(), receivePacket.getPort()));
                System.out.println("Добавили клиента " + name);
                break;
            }
            case 1: { // сообщение list (запрос без параметров)
                // Создание ObjectMapper для сериализации в JSON
                ObjectMapper mapper = new ObjectMapper();
                // Преобразование списка клиентов в JSON-строку
                String message = mapper.writeValueAsString(clients);
                //Конвертация строки в байты UTF-8
                byte[] dataMessage = message.getBytes(StandardCharsets.UTF_8);
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                bos.write((byte) 1); // отвечаем на команду "1"
                bos.write(writeInt(dataMessage.length));
                bos.write(dataMessage);

                DatagramPacket sendPacket = new DatagramPacket(
                        bos.toByteArray(),     // Данные для отправки (байты)
                        bos.size(),            // Длина данных
                        receivePacket.getAddress(),  // IP-адрес получателя
                        receivePacket.getPort()      // Порт получателя
                );
                socket.send(sendPacket);
                break;
            }
            case 2: {
                /*
                     команда (2) | int - id игрока | int - длина сообщения | сообщение
                 */
                int id = dis.readInt();
                int size = dis.readInt();
                byte[] msg = new byte[size];
                dis.read(msg);

                Optional<ClientData> otherClient = clients.stream()
                        .filter(c -> c.getId() == id).findFirst();
                if (otherClient.isPresent()) {
                    ClientData client = otherClient.get();
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    bos.write((byte) 2); // отвечаем на команду "2"
                    bos.write(writeInt(1)); // здесь надо найти клиента-отправителя
                    bos.write(writeInt(msg.length));
                    bos.write(msg);

                    DatagramPacket sendPacket = new DatagramPacket(
                            bos.toByteArray(),
                            bos.size(),
                            client.getClientAddress(),
                            client.getClientPort());
                    socket.send(sendPacket);
                } else {
                    //ToDO здесь надо ответить, что не нашли клиента-получателя
                }
                break;
            }
        }
    }

    private byte[] writeInt(int value) {
        byte[] result = new byte[4];
        result[0] = (byte) (value >> 24);
        result[1] = (byte) (value >> 16);
        result[2] = (byte) (value >> 8);
        result[3] = (byte) value;
        return result;
    }

    public static void main(String[] args) {
        UDPStarServer server = new UDPStarServer();
        server.start();
    }
}