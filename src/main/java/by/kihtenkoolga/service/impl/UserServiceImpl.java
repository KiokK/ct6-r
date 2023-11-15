package by.kihtenkoolga.service.impl;

import by.kihtenkoolga.cache.proxy.DeleteFromCache;
import by.kihtenkoolga.cache.proxy.GetFromCache;
import by.kihtenkoolga.cache.proxy.PostFromCache;
import by.kihtenkoolga.cache.proxy.PutToCache;
import by.kihtenkoolga.dao.UserDAO;
import by.kihtenkoolga.dto.UserDto;
import by.kihtenkoolga.exception.UserNotFoundException;
import by.kihtenkoolga.mapper.mapstruct.UserMapper;
import by.kihtenkoolga.model.User;
import by.kihtenkoolga.service.UserService;
import by.kihtenkoolga.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;
    private final UserMapper mapper = Mappers.getMapper(UserMapper.class);

    @Override
    @PutToCache
    public UserDto create(UserDto userDto) {
        User user = mapper.toUser(userDto);
        UserValidator.isUserValidWithoutId(user);
        user = userDAO.create(user);
        return mapper.toUserDto(user);
    }

    @Override
    @GetFromCache
    public Optional<UserDto> findById(UUID id) {
        UserValidator.isUserIdValid(id);
        return userDAO.findById(id)
                .map(mapper::toUserDto);
    }

    @Override
    public List<UserDto> findAll() {
        return userDAO.findAll().stream()
                .map(mapper::toUserDto)
                .toList();
    }

    @Override
    @PostFromCache
    public UserDto update(UserDto userDto) {
        return Optional.ofNullable(userDto)
                .map(mapper::toUser)
                .filter(UserValidator::isUserValid)
                .filter(userDAO::update)
                .map(mapper::toUserDto)
                .orElseThrow(() -> new UserNotFoundException(userDto.id));
    }

    @Override
    @DeleteFromCache
    public void deleteById(UUID id) {
        UserValidator.isUserIdValid(id);
        userDAO.deleteById(id);
    }

}
