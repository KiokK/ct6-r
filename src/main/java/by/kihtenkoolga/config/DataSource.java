package by.kihtenkoolga.config;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DataSource {
    private static BasicDataSource ds = new BasicDataSource();

    static {
        String DB_DRIVER_CLASSNAME = "org.postgresql.Driver";
        String URL = "jdbc:postgresql://localhost:3254/refl-task";
        String USERNAME = "postgres";
        String PASSWORD = "postgres";
        Integer maximumPoolSize = 20;

        ds.setDriverClassName(DB_DRIVER_CLASSNAME);
        ds.setUrl(URL);
        ds.setUsername(USERNAME);
        ds.setPassword(PASSWORD);
        ds.setMinIdle(5);
        ds.setMaxIdle(maximumPoolSize);
        ds.setInitialSize(5);
        ds.setMaxOpenPreparedStatements(100);
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
