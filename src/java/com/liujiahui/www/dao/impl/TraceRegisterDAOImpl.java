package com.liujiahui.www.dao.impl;

import com.liujiahui.www.dao.ConsumerDAO;
import com.liujiahui.www.dao.SupplierDAO;
import com.liujiahui.www.dao.TraceRegisterDAO;
import com.liujiahui.www.entity.dto.TraceAccountOnContractDTO;
import com.liujiahui.www.entity.po.SupplierPO;
import com.liujiahui.www.entity.po.UserPO;
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

import static com.liujiahui.www.dao.SupplierDAO.getaExist;
import static com.liujiahui.www.util.UtilDAO.close;

/**
 * 用户刀
 *
 * @author 刘家辉
 * @date 2023/03/16
 */
public class TraceRegisterDAOImpl implements TraceRegisterDAO {
    private static final TraceRegisterDAOImpl INSTANCE = new TraceRegisterDAOImpl();

    private TraceRegisterDAOImpl() {
    }

    public static TraceRegisterDAOImpl getInstance() {
        return INSTANCE;
    }


    @Override
    public Boolean register(UserPO userPo) throws SQLException, IOException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        Connection connection = UtilDAO.getConnection();
        PreparedStatement preparedStatement;
        String table;
        String tableAndLimit;
        if (userPo instanceof SupplierPO) {
            table = "suppliers";
            tableAndLimit = "suppliers(user_name, gender, phone_number, password,private_key,account_address,address,likes,reports) values(?,?,?,?,?,?,?,?,?)";
        } else {
            table = "consumer";
            tableAndLimit = "consumer(user_name, gender, phone_number, password,private_key,account_address) values(?,?,?,?,?,?)";
        }
        if (checkUserExist(table, userPo.getName(), connection)) {
            return false;
        }
        TraceAccountOnContractDTO traceAccountOnContractDTO = TraceFactoryService.getTraceContractService().initByContract(table);
        String sql = "insert into user." + tableAndLimit;
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, userPo.getName());
        preparedStatement.setString(2, userPo.getGender());
        preparedStatement.setString(3, userPo.getPhoneNumber());
        preparedStatement.setString(4, userPo.getPassword());
        preparedStatement.setString(5, traceAccountOnContractDTO.getPrivateKey());
        preparedStatement.setString(6, traceAccountOnContractDTO.getAccountAddress());
        if (userPo instanceof SupplierPO) {
            preparedStatement.setString(7, ((SupplierPO) userPo).getAddress());
            preparedStatement.setLong(8, 0);
            preparedStatement.setLong(9, 0);
        }
        preparedStatement.executeUpdate();
        close(connection,  preparedStatement,null);
        return true;

    }

    @Override
    public Boolean checkUserExist(String table, String name, Connection connection) throws SQLException {
        return getaExist(table, name, connection);
    }
}
