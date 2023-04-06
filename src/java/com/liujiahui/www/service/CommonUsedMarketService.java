package com.liujiahui.www.service;

import com.liujiahui.www.entity.bo.TraceChangePersonalBO;
import com.liujiahui.www.entity.po.TraceFeedbackPO;
import com.liujiahui.www.entity.po.TraceItemPO;

import java.util.List;

/**
 * 常用市场服务
 *
 * @author 刘家辉
 * @date 2023/04/04
 */
public interface CommonUsedMarketService {
    /**
     * 显示所有项
     *
     * @return {@link List}<{@link TraceItemPO}>
     */
    List<TraceItemPO> showAllItem();

    /**
     * 更新个人信息
     *
     * @param userChangeServiceBO 用户改变服务薄
     */
    void updatePersonalMessage(TraceChangePersonalBO userChangeServiceBO);

    /**
     * 获得商家历史
     *
     * @param name 名字
     * @return {@link List}<{@link TraceFeedbackPO}>
     */
    List<TraceFeedbackPO> getHistory(String name);

    /**
     * 上诉结果展示
     *
     * @return {@link List}<{@link TraceFeedbackPO}>
     */
    List<TraceFeedbackPO> showAppealResult();
}