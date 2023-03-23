package com.liujiahui.www.dao;

import com.liujiahui.www.entity.po.Feedback;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 跟踪供应商刀写下来
 *
 * @author 刘家辉
 * @date 2023/03/22
 */
public class UserTraceHistoryDAO {
    public static void writeDown(String seller, String buyer, String comment, int choice, String itemHash) throws SQLException, IOException {
        Connection connection = UtilDAO.getConnection();
        String type;
        if(choice == 1){
             type ="likes";
        }else {
             type ="reports";
        }
        String sql = "update user.suppliers set "+type+" = "+type+" + 1 where account_address = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,seller);
        preparedStatement.executeUpdate();
        String sql1 = "insert into user.consumer_feedback (seller_account,buyer_account,comment,like_report,item) values (?,?,?,?,?)";
        PreparedStatement preparedStatement1 = connection.prepareStatement(sql1);
        preparedStatement1.setString(1,seller);
        preparedStatement1.setString(2,buyer);
        preparedStatement1.setString(3,comment);
        preparedStatement1.setString(4,type);
        preparedStatement1.setString(5,itemHash);
        preparedStatement1.executeUpdate();
        UtilDAO.close(connection,null,preparedStatement);
        UtilDAO.close(null,null,preparedStatement1);
    }
    public static List<Feedback> getHistory(String name) throws SQLException, IOException {
        Connection connection = UtilDAO.getConnection();
        String sql = "select * from user.suppliers where user_name = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,name);
        ResultSet set = preparedStatement.executeQuery();
        String accountAddress = null;
        if(set.next()){
            accountAddress = set.getString("account_address");
        }
        String sql1 = "SELECT t1.*, t2.name FROM user.consumer_feedback t1 INNER JOIN user.item t2 ON t1.item = t2.hash where seller_account=?;";
        PreparedStatement preparedStatement1 = connection.prepareStatement(sql1);
        preparedStatement1.setString(1,accountAddress);
        ResultSet set1 = preparedStatement1.executeQuery();
        List<Feedback> list= new ArrayList<>();
        while(set1.next()) {
            Feedback feedback = new Feedback();
            feedback.setBuyer(set1.getString("buyer_account"));
            feedback.setSeller(set1.getString("seller_account"));
            if (set1.getString("like_report").equals("like")) {
                feedback.setLikeOrReport(true);
            }else{
                feedback.setLikeOrReport(false);
            }
            feedback.setItemHash(set1.getString("item"));
            feedback.setComment(set1.getString("comment"));
            feedback.setItemName(set1.getString("itemName"));
            list.add(feedback);
        }
        UtilDAO.close(connection,set,preparedStatement);
        UtilDAO.close(null,set,preparedStatement1);
        return list;
    }

}
