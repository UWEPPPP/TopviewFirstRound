package com.liujiahui.www.service;

import com.liujiahui.www.entity.bo.TraceChangePersonalBO;
import com.liujiahui.www.entity.po.FeedbackPO;
import com.liujiahui.www.entity.po.ItemPO;

import java.util.List;

/**
 * 常用市场服务
 *
 * @author 刘家辉
 * @date 2023/04/04
 */
public interface CommonUsedMarketService {
    /**
     * 查询价格
     * 价格区间查询
     *
     * @param max    最大值 min 最小值
     * @param min    最小值
     * @param choice 选择
     * @return {@link List}<{@link ItemPO}>
     */
    List<ItemPO> queryByPrice(int max, int min, int choice);

    /**
     * 按关键字查询
     * 关键字查询
     *
     * @param keyword 关键字
     * @return {@link List}<{@link ItemPO}>
     */
    List<ItemPO> queryByKeyword(String keyword);


    /**
     * 按类型查询
     * 类型查询
     *
     * @param type 类型
     * @return {@link List}<{@link ItemPO}>
     */
    List<ItemPO> queryByType(String type);

    /**
     * 查询由卖方
     * 卖家查询
     *
     * @param seller 卖家
     * @return {@link List}<{@link ItemPO}>
     */
    List<ItemPO> queryBySeller(String seller);
    /**
     * 显示所有项
     *
     * @return {@link List}<{@link ItemPO}>
     */
    List<ItemPO> showAllItem();

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
     * @return {@link List}<{@link FeedbackPO}>
     */
    List<FeedbackPO> getHistory(String name);

    /**
     * 上诉结果展示
     *
     * @return {@link List}<{@link FeedbackPO}>
     */
    List<FeedbackPO> showAppealResult();
}
