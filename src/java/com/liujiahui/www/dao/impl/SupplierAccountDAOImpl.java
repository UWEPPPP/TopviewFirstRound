package com.liujiahui.www.dao.impl;

import com.liujiahui.www.dao.SupplierAccountDAO;
import com.liujiahui.www.entity.bo.TraceRegisterBO;
import com.liujiahui.www.entity.dto.TraceAccountOnContractDTO;
import com.liujiahui.www.entity.dto.UserSaveDTO;
import com.liujiahui.www.entity.po.UserPO;
import com.liujiahui.www.service.impl.TraceFactoryService;
import com.liujiahui.www.util.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.liujiahui.www.dao.impl.ConsumerAccountDAOImpl.changeAccount;
import static com.liujiahui.www.util.ConnectionPool.close;


/**
 * 供应商刀
 *
 * @author 刘家辉
 * @date 2023/04/04
 */
public class SupplierAccountDAOImpl implements SupplierAccountDAO {
    public static UserPO entertainUser(String userAccount, String userPassword, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, userAccount);
        preparedStatement.setString(2, userPassword);
        ResultSet set = preparedStatement.executeQuery();
        if (set.next()) {
            UserPO user = new UserPO();
            user.setPrivateKey(set.getString("private_key"));
            user.setAddress(set.getString("account_address"));
            user.setName(set.getString("user_name"));
            user.setGender(set.getString("gender"));
            user.setPhoneNumber(set.getString("phone_number"));
            user.setPassword(set.getString("password"));
            close(preparedStatement, set);
            return user;
        } else {
            return null;
        }
    }

    @Override
    public UserPO login(String userAccount, String userPassword) {
        try {
            Connection connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement preparedStatement;
            preparedStatement = connection.prepareStatement("select * from user.suppliers  where user_name=? and password=?");
            ConnectionPool.getInstance().releaseConnection(connection);
            return entertainUser(userAccount, userPassword, preparedStatement);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Boolean register(TraceRegisterBO traceRegisterBO) {
        try {
            Connection connection = ConnectionPool.getInstance().getConnection();
            SupplierAccountDAO.getaExist("suppliers", traceRegisterBO.getName(), connection);
            TraceAccountOnContractDTO traceAccountOnContractDTO = TraceFactoryService.getTraceContractService().initByContract("suppliers");
            String sql = "insert into user.suppliers(user_name, gender, phone_number, `password`,private_key,account_address,address,likes,reports) values(?,?,?,?,?,?,?,?,?)";
            PreparedStatement preparedStatement = ConsumerAccountDAOImpl.setUser(traceRegisterBO, connection, traceAccountOnContractDTO, sql);
            preparedStatement.setString(7, traceRegisterBO.getAddress());
            preparedStatement.setLong(8, 0);
            preparedStatement.setLong(9, 0);
            int result = preparedStatement.executeUpdate();
            close(preparedStatement, null);
            ConnectionPool.getInstance().releaseConnection(connection);
            return result > 0;
        } catch (SQLException e) {
            System.out.println("初始化失败");
            throw new RuntimeException(e);
        }
    }

    @Override
    public Boolean updatePersonalInformation(String type, String change) {
        try {
            Connection connection = ConnectionPool.getInstance().getConnection();
            String name = UserSaveDTO.getInstance().getUserName();
            String sql = "update user.suppliers set " + type + " = ? where user_name = ?";
            return changeAccount(change, connection, name, sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getSupplierAccount(String name) {
        try {
            Connection connection = ConnectionPool.getInstance().getConnection();
            String sql = "select * from user.suppliers where user_name = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            ResultSet set = preparedStatement.executeQuery();
            if (set.next()) {
                String accountAddress = set.getString("account_address");
                close(preparedStatement, set);
                ConnectionPool.getInstance().releaseConnection(connection);
                return accountAddress;
            }
            throw new RuntimeException("查询失败");
        } catch (SQLException e) {
            System.out.println("查询商家历史失败");
            throw new RuntimeException(e);
        }
    }

}
