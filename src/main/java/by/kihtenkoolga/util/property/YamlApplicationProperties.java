package by.kihtenkoolga.util.property;

import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.Map;

public class YamlApplicationProperties  {

    private static Map<String, Map<String, Object>> yamlMap;

    public static final String PROPERTY_FILE = "application.yaml";

    public Map<String, Object> getPropertiesByKey(String groupKey) {
        if (yamlMap == null) {
            InputStream inputStream = getClass()
                    .getClassLoader()
                    .getResourceAsStream(PROPERTY_FILE);
            yamlMap = new Yaml().load(inputStream);
        }
        return yamlMap.get(groupKey);
    }

}
