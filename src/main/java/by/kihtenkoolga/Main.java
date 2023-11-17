package by.kihtenkoolga;

import by.kihtenkoolga.cache.CacheFactory;
import by.kihtenkoolga.dao.UserDAOImpl;
import by.kihtenkoolga.dto.UserDto;
import by.kihtenkoolga.service.UserService;
import by.kihtenkoolga.service.impl.UserServiceImpl;

import java.util.Optional;

import static by.kihtenkoolga.util.JsonUtil.getIvanDtoFromJson;
import static by.kihtenkoolga.util.XmlUtil.getMarinaDtoFromXml;

public class Main {

    private static UserService userService = new UserServiceImpl(new UserDAOImpl());

    public static void main(String[] args) {
        UserDto userIvan = getIvanDtoFromJson();
        UserDto userMarina = getMarinaDtoFromXml();

//        //Вывести в консоль xml представление dto
//        writeXmlToConsole(userIvan);

        //создать сущность и найти ее в бд
        //в DAO сущности присваивается UUID random
        userIvan = userService.create(userIvan);
        Optional<UserDto> foundUser = userService.findById(userIvan.id);
        System.out.println(foundUser);

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
