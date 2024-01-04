package by.kihtenkoolga.config;

import by.kihtenkoolga.cache.handler.AlgorithmCacheHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

@Configuration
@ComponentScan("by.kihtenkoolga.*")
@PropertySource(factory = YamlPropertySourceFactory.class, value = "classpath:application.yaml")
public class ApplicationConfig {

    @Value("${cache.algorithm-type}")
    private String classNameOfHandler;

    @Value("${cache.capacity}")
    private int size;

    @Bean
    public AlgorithmCacheHandler<Object, Object> cacheHandler() throws ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<?> beanClass = Class.forName(classNameOfHandler);
        try {
            Constructor<?> constructor = beanClass.getDeclaredConstructor(int.class);
            return (AlgorithmCacheHandler<Object, Object>) constructor.newInstance(size);
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException("The specified bean class does not have a constructor with the required argument");
        }
    }

}
