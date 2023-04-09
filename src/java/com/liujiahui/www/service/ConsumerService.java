package com.liujiahui.www.service;

import com.liujiahui.www.entity.bo.TraceFeedbackBO;
import com.liujiahui.www.entity.dto.TraceItemStatusDTO;
import com.liujiahui.www.entity.dto.TraceRealAndOutItemDTO;
import com.liujiahui.www.entity.dto.TraceTransactionDTO;
import com.liujiahui.www.entity.po.ItemPO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 消费者服务
 *
 * @author 刘家辉
 * @date 2023/04/06
 */
public interface ConsumerService {
    /**
     * 显示项
     *
     * @return {@link Map}<{@link String}, {@link List}<{@link ItemPO}>>
     */
    Map<String, List<ItemPO>> showItem();

    /**
     * 购买物品
     *
     * @param index  指数
     * @param seller 卖方
     * @return {@link TraceTransactionDTO}
     */
    TraceTransactionDTO buyItem(String seller, BigDecimal index);

    /**
     * 检查通过散列
     *
     * @param hash 哈希
     * @return {@link TraceRealAndOutItemDTO}
     */
    TraceRealAndOutItemDTO checkByHash(String hash);

    /**
     * 检查状态
     *
     * @param hash1 hash1
     * @return {@link TraceItemStatusDTO}
     */
    TraceItemStatusDTO checkStatus(String hash1);

    /**
     * 供应商写服务
     *
     * @param traceFeedbackBO 跟踪反馈波
     */
    void supplierWriteDownService(TraceFeedbackBO traceFeedbackBO);

    /**
     * 返回项目
     *
     * @param hash2 hash2
     */
    void returnItem(String hash2);

    /**
     * 检查生活
     *
     * @param hash3 hash3
     * @return {@link List}<{@link TraceItemStatusDTO}>
     */
    List<TraceItemStatusDTO> checkLife(String hash3);
}
