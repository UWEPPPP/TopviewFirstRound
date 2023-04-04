package com.liujiahui.www.dao;

import com.liujiahui.www.entity.po.UserPO;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
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
     * @param userPo 用户
     * @return {@link Boolean}
     * @throws SQLException sqlexception异常
     * @throws IOException  ioexception
     */
    Boolean register(UserPO userPo) throws SQLException, IOException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException;

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
