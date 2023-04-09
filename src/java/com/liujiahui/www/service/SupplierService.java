package com.liujiahui.www.service;

import com.liujiahui.www.entity.bo.TraceFeedbackBO;
import com.liujiahui.www.entity.bo.TraceItemBO;
import com.liujiahui.www.entity.bo.TraceItemUpdateBO;
import com.liujiahui.www.entity.po.FeedbackPO;
import com.liujiahui.www.entity.po.ItemPO;

import java.util.List;
import java.util.Map;

/**
 * 供应商服务
 *
 * @author 刘家辉
 * @date 2023/04/09
 */
public interface SupplierService {

    /**
     * 添加物品
     *
     * @param traceItemBO 跟踪项目博
     */
    void addItem(TraceItemBO traceItemBO);

    /**
     * 更新项目
     *
     * @param traceItemUpdateBO 跟踪项目更新博
     */
    void updateItem(TraceItemUpdateBO traceItemUpdateBO);

    /**
     * 更新物流
     *
     * @param index     指数
     * @param logistics 物流
     * @param status    状态
     */
    void updateLogistics(int index, String logistics, int status);

    /**
     * 显示项
     *
     * @return {@link Map}<{@link String}, {@link List}<{@link ItemPO}>>
     */
    Map<String, List<ItemPO>> showItem();

    /**
     * 删除项目
     *
     * @param index  指数
     * @param choice 选择
     */
    void removeItem(int index, Boolean choice);

    /**
     * 显示反馈
     *
     * @return {@link List}<{@link FeedbackPO}>
     */
    List<FeedbackPO> showFeedback();

    /**
     * 展示牌
     *
     * @return {@link Integer}
     */
    Integer showToken();

    /**
     * 吸引力反馈
     *
     * @param traceFeedbackBO 跟踪反馈波
     */
    void appealFeedback(TraceFeedbackBO traceFeedbackBO);
}
