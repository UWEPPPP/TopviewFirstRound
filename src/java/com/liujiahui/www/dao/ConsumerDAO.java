package com.liujiahui.www.dao;

import com.liujiahui.www.entity.bo.TraceRegisterBO;
import com.liujiahui.www.entity.dto.TraceAccountOnContractDTO;
import com.liujiahui.www.entity.dto.TraceInformationSaveDTO;
import com.liujiahui.www.entity.po.ConsumerPO;
import com.liujiahui.www.service.impl.TraceFactoryService;
import com.liujiahui.www.util.UtilDAO;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.liujiahui.www.dao.SupplierDAO.entertainUser;
import static com.liujiahui.www.dao.SupplierDAO.getaExist;
import static com.liujiahui.www.util.UtilDAO.close;

/**
 * 消费者刀
 *
 * @author 刘家辉
 * @date 2023/04/04
 */
public class ConsumerDAO {
    public ConsumerPO login(String userAccount, String userPassword) {
        try (Connection connection = UtilDAO.getConnection()) {
            PreparedStatement preparedStatement;
            preparedStatement = connection.prepareStatement("select * from user.consumer  where user_name=? and password=?");
            return entertainUser(userAccount, userPassword, preparedStatement);
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Boolean register(TraceRegisterBO traceRegisterBO){
         try {
             Connection connection = UtilDAO.getConnection();
             PreparedStatement preparedStatement;
                if (getaExist("consumer", traceRegisterBO.getName(), connection)) {
                    throw new RuntimeException("用户已存在");
                }
             TraceAccountOnContractDTO traceAccountOnContractDTO = TraceFactoryService.getTraceContractService().initByContract("consumer");
             String sql = "insert into user.suppliers(user_name, gender, phone_number, `password`,private_key,account_address) values(?,?,?,?,?,?)";
             preparedStatement = setUser(traceRegisterBO, connection, traceAccountOnContractDTO, sql);
             int result = preparedStatement.executeUpdate();
             close(connection, preparedStatement, null);
             return result > 0;
         } catch (SQLException | NoSuchPaddingException | IllegalBlockSizeException | IOException |
                  NoSuchAlgorithmException | BadPaddingException | InvalidKeyException e) {
             System.out.println("初始化失败");
             throw new RuntimeException(e);
         }
    }
    public void updatePersonalInformation(String type, String change) throws SQLException, IOException {
        Connection connection = UtilDAO.getConnection();
        String name = TraceInformationSaveDTO.getInstance().getUserName();
        String sql = "update user.consumer set " + type + " = ? where user_name = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, change);
        preparedStatement.setString(2, name);
        preparedStatement.executeUpdate();
        UtilDAO.close(connection, preparedStatement,null);
    }

    static PreparedStatement setUser(TraceRegisterBO traceRegisterBO, Connection connection, TraceAccountOnContractDTO traceAccountOnContractDTO, String sql) throws SQLException {
        PreparedStatement preparedStatement;
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, traceRegisterBO.getName());
        preparedStatement.setString(2, traceRegisterBO.getGender());
        preparedStatement.setString(3, traceRegisterBO.getPhone());
        preparedStatement.setString(4, traceRegisterBO.getPassword());
        preparedStatement.setString(5, traceAccountOnContractDTO.getPrivateKey());
        preparedStatement.setString(6, traceAccountOnContractDTO.getAccountAddress());
        return preparedStatement;
    }
}