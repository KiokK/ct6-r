package by.kihtenkoolga.cache;

import by.kihtenkoolga.cache.handler.AlgorithmCacheHandler;

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
    private static final String ID = DEFAULT_ID;

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

    public CacheFactory(AlgorithmCacheHandler<Object, Object> algorithmCacheHandler) {
        cacheHandler = algorithmCacheHandler;
    }

}
