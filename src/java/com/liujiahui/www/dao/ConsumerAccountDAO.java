package com.liujiahui.www.dao;

import com.liujiahui.www.entity.bo.TraceRegisterBO;
import com.liujiahui.www.entity.po.ConsumerPO;

import java.io.IOException;
import java.sql.SQLException;

public interface ConsumerAccountDAO {
    ConsumerPO login(String userAccount, String userPassword);

    Boolean register(TraceRegisterBO traceRegisterBO);

    void updatePersonalInformation(String type, String change) throws SQLException, IOException;
}
