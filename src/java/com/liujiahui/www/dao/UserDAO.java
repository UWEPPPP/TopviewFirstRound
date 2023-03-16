package com.liujiahui.www.dao;

import com.liujiahui.www.entity.po.Consumer;
import com.liujiahui.www.entity.po.Supplier;
import com.liujiahui.www.entity.po.User;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

/**
 * 用户刀
 *
 * @author 刘家辉
 * @date 2023/03/16
 */
public class UserDAO {
    public Boolean register(User user) throws SQLException, IOException {
        Connection connection = getConnection();
        PreparedStatement preparedStatement;
        if(checkUserExist("user.consumer",user.getName(),connection)
                || checkUserExist("user.suppliers",user.getName(),connection)){
            return false;
        }
        String sql= user instanceof Consumer?"insert into user.consumer(user_name, gender, phone_number, password) values(?,?,?,?)": "insert into user.suppliers(user_name, gender, phone_number, password, address) VALUES (?,?,?,?,?)";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, user.getName());
        ResultSet set = preparedStatement.executeQuery();
        preparedStatement.setString(1, user.getName());
        preparedStatement.setString(2, user.getGender());
        preparedStatement.setInt(3, user.getPhoneNumber());
        preparedStatement.setString(4, user.getPassword());
        if(user instanceof Supplier){
            preparedStatement.setString(5, ((Supplier) user).getAddress());
        }
        preparedStatement.executeUpdate();
        close(connection, set, preparedStatement);
        return true;

    }
    public Boolean checkUserExist(String table,String name,Connection connection) throws SQLException {
        PreparedStatement preparedStatement=null;
        ResultSet set=null;
        try {
            preparedStatement = connection.prepareStatement("select * from user." + table + " where user_name=?");
            preparedStatement.setString(1, name);
            set = preparedStatement.executeQuery();
            return set.next();
        } finally {
            close(connection,set , preparedStatement);
        }
    }
    public User login(User user) throws SQLException, IOException {
        Connection connection = getConnection();
        if(user instanceof Consumer){
            PreparedStatement preparedStatement = connection.prepareStatement("select * from user.consumer where user_name=? and `password`=?");
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getPassword());
            ResultSet set = preparedStatement.executeQuery();
            if(set.next()){
                Consumer consumer= new Consumer();
                consumer.setName(set.getString("user_name"));
                consumer.setPhoneNumber(set.getInt("phone_number"));
                consumer.setPassword(set.getString("password"));
                consumer.setGender(set.getString("gender"));
                return consumer;
            }
            return null;
        }else {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from user.suppliers where user_name=? and `password`=?");
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getPassword());
            ResultSet set = preparedStatement.executeQuery();
            if(set.next()){
                Supplier supplier = new Supplier();
                supplier.setName(set.getString("user_name"));
                supplier.setPhoneNumber(set.getInt("phone_number"));
                supplier.setPassword(set.getString("password"));
                supplier.setAddress(set.getString("address"));
                supplier.setGender(set.getString("gender"));
                return supplier;
            }
        }
        return null;

    }
    private Connection getConnection() throws SQLException, IOException {
        Properties properties=new Properties();
        FileReader fre=new FileReader("src/resource/properties");
        properties.load(fre);
        String url= properties.getProperty("URL");
        String username=properties.getProperty("username");
        String password=properties.getProperty("password");
        return DriverManager.getConnection(url,username,password);
    }
    public static void close(Connection connection, ResultSet set, PreparedStatement preparedStatement) throws SQLException {
        if (connection!=null) {
            connection.close();
        }
        if(set!=null) {
            set.close();
        }
        if(preparedStatement!=null) {
            preparedStatement.close();
        }
    }

}
