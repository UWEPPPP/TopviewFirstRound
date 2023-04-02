package com.liujiahui.www.dao.impl;

import com.liujiahui.www.dao.TraceAdminDAO;
import com.liujiahui.www.dao.util.UtilDAO;
import com.liujiahui.www.entity.po.TraceFeedbackPO;
import com.liujiahui.www.entity.po.TraceItemPO;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 跟踪管理daoimpl
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
        String sql = "select * from user.consumer_feedback Inner JOIN user.item_show ON item = item_show.hash";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<TraceFeedbackPO> list = new ArrayList<>();
        while (resultSet.next()) {
            TraceFeedbackPO feedbacks = new TraceFeedbackPO();
            feedbacks.setBuyer(resultSet.getString("buyer_account"));
            feedbacks.setSeller(resultSet.getString("seller_account"));
            feedbacks.setLikeOrReport(Objects.equals(resultSet.getString("like_or_report"), "likes"));
            feedbacks.setItemHash(resultSet.getString("item"));
            feedbacks.setItemName(resultSet.getString("name"));
            feedbacks.setComment(resultSet.getString("comment"));
            feedbacks.setAppeal(resultSet.getBoolean("is_appeal"));
            feedbacks.setSupplierComplaint(resultSet.getString("supplier_comment"));
            list.add(feedbacks);
        }
        return list;
    }

    @Override
    public TraceItemPO getSingleItem(String hash) throws SQLException, IOException {
        Connection connection = UtilDAO.getConnection();
        String sql = "select * from user.item_show INNER JOIN user.consumer_feedback ON item_show.hash=consumer_feedback.item  where hash = ?";
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
            traceItemPo.setToken(resultSet.getInt("price"));
        }
        return traceItemPo;
    }

    @Override
    public void resolveBadLikeOrAppeal(String hash, Boolean result) throws SQLException, IOException {
        Connection connection = UtilDAO.getConnection();
        String sql = "update user.consumer_feedback set appeal_result = ? where item = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setBoolean(1, result);
        preparedStatement.setString(2, hash);
        preparedStatement.executeUpdate();
    }
}