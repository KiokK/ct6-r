package by.kihtenkoolga.util.factory.impl;

import by.kihtenkoolga.dto.UserDto;
import by.kihtenkoolga.exception.ReaderException;
import by.kihtenkoolga.util.UserDtoTestData;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.File;

import static by.kihtenkoolga.util.Constants.PATH;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class XmlUtilTest {

    private static XmlUtil xmlUtil = new XmlUtil();
    private static final String FILE_ACTUAL = PATH+"test-values/ans.xml";
    public static final File USER_DTO_IVAN_XML = new File(PATH + "xml/user-dto-ivan.xml");


    @Nested
    class ReadObjectFromFile {

        @Test
        void readObjectFromFile() {
            //given
            UserDto expected = UserDtoTestData.getUserDtoIvan();

            //when
            UserDto actual = (UserDto) xmlUtil.readObjectFromFile(UserDto.class, USER_DTO_IVAN_XML);

            //then
            assertThat(actual).isEqualTo(expected);
        }

        @Test
        void readObjectFromFileShouldThrowReaderException() {
            //given
            String UNREAL_NAME = "no-exists.xml";
            File testFile = new File(UNREAL_NAME);

            //when //then
            assertThrows(ReaderException.class,
                    () -> xmlUtil.readObjectFromFile(UserDto.class, testFile));
        }

    }

    @Nested
    class WriteObjectToFile {

        @Test
        void writeObjectToFile() {
            //given
            UserDto expected = UserDtoTestData.getUserDtoIvan();
            File file = new File(FILE_ACTUAL);

            //when
            xmlUtil.writeObjectToFile(expected, file);
            File actualFile = new File(FILE_ACTUAL);

            //then
            assertThat(actualFile)
                    .isNotEmpty();
        }

    }
}
