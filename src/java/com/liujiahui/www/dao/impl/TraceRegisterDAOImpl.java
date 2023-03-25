package com.liujiahui.www.dao.impl;

import com.liujiahui.www.dao.TraceRegisterDAO;
import com.liujiahui.www.dao.util.UtilDAO;
import com.liujiahui.www.entity.dto.TraceAccountOnContractDTO;
import com.liujiahui.www.entity.po.TraceSupplierPO;
import com.liujiahui.www.entity.po.TraceUserPO;
import com.liujiahui.www.service.impl.TraceFactoryImplService;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.liujiahui.www.dao.util.UtilDAO.close;

/**
 * 用户刀
 *
 * @author 刘家辉
 * @date 2023/03/16
 */
public class TraceRegisterDAOImpl implements TraceRegisterDAO {
    private static final TraceRegisterDAOImpl INSTANCE = new TraceRegisterDAOImpl();
    private TraceRegisterDAOImpl(){}
    public static TraceRegisterDAOImpl getInstance(){
        return INSTANCE;
    }
    @Override
    public Boolean register(TraceUserPO traceUserPO) throws SQLException, IOException {
        Connection connection = UtilDAO.getConnection();
        PreparedStatement preparedStatement;
        String table;
        String tableAndLimit;
        if(traceUserPO instanceof TraceSupplierPO){
            table = "suppliers";
            tableAndLimit = "suppliers(user_name, gender, phone_number, password,private_key,account_address,address,likes,reports) values(?,?,?,?,?,?,?,?,?)";
        }else{
            table = "consumer";
            tableAndLimit = "consumer(user_name, gender, phone_number, password,private_key,account_address) values(?,?,?,?,?,?)";
        }
        if(checkUserExist(table, traceUserPO.getName(),connection)){
            return false;
        }
        TraceAccountOnContractDTO traceAccountOnContractDTO = TraceFactoryImplService.getTraceContractService().initByContract(table);
        String sql="insert into user."+tableAndLimit;
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, traceUserPO.getName());
        preparedStatement.setString(2, traceUserPO.getGender());
        preparedStatement.setString(3, traceUserPO.getPhoneNumber());
        preparedStatement.setString(4, traceUserPO.getPassword());
        preparedStatement.setString(5, traceAccountOnContractDTO.getPrivateKey());
        preparedStatement.setString(6, traceAccountOnContractDTO.getAccountAddress());
        if(traceUserPO instanceof TraceSupplierPO){
            preparedStatement.setString(7, ((TraceSupplierPO) traceUserPO).getAddress());
            preparedStatement.setLong(8, 0);
            preparedStatement.setLong(9, 0);
        }
        preparedStatement.executeUpdate();
        close(connection, null, preparedStatement);
        return true;

    }
    @Override
    public Boolean checkUserExist(String table, String name, Connection connection) throws SQLException {
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
