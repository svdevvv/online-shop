package project.DAO;

import project.Connections.connectionManager.ConnectionManager;
import project.dto.filter.RoleFilter;
import project.entity.RoleEntity;
import project.exceptions.*;

import javax.management.relation.Role;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class RoleDao {

    private static final RoleDao INSTANCE = new RoleDao();
    private static final String DELETE_SQL = """
            DELETE FROM role
            WHERE id = ?
            """;
    private static final String SAVE_SQL = """
            INSERT INTO role(id, role) 
            VALUES(?,?) 
            """;
    private static final String UPDATE_SQL = """
            UPDATE role
                SET role =?
            WHERE id = ?
            """;
    private static final String FIND_ALL_SQL = """
            SELECT 
                id,
                role
            FROM role
            """;
    private static final String FIND_BY_ID_SQL = FIND_ALL_SQL + """
            
            WHERE id = ?
            """;

    public List<RoleEntity> findAll(RoleFilter roleFilter) {
        List<Object> parameters = new ArrayList<>();
        List<String> whereSql = new ArrayList<>();
        if (roleFilter.getRole() != null) {
            whereSql.add("role LIKE ?");
            parameters.add(roleFilter.getRole());
        }

        parameters.add(roleFilter.getLimit());
        parameters.add(roleFilter.getOffset());
        var where = whereSql.stream()
                .collect(Collectors.joining(" AND ", " WHERE ", " LIMIT ? OFFSET ? "));
        var sql = FIND_ALL_SQL + where;
        try (Connection connection = ConnectionManager.get();
             var prepareStatement = connection.prepareStatement(sql)) {
            for (int i = 0; i < parameters.size(); i++) {
                prepareStatement.setObject(i + 1, parameters.get(i));
            }
            var resultSet = prepareStatement.executeQuery();
            List<RoleEntity> roles = new ArrayList<>();
            while (resultSet.next()) {
                roles.add(buildRole(resultSet));
            }
            return roles;
        } catch (SQLException e) {
            throw new FindAllException(e, " Exception in method findAll with parameters roleFilter in class RoleDao ");
        }
    }

    public List<RoleEntity> findAll() {
        try (var connection = ConnectionManager.get();
             var prepareStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            var resultSet = prepareStatement.executeQuery();
            List<RoleEntity> roles = new ArrayList<>();
            while (resultSet.next()) {
                roles.add(buildRole(resultSet));
            }
            return roles;
        } catch (SQLException e) {
            throw new FindAllException(e, " Exception in findAll method in RoleDao class");
        }
    }

    public Optional<RoleEntity> findById(Integer id) {
        try (var connection = ConnectionManager.get();
             var prepareStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            prepareStatement.setInt(1, id);

            var resultSet = prepareStatement.executeQuery();
            RoleEntity roleEntity = null;
            if (resultSet.next()) {
                roleEntity = buildRole(resultSet);
            }
            return Optional.ofNullable(roleEntity);
        } catch (SQLException e) {
            throw new FindByIdException(e, " Exception in findById method in RoleDao class");
        }
    }

    private static RoleEntity buildRole(ResultSet resultSet) throws SQLException {
        return new RoleEntity(
                resultSet.getInt("id"),
                resultSet.getString("role")
        );
    }

    public void update(RoleEntity roleEntity) {
        try (var connection = ConnectionManager.get();
             var prepareStatement = connection.prepareStatement(UPDATE_SQL)) {
            prepareStatement.setString(1, roleEntity.getRole());
            prepareStatement.setInt(2, roleEntity.getId());

            prepareStatement.executeUpdate();
        } catch (SQLException e) {
            throw new UpdateMethodException(e, " Exception in update method in RoleDao class");
        }
    }

    public RoleEntity save(RoleEntity roleEntity) {
        try (Connection connection = ConnectionManager.get();
             var prepareStatement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            prepareStatement.setInt(1, roleEntity.getId());
            prepareStatement.setString(2, roleEntity.getRole());

            prepareStatement.executeUpdate();
            var generatedKeys = prepareStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                roleEntity.setId(generatedKeys.getInt("id"));
            }
            return roleEntity;
        } catch (SQLException e) {
            throw new SaveMethodException(e, " Exception in save method in RoleDao class");
        }
    }

    public boolean delete(Integer id) {
        try (Connection connection = ConnectionManager.get();
             var prepareStatement = connection.prepareStatement(DELETE_SQL)) {
            prepareStatement.setInt(1, id);
            return prepareStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new DeleteMethodException(e, " ");
        }
    }

    public static RoleDao getInstance() {
        return INSTANCE;
    }
}
