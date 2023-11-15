package by.kihtenkoolga.cache.proxy;

import by.kihtenkoolga.cache.UserCacheHandler;
import by.kihtenkoolga.dto.UserDto;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import java.lang.reflect.Field;
import java.util.Optional;
import java.util.UUID;

import static by.kihtenkoolga.constants.EntityConstants.ID;

/**
 * Kлаcc обработки поиска данных в кэше перед взаимодействием с БД
 */
@Aspect
public class CacheAspect {

    @Around("@annotation(PutToCache)")
    public Object putCache(ProceedingJoinPoint joinPoint) throws Throwable {
        UserDto serviceDataResult = (UserDto) joinPoint.proceed();
        Field idField = serviceDataResult.getClass().getDeclaredField(ID);
        idField.setAccessible(true);
        UUID id = (UUID) idField.get(serviceDataResult);
        UserCacheHandler.cacheHandler.put(id, serviceDataResult);

        return serviceDataResult;
    }

    /**
     * Работает
     */
    @Around("@annotation(GetFromCache)")
    public Object getCache(ProceedingJoinPoint joinPoint) throws Throwable {
        UUID idFromService = (UUID) joinPoint.getArgs()[0];
        UserDto userFromCache = UserCacheHandler.cacheHandler.get(idFromService);
        if (userFromCache != null) {
            return Optional.of(userFromCache);
        }
        Optional<UserDto> serviceResult = (Optional<UserDto>) joinPoint.proceed();
        serviceResult.ifPresent(dto -> UserCacheHandler.cacheHandler.put(idFromService, dto));

        return serviceResult;
    }

    @Around("@annotation(PostFromCache)")
    public Object postCache(ProceedingJoinPoint joinPoint) throws Throwable {
        UserDto serviceDataResult = (UserDto) joinPoint.proceed();
        Field idField = serviceDataResult.getClass().getDeclaredField(ID);
        idField.setAccessible(true);
        UUID id = (UUID) idField.get(serviceDataResult);

        UserCacheHandler.cacheHandler.put(id, serviceDataResult);

        return serviceDataResult;
    }

    @Around("@annotation(DeleteFromCache)")
    public void deleteCache(ProceedingJoinPoint joinPoint) throws Throwable {
        UUID id = (UUID) joinPoint.getArgs()[0];
        joinPoint.proceed();
        UserCacheHandler.cacheHandler.remove(id);
    }

}
