package com.liujiahui.www.dao;

import com.liujiahui.www.entity.dto.UserInformationSaveDTO;
import com.liujiahui.www.entity.po.Item;
import com.liujiahui.www.solidity.ItemTrade;
import org.fisco.bcos.sdk.abi.datatypes.DynamicArray;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;
import org.fisco.bcos.sdk.utils.Numeric;

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
public class UserItemDAO {
    public static void addItem(String name, BigInteger price, String description, String accountAddress, BigInteger index) throws SQLException, IOException {
        Connection connection = UtilDAO.getConnection();
        String sql = "insert into user.item (name, price, description, owner, `index`,isSold) values (?,?,?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,name);
        BigDecimal bigDecimal = new BigDecimal(price);
        preparedStatement.setBigDecimal(2,bigDecimal);
        preparedStatement.setString(3,description);
        preparedStatement.setString(4,accountAddress);
        long longValue = index.longValue();
        preparedStatement.setLong(5,longValue);
        preparedStatement.setBoolean(6,false);
        preparedStatement.executeUpdate();
    }
    public static List<Item> showAllItem() throws SQLException, IOException {
        Connection connection = UtilDAO.getConnection();
        String sql = "select * from user.item";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet set = preparedStatement.executeQuery();
        List<Item> list = new ArrayList<>();
        while (set.next()){
            BigInteger price = new BigInteger(String.valueOf(set.getBigDecimal("price")));
            Item item = new Item(set.getInt("id"),set.getString("name"),price,set.getString("description"),set.getString("owner"),set.getBigDecimal("index"),set.getBoolean("isSold"));
            list.add(item);
        }
        UtilDAO.close(connection,null,preparedStatement);
        return list;
    }

    public static List<Item> showMyItem(String accountAddress) throws SQLException, IOException {
        Connection connection = UtilDAO.getConnection();
        String sql = "select * from user.item where owner = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,accountAddress);
        ResultSet set = preparedStatement.executeQuery();
        List<Item> list = new ArrayList<>();
        while (set.next()){
            BigInteger price = new BigInteger(String.valueOf(set.getBigDecimal("price")));
            Item item = new Item(set.getInt("id"),set.getString("name"),price,set.getString("description"),set.getString("owner"),set.getBigDecimal("index"),set.getBoolean("isSold"), Numeric.hexStringToByteArray(set.getString("hash")));
            list.add(item);
        }
        UtilDAO.close(connection,null,preparedStatement);
        return list;
    }

    public static List<Item> showSoldItem() throws SQLException, IOException, ContractException {
        UserInformationSaveDTO instance = UserInformationSaveDTO.getInstance();
        DynamicArray<ItemTrade.Item> soldItem = instance.getItemTradeSolidity().getSoldItem();
        List<Item> list = new ArrayList<>();
        int index=0;
        for (ItemTrade.Item item : soldItem.getValue()) {
            Item item1 = new Item(item.name,item.price,item.description);
            item1.setIndex(new BigDecimal(index));
            item1.setSold(item.isSold);
            list.add(item1);
            index++;
        }
        return list;
    }

    public static void updateItem(String oldName, String name, String description, String price) throws SQLException, IOException {
        Connection connection = UtilDAO.getConnection();
        String sql = "update user.item set name = ?,description = ?,price = ? where name = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,name);
        preparedStatement.setString(2,description);
        BigDecimal bigDecimal = new BigDecimal(price);
        preparedStatement.setBigDecimal(3,bigDecimal);
        preparedStatement.setString(4,oldName);
        preparedStatement.executeUpdate();
        UtilDAO.close(connection,null,preparedStatement);
    }
}
