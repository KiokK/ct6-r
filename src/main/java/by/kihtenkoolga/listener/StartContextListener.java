package by.kihtenkoolga.listener;

import by.kihtenkoolga.cache.CacheFactory;
import by.kihtenkoolga.cache.TypeOfCacheAlgorithm;
import by.kihtenkoolga.cache.handler.impl.LFUCacheHandler;
import by.kihtenkoolga.cache.handler.impl.LRUCacheHandler;
import by.kihtenkoolga.config.DataSource;
import by.kihtenkoolga.util.property.YamlApplicationProperties;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

import static by.kihtenkoolga.cache.TypeOfCacheAlgorithm.LFU;
import static by.kihtenkoolga.util.property.PropertiesConstant.CACHE_ALGORITHM_TYPE;
import static by.kihtenkoolga.util.property.PropertiesConstant.CACHE_CAPACITY;
import static by.kihtenkoolga.util.property.PropertiesConstant.CACHE_PROPERTY_GROUP;
import static by.kihtenkoolga.util.property.PropertiesConstant.DB_INIT;
import static by.kihtenkoolga.util.property.PropertiesConstant.DB_PROPERTY_GROUP;

@WebListener
public class StartContextListener implements ServletContextListener {
    public static YamlApplicationProperties app;
    public static CacheFactory cacheFactory;
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContextListener.super.contextInitialized(sce);
        app = new YamlApplicationProperties();
        Boolean isInitDb = (Boolean) app.getPropertiesByKey(DB_PROPERTY_GROUP).get(DB_INIT);
        if (isInitDb) {
            try (Connection connection = DataSource.getConnection()) {
                String script = readSQLScript(this.getClass().getResource("/schema.sql").getPath());
                executeScript(connection, script);
                script = readSQLScript(this.getClass().getResource("/data.sql").getPath());
                executeScript(connection, script);
            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            }
        }

        Map<String, Object> cacheProperties = app.getPropertiesByKey(CACHE_PROPERTY_GROUP);
        String typeOfHandler = (String) cacheProperties.get(CACHE_ALGORITHM_TYPE);
        int size = (int) cacheProperties.get(CACHE_CAPACITY);

        if (LFU.equals(TypeOfCacheAlgorithm.valueOf(typeOfHandler))) {
            cacheFactory = new CacheFactory(new LFUCacheHandler<>(size));
        } else {
            cacheFactory = new CacheFactory(new LRUCacheHandler<>(size));
        }
    }

    public static String readSQLScript(String filePath) throws IOException {
        StringBuilder script = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                script.append(line).append("\n");
            }
        }
        return script.toString();
    }

    public static void executeScript(Connection connection, String script) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            String[] sqlCommands = script.split(";");
            for (String sqlCommand : sqlCommands) {
                if (!sqlCommand.trim().isEmpty()) {
                    statement.addBatch(sqlCommand);
                }
            }
            statement.executeBatch();
        }
    }
}
