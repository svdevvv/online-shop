package org.dao.classes.blackListDao;

import org.connection.connectionManager.ConnectionManager;
import org.dao.classes.userDao.UserDao;
import org.dao.interfaces.CrudInterface;
import org.entity.blackListEntity.BlackListEntity;
import org.entity.roleEntity.RoleEntity;
import org.entity.userEntity.UserEntity;
import org.exceptions.findAllException.FindAllException;
import org.exceptions.findByIdException.FindByIdException;
import org.exceptions.saveMethodException.SaveMethodException;
import org.exceptions.updateMethodException.UpdateMethodException;
import org.postgresql.util.PGInterval;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class BlackListDao implements CrudInterface<Integer, BlackListEntity> {
    private static BlackListDao instance;
    private static final String USER_ID = "id";
    private static final String USER_LOGIN = "login";
    private static final String USER_ROLE = "role";
    private static final String REASON = "reason";
    private static final String DESCRIPTION = "description";
    private static final String BLOCK_TIME = "block_time";
    private static final String BLOCK_DURATION = "block_duration";

    private static final String SAVE_SQL = """
            INSERT INTO black_list (
                        user_id,
                        login,
                        role,
                        reason,
                        description,
                        block_time,
                        block_duration)
            VALUES (?,?,?,?,?,?,?)
            """;
    private static final String FIND_ALL_SQL = """
            SELECT u.id,
                   black_list.login,
                   r.role,
                   black_list.reason,
                   black_list.description,
                   black_list.block_time,
                   black_list.block_duration
            FROM black_list
            JOIN public."user" u on u.id = black_list.user_id
                JOIN public.role r on r.id = u.role_id
            """;
    private static final String FIND_BY_ID_SQL = FIND_ALL_SQL + """
           WHERE u.id = ?
           """;

    private static final String UPDATE_SQL = """
            UPDATE black_list
            SET block_duration = ?
            WHERE id = ?;
            """;

    private BlackListDao() {
    }

    @Override
    public void update(BlackListEntity entity) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
            if(entity.getBlock_duration() != null) {
                preparedStatement.setObject(1, entity.getBlock_duration());
            }
        } catch (SQLException e) {
            throw new UpdateMethodException(e, " Exception in update method in BlackListDao class.");
        }
    }

    @Override
    public BlackListEntity save(BlackListEntity entity) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, entity.getUserId());
            preparedStatement.setString(2, entity.getLogin());
            preparedStatement.setInt(3, entity.getRole().getId());
            preparedStatement.setString(4, entity.getReason());
            preparedStatement.setString(5, entity.getDescription());
            preparedStatement.setTimestamp(6, Timestamp.valueOf(entity.getBlock_time()));
            preparedStatement.setObject(7, entity.getBlock_duration());

            preparedStatement.executeUpdate();
            var generatedKeys = preparedStatement.getGeneratedKeys();
            while (generatedKeys.next()) {
                entity.setId(generatedKeys.getInt("id"));
            }
            return entity;
        } catch (SQLException e) {
            throw new SaveMethodException(e, " Exception in save method in BlackListDao class.");
        }
    }

    @Override
    public Optional<BlackListEntity> findById(Integer id) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            preparedStatement.setInt(1, id);
            var resultSet = preparedStatement.executeQuery();
            BlackListEntity entity = null;
            if (resultSet.next()) {
                entity = buildBlackList(resultSet);
            }
            return Optional.ofNullable(entity);

        } catch (SQLException e) {
            throw new FindByIdException(e, " Exception in findById method in BlackListDao class.");
        }
    }

    @Override
    public List<BlackListEntity> findAll() {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            var resultSet = preparedStatement.executeQuery();
            List<BlackListEntity> blackList = new ArrayList<>();
            while (resultSet.next()) {
                blackList.add(buildBlackList(resultSet));
            }
            return blackList;
        } catch (SQLException e) {
            throw new FindAllException(e, " Exception in findAll method in BlackListDao class.");
        }
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }

    private BlackListEntity buildBlackList(ResultSet resultSet) throws SQLException {
        var blockDuration = new PGInterval(BLOCK_DURATION);


        RoleEntity roleEntity = RoleEntity
                .builder()
                .role(resultSet.getString(USER_ROLE))
                .build();
        UserEntity userEntity = UserEntity
                .builder()
                .id(resultSet.getInt(USER_ID))
                .login(resultSet.getString(USER_LOGIN))
//
                .role(roleEntity)
                .build();
        return BlackListEntity
                .builder()
                .id(resultSet.getInt(USER_ID))
                .login(resultSet.getString(USER_LOGIN))
                .role(roleEntity)
                .reason(resultSet.getString(REASON))
                .description(resultSet.getString(DESCRIPTION))
                .block_time(resultSet.getTimestamp(BLOCK_TIME).toLocalDateTime())
                .block_duration(blockDuration)
                .build();

    }

    public static synchronized BlackListDao getInstance() {
        if (instance == null) {
            instance = new BlackListDao();
        }
        return instance;
    }
}
