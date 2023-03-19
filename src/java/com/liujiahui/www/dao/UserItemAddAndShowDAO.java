package com.liujiahui.www.dao;

import com.liujiahui.www.entity.po.Item;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户条目添加刀
 *
 * @author 刘家辉
 * @date 2023/03/19
 */
public class UserItemAddAndShowDAO {
    public static void addItem(String name, BigInteger price, String description) throws SQLException, IOException {
        Connection connection = UtilDAO.getConnection();
        String sql = "insert into user.item (name,price,description) values (?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,name);
        BigDecimal bigDecimal = new BigDecimal(price);
        preparedStatement.setBigDecimal(2,bigDecimal);
        preparedStatement.setString(3,description);
        preparedStatement.executeUpdate();
        UtilDAO.close(connection,null,preparedStatement);
    }
    public static List<Item> showItem() throws SQLException, IOException {
        Connection connection = UtilDAO.getConnection();
        String sql = "select * from user.item";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet set = preparedStatement.executeQuery();
        List<Item> list = new ArrayList<>();
        while (set.next()){
            BigInteger price = new BigInteger(String.valueOf(set.getBigDecimal("price")));
            Item item = new Item(set.getInt("id"),set.getString("name"),price,set.getString("description"));
            list.add(item);
        }
        UtilDAO.close(connection,null,preparedStatement);
        return list;
    }
}
