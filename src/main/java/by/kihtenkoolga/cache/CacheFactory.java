package by.kihtenkoolga.cache;

import by.kihtenkoolga.cache.handler.AlgorithmCacheHandler;
import by.kihtenkoolga.cache.handler.LFUCacheHandler;
import by.kihtenkoolga.cache.handler.LRUCacheHandler;
import by.kihtenkoolga.util.property.YamlApplicationProperties;

import java.util.Map;

import static by.kihtenkoolga.cache.TypeOfHandler.LFU;
import static by.kihtenkoolga.util.property.PropertiesConstant.CACHE_ALGORITHM_TYPE;
import static by.kihtenkoolga.util.property.PropertiesConstant.CACHE_CAPACITY;
import static by.kihtenkoolga.util.property.PropertiesConstant.CACHE_ID_FIELD_NAME;
import static by.kihtenkoolga.util.property.PropertiesConstant.CACHE_PROPERTY;

public class CacheFactory {

    private static final AlgorithmCacheHandler<Object, Object> cacheHandler;

    private static final String ID;

    public static AlgorithmCacheHandler<Object, Object> getCacheHandler() {
        return cacheHandler;
    }

    public static String getIdFieldName() {
        return ID;
    }

    static {
        Map<String, Object> cacheProperties = new YamlApplicationProperties().getPropertiesByKey(CACHE_PROPERTY);
        String typeOfHandler = (String) cacheProperties.get(CACHE_ALGORITHM_TYPE);
        int size = (int) cacheProperties.get(CACHE_CAPACITY);
        ID = (String) cacheProperties.get(CACHE_ID_FIELD_NAME);

        if (LFU.equals(TypeOfHandler.valueOf(typeOfHandler))) {
            cacheHandler = new LFUCacheHandler<>(size);
        } else {
            cacheHandler = new LRUCacheHandler<>(size);
        }
    }

}
