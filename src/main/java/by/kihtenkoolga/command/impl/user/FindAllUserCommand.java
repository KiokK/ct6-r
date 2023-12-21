package by.kihtenkoolga.command.impl.user;

import by.kihtenkoolga.command.Command;
import by.kihtenkoolga.config.PaginationInfo;
import by.kihtenkoolga.dao.UserDAOImpl;
import by.kihtenkoolga.dto.UserDto;
import by.kihtenkoolga.service.UserService;
import by.kihtenkoolga.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import static by.kihtenkoolga.util.ResponseUtil.setResponseJsonData;

@Slf4j
public class FindAllUserCommand implements Command {

    private static final UserService userService = new UserServiceImpl(new UserDAOImpl());

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        List<UserDto> userDtoList = findAllWithPagination(request);
        log.info(String.format("Find all users:\n %s", userDtoList));

        setResponseJsonData(response, userDtoList, userDtoList.getClass(), 200);
    }

    public static List<UserDto> findAllWithPagination(HttpServletRequest request) {
        PaginationInfo paginationInfo;

        try {
            int page = Integer.parseInt(request.getParameter("pageNumber"));
            int count = Integer.parseInt(request.getParameter("pageSize"));
            paginationInfo = new PaginationInfo(page, count);

        } catch (NumberFormatException e) {
            paginationInfo = PaginationInfo.DEFAULT;
        }

        log.info(String.format("Pagination with pageNumber=%s and pageSize=%s",
                paginationInfo.getPageNumber(), paginationInfo.getPageSize()));
        return userService.findAll(paginationInfo);
    }

}
