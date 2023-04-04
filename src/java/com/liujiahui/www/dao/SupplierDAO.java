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

import static com.liujiahui.www.util.UtilDAO.close;

/**
 * 供应商刀
 *
 * @author 刘家辉
 * @date 2023/04/04
 */
public class SupplierDAO {
    public ConsumerPO login(String userAccount, String userPassword) {
        try (Connection connection = UtilDAO.getConnection()) {
            PreparedStatement preparedStatement;
            preparedStatement = connection.prepareStatement("select * from user.suppliers  where user_name=? and password=?");
            return entertainUser(userAccount, userPassword, preparedStatement);
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    static ConsumerPO entertainUser(String userAccount, String userPassword, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, userAccount);
        preparedStatement.setString(2, userPassword);
        ResultSet set = preparedStatement.executeQuery();
        if (set.next()) {
            ConsumerPO consumer = new ConsumerPO();
            consumer.setPrivateKey(set.getString("private_key"));
            consumer.setAddress(set.getString("account_address"));
            consumer.setName(set.getString("user_name"));
            consumer.setGender(set.getString("gender"));
            consumer.setPhoneNumber(set.getString("phone_number"));
            consumer.setPassword(set.getString("password"));
            return consumer;
        } else {
            return null;
        }
    }

    public Boolean register(TraceRegisterBO traceRegisterBO) {
        try {
            Connection connection = UtilDAO.getConnection();
            getaExist("suppliers", traceRegisterBO.getName(), connection);
            TraceAccountOnContractDTO traceAccountOnContractDTO = TraceFactoryService.getTraceContractService().initByContract("suppliers");
            String sql = "insert into user.suppliers(user_name, gender, phone_number, `password`,private_key,account_address,address,likes,reports) values(?,?,?,?,?,?,?,?,?)";
            PreparedStatement preparedStatement = ConsumerDAO.setUser(traceRegisterBO, connection, traceAccountOnContractDTO, sql);
            preparedStatement.setString(7, traceRegisterBO.getAddress());
            preparedStatement.setLong(8, 0);
            preparedStatement.setLong(9, 0);
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
        String sql = "update user.suppliers set " + type + " = ? where user_name = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, change);
        preparedStatement.setString(2, name);
        preparedStatement.executeUpdate();
        UtilDAO.close(connection, preparedStatement, null);
    }

    public String getSupplierAccount(String name) {
        try {
            Connection connection = UtilDAO.getConnection();
            String sql = "select * from user.suppliers where user_name = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            ResultSet set = preparedStatement.executeQuery();
            if (set.next()) {
                close(connection, preparedStatement, set);
                return set.getString("account_address");
            }
            close(connection, preparedStatement, set);
            return null;
        } catch (SQLException | IOException e) {
            System.out.println("查询商家历史失败");
            throw new RuntimeException(e);
        }
    }

    public static Boolean getaExist(String table, String name, Connection connection) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet set = null;
        try {
            preparedStatement = connection.prepareStatement("select * from user." + table + " where user_name=?");
            preparedStatement.setString(1, name);
            set = preparedStatement.executeQuery();
            return set.next();
        } finally {
            close(null, preparedStatement, set);
        }
    }
}
