package by.kihtenkoolga.cache.proxy;

import by.kihtenkoolga.cache.CacheFactory;
import by.kihtenkoolga.cache.handler.LFUCacheHandler;
import by.kihtenkoolga.dto.UserDto;
import org.aspectj.lang.ProceedingJoinPoint;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static by.kihtenkoolga.util.UserDtoTestData.getUserDtoIvan;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CacheAspectTest {

    @Mock
    private ProceedingJoinPoint proceedingJoinPoint;

    @Mock
    private LFUCacheHandler<UUID, UserDto> cacheHandler;

    @InjectMocks
    private CacheFactory cacheFactory;

    private CacheAspect cacheAspect = new CacheAspect();

    @Nested
    class PutCache {

        @Test
        void putCacheShouldReturnExpectedDto() throws Throwable {
            //given
            UserDto expected = getUserDtoIvan();

            // when
            when(proceedingJoinPoint.proceed()).thenReturn(expected);
            Object actual = cacheAspect.putCache(proceedingJoinPoint);

            //then
            assertAll(
                    () -> verify(cacheHandler).put(expected.id, expected),
                    () -> assertThat(actual).isEqualTo(expected)
            );
        }

    }

    @Nested
    class GetCache {

        @Test
        void getCacheShouldReturnCacheWhenExists() throws Throwable {
            //given
            UserDto userDto = getUserDtoIvan();
            Optional<UserDto> expected = Optional.of(getUserDtoIvan());
            final Object[] ARGS = new Object[]{userDto.id};

            // when
            when(proceedingJoinPoint.getArgs()).thenReturn(ARGS);
            when(cacheHandler.get(userDto.id)).thenReturn(userDto);
            Object actual = cacheAspect.getCache(proceedingJoinPoint);

            //then
            assertAll(
                    () -> verify(proceedingJoinPoint, times(0)).proceed(),
                    () -> assertThat(actual).isEqualTo(expected)
            );
        }

        @Test
        void getCacheShouldReturnObjectAndAddToCache() throws Throwable {
            //given
            UserDto userDto = getUserDtoIvan();
            Optional<UserDto> expected = Optional.of(getUserDtoIvan());
            final Object[] ARGS = new Object[]{userDto.id};

            // when
            when(proceedingJoinPoint.getArgs()).thenReturn(ARGS);
            when(cacheHandler.get(userDto.id)).thenReturn(null);
            when(proceedingJoinPoint.proceed()).thenReturn(Optional.of(userDto));
            Object actual = cacheAspect.getCache(proceedingJoinPoint);

            //then
            assertThat(actual)
                    .isEqualTo(expected);
        }

    }

    @Nested
    class PostCache {

        @Test
        void postCacheShouldReturnDtoAfterPut() throws Throwable {
            //given
            UserDto expected = getUserDtoIvan();

            //when
            when(proceedingJoinPoint.proceed()).thenReturn(expected);
            Object actual = cacheAspect.postCache(proceedingJoinPoint);

            //then
            assertAll(
                    () -> verify(cacheHandler).put(expected.id, expected),
                    () -> assertThat(actual).isEqualTo(expected)
            );
        }

    }

    @Nested
    class DeleteCache {

        @Test
        void deleteCacheVerifySteps() throws Throwable {
            //given
            UserDto userDto = getUserDtoIvan();
            final boolean SERVICE_PROCESS_ANSWER = true;
            final Object[] ARGS = new Object[]{userDto.id};

            // when
            when(proceedingJoinPoint.getArgs()).thenReturn(ARGS);
            when(proceedingJoinPoint.proceed()).thenReturn(SERVICE_PROCESS_ANSWER);
            cacheAspect.deleteCache(proceedingJoinPoint);

            //then
            verify(cacheHandler).remove(userDto.id);
        }

    }
}
