package by.kihtenkoolga.command;

import by.kihtenkoolga.command.impl.UnknownCommand;
import by.kihtenkoolga.command.impl.user.CreateUserCommand;
import by.kihtenkoolga.command.impl.user.DeleteByIdUserCommand;
import by.kihtenkoolga.command.impl.user.FindAllUserCommand;
import by.kihtenkoolga.command.impl.user.FindByIdUserCommand;
import by.kihtenkoolga.command.impl.user.UpdateUserCommand;
import by.kihtenkoolga.command.impl.user.report.FindAllUserReportCommand;

import java.util.HashMap;
import java.util.Map;

public class CommandHelper {

    private final Map<CommandName, Command> commands = new HashMap<>();

    public CommandHelper() {
        commands.put(CommandName.USERS_CREATE, new CreateUserCommand());
        commands.put(CommandName.USERS_UPDATE, new UpdateUserCommand());
        commands.put(CommandName.USERS_FIND_BY_ID, new FindByIdUserCommand());
        commands.put(CommandName.USERS_FIND_ALL, new FindAllUserCommand());
        commands.put(CommandName.USERS_DELETE_BY_ID, new DeleteByIdUserCommand());
        commands.put(CommandName.REPORT_USERS_FIND_ALL, new FindAllUserReportCommand());
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
