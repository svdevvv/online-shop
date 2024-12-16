package org.dao.classes.userDao;

import lombok.SneakyThrows;
import org.connection.connectionManager.ConnectionManager;
import org.dao.interfaces.CrudInterface;
import org.entity.enums.gender.Gender;
import org.exceptions.findByLoginOrEmail.FindByLoginOrEmail;
import org.services.filters.userFilter.UserFilter;
import org.entity.roleEntity.RoleEntity;
import org.entity.userEntity.UserEntity;
import org.exceptions.deleteMethodException.DeleteMethodException;
import org.exceptions.findAllException.FindAllException;
import org.exceptions.findByIdException.FindByIdException;
import org.exceptions.saveMethodException.SaveMethodException;
import org.exceptions.updateMethodException.UpdateMethodException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserDao implements CrudInterface<Integer, UserEntity> {
    private static UserDao INSTANCE;
    private static final String USER_ID = "id";
    private static final String USER_LOGIN = "login";
    private static final String USER_PASSWORD = "password";
    private static final String USER_ROLE = "role";
    private static final String USER_FIRST_NAME = "first_name";
    private static final String USER_LAST_NAME = "last_name";
    private static final String USER_EMAIL = "email";
    private static final String USER_BIRTHDAY = "birthday";
    private static final String USER_GENDER = "gender";

    private static final String DELETE_SQL = """
            DELETE FROM "user"
            WHERE id = ?
            """;
    private static final String SAVE_SQL = """
            INSERT INTO "user"(login, password, role, first_name, last_name, email, birthday, gender)
            VALUES (?,?,?,?,?,?,?,?)
            """;
    private static final String UPDATE_SQL = """
            UPDATE "user"
            SET login = ?, password = ?, role = ?, first_name = ?, last_name = ?, email = ?, birthday = ?, gender = ?
            WHERE id = ?
            """;
    private static final String FIND_ALL_SQL = """
            SELECT "user".id,
                   "user".login,
                   "user".password,
                   r.role,
                   "user".first_name,
                   "user".last_name,
                   "user".email,
                   "user".birthday,
                   gender
            FROM "user"
                     JOIN role r
                          on r.id = "user".role
            """;
    private static final String FIND_BY_ID_SQL = FIND_ALL_SQL + """
            WHERE "user".id = ?
            """;
    private static final String FIND_BY_LOGIN_SQL = """
            SELECT u.id, login, password, r.role, first_name, last_name, email, birthday, gender
            FROM "user" u
            JOIN public.role r on r.id = u.role
            WHERE login = ? AND password = ?;
            """;

    private UserDao() {

    }

    @Override
    public boolean delete(Integer id) {
        try (var connection = ConnectionManager.get();
             var prepareStatement = connection.prepareStatement(DELETE_SQL)) {
            prepareStatement.setInt(1, id);
            return prepareStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DeleteMethodException(e, " Exception in delete methode in UserDao class");
        }
    }

    @SneakyThrows
    @Override
    public UserEntity save(UserEntity entity) {
        try (var connection = ConnectionManager.get();
             var prepareStatement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
//
            prepareStatement.setString(1, entity.getLogin());
            prepareStatement.setString(2, entity.getPassword());
            prepareStatement.setInt(3, entity.getRole().getId());
            prepareStatement.setString(4, entity.getFirstName());
            prepareStatement.setString(5, entity.getLastName());
            prepareStatement.setString(6, entity.getEmail());
            prepareStatement.setObject(7, entity.getBirthday());
            prepareStatement.setObject(8, entity.getGender().name());

            prepareStatement.executeUpdate();
            var generatedKeys = prepareStatement.getGeneratedKeys();
            generatedKeys.next();
            entity.setId(generatedKeys.getObject("id", Integer.class));
            return entity;
        }
    }

    @Override
    public void update(UserEntity entity) {
        try (var connection = ConnectionManager.get();
             var prepareStatement = connection.prepareStatement(UPDATE_SQL)) {
            if (entity.getLogin() != null) {
                prepareStatement.setString(1, entity.getLogin());
            }
            if (entity.getPassword() != null) {
                prepareStatement.setString(2, entity.getPassword());
            }
            if (entity.getRole() != null) {
                prepareStatement.setInt(3, entity.getRole().getId());
            }
            if (entity.getFirstName() != null) {
                prepareStatement.setString(4, entity.getFirstName());
            }
            if (entity.getLastName() != null) {
                prepareStatement.setString(5, entity.getLastName());
            }
            if (entity.getEmail() != null) {
                prepareStatement.setString(6, entity.getEmail());
            }
            if (entity.getBirthday() != null) {
                prepareStatement.setDate(7, Date.valueOf(entity.getBirthday()));
            }
            if (entity.getGender() != null) {
                prepareStatement.setObject(8, entity.getGender().name());
            }

            prepareStatement.executeUpdate();
        } catch (SQLException e) {
            throw new UpdateMethodException(e, " Exception in update method in UserDao class");
        }

    }

    public Optional<UserEntity> findByLoginAndPassword(String login , String password) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(FIND_BY_LOGIN_SQL)) {
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);

            var resultSet = preparedStatement.executeQuery();
            UserEntity user = null;
            while (resultSet.next()) {
                user = buildUser(resultSet);
            }
            return Optional.ofNullable(user);
        } catch (SQLException e) {
            throw new FindByLoginOrEmail(e, " Exception in findByLoginOrEmailAndPassword method in UserDao class.");
        }
    }

    @Override
    public Optional<UserEntity> findById(Integer id) {
        try (var connection = ConnectionManager.get();
             var prepareStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            prepareStatement.setInt(1, id);

            ResultSet resultSet = prepareStatement.executeQuery();
            UserEntity userEntity = null;
            if (resultSet.next()) {
                userEntity = buildUser(resultSet);
            }
            return Optional.ofNullable(userEntity);
        } catch (SQLException e) {
            throw new FindByIdException(e, " Exception in findById method in UserDao class");
        }
    }

    public List<UserEntity> findAll(UserFilter userFilter) {
        List<Object> parametersUser = new ArrayList<>();
        List<String> whereSqlUser = new ArrayList<>();
        if (userFilter
                    .getRole() != null) {
            whereSqlUser.add(" role LIKE ?");
            parametersUser.add(userFilter.getRole());
        }
        parametersUser.add(userFilter.getLimit());
        parametersUser.add(userFilter.getOffset());
        var whereUser = whereSqlUser.stream()
                .collect(Collectors.joining(" AND ", " WHERE ", " LIMIT ? OFFSET ?"));
        var sqlUser = FIND_ALL_SQL + whereUser;
        try (Connection connection = ConnectionManager.get();
             var prepareStatement = connection.prepareStatement(sqlUser)) {
            for (int i = 0; i < parametersUser.size(); i++) {
                prepareStatement.setObject(i + 1, parametersUser.get(i));
            }
            ResultSet resultSet = prepareStatement.executeQuery();
            List<UserEntity> users = new ArrayList<>();
            while (resultSet.next()) {
                users.add(buildUser(resultSet));
            }
            return users;
        } catch (SQLException e) {
            throw new FindAllException(e, " Exception in method findAll method with parameters UserFilter userFilter in UserDao class");
        }
    }

    @Override
    public List<UserEntity> findAll() {
        try (var connection = ConnectionManager.get();
             var prepareStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            var resultSet = prepareStatement.executeQuery();
            List<UserEntity> userEntities = new ArrayList<>();
            while (resultSet.next()) {
                UserEntity userEntity = buildUser(resultSet);
                userEntities.add(userEntity);
            }
            return userEntities;
        } catch (SQLException e) {
            throw new FindAllException(e, " Exception in findAll method in UserDao class");
        }
    }

    private UserEntity buildUser(ResultSet resultSet) throws SQLException {
        RoleEntity roleEntity = RoleEntity
                .builder()
                .role(resultSet.getString(USER_ROLE))
                .build();
        return UserEntity
                .builder()
                .id(resultSet.getInt(USER_ID))
                .login(resultSet.getString(USER_LOGIN))
                .password(resultSet.getString(USER_PASSWORD))
                .role(roleEntity)
                .firstName(resultSet.getString(USER_FIRST_NAME))
                .lastName(resultSet.getString(USER_LAST_NAME))
                .email(resultSet.getString(USER_EMAIL))
                .birthday(resultSet.getDate(USER_BIRTHDAY).toLocalDate())
                .gender(Gender.valueOf(resultSet.getString(USER_GENDER)))
                .build();
    }

    public static UserDao getInstance() {
        if (INSTANCE == null) {
            return new UserDao();
        }
        return INSTANCE;
    }
}
