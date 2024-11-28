package org.dao.classes.userInfoDao;

import org.connection.connectionManager.ConnectionManager;
import org.dao.interfaces.CrudInterface;
import org.entity.userEntity.UserEntity;
import org.entity.userInfoEntity.UserInfoEntity;
import org.exceptions.deleteMethodException.DeleteMethodException;
import org.exceptions.findAllException.FindAllException;
import org.exceptions.findByIdException.FindByIdException;
import org.exceptions.saveMethodException.SaveMethodException;
import org.exceptions.updateMethodException.UpdateMethodException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserInfoDao implements CrudInterface<Integer, UserInfoEntity> {
    private static UserInfoDao instance;
    private static final String USER_FIRST_NAME = "first_name";
    private static final String USER_LAST_NAME = "last_name";
    private static final String USER_ADDRESS = "address";
    private static final String USER_ID = "id";

    private static final String DELETE_SQL = """
            DELETE FROM user_info
            WHERE id = ?
            """;
    private static final String SAVE_SQL = """
            INSERT INTO user_info(first_name, last_name, address, user_id)
            VALUES (?,?,?,?)
            """;
    private static final String UPDATE_SQL = """
            UPDATE user_info
            SET first_name =?,
            last_name =?,
            address =?,
            user_id = ?
            WHERE id = ?
            """;
    private static final String FIND_ALL_SQL = """
            SELECT
                u.id,
                ui.first_name,
                ui.last_name,
                ui.address,
                u.login,
                r.role AS user_role
            FROM
                user_info ui
                    JOIN
                public.user u ON ui.user_id = u.id
                    JOIN
                role r ON u.role_id = r.id
            """;
    private static final String FIND_BY_ID_SQL = FIND_ALL_SQL + """
            WHERE u.id = ?;
            """;


    private UserInfoDao() {

    }


    @Override
    public boolean delete(Integer id) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(DELETE_SQL)) {
            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DeleteMethodException(e, " Exception in delete method in UserInfoDao class.");
        }
    }

    @Override
    public void update(UserInfoEntity entity) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
            if (entity.getFirstName() != null) {
                preparedStatement.setString(1, entity.getFirstName());
            }
            if (entity.getLastName() != null) {
                preparedStatement.setString(2, entity.getLastName());
            }
            if (entity.getAddress() != null) {
                preparedStatement.setString(3, entity.getAddress());
            }
            if (entity.getUser() != null) {
                preparedStatement.setInt(4, entity.getUser().getId());
            }

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new UpdateMethodException(e, " Exception in update method in UserInfoDao class.");
        }
    }

    @Override
    public UserInfoEntity save(UserInfoEntity entity) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, entity.getFirstName());
            preparedStatement.setString(2, entity.getLastName());
            preparedStatement.setString(3, entity.getAddress());
            preparedStatement.setInt(4, entity.getUser().getId());

            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                entity.setId(generatedKeys.getInt("id"));
            }
            return entity;
        } catch (SQLException e) {
            throw new SaveMethodException(e, " Exception in save method in UserInfoDao class.");
        }
    }

    @Override
    public Optional<UserInfoEntity> findById(Integer id) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL + id)) {
            preparedStatement.setInt(1, id);
            var resultSet = preparedStatement.executeQuery();
            UserInfoEntity userInfoEntity = null;
            if (resultSet.next()) {
                userInfoEntity = buildUserInfo(resultSet);
            }
            return Optional.ofNullable(userInfoEntity);
        } catch (SQLException e) {
            throw new FindByIdException(e, " Exception in findById method in UserInfoDao class.");
        }
    }

    private UserInfoEntity buildUserInfo(ResultSet resultSet) throws SQLException {
        UserEntity userEntity = UserEntity
                .builder()
                .id(resultSet.getInt(USER_ID))
                .build();
        return UserInfoEntity
                .builder()
                .id(resultSet.getInt("id"))
                .firstName(resultSet.getString(USER_FIRST_NAME))
                .lastName(resultSet.getString(USER_LAST_NAME))
                .address(resultSet.getString(USER_ADDRESS))
                .user(userEntity)
                .build();
    }

    @Override
    public List<UserInfoEntity> findAll() {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            var resultSet = preparedStatement.executeQuery();
            List<UserInfoEntity> userInfoEntities = new ArrayList<>();
            while(resultSet.next()) {
                UserInfoEntity userInfoEntity = buildUserInfo(resultSet);
                userInfoEntities.add(userInfoEntity);
            }
            return userInfoEntities;
        } catch (SQLException e) {
            throw new FindAllException(e, " Exception in findAll method in UserInfoDao class.");
        }
    }

    public static UserInfoDao getInstance() {
        if (instance == null) {
            return new UserInfoDao();
        }
        return instance;
    }
}