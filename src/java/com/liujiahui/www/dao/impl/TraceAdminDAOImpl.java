package com.liujiahui.www.dao.impl;

import com.liujiahui.www.dao.TraceAdminDAO;
import com.liujiahui.www.entity.po.TraceFeedbackPO;
import com.liujiahui.www.entity.po.TraceItemPO;
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
 * 跟踪管理
 *
 * @author 刘家辉
 * @date 2023/03/31
 */
public class TraceAdminDAOImpl implements TraceAdminDAO {
    private static final TraceAdminDAOImpl TRACE_ADMIN = new TraceAdminDAOImpl();

    private TraceAdminDAOImpl() {
    }

    static TraceAdminDAOImpl getInstance() {
        return TRACE_ADMIN;
    }

    @Override
    public List<TraceFeedbackPO> getAllFeedbackAndComplaint() throws SQLException, IOException {
        Connection connection = UtilDAO.getConnection();
        String sql = "select * from user.consumer_feedback Inner JOIN user.supplier_appeal ON consumer_feedback.item_hash = supplier_appeal.item_hash INNER JOIN user.item_show ON item_show.hash = supplier_appeal.item_hash where admin_is_read IS NULL  ";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        String sql1 = "select * from user.consumer_feedback INNER JOIN user.item_show ON item_hash=hash  where is_appeal IS NULL";
        PreparedStatement preparedStatement1 = connection.prepareStatement(sql1);
        ResultSet resultSet1 = preparedStatement1.executeQuery();
        List<TraceFeedbackPO> list = new ArrayList<>();
        while (resultSet.next()) {
            TraceFeedbackPO feedbacks = fillFeedback(resultSet);
            feedbacks.setAppeal(resultSet.getBoolean("is_appeal"));
            feedbacks.setSupplierComplaint(resultSet.getString("supplier_comment"));
            feedbacks.setItemName(resultSet.getString("name"));
            list.add(feedbacks);
        }
        while (resultSet1.next()) {
            TraceFeedbackPO feedbacks = fillFeedback(resultSet1);
            feedbacks.setAppeal(false);
            feedbacks.setItemName(resultSet1.getString("name"));
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

    @Override
    public TraceItemPO getSingleItem(String hash) throws SQLException, IOException {
        Connection connection = UtilDAO.getConnection();
        String sql = "select * from user.item_show INNER JOIN user.consumer_feedback ON item_show.hash=consumer_feedback.item_hash INNER JOIN user.item_behind ON item_behind.hash =consumer_feedback.item_hash where item_hash = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, hash);
        ResultSet resultSet = preparedStatement.executeQuery();
        TraceItemPO traceItemPo = new TraceItemPO();
        while (resultSet.next()) {
            traceItemPo.setId(resultSet.getInt("id"));
            traceItemPo.setName(resultSet.getString("name"));
            traceItemPo.setDescription(resultSet.getString("description"));
            traceItemPo.setOwner(resultSet.getString("buyer_account"));
            traceItemPo.setSeller(resultSet.getString("seller_account"));
            traceItemPo.setToken(resultSet.getInt("token"));
        }
        return traceItemPo;
    }

    @Override
    public void resolveBadLikeOrAppeal(String hash, Boolean result) throws SQLException, IOException {
        Connection connection = UtilDAO.getConnection();
        String sql = "update user.supplier_appeal set appeal_result = ?,admin_is_read = true where item_hash = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setBoolean(1, result);
        preparedStatement.setString(2, hash);
        preparedStatement.executeUpdate();
    }
}