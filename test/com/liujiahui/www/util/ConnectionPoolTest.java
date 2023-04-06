package com.liujiahui.www.util;

import junit.framework.TestCase;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionPoolTest extends TestCase {

    public void testGetConnection() {
        try {
            Connection connection = ConnectionPool.getInstance().getConnection();
            System.out.println(connection);
            ConnectionPool.getInstance().releaseConnection(connection);
            System.out.println(ConnectionPool.getInstance().getConnection());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void testReleaseConnection() {
    }
}