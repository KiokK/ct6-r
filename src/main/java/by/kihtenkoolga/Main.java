package by.kihtenkoolga;

import by.kihtenkoolga.cache.CacheFactory;
import by.kihtenkoolga.dao.UserDAOImpl;
import by.kihtenkoolga.dto.UserDto;
import by.kihtenkoolga.service.UserService;
import by.kihtenkoolga.service.impl.UserServiceImpl;
import by.kihtenkoolga.util.builder.impl.UserDtoReportBuilder;
import by.kihtenkoolga.util.factory.UtilWriter;
import by.kihtenkoolga.util.factory.impl.JsonUtil;
import by.kihtenkoolga.util.factory.impl.PdfUtil;
import by.kihtenkoolga.util.factory.impl.XmlUtil;

import java.io.File;
import java.util.List;


public class Main {

    private static UserService userService = new UserServiceImpl(new UserDAOImpl());
    private static XmlUtil xmlUtil = new XmlUtil();
    private static JsonUtil jsonUtil = new JsonUtil();
    private static UtilWriter pdfUtil = new PdfUtil();


    private static final String PATH = "src/main/resources/";
    public static final File USER_DTO_IVAN_XML = new File(PATH + "xml/user-dto-ivan.xml");
    public static final File USER_DTO_MARINA_XML = new File(PATH + "xml/user-dto-marina.xml");

    private static final File USER_DTO_IVAN_JSON = new File(PATH + "json/user-dto-ivan.json");
    private static final File USER_DTO_MARINA_JSON = new File(PATH + "json/user-dto-marina.json");

    public static void main(String[] args) {
        UserDto userIvan = (UserDto) jsonUtil.readObjectFromFile(UserDto.class, USER_DTO_IVAN_JSON);
        UserDto userMarina = (UserDto) xmlUtil.readObjectFromFile(UserDto.class, USER_DTO_MARINA_XML);

//        //Вывести в консоль xml представление dto
//        writeXmlToConsole(userIvan);

        //создать сущность и найти ее в бд
        //в DAO сущности присваивается UUID random
        // userIvan = userService.create(userIvan);
        // Optional<UserDto> foundUser = userService.findById(userIvan.id);
        //  System.out.println(foundUser);
        UserDtoReportBuilder reportBuilder = new UserDtoReportBuilder();
        String reportUsers = reportBuilder.withHead("Users report")
                .withBody(List.of(userIvan, userMarina))
                .build();
        String reportUser = reportBuilder.withHead("Report about one user")
                .withBody(userIvan)
                .build();
        pdfUtil.writeObjectToFile(reportUsers, new File(PATH + "pdf/user-dto-users-list.pdf"));
        pdfUtil.writeObjectToFile(reportUser, new File(PATH + "pdf/user-dto-ivan-ans.pdf"));
//        //обновить сущность и найи ее
//        userIvan.phone = "+375259517645";
//        userService.update(userIvan);
//        System.out.println(userService.findById(userIvan.id));

        //Чтобы получить все данные из кэша
        System.out.println("\nAll cache data: ");
        System.out.println(CacheFactory.getCacheHandler().getCopyOfCacheData());

//        userService.findAll();
//        userService.deleteById(userIvan.id);
    }

}
