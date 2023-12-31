package by.kihtenkoolga.command.impl.user;

import by.kihtenkoolga.command.Command;
import by.kihtenkoolga.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static by.kihtenkoolga.constants.EntityConstants.ID;

@Slf4j
@Component
@RequiredArgsConstructor
public class DeleteByIdUserCommand implements Command {

    private final UserService userService;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        UUID findId;
        try {
            findId = UUID.fromString(request.getParameter(ID));

        } catch (IllegalArgumentException e) {
            log.info(String.format("Failed parse UUID (%s) for delete user by id", request.getParameter(ID)));
            response.setStatus(404);
            return;
        }

        log.info(String.format("Delete user by id: %s", findId));
        userService.deleteById(findId);
        response.setStatus(204);
    }

}
