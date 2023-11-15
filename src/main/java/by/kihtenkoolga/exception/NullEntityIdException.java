package by.kihtenkoolga.exception;

public class NullEntityIdException extends RuntimeException{

    public NullEntityIdException() {
        super("Can't find entity by id == null");
    }

}
