package by.kihtenkoolga.util;

import by.kihtenkoolga.dto.UserDto;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class JsonReaderUtil {

    private static final String PATH = "src/main/resources/json/";
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final File USER_DTO_IVAN = new File(PATH+"user-dto-ivan.json");
    private static final File USER_DTO_MARINA = new File(PATH+"user-dto-marina.json");

    public static UserDto getIvanDtoFromJson() {
        try {
            return objectMapper.readValue(USER_DTO_IVAN, UserDto.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static UserDto getMarinaDtoFromJson() {
        try {
            return objectMapper.readValue(USER_DTO_MARINA, UserDto.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
