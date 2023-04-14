package com.liujiahui.www.dao.impl;

import com.liujiahui.www.dao.SupplierAppealDAO;
import com.liujiahui.www.entity.dto.UserSaveDTO;
import com.liujiahui.www.entity.po.FeedbackPO;
import com.liujiahui.www.util.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.liujiahui.www.util.ConnectionPool.close;

/**
 * @author 刘家辉
 * @date 2023/04/05
 */
public class SupplierAppealDAOImpl implements SupplierAppealDAO {
    @Override
    public int getResultAppealSize(String account, String identityCheck, String judge) {
        try {

            Connection connection = ConnectionPool.getInstance().getConnection();
            String sql2 = "select count(*) from user.consumer_feedback INNER  JOIN user.supplier_appeal on supplier_appeal.item_hash=consumer_feedback.item_hash  where " + identityCheck + "=? and appeal_result IS NOT NULL and " + judge + "!= true";
            PreparedStatement preparedStatement2 = connection.prepareStatement(sql2);
            preparedStatement2.setString(1, account);
            ResultSet set2 = preparedStatement2.executeQuery();
            if (set2.next()) {
                int size = set2.getInt(1);
                close(preparedStatement2, set2);
                ConnectionPool.getInstance().releaseConnection(connection);
                return size;
            }
            throw new RuntimeException("获取申诉结果异常");
        } catch (SQLException e) {
            System.out.println("获取申诉结果异常");
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<FeedbackPO> showReportAndAppealResult(String accountAddress) throws SQLException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        String identity = Objects.equals(UserSaveDTO.getInstance().getIdentity(), "consumer") ? "buyer_account" : "seller_account";
        String judge = "buyer_account".equals(identity) ? "consumer_is_read" : "supplier_is_read";
        String sql = "SELECT * FROM user.consumer_feedback INNER JOIN user.supplier_appeal on consumer_feedback.item_hash = supplier_appeal.item_hash  WHERE " + identity + "=? and is_appeal IS NOT NULL ";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, accountAddress);
        ResultSet set = preparedStatement.executeQuery();
        List<FeedbackPO> list = new ArrayList<>();
        while (set.next()) {
            FeedbackPO feedbackPo = new FeedbackPO();
            feedbackPo.setBuyer(set.getString("buyer_account"));
            feedbackPo.setItemHash(set.getString("item_hash"));
            feedbackPo.setAppealResult(set.getBoolean("appeal_result"));
            feedbackPo.setSeller(set.getString("seller_account"));
            list.add(feedbackPo);
        }
        updateResultRead(accountAddress, identity, judge);
        close(preparedStatement, set);
        ConnectionPool.getInstance().releaseConnection(connection);
        return list;
    }

    @Override
    public void updateResultRead(String accountAddress, String identity, String judge) {
        try {
            Connection connection = ConnectionPool.getInstance().getConnection();
            String sql1 = "update user.supplier_appeal INNER JOIN user.consumer_feedback ON consumer_feedback.item_hash=supplier_appeal.item_hash  set " + judge + " = true where " + identity + "=?";
            ConsumerFeedbackDAOImpl.sameUpdate(accountAddress, connection, sql1);
        } catch (SQLException e) {
            System.out.println("更新申诉结果异常");
            throw new RuntimeException(e);
        }
    }

    @Override
    public void insert(String hash, String comment) throws SQLException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        String sql = "insert into user.supplier_appeal  ( supplier_is_read, consumer_is_read, item_hash, supplier_comment) values(false,false,?,?)  ";
        PreparedStatement preparedStatement;
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, hash);
        preparedStatement.setString(2, comment);
        int result = preparedStatement.executeUpdate();
        close(preparedStatement, null);
        ConnectionPool.getInstance().releaseConnection(connection);
        if (result == 0) {
            throw new RuntimeException("插入失败");
        }
    }

    @Override
    public void resolveBadAppeal(String hash, Boolean result) {
        Connection connection = null;
        PreparedStatement preparedStatement;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            String sql = "update user.supplier_appeal set appeal_result = ?,admin_is_read = true where item_hash = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setBoolean(1, result);
            preparedStatement.setString(2, hash);
            int results = preparedStatement.executeUpdate();
            close(preparedStatement, null);
            ConnectionPool.getInstance().releaseConnection(connection);
            if (results == 0) {
                throw new RuntimeException("更新失败");
            }
        } catch (SQLException e) {
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        }
    }


}
