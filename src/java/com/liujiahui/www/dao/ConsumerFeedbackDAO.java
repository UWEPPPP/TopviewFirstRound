package com.liujiahui.www.dao;

import com.liujiahui.www.entity.po.TraceFeedbackPO;
import com.liujiahui.www.util.UtilDAO;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 刘家辉
 * @date 2023/04/04
 */
public class ConsumerFeedbackDAO {
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

    public List<TraceFeedbackPO> getSupplierHistory(String accountAddress){
        try {
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
        } catch (SQLException | IOException e) {
            System.out.println("连接异常");
            throw new RuntimeException(e);
        }

    }

}
