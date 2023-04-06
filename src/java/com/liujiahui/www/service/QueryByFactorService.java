package com.liujiahui.www.service;

import com.liujiahui.www.entity.po.ItemPO;

import java.util.List;

/**
 * 跟踪查询服务
 *
 * @author 刘家辉
 * @date 2023/03/26
 */
public interface QueryByFactorService {
    /**
     * 价格区间查询
     *
     * @param max 最大值 min 最小值
     */
    List<ItemPO> queryByPrice(int max, int min, int choice);

    /**
     * 关键字查询
     *
     * @param keyword 关键字
     */
    List<ItemPO> queryByKeyword(String keyword);


    /**
     * 类型查询
     *
     * @param type 类型
     */
    List<ItemPO> queryByType(String type);

    /**
     * 卖家查询
     *
     * @param seller 卖家
     */
    List<ItemPO> queryBySeller(String seller);

}
