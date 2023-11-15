package by.kihtenkoolga.cache;

import by.kihtenkoolga.cache.handler.CacheHandler;
import by.kihtenkoolga.cache.handler.LFUCacheHandler;
import by.kihtenkoolga.cache.handler.LRUCacheHandler;
import by.kihtenkoolga.dto.UserDto;
import by.kihtenkoolga.util.YamlReader;

import java.util.Map;
import java.util.UUID;

public class UserCacheHandler {

    public static final CacheHandler<UUID, UserDto> cacheHandler;

    static {
        String typeOfHandler = "LFU"; //appParams.get("algorithm-type");
        int size = 13;
        if ("LFU".equals(typeOfHandler)) {
            cacheHandler = new LFUCacheHandler<>(size);
        } else {
            cacheHandler = new LRUCacheHandler<>(size);
        }
    }

}
