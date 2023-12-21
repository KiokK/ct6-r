package by.kihtenkoolga.command.impl;

import by.kihtenkoolga.command.Command;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UnknownCommand implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute(MESSAGE, "Unknown command!");
        log.info("Unknown command found!");
        response.setStatus(404);
    }

}
