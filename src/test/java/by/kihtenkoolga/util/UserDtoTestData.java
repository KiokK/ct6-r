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
        return new UserDto();
    }

    public static UserDto getUserDtoIvan() {
        UserDto u = new UserDto();
        u.id = UUID_IVAN;
        u.name = NAME_IVAN;
        u.surname = SURNAME_IVAN;
        u.phone = PHONE_IVAN;
        return u;
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
        UserDto u = new UserDto();
        u.id = UUID_MARINA;
        u.name = NAME_MARINA;
        u.surname = SURNAME_MARINA;
        u.phone = PHONE_MARINA;
        return u;
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
        UserDto u = new UserDto();
        u.id = UUID_EVGENI;
        u.name = NAME_EVGENI;
        u.surname = SURNAME_EVGENI;
        u.phone = PHONE_EVGENI;
        return u;
    }

    public static User getUserEvgeni() {
        return User.builder()
                .id(UUID_EVGENI)
                .name(NAME_EVGENI)
                .surname(SURNAME_EVGENI)
                .phone(PHONE_EVGENI)
                .build();
    }
}
