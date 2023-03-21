package com.liujiahui.www.dao;

import com.liujiahui.www.entity.dto.UserInformationSaveDTO;
import org.fisco.bcos.sdk.utils.Numeric;

import java.io.IOException;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 用户买刀
 *
 * @author 刘家辉
 * @date 2023/03/21
 */
public class UserBuyDAO {
    public static void buyItem(String accountAddress, BigInteger id, byte[] hash) throws SQLException, IOException, SQLException, IOException {
        Connection connection = UtilDAO.getConnection();
        String account = UserInformationSaveDTO.getInstance().getAccount();
        String sql = "update user.item set isSold = ?,owner = ?,hash=? where owner = ? and `index` = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setBoolean(1,true);
        preparedStatement.setString(2,account);
        String hexString = Numeric.toHexString(hash);
        preparedStatement.setString(3,hexString);
        preparedStatement.setString(4,accountAddress);
        preparedStatement.setBigDecimal(5,new java.math.BigDecimal(id));
        preparedStatement.executeUpdate();
        UtilDAO.close(connection,null,preparedStatement);
    }
}
