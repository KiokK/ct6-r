package by.kihtenkoolga.command.impl.user;

import by.kihtenkoolga.command.Command;
import by.kihtenkoolga.dao.UserDAOImpl;
import by.kihtenkoolga.dto.UserDto;
import by.kihtenkoolga.exception.EntityNotFoundException;
import by.kihtenkoolga.service.UserService;
import by.kihtenkoolga.service.impl.UserServiceImpl;
import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import static by.kihtenkoolga.util.RequestUtil.getRequestBody;
import static by.kihtenkoolga.util.ResponseUtil.setResponseJsonData;

@Slf4j
public class UpdateUserCommand implements Command {

    private final UserService userService = new UserServiceImpl(new UserDAOImpl());

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        UserDto userDto = new Gson().fromJson(getRequestBody(request), UserDto.class);
        try {
            userDto = userService.update(userDto);
            log.info(String.format("Updated user: %s", userDto));

            setResponseJsonData(response, userDto, UserDto.class, 200);
        } catch (EntityNotFoundException e) {
            response.setStatus(404);
        }
    }

}
