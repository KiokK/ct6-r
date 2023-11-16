package by.kihtenkoolga.exception;

import java.util.UUID;

public class EntityNotFoundException extends RuntimeException {

    /**
     * @param uuid идентификатор {@code Entity}
     */
    public EntityNotFoundException(UUID uuid) {
        super(String.format("Entity with uuid: %s not found", uuid));
    }

}
