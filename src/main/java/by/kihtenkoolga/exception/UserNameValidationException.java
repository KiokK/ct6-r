package by.kihtenkoolga.exception;

public class UserNameValidationException extends RuntimeException{

    public UserNameValidationException(String name) {
        super(String.format("User with has invalid name: '%s'", name));
    }
}
