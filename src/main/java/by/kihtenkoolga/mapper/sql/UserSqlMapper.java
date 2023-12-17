package by.kihtenkoolga.mapper.sql;

import by.kihtenkoolga.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static by.kihtenkoolga.constants.EntityConstants.CREATED_AT;
import static by.kihtenkoolga.constants.EntityConstants.ID;
import static by.kihtenkoolga.constants.EntityConstants.PHONE;
import static by.kihtenkoolga.constants.EntityConstants.SURNAME;
import static by.kihtenkoolga.constants.EntityConstants.USERNAME;

public class UserSqlMapper {

    public User toEntity(ResultSet rs) {
        try {
            if (rs.isAfterLast()) {
                return null;
            }
            return User.builder()
                    .id(UUID.fromString(rs.getString(ID)))
                    .name(rs.getString(USERNAME))
                    .surname(rs.getString(SURNAME))
                    .phone(rs.getString(PHONE))
                    .createdAt(rs.getDate(CREATED_AT).toLocalDate())
                    .build();
        } catch (SQLException ignore) {
            return null;
        }
    }

    public List<User> toEntityList(ResultSet rs) {
        List<User> users = new ArrayList<>();
        try {
            while (rs.next()) {
                User user = toEntity(rs);
                if (user == null) {
                    return users;
                }
                users.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }
}
