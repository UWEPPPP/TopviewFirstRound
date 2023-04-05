package com.liujiahui.www.service.impl;

import com.liujiahui.www.dao.impl.TraceFactoryDAO;
import com.liujiahui.www.entity.po.TraceItemPO;
import com.liujiahui.www.service.TraceQueryService;

import java.util.List;

/**
 * @author 刘家辉
 * @date 2023/03/26
 */
public class TraceQueryServiceImpl implements TraceQueryService {
    private static final TraceQueryServiceImpl INSTANCE = new TraceQueryServiceImpl();

    private TraceQueryServiceImpl() {
    }

    public static TraceQueryServiceImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public List<TraceItemPO> queryByPrice(int max, int min, int choice) {
        return TraceFactoryDAO.getItemShowDAO().queryByPrice(max, min, choice);
    }

    @Override
    public List<TraceItemPO> queryByKeyword(String keyword) {
        return TraceFactoryDAO.getItemShowDAO().queryByKeyword(keyword);
    }

    @Override
    public List<TraceItemPO> queryByType(String type) {
        return TraceFactoryDAO.getItemShowDAO().queryByType(type);
    }

    @Override
    public List<TraceItemPO> queryBySeller(String seller) {
        return TraceFactoryDAO.getItemShowDAO().queryBySeller(seller);
    }
}
