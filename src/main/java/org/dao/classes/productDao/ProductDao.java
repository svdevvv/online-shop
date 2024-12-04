package org.dao.classes.productDao;

import org.connection.connectionManager.ConnectionManager;
import org.dao.interfaces.CrudInterface;
import org.entity.productEntity.ProductEntity;
import org.exceptions.deleteMethodException.DeleteMethodException;
import org.exceptions.saveMethodException.SaveMethodException;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class ProductDao implements CrudInterface<Integer, ProductEntity> {
    private static ProductDao INSTANCE;
    private static final String SAVE_SQL = """
            INSERT INTO product ( name, description, price)
            VALUES (?,?,?)
            """;
    private static final String DELETE_SQL = """
            DELETE FROM product
            WHERE id = ?
            """;

    private ProductDao() {
    }

    @Override
    public boolean delete(Integer id) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(DELETE_SQL)) {
            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DeleteMethodException(e, " Exception in delete method in ProductDao class.");
        }
    }

    @Override
    public void update(ProductEntity entity) {

    }

    @Override
    public ProductEntity save(ProductEntity entity) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setString(2, entity.getDescription());
            preparedStatement.setBigDecimal(3, entity.getPrice());

            preparedStatement.executeUpdate();
            var generatedKeys = preparedStatement.getGeneratedKeys();
            while (generatedKeys.next()) {
                entity.setId(generatedKeys.getInt("id"));
            }
            return entity;
        } catch (SQLException e) {
            throw new SaveMethodException(e, " Exception in save method in ProductDao class.");
        }
    }

    @Override
    public Optional<ProductEntity> findById(Integer id) {
        return Optional.empty();
    }

    @Override
    public List<ProductEntity> findAll() {
        return List.of();
    }

    public static ProductDao getInstance() {
        if (INSTANCE == null) {
            return new ProductDao();
        }
        return INSTANCE;
    }
}
