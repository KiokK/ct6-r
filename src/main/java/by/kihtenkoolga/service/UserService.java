package by.kihtenkoolga.service;

import by.kihtenkoolga.dto.UserDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {

    UserDto create(UserDto userDto);

    Optional<UserDto> findById(UUID id);

    List<UserDto> findAll();

    UserDto update(UserDto userDto);

    void deleteById(UUID id);

}
