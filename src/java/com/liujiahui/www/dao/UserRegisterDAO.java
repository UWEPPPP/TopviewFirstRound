package com.liujiahui.www.dao;

import com.liujiahui.www.entity.po.Supplier;
import com.liujiahui.www.entity.po.User;
import com.liujiahui.www.entity.dto.UserAccountOnContractDTO;
import com.liujiahui.www.service.ContractRegisterService;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.liujiahui.www.dao.UtilDAO.close;

/**
 * 用户刀
 *
 * @author 刘家辉
 * @date 2023/03/16
 */
public class UserRegisterDAO {
    public Boolean register(User user) throws SQLException, IOException {
        Connection connection = UtilDAO.getConnection();
        PreparedStatement preparedStatement;
        String table;
        String tableAndLimit;
        if(user instanceof Supplier){
            table = "suppliers";
            tableAndLimit = "suppliers(user_name, gender, phone_number, password,private_key,account_address,address) values(?,?,?,?,?,?,?)";
        }else{
            table = "consumer";
            tableAndLimit = "consumer(user_name, gender, phone_number, password,private_key,account_address) values(?,?,?,?,?,?)";
        }
        if(checkUserExist(table,user.getName(),connection)){
            return false;
        }
        UserAccountOnContractDTO userAccountOnContractDTO = ContractRegisterService.initByContract();
        String sql="insert into user."+tableAndLimit;
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, user.getName());
        preparedStatement.setString(2, user.getGender());
        preparedStatement.setString(3, user.getPhoneNumber());
        preparedStatement.setString(4, user.getPassword());
        preparedStatement.setString(5, userAccountOnContractDTO.getPrivateKey());
        preparedStatement.setString(6, userAccountOnContractDTO.getAccountAddress());
        if(user instanceof Supplier){
            preparedStatement.setString(7, ((Supplier) user).getAddress());
        }
        preparedStatement.executeUpdate();
        close(connection, null, preparedStatement);
        return true;

    }
    public Boolean checkUserExist(String table,String name,Connection connection) throws SQLException {
        PreparedStatement preparedStatement=null;
        ResultSet set=null;
        try {
            preparedStatement = connection.prepareStatement("select * from user." + table + " where user_name=?");
            preparedStatement.setString(1, name);
            set = preparedStatement.executeQuery();
            return set.next();
        } finally {
            close(null,set , preparedStatement);
        }
    }
}
