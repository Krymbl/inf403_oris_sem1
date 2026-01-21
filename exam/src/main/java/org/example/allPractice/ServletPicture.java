package org.example.allPractice;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

//15. Написать метод сервлета,
// который принимает имя картинки и отдает ее пользователю
public class ServletPicture extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String pictureName = request.getParameter("pictureName");
        Path imagePath = Paths.get("/images/", pictureName);
        File file = new File(imagePath.toString());
        String mimeType = getServletContext().getMimeType(pictureName);

        if (mimeType == null) {
            mimeType = "image/jpeg";
        }
        response.setContentType(mimeType);

        try(InputStream is = new FileInputStream(file);
        OutputStream os = response.getOutputStream()) {
            byte[] buffer = new byte[1024];
            int len;
            while ((len = is.read(buffer)) != -1) {
                os.write(buffer,0,len);
            }
        }


    }
}
