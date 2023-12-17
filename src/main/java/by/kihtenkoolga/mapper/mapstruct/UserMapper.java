package by.kihtenkoolga.mapper.mapstruct;

import by.kihtenkoolga.dto.UserDto;
import by.kihtenkoolga.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface UserMapper {

    @Mapping(target = "createdAt", ignore = true)
    User toUser(UserDto userDto);

    UserDto toUserDto(User user);

    @Mapping(target = "id",source = "productDto.id")
    @Mapping(target = "name",source = "productDto.name")
    @Mapping(target = "surname",source = "productDto.surname")
    @Mapping(target = "phone",source = "productDto.phone")
    User merge(User user, UserDto productDto);

    List<UserDto> toListUserDto(List<User> users);

}
