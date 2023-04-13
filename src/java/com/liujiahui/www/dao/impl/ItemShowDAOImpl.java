package com.liujiahui.www.dao.impl;

import com.liujiahui.www.dao.ItemShowDAO;
import com.liujiahui.www.entity.po.ItemPO;
import com.liujiahui.www.util.ConnectionPool;
import org.fisco.bcos.sdk.utils.Numeric;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.liujiahui.www.util.ConnectionPool.close;

/**
 * 项显示刀
 *
 * @author 刘家辉
 * @date 2023/04/05
 */
public class ItemShowDAOImpl implements ItemShowDAO {

    @Override
    public List<ItemPO> queryByPrice(int min, int max, String order) {
        try {
            Connection connection = ConnectionPool.getInstance().getConnection();
            String sql = "select * from user.item_show INNER join user.item_behind ib on item_show.hash = ib.hash where price between ? and ? order by price  " + order;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, min);
            preparedStatement.setInt(2, max);
            ConnectionPool.getInstance().releaseConnection(connection);
            return querySame(preparedStatement);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public List<ItemPO> queryByKeyword(String keyword) {
        try {
            Connection connection = ConnectionPool.getInstance().getConnection();
            String sql = "select * from user.item_show INNER JOIN user.item_behind ON item_show.hash=item_behind.hash        where name like ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "%" + keyword + "%");
            ConnectionPool.getInstance().releaseConnection(connection);
            return querySame(preparedStatement);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public List<ItemPO> queryByType(String type) {
        try {
            Connection connection = ConnectionPool.getInstance().getConnection();
            String sql = "select * from user.item_show INNER JOIN user.item_behind ib on item_show.hash = ib.hash where type=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, type);
            ConnectionPool.getInstance().releaseConnection(connection);
            return querySame(preparedStatement);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public List<ItemPO> queryBySeller(String seller) {
        try {
            Connection connection = ConnectionPool.getInstance().getConnection();
            String sql = "select * from user.item_behind INNER JOIN user.item_show ON item_behind.hash " +
                    "= item_show.hash where seller_address=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, seller);
            ConnectionPool.getInstance().releaseConnection(connection);
            return querySame(preparedStatement);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    private List<ItemPO> querySame(PreparedStatement preparedStatement) throws SQLException {
        ResultSet set = preparedStatement.executeQuery();
        List<ItemPO> list = new ArrayList<>();
        while (set.next()) {
            ItemPO po = new ItemPO();
            po.setId(set.getInt("id"));
            po.setName(set.getString("name"));
            po.setPrice(BigInteger.valueOf(set.getInt("price")));
            po.setDescription(set.getString("description"));
            po.setOwner(set.getString("owner_address"));
            po.setOwnerName(set.getString("owner_name"));
            po.setIndex(set.getBigDecimal("index"));
            po.setSold(set.getBoolean("isSold"));
            po.setType(set.getString("type"));
            list.add(po);
        }
        close(preparedStatement, set);
        return list;
    }

    @Override
    public List<ItemPO> showAllItem() throws SQLException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        String sql = "select * from user.item_show INNER JOIN user.item_behind on item_show.hash = item_behind.hash where isRemoved = false";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet set = preparedStatement.executeQuery();
        List<ItemPO> list = new ArrayList<>();
        while (set.next()) {
            BigInteger price = new BigInteger(String.valueOf(set.getBigDecimal("price")));
            ItemPO itemPo = new ItemPO(set.getInt("id"), set.getString("name"), price, set.getString("description"), set.getString("owner_address"), set.getBigDecimal("index"), set.getBoolean("isSold"), set.getString("owner_name"));
            itemPo.setType(set.getString("type"));
            list.add(itemPo);
        }
        close(preparedStatement, set);
        ConnectionPool.getInstance().releaseConnection(connection);
        return list;
    }

    @Override
    public void updateItem(String oldName, String name, String description, String price) throws SQLException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        String sql = "update user.item_show Inner join user.item_behind on item_show.hash=item_behind.hash INNER JOIN user.consumer_feedback on item_hash=item_show.hash set name = ?,description = ?,price = ?,consumer_feedback.item_name=?,item_behind.item_name=? where name = ?";
        PreparedStatement preparedStatement;
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, name);
        preparedStatement.setString(2, description);
        BigDecimal bigDecimal = new BigDecimal(price);
        preparedStatement.setBigDecimal(3, bigDecimal);
        preparedStatement.setString(4, name);
        preparedStatement.setString(5, name);
        preparedStatement.setString(6, oldName);
        int result = preparedStatement.executeUpdate();
        close(preparedStatement, null);
        ConnectionPool.getInstance().releaseConnection(connection);
        if (result == 0) {
            throw new RuntimeException("更新失败");
        }
    }

    @Override
    public void insert(String name, BigInteger price, String description, String userName, int type, String hash) throws SQLException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        String sql = "insert into user.item_show (name, price, description,owner_name,type,hash) values (?,?,?,?,?,?)";
        PreparedStatement preparedStatement;
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, name);
        BigDecimal bigDecimal = new BigDecimal(price);
        preparedStatement.setBigDecimal(2, bigDecimal);
        preparedStatement.setString(3, description);
        preparedStatement.setString(4, userName);
        String itemType = null;
        switch (type) {
            case 0:
                itemType = "Food";
                break;
            case 1:
                itemType = "Clothes";
                break;
            case 2:
                itemType = "Electronic";
                break;
            case 3:
                itemType = "Furniture";
                break;
            case 4:
                itemType = "Other";
                break;
            default:
        }
        preparedStatement.setString(5, itemType);
        preparedStatement.setString(6, hash);
        int result = preparedStatement.executeUpdate();
        close(preparedStatement, null);
        ConnectionPool.getInstance().releaseConnection(connection);
        if (result == 0) {
            throw new RuntimeException("insert item failed");
        }
    }

    @Override
    public Map<String, List<ItemPO>> showConsumerItem(String accountAddress) throws SQLException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        String sql = "select * from user.item_show INNER JOIN user.item_behind on item_show.hash = item_behind.hash " +
                " where owner_address = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, accountAddress);
        ResultSet set = preparedStatement.executeQuery();
        List<ItemPO> list = new ArrayList<>();
        while (set.next()) {
            BigInteger price = new BigInteger(String.valueOf(set.getBigDecimal("price")));
            ItemPO traceItem = new ItemPO(set.getInt("id"), set.getString("name"), price, set.getString("description"), set.getString("owner_name"), set.getBigDecimal("index"), set.getBoolean("isSold"), Numeric.hexStringToByteArray(set.getString("hash")));
            list.add(traceItem);
        }
        close(preparedStatement, set);
        HashMap<String, List<ItemPO>> listHashMap = new HashMap<>(1);
        listHashMap.put("item", list);
        ConnectionPool.getInstance().releaseConnection(connection);
        return listHashMap;
    }


    @Override
    public ItemPO getSingleItem(String hash) throws SQLException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        String sql = "select * from user.item_show INNER JOIN user.item_behind ON item_show.hash=item_behind.hash  where item_show.hash = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, hash);
        ResultSet resultSet = preparedStatement.executeQuery();
        ItemPO itemPo = new ItemPO();
        while (resultSet.next()) {
            itemPo.setId(resultSet.getInt("id"));
            itemPo.setName(resultSet.getString("name"));
            itemPo.setDescription(resultSet.getString("description"));
            itemPo.setOwner(resultSet.getString("owner_address"));
            itemPo.setSeller(resultSet.getString("seller_address"));
            itemPo.setToken(resultSet.getInt("token"));
        }
        close(preparedStatement, resultSet);
        ConnectionPool.getInstance().releaseConnection(connection);
        return itemPo;
    }


}
