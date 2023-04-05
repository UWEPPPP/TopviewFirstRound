package com.liujiahui.www.dao.impl;

import com.liujiahui.www.dao.ConsumerFeedbackDAO;
import com.liujiahui.www.entity.po.TraceFeedbackPO;
import com.liujiahui.www.util.UtilDAO;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author 刘家辉
 * @date 2023/04/04
 */
public class ConsumerFeedbackDAOImpl implements ConsumerFeedbackDAO {
    @Override
    public int getFeedbackNumber(String address) {
        Connection connection;
        try {
            connection = UtilDAO.getConnection();
            String sql1 = "select count(*) from user.consumer_feedback where seller_account=? and is_read=false";
            PreparedStatement preparedStatement1;
            preparedStatement1 = connection.prepareStatement(sql1);
            preparedStatement1.setString(1, address);
            ResultSet set1;
            set1 = preparedStatement1.executeQuery();
            if (set1.next()) {
                UtilDAO.close(connection, preparedStatement1, set1);
                return set1.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("set处异常");
        } catch (IOException e) {
            System.out.println("连接异常");
            throw new RuntimeException(e);
        }
        return 0;
    }

    @Override
    public List<TraceFeedbackPO> getSupplierHistory(String accountAddress) throws SQLException, IOException {

            Connection connection = UtilDAO.getConnection();
            String sql1 = "SELECT t1.*, t2.name FROM user.consumer_feedback t1 INNER JOIN user.item_show t2 ON t1.item_hash = t2.hash where seller_account=?;";
            PreparedStatement preparedStatement1 = connection.prepareStatement(sql1);
            preparedStatement1.setString(1, accountAddress);
            ResultSet set1 = preparedStatement1.executeQuery();
            List<TraceFeedbackPO> list = new ArrayList<>();
            while (set1.next()) {
                TraceFeedbackPO traceFeedbackPo = new TraceFeedbackPO();
                traceFeedbackPo.setBuyer(set1.getString("buyer_account"));
                traceFeedbackPo.setSeller(set1.getString("seller_account"));
                traceFeedbackPo.setLikeOrReport("like".equals(set1.getString("like_report")));
                traceFeedbackPo.setItemHash(set1.getString("item_hash"));
                traceFeedbackPo.setComment(set1.getString("comment"));
                traceFeedbackPo.setItemName(set1.getString("Name"));
                list.add(traceFeedbackPo);
            }
            UtilDAO.close(null, preparedStatement1, set1);
            return list;


    }

    @Override
    public void updateFeedback(String hash) throws SQLException, IOException {
        Connection connection = UtilDAO.getConnection();
        String sql1 = "update user.consumer_feedback set is_appeal = true where item_hash = ?";
        PreparedStatement preparedStatement1 = connection.prepareStatement(sql1);
        preparedStatement1.setString(1, hash);
        preparedStatement1.executeUpdate();
        UtilDAO.close(null, preparedStatement1, null);
    }

    @Override
    public void writeDown(String seller, String buyer, String comment, int choice, String itemHash) throws SQLException, IOException {
        Connection connection = UtilDAO.getConnection();
        String type;
        if (choice == 1) {
            type = "likes";
        } else {
            type = "reports";
        }
        String sql = "update user.suppliers set " + type + " = " + type + " + 1 where account_address = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, seller);
        preparedStatement.executeUpdate();
        String sql1 = "insert into user.consumer_feedback (seller_account,buyer_account,comment,like_report,item_hash,is_read) values (?,?,?,?,?,?)";
        PreparedStatement preparedStatement1 = connection.prepareStatement(sql1);
        preparedStatement1.setString(1, seller);
        preparedStatement1.setString(2, buyer);
        preparedStatement1.setString(3, comment);
        preparedStatement1.setString(4, type);
        preparedStatement1.setString(5, itemHash);
        preparedStatement1.setBoolean(6, false);
        preparedStatement1.executeUpdate();
        UtilDAO.close(connection, preparedStatement, null);
        UtilDAO.close(null,  preparedStatement1, null);
    }

    @Override
    public List<TraceFeedbackPO> getAllFeedbackAndComplaint() throws SQLException, IOException {
        Connection connection = UtilDAO.getConnection();
        String sql = "select * from user.consumer_feedback Inner JOIN user.supplier_appeal ON consumer_feedback.item_hash = supplier_appeal.item_hash where admin_is_read IS NULL  ";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        String sql1 = "select * from user.consumer_feedback   where is_appeal IS NULL";
        PreparedStatement preparedStatement1 = connection.prepareStatement(sql1);
        ResultSet resultSet1 = preparedStatement1.executeQuery();
        List<TraceFeedbackPO> list = new ArrayList<>();
        while (resultSet.next()) {
            TraceFeedbackPO feedbacks = fillFeedback(resultSet);
            feedbacks.setAppeal(resultSet.getBoolean("is_appeal"));
            feedbacks.setSupplierComplaint(resultSet.getString("supplier_comment"));
            feedbacks.setItemName(resultSet.getString("item_name"));
            list.add(feedbacks);
        }
        while (resultSet1.next()) {
            TraceFeedbackPO feedbacks = fillFeedback(resultSet1);
            feedbacks.setAppeal(false);
            feedbacks.setItemName(resultSet1.getString("item_name"));
            list.add(feedbacks);
        }
        return list;
    }

    private TraceFeedbackPO fillFeedback(ResultSet resultSet1) throws SQLException {
        TraceFeedbackPO feedbacks = new TraceFeedbackPO();
        feedbacks.setBuyer(resultSet1.getString("buyer_account"));
        feedbacks.setSeller(resultSet1.getString("seller_account"));
        feedbacks.setLikeOrReport(Objects.equals(resultSet1.getString("like_report"), "likes"));
        feedbacks.setItemHash(resultSet1.getString("item_hash"));
        feedbacks.setComment(resultSet1.getString("comment"));
        return feedbacks;
    }

}
