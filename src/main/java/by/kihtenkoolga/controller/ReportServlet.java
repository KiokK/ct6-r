package by.kihtenkoolga.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

import static by.kihtenkoolga.command.Command.defineAndExecuteCommand;

@Slf4j
@WebServlet(value = "/users/report")
public class ReportServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("Define GET query, Users report");
        defineAndExecuteCommand(req, resp);
    }

}
