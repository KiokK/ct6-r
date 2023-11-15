package by.kihtenkoolga.config;

import by.kihtenkoolga.dao.UserDAOImpl;
import by.kihtenkoolga.service.impl.UserServiceImpl;

import java.util.HashMap;
import java.util.Map;

public class ComponentFactory {
    private static Map<Class<?>, Object> container = new HashMap<>();

    static {
        UserDAOImpl userDAO = new UserDAOImpl();

        container.put(UserDAOImpl.class, userDAO);
        container.put(UserServiceImpl.class, new UserServiceImpl(userDAO));
    }

    public static <T> T getComponent(Class<T> clazz) {
        return (T) container.get(clazz);
    }
}
