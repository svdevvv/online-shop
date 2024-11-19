package project.Connections.JdbcConnection;

import project.Connections.connectionManager.ConnectionManager;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.SQLException;

public class JdbcRunner {
    public static void main(String[] args) throws SQLException {
        Class<Driver> driverClass = Driver.class;

        try(Connection connection = ConnectionManager.get()){
            System.out.println(connection.getTransactionIsolation());
        }
    }
}
