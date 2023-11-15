package by.kihtenkoolga.validator;

import by.kihtenkoolga.exception.NullEntityIdException;
import by.kihtenkoolga.model.User;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.UUID;

public class UserValidator {


    public static boolean isUserValid(User user) {
        isUserIdValid(user.getId());
        isUserValidWithoutId(user);

        return true;
    }

    public static boolean isUserValidWithoutId(User user) {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        validator.validate(user);

        return true;
    }

    public static boolean isUserIdValid(UUID id) {
        if (id == null) {
            throw new NullEntityIdException();
        }

        return true;
    }

}
