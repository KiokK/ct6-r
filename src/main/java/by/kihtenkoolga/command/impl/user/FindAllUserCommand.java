package by.kihtenkoolga.command.impl.user;

import by.kihtenkoolga.command.Command;
import by.kihtenkoolga.config.PaginationInfo;
import by.kihtenkoolga.dto.UserDto;
import by.kihtenkoolga.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

import static by.kihtenkoolga.util.RequestUtil.getPaginationInfo;
import static by.kihtenkoolga.util.ResponseUtil.setResponseJsonData;

@Slf4j
@Component
@RequiredArgsConstructor
public class FindAllUserCommand implements Command {

    private final UserService userService;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        PaginationInfo pagination = getPaginationInfo(request);
        log.info(String.format("Pagination pageNumber=%s and pageSize=%s", pagination.getPageNumber(), pagination.getPageSize()));

        List<UserDto> userDtoList = userService.findAll(pagination);
        log.info(String.format("Find all users:\n %s", userDtoList));

        setResponseJsonData(response, userDtoList, userDtoList.getClass(), 200);
    }

}
