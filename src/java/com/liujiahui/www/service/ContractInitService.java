package com.liujiahui.www.service;

import com.liujiahui.www.entity.dto.TraceAccountOnContractDTO;

import java.math.BigInteger;

/**
 * 跟踪合同服务
 *
 * @author 刘家辉
 * @date 2023/03/25
 */
public interface ContractInitService {
    /**
     * @param privateKey 私钥
     * @return {@link BigInteger}
     */
    BigInteger getBalance(String privateKey);

    /**
     * 载入合约
     *
     * @param table
     * @return {@link TraceAccountOnContractDTO}
     */
    TraceAccountOnContractDTO initByContract(String table);
}
