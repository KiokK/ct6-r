package by.kihtenkoolga.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan("by.kihtenkoolga.*")
@PropertySource(factory = YamlPropertySourceFactory.class, value = "classpath:application.yaml")
public class ApplicationConfig {

}
