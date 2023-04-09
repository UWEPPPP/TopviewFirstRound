package com.liujiahui.www.service;

import com.liujiahui.www.entity.bo.TraceLoginBO;
import com.liujiahui.www.entity.bo.TraceRegisterBO;
import com.liujiahui.www.entity.dto.TraceAccountOnContractDTO;
import com.liujiahui.www.entity.dto.UserSaveDTO;

import java.math.BigInteger;

/**
 * 用户注册和登录服务
 *
 * @author 刘家辉
 * @date 2023/03/24
 */
public interface RegisterOrLoginService {
    /**
     * 登录顺便获得余额
     *
     * @param privateKey 私钥
     * @return {@link BigInteger}
     */
    BigInteger loadByContract(String privateKey);

    /**
     * init契约式
     * 载入合约
     *
     * @param table 表格
     * @return {@link TraceAccountOnContractDTO}
     */
    TraceAccountOnContractDTO initByContract(String table);
    /**
     * 登录
     *
     * @param traceLoginBO 用户登录博
     * @return {@link UserSaveDTO}
     */
    UserSaveDTO login(TraceLoginBO traceLoginBO);

    /**
     * 注册
     *
     * @param traceRegisterBO 用户登记薄
     * @return {@link Boolean}
     */
    Boolean register(TraceRegisterBO traceRegisterBO);
}
