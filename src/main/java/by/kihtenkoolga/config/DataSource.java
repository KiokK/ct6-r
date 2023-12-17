package by.kihtenkoolga.config;

import by.kihtenkoolga.util.property.YamlApplicationProperties;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

import static by.kihtenkoolga.util.property.PropertiesConstant.DB_DRIVER_CLASS_NAME;
import static by.kihtenkoolga.util.property.PropertiesConstant.DB_PASSWORD;
import static by.kihtenkoolga.util.property.PropertiesConstant.DB_PROPERTY_GROUP;
import static by.kihtenkoolga.util.property.PropertiesConstant.DB_URL;
import static by.kihtenkoolga.util.property.PropertiesConstant.DB_USERNAME;

/**
 * Класс предоставляющий соединение к БД
 */
public class DataSource {

    private static Map<String, Object> dbProperties;

    static {
        dbProperties = new YamlApplicationProperties().getPropertiesByKey(DB_PROPERTY_GROUP);
    }

    private static final String DRIVER_CLASSNAME = (String) dbProperties.get(DB_DRIVER_CLASS_NAME);
    private static final String URL = (String) dbProperties.get(DB_URL);
    private static final String USERNAME = (String) dbProperties.get(DB_USERNAME);
    private static final String PASSWORD = (String) dbProperties.get(DB_PASSWORD);

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
