package by.kihtenkoolga.command.impl.user;

import by.kihtenkoolga.command.Command;
import by.kihtenkoolga.dto.UserDto;
import by.kihtenkoolga.service.UserService;
import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static by.kihtenkoolga.util.RequestUtil.getRequestBody;
import static by.kihtenkoolga.util.ResponseUtil.setResponseJsonData;

@Slf4j
@Component
@RequiredArgsConstructor
public class CreateUserCommand implements Command {

    private final UserService userService;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        UserDto userDto = new Gson().fromJson(getRequestBody(request), UserDto.class);
        userDto = userService.create(userDto);
        log.info(String.format("Create: %s", userDto.toString()));

        setResponseJsonData(response, userDto, userDto.getClass(), 201);
    }

}
