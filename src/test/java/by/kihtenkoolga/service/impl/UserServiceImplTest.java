package by.kihtenkoolga.service.impl;

import by.kihtenkoolga.cache.CacheHandler;
import by.kihtenkoolga.dao.UserDAO;
import by.kihtenkoolga.dto.UserDto;
import by.kihtenkoolga.exception.NullEntityIdException;
import by.kihtenkoolga.exception.EntityNotFoundException;
import by.kihtenkoolga.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.ValidationException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static by.kihtenkoolga.util.Constants.NAME_EVGENI;
import static by.kihtenkoolga.util.Constants.UUID_IVAN;
import static by.kihtenkoolga.util.Constants.UUID_NO_REAL;
import static by.kihtenkoolga.util.UserDtoTestData.getEmptyUserDto;
import static by.kihtenkoolga.util.UserDtoTestData.getUserDtoEvgeni;
import static by.kihtenkoolga.util.UserDtoTestData.getUserDtoIvan;
import static by.kihtenkoolga.util.UserDtoTestData.getUserDtoMarina;
import static by.kihtenkoolga.util.UserDtoTestData.getUserEvgeni;
import static by.kihtenkoolga.util.UserDtoTestData.getUserIvan;
import static by.kihtenkoolga.util.UserDtoTestData.getUserMarina;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserDAO userDAO;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        CacheHandler.cacheHandler.clean();
    }

    @Nested
    class Create {

        @Test
        void createShouldThrowException() {
            assertAll(
                    () ->
                            assertThrows(ValidationException.class, () -> userService.create(getEmptyUserDto())),
                    () ->
                            assertThrows(IllegalArgumentException.class, () -> userService.create(null))
            );
        }

        @Test
        void createShouldReturnNewDtoFieldsEqualsCreatedEntity() {
            //given
            User newUser = getUserIvan();
            newUser.setId(null);
            UserDto newDto = getUserDtoIvan();
            newDto.id = null;
            User expectedUser = getUserIvan();
            UserDto expectedDto = getUserDtoIvan();

            //when
            doReturn(expectedUser)
                    .when(userDAO).create(newUser);
            UserDto actualDto = userService.create(newDto);

            //then
            assertThat(actualDto)
                    .isEqualTo(expectedDto);
        }

    }

    @Nested
    class FindById {

        @Test
        void findByIdNullShouldThrowNullEntityIdException() {
            assertThrows(
                    NullEntityIdException.class, () -> userService.findById(null));
        }

        @Test
        void findByIdNoExistsIdShouldReturnEmpty() {
            //when
            doReturn(Optional.empty())
                    .when(userDAO).findById(UUID_NO_REAL);
            Optional<UserDto> actual = userService.findById(UUID_NO_REAL);

            //then
            assertThat(actual)
                    .isEmpty();
        }

        @Test
        void findByIdShouldReturnOptionalUserDto() {
            //given
            UUID testId = UUID_IVAN;
            Optional<UserDto> expected = Optional.of(getUserDtoIvan());
            Optional<User> expectedFromDao = Optional.of(getUserIvan());

            //when
            doReturn(expectedFromDao)
                    .when(userDAO).findById(testId);
            Optional<UserDto> actual = userService.findById(testId);

            //then
            assertThat(actual)
                    .isEqualTo(expected);
        }

    }

    @Nested
    class FindAll {

        @Test
        void findAll() {
            //given
            List<User> expectedDao = List.of(getUserIvan(), getUserMarina(), getUserEvgeni());
            List<UserDto> expected = List.of(getUserDtoIvan(), getUserDtoMarina(), getUserDtoEvgeni());

            //when
            doReturn(expectedDao)
                    .when(userDAO).findAll();
            List<UserDto> actual = userService.findAll();

            //then
            assertThat(actual)
                    .isEqualTo(expected);
        }

    }

    @Nested
    class Update {

        @Test
        void updateShouldReturnDtoUpdatedUser() {
            //given
            User updatedUser = getUserIvan();
            updatedUser.setName(NAME_EVGENI);
            UserDto expected = getUserDtoIvan();
            expected.name = NAME_EVGENI;

            //when
            doReturn(true)
                    .when(userDAO).update(updatedUser);
            UserDto actual = userService.update(expected);

            //then
            assertThat(actual)
                    .isEqualTo(expected);
        }

        @Test
        void updateNoExistsUserShouldThrowValidationException() {
            //given
            UserDto updatedUser = getUserDtoIvan();
            updatedUser.id = UUID_NO_REAL;
            updatedUser.name = NAME_EVGENI;

            //when//then
            assertThrows(EntityNotFoundException.class,
                    () -> userService.update(updatedUser));
        }

    }

    @Nested
    class DeleteById {

        @Test
        void deleteByIdVerify() {
            //when
            userService.deleteById(UUID_NO_REAL);

            //then
            verify(userDAO).deleteById(UUID_NO_REAL);
        }

        @Test
        void deleteByIdShouldThrowNullEntityIdException() {
            //then
            assertAll(
                    () -> assertThrows(NullEntityIdException.class, () -> userService.deleteById(null)),
                    () -> verify(userDAO, times(0)).deleteById(UUID_NO_REAL)
            );
        }

    }

}