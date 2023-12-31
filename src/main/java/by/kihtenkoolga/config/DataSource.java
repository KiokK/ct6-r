package by.kihtenkoolga.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Класс предоставляющий соединение к БД
 */
@Component
public class DataSource {

    private final HikariConfig config = new HikariConfig();

    private static HikariDataSource ds;

    @Value("${datasource.driver-class-name}")
    private String driverClassname;

    @Value("${datasource.url}")
    private String url;

    @Value("${datasource.username}")
    private String username;

    @Value("${datasource.password}")
    private String password;

    @PostConstruct
    public void initDataSource(){
        try {
            Class.forName(driverClassname).getDeclaredConstructor().newInstance();

        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

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
