package com.liujiahui.www.util;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @author 刘家辉
 * @date 2023/04/06
 */
public class ConnectionPool {
    private static String url;
    private static String username;
    private static String password;
    private static ConnectionPool instance;
    private final int maxConnections = 10;
    private BlockingQueue<Connection> connectionPool;

    private ConnectionPool() {
        try (FileReader fre = new FileReader("src/resource/properties")) {
            Properties properties = new Properties();
            properties.load(fre);
            url = properties.getProperty("URL");
            username = properties.getProperty("username");
            password = properties.getProperty("password");
            connectionPool = new ArrayBlockingQueue<>(maxConnections);
            for (int i = 0; i < maxConnections; i++) {
                try {
                    Connection connection = DriverManager.getConnection(url, username, password);
                    connectionPool.add(connection);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static synchronized ConnectionPool getInstance() {
        if (instance == null) {
            instance = new ConnectionPool();
        }
        return instance;
    }

    public static void close(PreparedStatement preparedStatement, ResultSet set) {
        try {
            if (set != null) {
                set.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public synchronized Connection getConnection() throws SQLException {
        if (!connectionPool.isEmpty()) {
            return connectionPool.remove();
        }
        throw new SQLException("Connection limit exceeded");
    }

    public synchronized void releaseConnection(Connection connection) throws SQLException {
        if (connection != null && !connection.isClosed() && connectionPool.size() < maxConnections) {
            connectionPool.add(connection);
        } else {
            throw new SQLException("Unable to release connection");
        }
    }


}

