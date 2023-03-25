package com.liujiahui.www.dao;

import com.liujiahui.www.entity.po.TraceItemPO;

import java.util.List;

/**
 * 条件查询
 *
 * @author 刘家辉
 * @date 2023/03/26
 */
public interface TraceQueryDAO {
    /**
     * 价格区间查询
     *
     * @param max 最大值 min 最小值
     */
    List<TraceItemPO> queryByPrice(int max,int min);

    /**
     * 关键字查询
     *
     * @param keyword 关键字
     */
    List<TraceItemPO> queryByKeyword(String keyword);


    /**
     * 类型查询
     *
     * @param type 类型
     */
    List<TraceItemPO> queryByType(String type);

    /**
     * 卖家查询
     *
     * @param seller 卖家
     */
    List<TraceItemPO> queryBySeller(String seller);
}
