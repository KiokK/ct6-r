package by.kihtenkoolga.command;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static by.kihtenkoolga.listener.InitContextListener.commandHelper;

public interface Command {

    String COMMAND_NAME = "command";
    String MESSAGE = "message";

    void execute(HttpServletRequest request, HttpServletResponse response);

    static void defineAndExecuteCommand(HttpServletRequest request, HttpServletResponse response) {
        String commandName = request.getParameter(COMMAND_NAME);
        Command command = commandHelper.getCommand(commandName);

        if (command == null) {
            response.setStatus(404);
            return;
        }

        command.execute(request, response);
    }

}
