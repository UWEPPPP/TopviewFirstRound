package com.liujiahui.www.controller;

import com.liujiahui.www.entity.po.TraceItemPO;
import com.liujiahui.www.service.TraceQueryService;
import com.liujiahui.www.service.impl.TraceFactoryImplService;

import java.util.List;

/**
 * 跟踪查询控制器
 *
 * @author 刘家辉
 * @date 2023/03/26
 */
public class TraceQueryController {
    private static final TraceQueryService TRACE_QUERY_SERVICE = TraceFactoryImplService.getTraceQueryService();

    public List<TraceItemPO> queryByPrice(int max, int min, int choice) {
        return TRACE_QUERY_SERVICE.queryByPrice(max, min, choice);
    }

    public List<TraceItemPO> queryByKeyword(String keyword) {
        return TRACE_QUERY_SERVICE.queryByKeyword(keyword);
    }


    public List<TraceItemPO> queryByType(String type) {
        return TRACE_QUERY_SERVICE.queryByType(type);
    }

    public List<TraceItemPO> queryBySeller(String seller) {
        return TRACE_QUERY_SERVICE.queryBySeller(seller);
    }
}
