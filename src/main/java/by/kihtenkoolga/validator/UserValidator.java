package by.kihtenkoolga.validator;

import by.kihtenkoolga.dto.UserDto;
import by.kihtenkoolga.exception.NullEntityIdException;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;
import java.util.UUID;

public class UserValidator {

    public static final String PHONE_REGEXP = "^\\s*\\+?375(25|29|33|34)(\\d{7})";
    private static final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    private static final Validator validator = validatorFactory.getValidator();

    public static boolean isUserDtoValid(UserDto userDto) {
        isUserIdValid(userDto.id);
        isUserDtoValidWithoutId(userDto);

        return true;
    }

    public static boolean isUserDtoValidWithoutId(UserDto userDto) {
        Set<ConstraintViolation<UserDto>> violations = validator.validate(userDto);
        if (!validator.validate(userDto).isEmpty()) {
            throw new ValidationException(
                    violations.stream()
                            .findAny()
                            .orElseThrow()
                            .getMessage());
        }

        return true;
    }

    public static boolean isUserIdValid(UUID id) {
        if (id == null) {
            throw new NullEntityIdException();
        }

        return true;
    }

}
