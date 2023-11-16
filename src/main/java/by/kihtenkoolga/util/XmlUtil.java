package by.kihtenkoolga.util;

import by.kihtenkoolga.dto.UserDto;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class XmlUtil {

    private static final String PATH = "src/main/resources/xml/";
    private static File USER_DTO_IVAN = new File(PATH+"user-dto-ivan.xml");
    private static File USER_DTO_MARINA = new File(PATH+"user-dto-marina.xml");

    public static UserDto getIvanDto() {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(UserDto.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            return (UserDto) unmarshaller.unmarshal(USER_DTO_IVAN);
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }

    public static UserDto getMarinaDto() {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(UserDto.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            return (UserDto) unmarshaller.unmarshal(USER_DTO_MARINA);
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Выводит в консоль dto в формате xml
     * @param userDto dto, которое будет выведено
     */
    public static void xmlFromDtoWriteToConsole(UserDto userDto) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(UserDto.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            marshaller.marshal(userDto, System.out);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

}
