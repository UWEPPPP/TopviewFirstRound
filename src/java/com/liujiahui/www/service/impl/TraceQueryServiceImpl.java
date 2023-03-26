package com.liujiahui.www.service.impl;

import com.liujiahui.www.dao.TraceQueryDAO;
import com.liujiahui.www.dao.impl.TraceFactoryDAO;
import com.liujiahui.www.entity.po.TraceItemPO;
import com.liujiahui.www.service.TraceQueryService;

import java.util.List;

/**
 * @author 刘家辉
 * @date 2023/03/26
 */
public class TraceQueryServiceImpl  implements TraceQueryService {
    private static final TraceQueryServiceImpl INSTANCE = new TraceQueryServiceImpl();
    private TraceQueryServiceImpl() {
    }
    public static TraceQueryServiceImpl getInstance() {
        return INSTANCE;
    }
    private static final TraceQueryDAO TRACE_QUERY_DAO = TraceFactoryDAO.getTraceQueryDAO();
    @Override
    public List<TraceItemPO> queryByPrice(int max, int min, int choice) {
       return TRACE_QUERY_DAO.queryByPrice(max,min,choice);
    }

    @Override
    public List<TraceItemPO> queryByKeyword(String keyword) {
        return TRACE_QUERY_DAO.queryByKeyword(keyword);
    }

    @Override
    public List<TraceItemPO> queryByType(String type) {
        return TRACE_QUERY_DAO.queryByType(type);
    }

    @Override
    public List<TraceItemPO> queryBySeller(String seller) {
        return TRACE_QUERY_DAO.queryBySeller(seller);
    }
}
