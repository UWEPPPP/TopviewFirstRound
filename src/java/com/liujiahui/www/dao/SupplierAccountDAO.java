package com.liujiahui.www.dao;

import com.liujiahui.www.entity.bo.TraceRegisterBO;
import com.liujiahui.www.entity.po.ConsumerPO;
import com.liujiahui.www.entity.po.UserPO;
import com.liujiahui.www.util.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 供应商账户刀
 *
 * @author 刘家辉
 * @date 2023/04/06
 */
public interface SupplierAccountDAO {
    /**
     * gata存在
     *
     * @param table      表格
     * @param name       名字
     * @param connection 连接
     * @return {@link Boolean}
     * @throws SQLException sqlexception异常
     */
    static Boolean getaExist(String table, String name, Connection connection) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet set = null;
        try {
            preparedStatement = connection.prepareStatement("select * from user." + table + " where user_name=?");
            preparedStatement.setString(1, name);
            set = preparedStatement.executeQuery();
            return set.next();
        } finally {
            ConnectionPool.close(preparedStatement, set);
        }
    }

    /**
     * 登录
     *
     * @param userAccount  用户账户
     * @param userPassword 用户密码
     * @return {@link ConsumerPO}
     */
    UserPO login(String userAccount, String userPassword);

    /**
     * 注册
     *
     * @param traceRegisterBO 跟踪登记薄
     * @return {@link Boolean}
     */
    Boolean register(TraceRegisterBO traceRegisterBO);

    /**
     * 更新个人信息
     *
     * @param type   类型
     * @param change 改变
     * @return {@link Boolean}
     * @throws SQLException
     */
    Boolean updatePersonalInformation(String type, String change) throws SQLException;

    /**
     * 得到供应商账户
     *
     * @param name 名字
     * @return {@link String}
     */
    String getSupplierAccount(String name);
}
