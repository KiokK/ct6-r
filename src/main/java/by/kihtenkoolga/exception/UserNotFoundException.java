package by.kihtenkoolga.exception;

import java.util.UUID;

public class UserNotFoundException extends RuntimeException {

    /**
     * @param uuid идентификатор {@code User}
     */
    public UserNotFoundException(UUID uuid) {
        super(String.format("User with uuid: %s not found", uuid));
    }

}
