package by.kihtenkoolga.util.factory.impl;

import by.kihtenkoolga.exception.ReaderException;
import by.kihtenkoolga.exception.WriterException;
import by.kihtenkoolga.util.factory.UtilReader;
import by.kihtenkoolga.util.factory.UtilWriter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@Component
public class JsonUtil implements UtilReader, UtilWriter {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Object readObjectFromFile(Class<?> clazz, File fileInput) {
        try {
            return objectMapper.readValue(fileInput, clazz);
        } catch (IOException e) {
            throw new ReaderException();
        }
    }

    @Override
    public void writeObjectToFile(Object object, File fileOutput) {
        try {
            objectMapper.writeValue(fileOutput, object);
        } catch (IOException e) {
            throw new WriterException();
        }
    }
}
