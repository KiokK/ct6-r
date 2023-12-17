package by.kihtenkoolga.util.factory.impl;

import by.kihtenkoolga.dto.UserDto;
import by.kihtenkoolga.util.UserDtoTestData;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.File;

import static by.kihtenkoolga.util.Constants.PATH;
import static org.assertj.core.api.Assertions.assertThat;

class PdfUtilTest {

    private static PdfUtil pdfUtil = new PdfUtil();
    private static final String FILE_ACTUAL = PATH+"test-values/ans.pdf";

    @Nested
    class WriteObjectToFile {

        @Test
        void writeObjectToFile() {
            //given
            UserDto expected = UserDtoTestData.getUserDtoIvan();
            File file = new File(FILE_ACTUAL);

            //when
            pdfUtil.writeObjectToFile(expected, file);
            File actualFile = new File(FILE_ACTUAL);

            //then
            assertThat(actualFile).isNotEmpty();
        }

    }
}
