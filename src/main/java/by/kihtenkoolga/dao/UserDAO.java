package by.kihtenkoolga.dao;

import by.kihtenkoolga.model.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserDAO {

    User create(User user);

    Optional<User> findById(UUID id);

    List<User> findAll();

    boolean update(User user);

    boolean deleteById(UUID id);

}
