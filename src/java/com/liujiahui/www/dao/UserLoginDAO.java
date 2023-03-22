package com.liujiahui.www.dao;

import com.liujiahui.www.entity.dto.UserAccountOnJavaDTO;
import com.liujiahui.www.entity.dto.UserInformationSaveDTO;
import com.liujiahui.www.service.ContractLoginAndRegisterService;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * 用户登录持久层
 *
 * @author 刘家辉
 * @date 2023/03/17
 */
public class UserLoginDAO {
    public static UserInformationSaveDTO login(UserAccountOnJavaDTO account) throws SQLException, IOException, ContractException {
        String userAccount=account.getAccount();
        String userPassword=account.getPassword();
        String identity=account.getIdentity();
        try (Connection connection = UtilDAO.getConnection()) {
            PreparedStatement preparedStatement;
            preparedStatement = connection.prepareStatement("select * from user." + identity + " where user_name=? and password=?");
            preparedStatement.setString(1, userAccount);
            preparedStatement.setString(2, userPassword);
            ResultSet set = preparedStatement.executeQuery();
            if (set.next()) {
                UserInformationSaveDTO user = UserInformationSaveDTO.getInstance();
                String balance = String.valueOf(ContractLoginAndRegisterService.getBalance(set.getString("private_key")));
                user.setUserName(set.getString("user_name"));
                user.setBalance(balance);
                user.setGender(set.getString("gender"));
                user.setPhone(set.getString("phone_number"));
                user.setIdentity(identity);
                user.setContractAccount(set.getString("account_address"));
                return user;
            } else {
                return null;
            }
        }
    }

}
