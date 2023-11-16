package by.kihtenkoolga.config;

import by.kihtenkoolga.util.property.YamlApplicationProperties;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import static by.kihtenkoolga.util.property.PropertiesConstant.DB_DRIVER_CLASS_NAME;
import static by.kihtenkoolga.util.property.PropertiesConstant.DB_MAXIMUM_POOL_SIZE;
import static by.kihtenkoolga.util.property.PropertiesConstant.DB_MAX_OPEN_PREPARED_STATEMENTS;
import static by.kihtenkoolga.util.property.PropertiesConstant.DB_MIN_IDLE;
import static by.kihtenkoolga.util.property.PropertiesConstant.DB_PASSWORD;
import static by.kihtenkoolga.util.property.PropertiesConstant.DB_PROPERTY;
import static by.kihtenkoolga.util.property.PropertiesConstant.DB_URL;
import static by.kihtenkoolga.util.property.PropertiesConstant.DB_USERNAME;

public class DataSource {
    private static BasicDataSource ds = new BasicDataSource();

    static {
        Map<String, Object> dbProperties = new YamlApplicationProperties().getPropertiesByKey(DB_PROPERTY);
        String DRIVER_CLASSNAME = (String) dbProperties.get(DB_DRIVER_CLASS_NAME);
        String URL = (String) dbProperties.get(DB_URL);
        String USERNAME = (String) dbProperties.get(DB_USERNAME);
        String PASSWORD = (String) dbProperties.get(DB_PASSWORD);
        Integer maximumPoolSize = (Integer) dbProperties.get(DB_MAXIMUM_POOL_SIZE);
        Integer minIdle = (Integer) dbProperties.get(DB_MIN_IDLE);
        Integer maxOpenPreparedStatements = (Integer) dbProperties.get(DB_MAX_OPEN_PREPARED_STATEMENTS);

        ds.setDriverClassName(DRIVER_CLASSNAME);
        ds.setUrl(URL);
        ds.setUsername(USERNAME);
        ds.setPassword(PASSWORD);
        ds.setMinIdle(minIdle);
        ds.setMaxIdle(maximumPoolSize);
        ds.setInitialSize(minIdle);
        ds.setMaxOpenPreparedStatements(maxOpenPreparedStatements);
    }

    private static class DataSourceHolder {
        private static final DataSource INSTANCE = new DataSource();
    }

    public static DataSource getInstance() {
        return DataSourceHolder.INSTANCE;
    }

    public static Connection getConnection() {
        try {
            return getInstance().getBds().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public BasicDataSource getBds() {
        return ds;
    }

    public void setBds(BasicDataSource bds) {
        this.ds = bds;
    }
}
