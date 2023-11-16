package by.kihtenkoolga.cache;

import by.kihtenkoolga.cache.handler.AlgorithmCacheHandler;
import by.kihtenkoolga.cache.handler.LFUCacheHandler;
import by.kihtenkoolga.cache.handler.LRUCacheHandler;
import by.kihtenkoolga.util.property.YamlApplicationProperties;

import java.util.Map;

import static by.kihtenkoolga.cache.TypeOfHandler.LFU;
import static by.kihtenkoolga.util.property.PropertiesConstant.CACHE_ALGORITHM_TYPE;
import static by.kihtenkoolga.util.property.PropertiesConstant.CACHE_CAPACITY;
import static by.kihtenkoolga.util.property.PropertiesConstant.CACHE_PROPERTY;

public class CacheHandler {

    public static final AlgorithmCacheHandler<Object, Object> cacheHandler;

    static {
        Map<String, Object> cacheProperties = new YamlApplicationProperties().getPropertiesByKey(CACHE_PROPERTY);
        String typeOfHandler = (String) cacheProperties.get(CACHE_ALGORITHM_TYPE);
        int size = (int) cacheProperties.get(CACHE_CAPACITY);

        if (LFU.equals(TypeOfHandler.valueOf(typeOfHandler))) {
            cacheHandler = new LFUCacheHandler<>(size);
        } else {
            cacheHandler = new LRUCacheHandler<>(size);
        }
    }

}
