package by.kihtenkoolga.util;

import by.kihtenkoolga.config.PaginationInfo;
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

    public static PaginationInfo getPaginationInfo(HttpServletRequest request) {
        try {
            int page = Integer.parseInt(request.getParameter("pageNumber"));
            int count = Integer.parseInt(request.getParameter("pageSize"));
            return new PaginationInfo(page, count);

        } catch (NumberFormatException e) {
            return PaginationInfo.DEFAULT;
        }
    }

}
