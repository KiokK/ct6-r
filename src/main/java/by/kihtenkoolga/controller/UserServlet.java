package by.kihtenkoolga.controller;

import by.kihtenkoolga.config.PaginationInfo;
import by.kihtenkoolga.dao.UserDAOImpl;
import by.kihtenkoolga.dto.UserDto;
import by.kihtenkoolga.service.UserService;
import by.kihtenkoolga.service.impl.UserServiceImpl;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(value = "/users")
public class UserServlet extends HttpServlet {

    private UserService userService = new UserServiceImpl(new UserDAOImpl());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PaginationInfo paginationInfo = PaginationInfo.DEFAULT;
        if (!req.getParameterMap().isEmpty()) {
            int page = Integer.parseInt(req.getParameter("pageNumber"));
            int count = Integer.parseInt(req.getParameter("pageSize"));
            paginationInfo = new PaginationInfo(page, count);
        }

        List<UserDto> userDtoList = userService.findAll(paginationInfo);
        String json = new Gson().toJson(userDtoList);

        try (PrintWriter wr = resp.getWriter()) {
            wr.write(json);
            resp.setContentType("application/json");
            resp.setStatus(200);
        }
    }

}
