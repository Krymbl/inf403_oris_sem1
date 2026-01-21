package org.example.allPractice;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

//18. Отправить http запрос через
// сокеты (в http запросе передаётся
// название товара, категория)
public class HttpSocket {
    public static void main(String[] args) {
        String host = "localhost";
        int port = 8080;

        try (Socket socket = new Socket(host, port);
             PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)){
            writer.print("GET /index?name=iphone&category=tech HTTP/1.1\r\n");
            writer.print("Host: " + host + "\r\n\r\n");;
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
