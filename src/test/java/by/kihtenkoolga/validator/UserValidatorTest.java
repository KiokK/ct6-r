package by.kihtenkoolga.validator;

import by.kihtenkoolga.dto.UserDto;
import by.kihtenkoolga.exception.NullEntityIdException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import javax.validation.ValidationException;
import java.util.stream.Stream;

import static by.kihtenkoolga.util.Constants.EMPTY_STRING;
import static by.kihtenkoolga.util.Constants.PHONE_INCORRECT;
import static by.kihtenkoolga.util.UserDtoTestData.builderWithDefaultCorrectFields;
import static by.kihtenkoolga.util.UserDtoTestData.getEmptyUserDto;
import static by.kihtenkoolga.util.UserDtoTestData.getUserDtoIvan;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.assertj.core.api.Assertions.assertThat;

class UserValidatorTest {

    @Nested
    class IsUserDtoValid {
        @Test
        void isUserDtoValidCorrectDtoReturnTrue() {
            //given
            UserDto correctDto = getUserDtoIvan();

            //when //then
            assertThat(UserValidator.isUserDtoValid(correctDto))
                    .isTrue();
        }

        @Test
        void isUserDtoValidThrowsNullEntityIdException() {
            assertThrows(NullEntityIdException.class,
                    () -> UserValidator.isUserDtoValid(getEmptyUserDto()));
        }

        @Test
        void isUserDtoValidThrowsNullPointerException() {
            assertThrows(NullPointerException.class,
                    () -> UserValidator.isUserDtoValid(null));
        }

        @ParameterizedTest
        @MethodSource("argsForValidationExceptionTesting")
        void isUserDtoValidThrowsValidationException(UserDto argDto) {
            assertThrows(ValidationException.class,
                    () -> UserValidator.isUserDtoValid(argDto));
        }

        static Stream<UserDto> argsForValidationExceptionTesting() {
            return Stream.of(
                    builderWithDefaultCorrectFields()
                            .surname(EMPTY_STRING).build(),
                    builderWithDefaultCorrectFields()
                            .name(EMPTY_STRING).build(),
                    builderWithDefaultCorrectFields()
                            .name(null).build(),
                    builderWithDefaultCorrectFields()
                            .phone(PHONE_INCORRECT).build()
            );
        }
    }
}
