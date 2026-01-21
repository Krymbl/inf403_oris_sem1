package org.example.httpRequestSocket;

import jakarta.servlet.http.HttpServlet;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

//передать http запрос через сокеты
public class HttpRequestSocket {
    public static void main(String[] args) {
        String host = "example.com";
        int port = 80;
        try (Socket socket = new Socket(host, port);
        PrintWriter write = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true)) {
            String httpRequest = "GET /index HTTP/1.1\r\n" +
                    "Host: " + host + "\r\n" +
                    "Admin-Agent: JavaSocketClient\r\n" +
                    "\r\n";
            write.print(httpRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
