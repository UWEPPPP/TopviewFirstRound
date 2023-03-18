package com.liujiahui.www.dao;

import com.liujiahui.www.entity.dto.UserInformationSaveDTO;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 用户改变刀
 *
 * @author 刘家辉
 * @date 2023/03/18
 */
public class UserChangePersonalDAO {
    public static void change(String type, String change,String identity) throws SQLException, IOException {
        Connection connection = UtilDAO.getConnection();
        String name = UserInformationSaveDTO.getInstance().getUserName();
        String sql = "update user."+identity+" set "+type + " = ? where user_name = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,change);
        preparedStatement.setString(2,name);
        preparedStatement.executeUpdate();
        UtilDAO.close(connection,null,preparedStatement);
    }
}
