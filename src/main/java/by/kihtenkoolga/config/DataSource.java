package by.kihtenkoolga.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import static by.kihtenkoolga.listener.InitContextListener.app;
import static by.kihtenkoolga.util.property.PropertiesConstant.DB_DRIVER_CLASS_NAME;
import static by.kihtenkoolga.util.property.PropertiesConstant.DB_PASSWORD;
import static by.kihtenkoolga.util.property.PropertiesConstant.DB_PROPERTY_GROUP;
import static by.kihtenkoolga.util.property.PropertiesConstant.DB_URL;
import static by.kihtenkoolga.util.property.PropertiesConstant.DB_USERNAME;

/**
 * Класс предоставляющий соединение к БД
 */
public class DataSource {

    private static final HikariConfig config = new HikariConfig();
    private static final HikariDataSource ds;

    static {
        Map<String, Object> dbProperties = app.getPropertiesByKey(DB_PROPERTY_GROUP);

        try {
            String driverClassname = (String) dbProperties.get(DB_DRIVER_CLASS_NAME);
            Class.forName(driverClassname).getDeclaredConstructor().newInstance();

        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        String url = (String) dbProperties.get(DB_URL);
        String username = (String) dbProperties.get(DB_USERNAME);
        String password = (String) dbProperties.get(DB_PASSWORD);

        config.setJdbcUrl(url);
        config.setUsername(username);
        config.setPassword(password);
        config.setMaximumPoolSize(5);

        ds = new HikariDataSource(config);
    }

    public static Connection getConnection() {
        try {
            return ds.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
