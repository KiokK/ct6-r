package by.kihtenkoolga.controller;

import by.kihtenkoolga.config.PaginationInfo;
import by.kihtenkoolga.dao.UserDAOImpl;
import by.kihtenkoolga.dto.UserDto;
import by.kihtenkoolga.service.UserService;
import by.kihtenkoolga.service.impl.UserServiceImpl;
import by.kihtenkoolga.util.builder.impl.UserDtoReportBuilder;
import by.kihtenkoolga.util.factory.UtilWriter;
import by.kihtenkoolga.util.factory.impl.PdfUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.List;

@WebServlet(value = "/users/report")
public class ReportServlet extends HttpServlet {

    private UserService userService = new UserServiceImpl(new UserDAOImpl());
    private UtilWriter pdfUtil = new PdfUtil();
    private UserDtoReportBuilder reportBuilder = new UserDtoReportBuilder();
    private static final String ANS_PDF = "/pdf/user-list.pdf";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<UserDto> userDtoList = userService.findAll(PaginationInfo.DEFAULT);

        if (userDtoList.isEmpty()) {
            resp.setStatus(404);
            return;
        }

        String reportUsers = reportBuilder.withHead("Users report")
                .withBody(userDtoList)
                .build();

        try (OutputStream os = resp.getOutputStream()) {
            File file = new File(this.getClass().getResource("/").getPath()+ANS_PDF);
            file.createNewFile();

            pdfUtil.writeObjectToFile(reportUsers, file);

            byte[] byteArray = new FileInputStream(file).readAllBytes();
            resp.setContentType(Files.probeContentType(file.toPath()));
            resp.setHeader("Content-Disposition", "filename=\"user-list.pdf\"");
            resp.setContentLength(byteArray.length);

            os.write(byteArray , 0, byteArray.length);
            resp.setStatus(200);

        } catch (RuntimeException | IOException e) {
            throw new RuntimeException(e);
        }

    }
}
