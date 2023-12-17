package by.kihtenkoolga.util.builder.impl;

import by.kihtenkoolga.dto.UserDto;
import by.kihtenkoolga.util.builder.ObjectReportBuilder;

public class UserDtoReportBuilder extends ObjectReportBuilder<UserDtoReportBuilder, UserDto> {

    /**
     * @param userDto объект из которого получается информация
     * @return строка содержащая информацию о dto
     */
    @Override
    public String toStringPerformance(UserDto userDto) {
        return userDto.name +
                ' ' +
                userDto.surname +
                " - phone: " +
                userDto.phone +
                "\n";
    }

}
