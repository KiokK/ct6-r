package by.kihtenkoolga.cache;

import by.kihtenkoolga.cache.handler.AlgorithmCacheHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Фабрика возвращающая инициализированный обработчик кэша
 */
@Component
public class CacheFactory {

    /**
     * имя поля, по которому будут кэшироваться объекты
     */
    private static String ID;

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

    @Autowired
    public CacheFactory(AlgorithmCacheHandler<Object, Object> cacheHandler,
                        @Value("${cache.cache-field:id}") String id) {
        ID = id;
        this.cacheHandler = cacheHandler;
    }

    /**
     * @return имя поля, по которому будут кэшироваться объекты
     */
    public static String getIdFieldName() {
        return ID;
    }

}
