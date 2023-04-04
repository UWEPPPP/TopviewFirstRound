package com.liujiahui.www.util;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

/**
 * 集合方法
 *
 * @author 刘家辉
 * @date 2023/03/17
 */
public class UtilDAO {
    public static Connection getConnection() throws SQLException, IOException {
        Properties properties = new Properties();
        FileReader fre = new FileReader("src/resource/properties");
        properties.load(fre);
        String url = properties.getProperty("URL");
        String username = properties.getProperty("username");
        String password = properties.getProperty("password");
        return DriverManager.getConnection(url, username, password);
    }

    public static void close(Connection connection,PreparedStatement preparedStatement, ResultSet set) throws SQLException {
        if (set != null) {
            set.close();
        }
        if (preparedStatement != null) {
            preparedStatement.close();
        }
        if (connection != null) {
            connection.close();
        }

    }
}
