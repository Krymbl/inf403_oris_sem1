package net_HW;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class RequestHandler {

    final static Logger logger = LogManager.getLogger(RequestHandler.class);

    public void handle(Socket clientSocket) {
        try {

            // Поток для чтения данных от клиента
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
            // Читаем пакет от клиента
            String lineOne = reader.readLine();
            System.out.println(lineOne); //"GET /home HTTP/1.1"
            logger.debug(lineOne);
            String[] components = lineOne.split(" ");
            //TODO реализовать определение метода (GET, POST,...) для передачи как параметра в сервис
            // http://localhost:8080/resource/part?name=tat&region=16
            // URI /resource/part
            //
            // При наличии извлечь параметры и поместить в Map
            String method = components[0];

            String[] resourceAndParams = components[1].split("\\?",2);
            String resource = resourceAndParams[0];
            Map<String, String> paramsMap = (resourceAndParams.length > 1 ? parseParams(resourceAndParams[1]) : new HashMap<>());
            System.out.println(method + " " + resource + " " + paramsMap);

            if (resource.equals("/shutdown")) {
                logger.info("server stopped by client");
                //break;
            }
            while (true) {
                // Читаем пакет от клиента
                String message = reader.readLine();
                System.out.println(message);
                logger.debug(message);

                if (message.isEmpty()) {
                    logger.debug("end of request header");
                    OutputStream os = clientSocket.getOutputStream();
                    logger.debug("outputStream" + os);
                    IResourceService resourceService = Application.resourceMap.get(resource);
                    if (resourceService != null) {
                        // TODO передавать метод, передавать Map с параметрами в функцию service
                        resourceService.service(method, paramsMap, os);
                    } else {
                        new NotFoundService().service(method, paramsMap, os);
                    }
                    os.flush();
                    logger.debug("outputStream" + os);
                    break;
                }

                //clientSocket.close();
            }
        } catch (IOException e) {
            logger.atError().withThrowable(e);
        }


    }
    private static Map<String, String> parseParams(String stringParams) {
        String[] parseParams = stringParams.split("&");
        Map<String, String> paramMap = new HashMap<>();
        for (String param : parseParams) {
            String[] keyValue = param.split("=");
            paramMap.put(keyValue[0], keyValue[1]);
        }
        return paramMap;
    }

}