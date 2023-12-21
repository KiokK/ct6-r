package by.kihtenkoolga.util;

import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;

public class RequestUtil {

    public static String getRequestBody(HttpServletRequest request) {
        try {
            return request.getReader().lines()
                    .reduce("", (accumulator, actual) -> accumulator + actual);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
