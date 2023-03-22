package com.liujiahui.www.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 跟踪供应商刀写下来
 *
 * @author 刘家辉
 * @date 2023/03/22
 */
public class TraceSupplierWriteDownDAO {
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


}
