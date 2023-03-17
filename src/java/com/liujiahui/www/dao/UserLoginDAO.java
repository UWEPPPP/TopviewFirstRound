package com.liujiahui.www.dao;

import com.liujiahui.www.entity.po.UserAccountOnJavaBO;
import com.liujiahui.www.entity.po.UserAfterLoginBO;
import com.liujiahui.www.service.ContractLoginService;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.liujiahui.www.dao.UtilDAO.close;

/**
 * 用户登录持久层
 *
 * @author 刘家辉
 * @date 2023/03/17
 */
public class UserLoginDAO {
    public static UserAfterLoginBO login(UserAccountOnJavaBO account) throws SQLException, IOException, ContractException {
        String userAccount=account.getAccount();
        String userPassword=account.getPassword();
        String identity=account.getIdentity();
        Connection connection = UtilDAO.getConnection();
        PreparedStatement preparedStatement;
        if(!checkUserRight(identity,userAccount,userPassword,connection)){
            return null;
        }
        try {
            preparedStatement = connection.prepareStatement("select * from user." + identity + " where user_name=?");
            preparedStatement.setString(1, userAccount);
            ResultSet set = preparedStatement.executeQuery();
            set.next();
            UserAfterLoginBO user = new UserAfterLoginBO();
            String balance= String.valueOf(ContractLoginService.getBalance(set.getString("account_address"),set.getString("private_key")));
            user.setName(set.getString("user_name"));
            user.setAccountAddress(set.getString("account_address"));
            user.setBalance(balance);
            return user;
        } finally {
            connection.close();
        }
    }
    public static Boolean checkUserRight(String table, String name, String password, Connection connection) throws SQLException {
        PreparedStatement preparedStatement=null;
        ResultSet set=null;
        try {
            preparedStatement = connection.prepareStatement("select * from user." + table + " where user_name=? and password=?");
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, password);
            set = preparedStatement.executeQuery();
            return set.next();
        } finally {
            close(null,set , preparedStatement);
        }
    }

}
