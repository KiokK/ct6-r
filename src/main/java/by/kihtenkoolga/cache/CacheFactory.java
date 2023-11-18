package by.kihtenkoolga.cache;

import by.kihtenkoolga.cache.handler.AlgorithmCacheHandler;
import by.kihtenkoolga.cache.handler.LFUCacheHandler;
import by.kihtenkoolga.cache.handler.LRUCacheHandler;
import by.kihtenkoolga.util.property.YamlApplicationProperties;

import java.util.Map;

import static by.kihtenkoolga.cache.TypeOfCacheAlgorithm.LFU;
import static by.kihtenkoolga.util.property.PropertiesConstant.CACHE_ALGORITHM_TYPE;
import static by.kihtenkoolga.util.property.PropertiesConstant.CACHE_CAPACITY;
import static by.kihtenkoolga.util.property.PropertiesConstant.CACHE_ID_FIELD_NAME;
import static by.kihtenkoolga.util.property.PropertiesConstant.CACHE_PROPERTY_GROUP;

/**
 * Фабрика возвращающая инициализированный обработчик кэша. Создается автоматически при запуске приложения
 */
public class CacheFactory {

    /**
     * Обработчик кэша
     */
    private static AlgorithmCacheHandler<Object, Object> cacheHandler;

    private static final String DEFAULT_ID = "id";

    /**
     * имя поля, по которому будут кэшироваться объекты
     */
    private static final String ID;

    /**
     * @return Обработчик кэша
     */
    public static AlgorithmCacheHandler<Object, Object> getCacheHandler() {
        return cacheHandler;
    }

    /**
     * @return имя поля, по которому будут кэшироваться объекты
     */
    public static String getIdFieldName() {
        return ID;
    }

    static {
        Map<String, Object> cacheProperties = new YamlApplicationProperties().getPropertiesByKey(CACHE_PROPERTY_GROUP);
        String typeOfHandler = (String) cacheProperties.get(CACHE_ALGORITHM_TYPE);
        int size = (int) cacheProperties.get(CACHE_CAPACITY);
        String idFromProperty = (String) cacheProperties.get(CACHE_ID_FIELD_NAME);
        ID = (idFromProperty == null) ? DEFAULT_ID : idFromProperty;

        if (LFU.equals(TypeOfCacheAlgorithm.valueOf(typeOfHandler))) {
            cacheHandler = new LFUCacheHandler<>(size);
        } else {
            cacheHandler = new LRUCacheHandler<>(size);
        }
    }

    public CacheFactory(AlgorithmCacheHandler<Object, Object> algorithmCacheHandler) {
        cacheHandler = algorithmCacheHandler;
    }

}
