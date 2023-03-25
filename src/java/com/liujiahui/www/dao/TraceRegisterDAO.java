package com.liujiahui.www.dao;

import com.liujiahui.www.entity.po.TraceUserPO;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * 注册持久层
 *
 * @author 刘家辉
 * @date 2023/03/25
 */
public interface TraceRegisterDAO {
    /**
     * 注册
     *
     * @param traceUserPO 用户
     * @return {@link Boolean}
     * @throws SQLException sqlexception异常
     * @throws IOException  ioexception
     */
    Boolean register(TraceUserPO traceUserPO) throws SQLException, IOException;

    /**
     * 检查用户存在
     *
     * @param table      表格
     * @param name       名字
     * @param connection 连接
     * @return {@link Boolean}
     * @throws SQLException sqlexception异常
     */
    Boolean checkUserExist(String table, String name, Connection connection) throws SQLException;
}
