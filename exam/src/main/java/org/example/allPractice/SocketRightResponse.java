package org.example.allPractice;

import java.io.*;
import java.net.Socket;

//8. Написать код сокета,
// который отправляет запрос на сервер
// и записывает ответ в файл с правильным расширением.
public class SocketRightResponse {

    public static void main(String[] args) {
        String host = "example.com";
        int port = 8080;
        String nameFile = "response";

        try (Socket socket = new Socket(host,port);
        PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            writer.println("GET /index HTTP/1.1\r\n");
            writer.println("Host:" + host + "\r\n\r\n");
            writer.flush();

            String line;
            String mime = "";

            while(!(line = reader.readLine()).isEmpty()) {
                if (line.contains("Content-Type:")) {
                    mime = line.split(" ")[1];
                    mime = mime.split(";")[0];
                }
            }
            if (mime.contains("text/")) {
                nameFile+="txt";
                BufferedWriter out = new BufferedWriter(new FileWriter(nameFile));
                while ((line = reader.readLine()) != null) {
                    out.write(line + "\n");
                }
                out.close();
            } else {
                nameFile+=mime.split("/")[1];

                OutputStream out = new BufferedOutputStream(new FileOutputStream(nameFile));
                byte[] buffer = new byte[1024];
                int len;
                while((len = socket.getInputStream().read()) != -1) {
                    out.write(buffer,0, len);
                }
                out.close();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
