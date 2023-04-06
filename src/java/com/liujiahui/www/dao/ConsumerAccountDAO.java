package com.liujiahui.www.dao;

import com.liujiahui.www.entity.bo.TraceRegisterBO;
import com.liujiahui.www.entity.po.ConsumerPO;

import java.io.IOException;
import java.sql.SQLException;

/**
 * 消费者账户刀
 *
 * @author 刘家辉
 * @date 2023/04/05
 */
public interface ConsumerAccountDAO {
    /**
     * 登录
     *
     * @param userAccount  用户账户
     * @param userPassword 用户密码
     * @return {@link ConsumerPO}
     */
    ConsumerPO login(String userAccount, String userPassword);

    /**
     * 注册
     *
     * @param traceRegisterBO 跟踪登记薄
     * @return {@link Boolean}
     * @throws SQLException sqlexception异常
     */
    Boolean register(TraceRegisterBO traceRegisterBO) throws SQLException;

    /**
     * 更新个人信息
     *
     * @param type   类型
     * @param change 改变
     */
    Boolean updatePersonalInformation(String type, String change);
}
