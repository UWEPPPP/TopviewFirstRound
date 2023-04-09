package com.liujiahui.www.dao.impl;

import com.liujiahui.www.dao.ConsumerAccountDAO;
import com.liujiahui.www.entity.bo.TraceRegisterBO;
import com.liujiahui.www.entity.dto.TraceAccountOnContractDTO;
import com.liujiahui.www.entity.dto.UserSaveDTO;
import com.liujiahui.www.entity.po.UserPO;
import com.liujiahui.www.service.factory.TraceFactoryService;
import com.liujiahui.www.util.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static com.liujiahui.www.dao.SupplierAccountDAO.getaExist;
import static com.liujiahui.www.dao.impl.SupplierAccountDAOImpl.entertainUser;
import static com.liujiahui.www.util.ConnectionPool.close;


/**
 * 消费者刀
 *
 * @author 刘家辉
 * @date 2023/04/04
 */
public class ConsumerAccountDAOImpl implements ConsumerAccountDAO {
    static Boolean changeAccount(String change, Connection connection, String name, String sql) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, change);
        preparedStatement.setString(2, name);
        int result = preparedStatement.executeUpdate();
        close(preparedStatement, null);
        ConnectionPool.getInstance().releaseConnection(connection);
        return result > 0;
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

    @Override
    public UserPO login(String userAccount, String userPassword) {
        Connection connection;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement preparedStatement;
            preparedStatement = connection.prepareStatement("select * from user.consumer  where user_name=? and password=?");
            ConnectionPool.getInstance().releaseConnection(connection);
            return entertainUser(userAccount, userPassword, preparedStatement);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Boolean register(TraceRegisterBO traceRegisterBO) throws SQLException {

        Connection connection = ConnectionPool.getInstance().getConnection();
        PreparedStatement preparedStatement;
        String check = "consumer";
        if (getaExist(check, traceRegisterBO.getName(), connection)) {
            throw new RuntimeException("用户已存在");
        }
        TraceAccountOnContractDTO traceAccountOnContractDTO = TraceFactoryService.getTraceRegisterAndLoginService().initByContract("consumer");
        String sql = "insert into user.suppliers(user_name, gender, phone_number, `password`,private_key,account_address) values(?,?,?,?,?,?)";
        preparedStatement = setUser(traceRegisterBO, connection, traceAccountOnContractDTO, sql);
        int result = preparedStatement.executeUpdate();
        close(preparedStatement, null);
        ConnectionPool.getInstance().releaseConnection(connection);
        return result > 0;
    }

    @Override
    public Boolean updatePersonalInformation(String type, String change) {
        try {
            Connection connection = ConnectionPool.getInstance().getConnection();
            String name = UserSaveDTO.getInstance().getUserName();
            String sql = "update user.consumer set " + type + " = ? where user_name = ?";
            return changeAccount(change, connection, name, sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}