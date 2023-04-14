package com.liujiahui.www.dao.impl;

import com.liujiahui.www.dao.ItemBehindDAO;
import com.liujiahui.www.entity.dto.UserSaveDTO;
import com.liujiahui.www.entity.po.FeedbackPO;
import com.liujiahui.www.entity.po.ItemPO;
import com.liujiahui.www.util.ConnectionPool;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.liujiahui.www.util.ConnectionPool.close;

/**
 * 项目背后刀
 *
 * @author 刘家辉
 * @date 2023/04/05
 */
public class ItemBehindDAOImpl implements ItemBehindDAO {

    @Override
    public List<ItemPO> getAllItem(String accountAddress) throws SQLException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        String sql = "SELECT * FROM user.item_show INNER JOIN user.item_behind" +
                " ON item_show.hash = item_behind.hash WHERE item_behind.seller_address = ?";
        PreparedStatement preparedStatement;
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, accountAddress);
        ResultSet set = preparedStatement.executeQuery();
        List<ItemPO> list = new ArrayList<>();
        while (set.next()) {
            BigInteger price = new BigInteger(String.valueOf(set.getBigDecimal("price")));
            ItemPO itemPo = new ItemPO(set.getInt("id"), set.getString("name"), price, set.getString("description"), set.getString("owner_address"), set.getBigDecimal("index"), set.getBoolean("isSold"));
            itemPo.setType(set.getString("type"));
            list.add(itemPo);
        }
        close(preparedStatement, set);
        ConnectionPool.getInstance().releaseConnection(connection);
        return list;
    }

    @Override
    public void removeOrRestoredItem(int index, Boolean choice, String contractAccount) throws SQLException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        String sql = "update user.item_behind set isRemoved = ?  where `index`=? and seller_address = ?";
        PreparedStatement preparedStatement;
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setBoolean(1, choice);
        preparedStatement.setInt(2, index);
        preparedStatement.setString(3, contractAccount);
        int result = preparedStatement.executeUpdate();
        close(preparedStatement, null);
        ConnectionPool.getInstance().releaseConnection(connection);
        if (result == 0) {
            throw new RuntimeException("更新失败");
        }
    }

    @Override
    public List<FeedbackPO> showAndUpdateFeedback(String accountAddress) throws SQLException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        String sql = "SELECT * FROM user.consumer_feedback  WHERE seller_account = ?";
        PreparedStatement preparedStatement;
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, accountAddress);
        ResultSet set = preparedStatement.executeQuery();
        List<FeedbackPO> list = new ArrayList<>();
        while (set.next()) {
            FeedbackPO feedbackPo = new FeedbackPO();
            feedbackPo.setBuyer(set.getString("buyer_account"));
            feedbackPo.setSeller(set.getString("seller_account"));
            feedbackPo.setLikeOrReport(Objects.equals(set.getString("like_report"), "likes"));
            feedbackPo.setComment(set.getString("comment"));
            feedbackPo.setItemName(set.getString("item_name"));
            feedbackPo.setRead(set.getBoolean("is_read"));
            feedbackPo.setItemHash(set.getString("item_hash"));
            list.add(feedbackPo);
        }
        String sql1 = "update user.consumer_feedback set is_read = true where seller_account = ?";
        PreparedStatement preparedStatement1 = connection.prepareStatement(sql1);
        preparedStatement1.setString(1, accountAddress);
        int result = preparedStatement1.executeUpdate();
        close(preparedStatement, set);
        close(preparedStatement1, null);
        ConnectionPool.getInstance().releaseConnection(connection);
        if (result == 0) {
            throw new RuntimeException("更新失败");
        }
        return list;
    }

    @Override
    public void insert(String name, String accountAddress, BigInteger index, String hash, BigInteger token) throws SQLException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        String sql1 = "insert into user.item_behind (owner_address,isSold,`index`,seller_address,hash,isRemoved,token,item_name) values (?,?,?,?,?,?,?,?)";
        PreparedStatement preparedStatement1 = connection.prepareStatement(sql1);
        preparedStatement1.setString(1, accountAddress);
        preparedStatement1.setBoolean(2, false);
        preparedStatement1.setBigDecimal(3, new BigDecimal(index));
        preparedStatement1.setString(4, accountAddress);
        preparedStatement1.setString(5, hash);
        //是否下架了
        preparedStatement1.setBoolean(6, false);
        preparedStatement1.setBigDecimal(7, BigDecimal.valueOf(token.longValue()));
        preparedStatement1.setString(8, name);
        ConnectionPool.getInstance().releaseConnection(connection);
        if (preparedStatement1.executeUpdate() == 0) {
            throw new RuntimeException("插入失败");
        }
        close(preparedStatement1, null);
    }

    @Override
    public ItemPO returnItem(String hash) throws SQLException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        String sql = "update user.item_behind set isSold = ?,owner_address=seller_address where hash = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        String sql1 = "select `index` from user.item_behind where hash = ?";
        PreparedStatement preparedStatement1 = connection.prepareStatement(sql1);
        preparedStatement.setBoolean(1, false);
        preparedStatement.setString(2, hash);
        int result = preparedStatement.executeUpdate();
        if (result == 0) {
            throw new RuntimeException("退款失败");
        }
        preparedStatement1.setString(1, hash);
        ResultSet set = preparedStatement1.executeQuery();
        if (set.next()) {
            ItemPO po = new ItemPO();
            po.setIndex(set.getBigDecimal("index"));
            close(preparedStatement, set);
            ConnectionPool.getInstance().releaseConnection(connection);
            return po;
        }
        throw new RuntimeException("退款失败");
    }

    @Override
    public void buyItem(String accountAddress, BigInteger index) throws SQLException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        String account = UserSaveDTO.getInstance().getContractAccount();
        String sql = "update user.item_behind  set isSold = ?,owner_address = ? where seller_address = ? and `index` = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setBoolean(1, true);
        preparedStatement.setString(2, account);
        preparedStatement.setString(3, accountAddress);
        preparedStatement.setBigDecimal(4, new java.math.BigDecimal(index));
        int result = preparedStatement.executeUpdate();
        close(preparedStatement, null);
        ConnectionPool.getInstance().releaseConnection(connection);
        if (result == 0) {
            throw new RuntimeException("购买失败");
        }
    }
}
