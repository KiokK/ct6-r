package by.kihtenkoolga.cache;

import by.kihtenkoolga.cache.handler.CacheHandler;
import by.kihtenkoolga.cache.handler.LFUCacheHandler;
import by.kihtenkoolga.cache.handler.LRUCacheHandler;
import by.kihtenkoolga.dto.UserDto;
import by.kihtenkoolga.util.property.YamlApplicationProperties;

import java.util.Map;
import java.util.UUID;

import static by.kihtenkoolga.util.property.PropertiesConstant.CACHE_ALGORITHM_TYPE;
import static by.kihtenkoolga.util.property.PropertiesConstant.CACHE_CAPACITY;
import static by.kihtenkoolga.util.property.PropertiesConstant.CACHE_PROPERTY;

public class UserCacheHandler {

    public static final CacheHandler<UUID, UserDto> cacheHandler;

    static {
        Map<String, Object> cacheProperties =  new YamlApplicationProperties().getPropertiesByKey(CACHE_PROPERTY);
        String typeOfHandler = (String) cacheProperties.get(CACHE_ALGORITHM_TYPE);
        int size = (int) cacheProperties.get(CACHE_CAPACITY);

        if (TypeOfHandler.LFU.equals(TypeOfHandler.valueOf(typeOfHandler))) {
            cacheHandler = new LFUCacheHandler<>(size);
        } else {
            cacheHandler = new LRUCacheHandler<>(size);
        }
    }

}
