package net_HW;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class HttpServer {
    private static final Logger log = LogManager.getLogger(HttpServer.class);

    public static void main(String[] args) {
        log.info("Starting HttpServer");
        try {


            ServerSocket serverSocket = new ServerSocket(8080);
            // Ожидаем подключения клиента
            while (true) {
                Socket clientSocket = serverSocket.accept();
                // Дождались клиента
                // Поток для чтения данных от клиента
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(clientSocket.getInputStream()));
                String lineOne = reader.readLine();
                System.out.println(lineOne);
                String[] components = lineOne.split(" ");
                String resource = components[1];

                if (resource.equals("/shutdown")) {
                    break;
                }

                while (true) {
                    // Читаем пакет от клиента
                    String message = reader.readLine();
                    System.out.println(message);

                    if (message.isEmpty()) {
                        OutputStream os = clientSocket.getOutputStream();
                        os.write("HTTP/1.1 200 OK\r\n".getBytes());
                        os.write("Content-Type: text/html;charset=UTF-8\r\n".getBytes());
                        os.write("\r\n".getBytes());
                        os.write("<html><body>Hello!</body></html>".getBytes());
                        os.flush();
                        break;
                    }
                }
                clientSocket.close();
            }
            serverSocket.close();


        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}