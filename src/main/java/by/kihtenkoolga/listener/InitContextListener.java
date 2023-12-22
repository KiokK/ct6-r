package by.kihtenkoolga.listener;

import by.kihtenkoolga.cache.CacheFactory;
import by.kihtenkoolga.cache.TypeOfCacheAlgorithm;
import by.kihtenkoolga.cache.handler.impl.LFUCacheHandler;
import by.kihtenkoolga.cache.handler.impl.LRUCacheHandler;
import by.kihtenkoolga.command.CommandHelper;
import by.kihtenkoolga.config.DataSource;
import by.kihtenkoolga.util.property.YamlApplicationProperties;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import static by.kihtenkoolga.cache.TypeOfCacheAlgorithm.LFU;
import static by.kihtenkoolga.util.DbScriptUtil.executeScript;
import static by.kihtenkoolga.util.DbScriptUtil.readSQLScript;
import static by.kihtenkoolga.util.FilesUtil.getFileFromResources;
import static by.kihtenkoolga.util.property.PropertiesConstant.CACHE_ALGORITHM_TYPE;
import static by.kihtenkoolga.util.property.PropertiesConstant.CACHE_CAPACITY;
import static by.kihtenkoolga.util.property.PropertiesConstant.CACHE_PROPERTY_GROUP;
import static by.kihtenkoolga.util.property.PropertiesConstant.DB_INIT;
import static by.kihtenkoolga.util.property.PropertiesConstant.DB_PROPERTY_GROUP;

@WebListener
public class InitContextListener implements ServletContextListener {

    public static YamlApplicationProperties app;
    public static CacheFactory cacheFactory;
    public static final CommandHelper commandHelper = new CommandHelper();

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContextListener.super.contextInitialized(sce);
        app = new YamlApplicationProperties();
        Boolean isInitDb = (Boolean) app.getPropertiesByKey(DB_PROPERTY_GROUP).get(DB_INIT);

        if (isInitDb) {
            initAndExecuteDataBaseScripts();
        }

        initCache();
    }

    private void initAndExecuteDataBaseScripts() {
        try (Connection connection = DataSource.getConnection()) {
            String script = readSQLScript(getFileFromResources("/schema.sql"));
            executeScript(connection, script);
            script = readSQLScript(getFileFromResources("/data.sql"));
            executeScript(connection, script);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void initCache() {
        Map<String, Object> cacheProperties = app.getPropertiesByKey(CACHE_PROPERTY_GROUP);
        String typeOfHandler = (String) cacheProperties.get(CACHE_ALGORITHM_TYPE);
        int size = (int) cacheProperties.get(CACHE_CAPACITY);

        if (LFU.equals(TypeOfCacheAlgorithm.valueOf(typeOfHandler))) {
            cacheFactory = new CacheFactory(new LFUCacheHandler<>(size));
        } else {
            cacheFactory = new CacheFactory(new LRUCacheHandler<>(size));
        }
    }

}
