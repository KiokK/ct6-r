package by.kihtenkoolga.cache.proxy;

import by.kihtenkoolga.cache.CacheFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import java.lang.reflect.Field;
import java.util.Optional;

import static by.kihtenkoolga.cache.CacheFactory.getIdFieldName;

/**
 * Kлаcc обработки поиска данных в кэше перед взаимодействием и(или) после взаимодействия с БД.
 * Методы используют ревлексию для получения данных к уникальному полю объекта, определенного в
 * {@link by.kihtenkoolga.cache.CacheFactory#getIdFieldName()}
 */
@Aspect
public class CacheAspect {

    /**
     * Вставка обновленных данных в кэш: обновление в dao и потом обновление в кеше
     *
     * @param joinPoint вход в метод
     * @param <K>       уникальный id кэшируемого значения
     * @param <V>       кэшируемое значение
     * @return объект из dao, который так же уже пресутствует в кэше
     * @throws Throwable ошибка доступа к id объекта по полю {@link by.kihtenkoolga.cache.CacheFactory#getIdFieldName()}
     *                   или если процесс выбросит исключение
     */
    @Around("@annotation(PutToCache)")
    public <K, V> Object putCache(ProceedingJoinPoint joinPoint) throws Throwable {
        V serviceDataResult = (V) joinPoint.proceed();
        Field idField = serviceDataResult.getClass().getDeclaredField(getIdFieldName());
        idField.setAccessible(true);
        K id = (K) idField.get(serviceDataResult);
        CacheFactory.getCacheHandler().put(id, serviceDataResult);

        return serviceDataResult;
    }

    /**
     * Находит данные в кэше, если не находит, тогда берет из dao, вставляет в кэш и возвращает
     *
     * @param joinPoint вход в метод
     * @param <K>       уникальный id кэшируемого значения
     * @param <V>       кэшируемое значение
     * @return объект из dao, который так же уже пресутствует в кэше
     * @throws Throwable если процесс выбросит исключение
     */
    @Around("@annotation(GetFromCache)")
    public <K, V> Object getCache(ProceedingJoinPoint joinPoint) throws Throwable {
        K idFromService = (K) joinPoint.getArgs()[0];
        V valueFromCache = (V) CacheFactory.getCacheHandler().get(idFromService);
        if (valueFromCache != null) {
            return Optional.of(valueFromCache);
        }
        Optional<V> serviceResult = (Optional<V>) joinPoint.proceed();
        serviceResult.ifPresent(dto -> CacheFactory.getCacheHandler().put(idFromService, dto));

        return serviceResult;
    }

    /**
     * Обновляет данные сначала в dao, а потом в кэше
     *
     * @param joinPoint вход в метод
     * @param <K>       уникальный id кэшируемого значения
     * @param <V>       кэшируемое значение
     * @return объект из dao, который так же уже пресутствует в кэше
     * @throws Throwable ошибка доступа к id объекта по полю {@link by.kihtenkoolga.cache.CacheFactory#getIdFieldName()}
     *                   или если процесс выбросит исключение
     */
    @Around("@annotation(PostFromCache)")
    public <K, V> Object postCache(ProceedingJoinPoint joinPoint) throws Throwable {
        V processDataResult = (V) joinPoint.proceed();
        Field idField = processDataResult.getClass().getDeclaredField(getIdFieldName());
        idField.setAccessible(true);
        K id = (K) idField.get(processDataResult);

        CacheFactory.getCacheHandler().put(id, processDataResult);

        return processDataResult;
    }

    /**
     * Удаляет данные сначала из dao, а потом из кэша
     *
     * @param joinPoint вход в метод
     * @param <K>       уникальный id кэшируемого значения
     * @throws Throwable если процесс выбросит исключение
     */
    @Around("@annotation(DeleteFromCache)")
    public <K> void deleteCache(ProceedingJoinPoint joinPoint) throws Throwable {
        K id = (K) joinPoint.getArgs()[0];
        joinPoint.proceed();
        CacheFactory.getCacheHandler().remove(id);
    }

}
