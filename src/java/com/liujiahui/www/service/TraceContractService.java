package com.liujiahui.www.service;

import com.liujiahui.www.entity.dto.TraceAccountOnContractDTO;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;

import java.math.BigInteger;

/**
 * 跟踪合同服务
 *
 * @author 刘家辉
 * @date 2023/03/25
 */
public interface TraceContractService {
    /**
     * @param privateKey 私钥
     * @return {@link BigInteger}
     * @throws ContractException 合同例外
     * @description 在使用用户密钥载入合约时顺便获取余额
     */
    BigInteger getBalance(String privateKey) throws ContractException;

    /**
     * 载入合约
     * @param table
     * @return {@link TraceAccountOnContractDTO}
     */
    TraceAccountOnContractDTO initByContract(String table);
}
