package by.kihtenkoolga.util.factory.impl;

import by.kihtenkoolga.dto.UserDto;
import by.kihtenkoolga.exception.ReaderException;
import by.kihtenkoolga.exception.WriterException;
import by.kihtenkoolga.util.factory.UtilReader;
import by.kihtenkoolga.util.factory.UtilWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class XmlUtil implements UtilReader, UtilWriter {

    @Override
    public Object readObjectFromFile(Class<?> clazz, File fileInput) {
        if (!fileInput.exists()) {
            throw new ReaderException();
        }
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(UserDto.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            return unmarshaller.unmarshal(fileInput);
        } catch (JAXBException e) {
            throw new ReaderException();
        }
    }

    @Override
    public void writeObjectToFile(Object object, File fileOutput) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(object.getClass());
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            marshaller.marshal(object, fileOutput);
        } catch (JAXBException e) {
            throw new WriterException();
        }
    }

}
