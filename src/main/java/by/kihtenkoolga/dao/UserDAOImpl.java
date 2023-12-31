package by.kihtenkoolga.dao;

import by.kihtenkoolga.config.DataSource;
import by.kihtenkoolga.config.PaginationInfo;
import by.kihtenkoolga.mapper.sql.UserSqlMapper;
import by.kihtenkoolga.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserDAOImpl implements UserDAO {

    private final UserSqlMapper sqlMapper;

    private final String FIND_ALL = "SELECT * FROM users LIMIT ? OFFSET ?;";
    private final String FIND_BY_ID = "SELECT * from users WHERE id = (?);";
    private final String DELETE_BY_ID = "delete from users WHERE id = (?);";
    private final String INSERT_USER =
            "INSERT INTO users (id, name, surname, phone, created_at) VALUES (?, ?, ?, ?, ?) RETURNING *;";
    private final String UPDATE_USER = "UPDATE users SET name=?, surname=?, phone=? WHERE id=?;";

    @Override
    public User create(User user) {
        try (Connection connection = DataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER);
            user.setId(UUID.randomUUID());
            preparedStatement.setString(1, user.getId().toString());
            preparedStatement.setString(2, user.getName());
            preparedStatement.setString(3, user.getSurname());
            preparedStatement.setString(4, user.getPhone());
            preparedStatement.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));

            if (preparedStatement.execute()) {
                return user;
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<User> findById(UUID id) {
        try (Connection connection = DataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID);
            preparedStatement.setString(1, id.toString());
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                return Optional.of(sqlMapper.toEntity(rs));
            }

            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> findAll(PaginationInfo paginationInfo) {
        try  (Connection connection = DataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL);
            preparedStatement.setInt(1, paginationInfo.getPageSize());
            preparedStatement.setInt(2, paginationInfo.getOffset());
            ResultSet rs = preparedStatement.executeQuery();

            return sqlMapper.toEntityList(rs);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(User user) {
        try (Connection connection = DataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getSurname());
            preparedStatement.setString(3, String.valueOf(user.getPhone()));
            preparedStatement.setString(4, String.valueOf(user.getId()));

            if (preparedStatement.executeUpdate() == 1) {
                return true;
            }
            return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteById(UUID id) {
        try (Connection connection = DataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BY_ID);
            preparedStatement.setString(1, id.toString());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
