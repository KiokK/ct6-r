package by.kihtenkoolga.cache.proxy;

import by.kihtenkoolga.cache.CacheFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import java.lang.reflect.Field;
import java.util.Optional;

import static by.kihtenkoolga.cache.CacheFactory.getIdFieldName;

/**
 * Kлаcc обработки поиска данных в кэше перед взаимодействием с БД
 */
@Aspect
public class CacheAspect {

    @Around("@annotation(PutToCache)")
    public<K, V> Object putCache(ProceedingJoinPoint joinPoint) throws Throwable {
        V serviceDataResult = (V) joinPoint.proceed();
        Field idField = serviceDataResult.getClass().getDeclaredField(getIdFieldName());
        idField.setAccessible(true);
        K id = (K) idField.get(serviceDataResult);
        CacheFactory.getCacheHandler().put(id, serviceDataResult);

        return serviceDataResult;
    }

    @Around("@annotation(GetFromCache)")
    public<K, V> Object getCache(ProceedingJoinPoint joinPoint) throws Throwable {
        K idFromService = (K) joinPoint.getArgs()[0];
        V valueFromCache = (V) CacheFactory.getCacheHandler().get(idFromService);
        if (valueFromCache != null) {
            return Optional.of(valueFromCache);
        }
        Optional<V> serviceResult = (Optional<V>) joinPoint.proceed();
        serviceResult.ifPresent(dto -> CacheFactory.getCacheHandler().put(idFromService, dto));

        return serviceResult;
    }

    @Around("@annotation(PostFromCache)")
    public<K, V> Object postCache(ProceedingJoinPoint joinPoint) throws Throwable {
        V serviceDataResult = (V) joinPoint.proceed();
        Field idField = serviceDataResult.getClass().getDeclaredField(getIdFieldName());
        idField.setAccessible(true);
        K id = (K) idField.get(serviceDataResult);

        CacheFactory.getCacheHandler().put(id, serviceDataResult);

        return serviceDataResult;
    }

    @Around("@annotation(DeleteFromCache)")
    public<K> void deleteCache(ProceedingJoinPoint joinPoint) throws Throwable {
        K id = (K) joinPoint.getArgs()[0];
        joinPoint.proceed();
        CacheFactory.getCacheHandler().remove(id);
    }

}
