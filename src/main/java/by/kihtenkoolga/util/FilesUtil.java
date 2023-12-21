package by.kihtenkoolga.util;

import java.util.Objects;

public class FilesUtil {

    public static final String RESOURCES = Objects.requireNonNull(FilesUtil.class.getResource("/")).getPath();

    public static String getFileFromResources(String nameWithPath) {
        return Objects.requireNonNull(FilesUtil.class.getResource(nameWithPath)).getPath();
    }

}
