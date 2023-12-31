package by.kihtenkoolga.cache;

import by.kihtenkoolga.cache.handler.AlgorithmCacheHandler;
import by.kihtenkoolga.cache.handler.impl.LFUCacheHandler;
import by.kihtenkoolga.cache.handler.impl.LRUCacheHandler;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import static by.kihtenkoolga.cache.TypeOfCacheAlgorithm.LFU;

/**
 * Фабрика возвращающая инициализированный обработчик кэша
 */
@Component
@NoArgsConstructor
public class CacheFactory {

    @Value("${cache.algorithm-type}")
    private String typeOfHandler;

    @Value("${cache.capacity}")
    private int size;

    @Value("${cache.cache-field:id}")
    private String id;

    /**
     * имя поля, по которому будут кэшироваться объекты
     */
    private static String ID = "id";

    /**
     * Обработчик кэша
     */
    private static AlgorithmCacheHandler<Object, Object> cacheHandler;

    /**
     * @return Обработчик кэша
     */
    public static AlgorithmCacheHandler<Object, Object> getCacheHandler() {
        return cacheHandler;
    }

    public CacheFactory(AlgorithmCacheHandler<Object, Object> algorithmCacheHandler) {
        cacheHandler = algorithmCacheHandler;
    }

    /**
     * @return имя поля, по которому будут кэшироваться объекты
     */
    public static String getIdFieldName() {
        return ID;
    }

    @PostConstruct
    public void initCacheFactory() {
        ID = id;
        if (LFU.equals(TypeOfCacheAlgorithm.valueOf(typeOfHandler))) {
            cacheHandler = new LFUCacheHandler<>(size);
        } else {
            cacheHandler = new LRUCacheHandler<>(size);
        }
    }

}
