package by.kihtenkoolga.util;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

public class ResponseUtil {

    public static final String CONTENT_TYPE_JSON = "application/json";

    public static void setResponseJsonData(HttpServletResponse response, Object value, Class<?> valueClass, int status) {
        String json = new Gson().toJson(value, valueClass);

        try (PrintWriter wr = response.getWriter()) {
            wr.write(json);
            response.setContentType(CONTENT_TYPE_JSON);
            response.setStatus(status);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
