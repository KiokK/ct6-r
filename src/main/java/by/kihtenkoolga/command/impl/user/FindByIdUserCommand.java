package by.kihtenkoolga.command.impl.user;

import by.kihtenkoolga.command.Command;
import by.kihtenkoolga.dao.UserDAOImpl;
import by.kihtenkoolga.dto.UserDto;
import by.kihtenkoolga.service.UserService;
import by.kihtenkoolga.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;
import java.util.UUID;

import static by.kihtenkoolga.util.ResponseUtil.setResponseJsonData;
import static by.kihtenkoolga.constants.EntityConstants.ID;

@Slf4j
public class FindByIdUserCommand implements Command {

    private final UserService userService = new UserServiceImpl(new UserDAOImpl());

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        UUID findId;
        try {
            findId = UUID.fromString(request.getParameter(ID));
        } catch (NumberFormatException e) {
            response.setStatus(404);
            return;
        }

        Optional<UserDto> foundUser = userService.findById(findId);
        log.info(String.format("Found user: %s", foundUser));

        if (foundUser.isPresent()) {
            setResponseJsonData(response, foundUser.get(), UserDto.class, 200);
        } else {
            response.setStatus(404);
        }
    }

}
