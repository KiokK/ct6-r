package by.kihtenkoolga.cache.handler;

import by.kihtenkoolga.dto.UserDto;
import by.kihtenkoolga.util.UserDtoTestData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class LRUCacheHandlerTest {

    private LRUCacheHandler<UUID, UserDto> cacheHandler;

    @BeforeEach
    void setUp() {
        final int CAPACITY = 2;
        cacheHandler = new LRUCacheHandler<>(CAPACITY);
    }

    @Nested
    class Put {

        @Test
        void putThreeObjectsShouldDeleteTheOldest() {
            //given
            final int EXPECTED_SIZE = 2;
            UserDto firstDto = UserDtoTestData.getUserDtoIvan();
            UserDto secondDto = UserDtoTestData.getUserDtoMarina();
            UserDto thirdDto = UserDtoTestData.getUserDtoEvgeni();

            //when
            cacheHandler.put(firstDto.id, firstDto);
            cacheHandler.put(secondDto.id, secondDto);
            cacheHandler.put(thirdDto.id, thirdDto);
            Map<UUID, CacheValue<UUID, UserDto>> actualCacheBase = cacheHandler.getCopyOfCacheData();

            //then
            assertAll(
                    () -> assertThat(actualCacheBase.size()).isEqualTo(EXPECTED_SIZE),
                    () -> assertThat(actualCacheBase.get(secondDto.id).getValue()).isEqualTo(secondDto),
                    () -> assertThat(actualCacheBase.get(thirdDto.id).getValue()).isEqualTo(thirdDto)
            );
        }

        @Test
        void putTwoEqualsObjectShouldPutOne() {
            //given
            final int EXPECTED_SIZE = 1;
            UserDto testDto = UserDtoTestData.getUserDtoIvan();

            //when
            cacheHandler.put(testDto.id, testDto);
            cacheHandler.put(testDto.id, testDto);
            Map<UUID, CacheValue<UUID, UserDto>> actualCacheBase = cacheHandler.getCopyOfCacheData();

            //then
            assertAll(
                    () -> assertThat(actualCacheBase.size()).isEqualTo(EXPECTED_SIZE),
                    () -> assertThat(actualCacheBase.get(testDto.id).getValue()).isEqualTo(testDto)
            );
        }

    }

    @Nested
    class Get {

        @Test
        void getShouldSwapOrderOfElements() {
            //given
            final int EXPECTED_SIZE = 2;
            UserDto firstDto = UserDtoTestData.getUserDtoIvan();
            UserDto secondDto = UserDtoTestData.getUserDtoMarina();
            UserDto thirdDto = UserDtoTestData.getUserDtoEvgeni();

            //when
            cacheHandler.put(firstDto.id, firstDto);
            cacheHandler.put(secondDto.id, secondDto);
            UserDto actualValueByFirstId = cacheHandler.get(firstDto.id);
            cacheHandler.put(thirdDto.id, thirdDto);
            UserDto actualValueByThirdId = cacheHandler.get(thirdDto.id);
            Map<UUID, CacheValue<UUID, UserDto>> actualCacheBase = cacheHandler.getCopyOfCacheData();

            //then
            assertAll(
                    () -> assertThat(actualCacheBase.size()).isEqualTo(EXPECTED_SIZE),
                    () -> assertThat(firstDto).isEqualTo(actualValueByFirstId),
                    () -> assertThat(thirdDto).isEqualTo(actualValueByThirdId)
            );
        }

    }

    @Nested
    class Remove {

        @Test
        void removeShould() {
            //given
            final int EXPECTED_SIZE = 1;
            UserDto firstDto = UserDtoTestData.getUserDtoIvan();
            UserDto secondDto = UserDtoTestData.getUserDtoMarina();

            //when
            cacheHandler.put(firstDto.id, firstDto);
            cacheHandler.put(secondDto.id, secondDto);
            cacheHandler.remove(firstDto.id);
            Map<UUID, CacheValue<UUID, UserDto>> actualCacheBase = cacheHandler.getCopyOfCacheData();

            //then
            assertAll(
                    () -> assertThat(actualCacheBase.size()).isEqualTo(EXPECTED_SIZE),
                    () -> assertThat(actualCacheBase.get(secondDto.id).getValue()).isEqualTo(secondDto)
            );
        }

    }

}
