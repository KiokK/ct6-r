package by.kihtenkoolga.validator;

import by.kihtenkoolga.exception.NullEntityIdException;
import by.kihtenkoolga.model.User;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;
import java.util.UUID;

public class UserValidator {

    private static final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    private static final Validator validator = validatorFactory.getValidator();

    public static boolean isUserValid(User user) {
        isUserIdValid(user.getId());
        isUserValidWithoutId(user);

        return true;
    }

    public static boolean isUserValidWithoutId(User user) {
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        if (!validator.validate(user).isEmpty()) {
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
