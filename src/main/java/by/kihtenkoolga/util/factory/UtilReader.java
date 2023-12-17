package by.kihtenkoolga.util.factory;

import java.io.File;

public interface UtilReader {

    Object readObjectFromFile(Class<?> clazz, File fileInput);

}
