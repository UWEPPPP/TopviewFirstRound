package com.liujiahui.www.service;

import com.liujiahui.www.entity.bo.TraceFeedbackBO;
import com.liujiahui.www.entity.dto.TraceItemStatusDTO;
import com.liujiahui.www.entity.dto.TraceRealAndOutItemDTO;
import com.liujiahui.www.entity.dto.TraceTransactionDTO;
import com.liujiahui.www.entity.po.TraceItemPO;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface ConsumerService {
    /**
     * 显示项
     *
     * @return {@link Map}<{@link String}, {@link List}<{@link TraceItemPO}>>
     * @throws SQLException      sqlexception异常
     * @throws IOException       ioexception
     * @throws ContractException 合同例外
     */
    Map<String, List<TraceItemPO>> showItem() throws SQLException, IOException, ContractException;

    /**
     * 购买物品
     *
     * @param seller
     * @param index  指数
     * @return {@link TraceTransactionDTO}
     * @throws ContractException 合同例外
     * @throws SQLException      sqlexception异常
     * @throws IOException       ioexception
     */
    TraceTransactionDTO buyItem(String seller, BigDecimal index) throws ContractException, SQLException, IOException;

    /**
     * 检查通过散列
     *
     * @param hash
     * @return {@link TraceRealAndOutItemDTO}
     * @throws ContractException 合同例外
     */
    TraceRealAndOutItemDTO checkByHash(String hash) throws ContractException;

    /**
     * 检查状态
     *
     * @param hash1
     * @return {@link TraceItemStatusDTO}
     * @throws ContractException 合同例外
     */
    TraceItemStatusDTO checkStatus(String hash1) throws ContractException;

    /**
     * 供应商写服务
     *
     * @param traceFeedbackBO 跟踪反馈波
     * @throws SQLException sqlexception异常
     * @throws IOException  ioexception
     */
    void supplierWriteDownService(TraceFeedbackBO traceFeedbackBO) throws SQLException, IOException;

    /**
     * 返回项目
     *
     * @param hash2 hash2
     * @throws SQLException sqlexception异常
     * @throws IOException  ioexception
     */
    void returnItem(String hash2) throws SQLException, IOException;

    /**
     * 检查生活
     *
     * @param hash3 hash3
     * @return {@link List}<{@link TraceItemStatusDTO}>
     * @throws ContractException 合同例外
     */
    List<TraceItemStatusDTO> checkLife(String hash3) throws ContractException;
}
