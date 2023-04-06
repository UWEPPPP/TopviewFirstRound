package com.liujiahui.www.controller;

import com.liujiahui.www.entity.po.ItemPO;
import com.liujiahui.www.service.QueryByFactorService;
import com.liujiahui.www.service.impl.TraceFactoryService;

import java.util.List;

/**
 * 跟踪查询控制器
 *
 * @author 刘家辉
 * @date 2023/03/26
 */
public class TraceQueryController {
    private static final QueryByFactorService TRACE_QUERY_SERVICE = TraceFactoryService.getTraceQueryService();

    public List<ItemPO> queryByPrice(int max, int min, int choice) {
        return TRACE_QUERY_SERVICE.queryByPrice(max, min, choice);
    }

    public List<ItemPO> queryByKeyword(String keyword) {
        return TRACE_QUERY_SERVICE.queryByKeyword(keyword);
    }


    public List<ItemPO> queryByType(String type) {
        return TRACE_QUERY_SERVICE.queryByType(type);
    }

    public List<ItemPO> queryBySeller(String seller) {
        return TRACE_QUERY_SERVICE.queryBySeller(seller);
    }
}
