package com.liujiahui.www.dao;

import com.liujiahui.www.entity.bo.TraceRegisterBO;
import com.liujiahui.www.entity.po.ConsumerPO;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.liujiahui.www.util.UtilDAO.close;

public interface SupplierAccountDAO {
    static Boolean getaExist(String table, String name, Connection connection) throws SQLException {
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

    ConsumerPO login(String userAccount, String userPassword);

    Boolean register(TraceRegisterBO traceRegisterBO);

    void updatePersonalInformation(String type, String change) throws SQLException, IOException;

    String getSupplierAccount(String name);
}
