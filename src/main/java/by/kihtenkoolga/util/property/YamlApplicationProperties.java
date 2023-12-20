package by.kihtenkoolga.util.property;

import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.Map;

/**
 * Класс, который считывает настройки приложения из application.yaml по ключам из {@link PropertiesConstant}
 */
public class YamlApplicationProperties  {

    private static Map<String, Map<String, Object>> yamlMap;

    public static final String PROPERTY_FILE = "application.yaml";

    {
        InputStream inputStream = getClass()
                .getClassLoader()
                .getResourceAsStream(PROPERTY_FILE);
        yamlMap = new Yaml().load(inputStream);
    }

    /**
     * Метод получения настроект из application.yaml по ключам группы, указанных в {@link PropertiesConstant}
     * @param groupKey ключ для определенной группы настроек
     * @return настройки для подключения
     */
    public Map<String, Object> getPropertiesByKey(String groupKey) {
        return yamlMap.get(groupKey);
    }

}
