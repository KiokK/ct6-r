package by.kihtenkoolga.command;

import by.kihtenkoolga.command.impl.UnknownCommand;
import by.kihtenkoolga.command.impl.user.CreateUserCommand;
import by.kihtenkoolga.command.impl.user.DeleteByIdUserCommand;
import by.kihtenkoolga.command.impl.user.FindAllUserCommand;
import by.kihtenkoolga.command.impl.user.FindByIdUserCommand;
import by.kihtenkoolga.command.impl.user.UpdateUserCommand;
import by.kihtenkoolga.command.impl.user.report.FindAllUserReportCommand;
import by.kihtenkoolga.service.UserService;
import by.kihtenkoolga.util.builder.impl.UserDtoReportBuilder;
import by.kihtenkoolga.util.factory.impl.PdfUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CommandHelper {

    private Map<CommandName, Command> commands = new HashMap<>();

    @Autowired
    public CommandHelper(UserService userService, UserDtoReportBuilder userDtoReportBuilder, PdfUtil pdfUtil) {
        commands.put(CommandName.USERS_CREATE, new CreateUserCommand(userService));
        commands.put(CommandName.USERS_UPDATE, new UpdateUserCommand(userService));
        commands.put(CommandName.USERS_FIND_BY_ID, new FindByIdUserCommand(userService));
        commands.put(CommandName.USERS_FIND_ALL, new FindAllUserCommand(userService));
        commands.put(CommandName.USERS_DELETE_BY_ID, new DeleteByIdUserCommand(userService));
        commands.put(CommandName.REPORT_USERS_FIND_ALL, new FindAllUserReportCommand(pdfUtil, userDtoReportBuilder, userService));
    }

    public Command getCommand(String commandName) {
        commandName = commandName.replace('-', '_').toUpperCase();
        CommandName key = CommandName.valueOf(commandName);
        Command command = commands.get(key);

        if (command == null) {
            command = new UnknownCommand();
        }

        return command;
    }

}
