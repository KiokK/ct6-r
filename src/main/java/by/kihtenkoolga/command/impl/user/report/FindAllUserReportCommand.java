package by.kihtenkoolga.command.impl.user.report;

import by.kihtenkoolga.command.Command;
import by.kihtenkoolga.dto.UserDto;
import by.kihtenkoolga.util.builder.impl.UserDtoReportBuilder;
import by.kihtenkoolga.util.factory.UtilWriter;
import by.kihtenkoolga.util.factory.impl.PdfUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.List;

import static by.kihtenkoolga.command.impl.user.FindAllUserCommand.findAllWithPagination;
import static by.kihtenkoolga.util.FilesUtil.RESOURCES;

@Slf4j
public class FindAllUserReportCommand implements Command {

    private final UtilWriter pdfUtil = new PdfUtil();
    private final UserDtoReportBuilder reportBuilder = new UserDtoReportBuilder();
    private static final String ANS_PDF = "/pdf/user-list.pdf";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        List<UserDto> userDtoList = findAllWithPagination(request);
        log.info(String.format("Find all users:\n %s", userDtoList));

        String reportUsers = reportBuilder.withHead("Users report")
                .withBody(userDtoList)
                .build();

        try (OutputStream os = response.getOutputStream()) {
            File file = new File(RESOURCES + ANS_PDF);
            file.createNewFile();

            pdfUtil.writeObjectToFile(reportUsers, file);
            FileInputStream inputStream = new FileInputStream(file);
            byte[] byteArray = inputStream.readAllBytes();
            inputStream.close();

            response.setContentType(Files.probeContentType(file.toPath()));
            response.setHeader("Content-Disposition", "filename=\"user-list.pdf\"");
            response.setContentLength(byteArray.length);

            os.write(byteArray, 0, byteArray.length);
            response.setStatus(200);

        } catch (RuntimeException | IOException e) {
            throw new RuntimeException(e);
        }
    }

}
