package by.kihtenkoolga.util;

import by.kihtenkoolga.dto.UserDto;
import by.kihtenkoolga.model.User;

import static by.kihtenkoolga.util.Constants.NAME_EVGENI;
import static by.kihtenkoolga.util.Constants.NAME_IVAN;
import static by.kihtenkoolga.util.Constants.NAME_MARINA;
import static by.kihtenkoolga.util.Constants.PHONE_EVGENI;
import static by.kihtenkoolga.util.Constants.PHONE_IVAN;
import static by.kihtenkoolga.util.Constants.PHONE_MARINA;
import static by.kihtenkoolga.util.Constants.SURNAME_EVGENI;
import static by.kihtenkoolga.util.Constants.SURNAME_IVAN;
import static by.kihtenkoolga.util.Constants.SURNAME_MARINA;
import static by.kihtenkoolga.util.Constants.UUID_EVGENI;
import static by.kihtenkoolga.util.Constants.UUID_IVAN;
import static by.kihtenkoolga.util.Constants.UUID_MARINA;

public class UserDtoTestData {

    public static UserDto getEmptyUserDto() {
        return UserDto.builder().build();
    }

    public static UserDto getUserDtoIvan() {
        return UserDto.builder()
                .id(UUID_IVAN)
                .name(NAME_IVAN)
                .surname(SURNAME_IVAN)
                .phone(PHONE_IVAN)
                .build();
    }

    public static User getUserIvan() {
        return User.builder()
                .id(UUID_IVAN)
                .name(NAME_IVAN)
                .surname(SURNAME_IVAN)
                .phone(PHONE_IVAN)
                .build();
    }


    public static UserDto getUserDtoMarina() {
        return UserDto.builder()
                .id(UUID_MARINA)
                .name(NAME_MARINA)
                .surname(SURNAME_MARINA)
                .phone(PHONE_MARINA)
                .build();
    }

    public static User getUserMarina() {
        return User.builder()
                .id(UUID_MARINA)
                .name(NAME_MARINA)
                .surname(SURNAME_MARINA)
                .phone(PHONE_MARINA)
                .build();
    }

    public static UserDto getUserDtoEvgeni() {
        return UserDto.builder()
                .id(UUID_EVGENI)
                .name(NAME_EVGENI)
                .surname(SURNAME_EVGENI)
                .phone(PHONE_EVGENI)
                .build();
    }

    public static User getUserEvgeni() {
        return User.builder()
                .id(UUID_EVGENI)
                .name(NAME_EVGENI)
                .surname(SURNAME_EVGENI)
                .phone(PHONE_EVGENI)
                .build();
    }

    public static UserDto.UserDtoBuilder builderWithDefaultCorrectFields() {
        return UserDto.builder()
                .id(UUID_EVGENI)
                .name(NAME_EVGENI)
                .surname(SURNAME_EVGENI)
                .phone(PHONE_EVGENI);
    }

}
