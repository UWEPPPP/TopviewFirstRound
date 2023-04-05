package com.liujiahui.www.dao.impl;

import com.liujiahui.www.dao.ItemShowDAO;
import com.liujiahui.www.entity.po.TraceItemPO;
import com.liujiahui.www.util.UtilDAO;
import org.fisco.bcos.sdk.utils.Numeric;

import java.io.IOException;
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

/**
 * 项显示刀
 *
 * @author 刘家辉
 * @date 2023/04/05
 */
public class ItemShowDAOImpl implements ItemShowDAO {

    @Override
    public List<TraceItemPO> queryByPrice(int min, int max, int choice) {
        String order;
        if (choice == 1) {
            order = "asc";
        } else {
            order = "desc";
        }
        try (Connection connection = UtilDAO.getConnection()) {
            String sql = "select * from user.item_show INNER join user.item_behind ib on item_show.hash = ib.hash where price between ? and ? order by price  " + order;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, min);
            preparedStatement.setInt(2, max);
            return querySame(preparedStatement);
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public List<TraceItemPO> queryByKeyword(String keyword) {
        try (Connection connection = UtilDAO.getConnection()) {
            String sql = "select * from user.item_show INNER JOIN user.item_behind ON item_show.hash=item_behind.hash        where name like ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "%" + keyword + "%");
            return querySame(preparedStatement);
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public List<TraceItemPO> queryByType(String type) {
        try (Connection connection = UtilDAO.getConnection()) {
            String sql = "select * from user.item_show INNER JOIN user.item_behind ib on item_show.hash = ib.hash where type=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, type);
            return querySame(preparedStatement);
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public List<TraceItemPO> queryBySeller(String seller) {
        try (Connection connection = UtilDAO.getConnection()) {
            String sql = "select * from user.item_behind INNER JOIN user.item_show ON item_behind.hash " +
                    "= item_show.hash where seller_address=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, seller);
            return querySame(preparedStatement);
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }


    private List<TraceItemPO> querySame(PreparedStatement preparedStatement) throws SQLException {
        ResultSet set = preparedStatement.executeQuery();
        List<TraceItemPO> list = new ArrayList<>();
        while (set.next()) {
            TraceItemPO po = new TraceItemPO();
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
        return list;
    }
    @Override
    public List<TraceItemPO> showAllItem() throws SQLException, IOException {
        Connection connection = UtilDAO.getConnection();
        String sql = "select * from user.item_show INNER JOIN user.item_behind on item_show.hash = item_behind.hash where isRemoved = false";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet set = preparedStatement.executeQuery();
        List<TraceItemPO> list = new ArrayList<>();
        while (set.next()) {
            BigInteger price = new BigInteger(String.valueOf(set.getBigDecimal("price")));
            TraceItemPO traceItemPo = new TraceItemPO(set.getInt("id"), set.getString("name"), price, set.getString("description"), set.getString("owner_address"), set.getBigDecimal("index"), set.getBoolean("isSold"), set.getString("owner_name"));
            traceItemPo.setType(set.getString("type"));
            list.add(traceItemPo);
        }
        UtilDAO.close(connection, preparedStatement,set);
        return list;
    }

    @Override
    public void updateItem(String oldName, String name, String description, String price) throws SQLException, IOException {
        Connection connection = UtilDAO.getConnection();
        String sql = "update user.item_show set name = ?,description = ?,price = ? where name = ?";
        PreparedStatement preparedStatement;
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, name);
        preparedStatement.setString(2, description);
        BigDecimal bigDecimal = new BigDecimal(price);
        preparedStatement.setBigDecimal(3, bigDecimal);
        preparedStatement.setString(4, oldName);
        preparedStatement.executeUpdate();
        UtilDAO.close(connection, preparedStatement, null);
    }

    @Override
    public void insert(String name, BigInteger price, String description, String userName, int type, String hash) throws SQLException, IOException {
        Connection connection = UtilDAO.getConnection();
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
        preparedStatement.executeUpdate();
    }

    @Override
    public Map<String, List<TraceItemPO>> showConsumerItem(String accountAddress) throws SQLException, IOException {
        Connection connection = UtilDAO.getConnection();
        String sql = "select * from user.item_show INNER JOIN user.item_behind on item_show.hash = item_behind.hash " +
                " where owner_address = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, accountAddress);
        ResultSet set = preparedStatement.executeQuery();
        List<TraceItemPO> list = new ArrayList<>();
        while (set.next()) {
            BigInteger price = new BigInteger(String.valueOf(set.getBigDecimal("price")));
            TraceItemPO traceItem = new TraceItemPO(set.getInt("id"), set.getString("name"), price, set.getString("description"), set.getString("owner_name"), set.getBigDecimal("index"), set.getBoolean("isSold"), Numeric.hexStringToByteArray(set.getString("hash")));
            list.add(traceItem);
        }
        UtilDAO.close(connection, preparedStatement, null);
        HashMap<String, List<TraceItemPO>> listHashMap = new HashMap<>(1);
        listHashMap.put("item", list);
        return listHashMap;
    }


    @Override
    public TraceItemPO getSingleItem(String hash) throws SQLException, IOException {
        Connection connection = UtilDAO.getConnection();
        String sql = "select * from user.item_show INNER JOIN user.item_behind ON item_show.hash=item_behind.hash  where item_show.hash = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, hash);
        ResultSet resultSet = preparedStatement.executeQuery();
        TraceItemPO traceItemPo = new TraceItemPO();
        while (resultSet.next()) {
            traceItemPo.setId(resultSet.getInt("id"));
            traceItemPo.setName(resultSet.getString("name"));
            traceItemPo.setDescription(resultSet.getString("description"));
            traceItemPo.setOwner(resultSet.getString("owner_account"));
            traceItemPo.setSeller(resultSet.getString("seller_account"));
            traceItemPo.setToken(resultSet.getInt("token"));
        }
        return traceItemPo;
    }


}
