package com.liujiahui.www.service.impl;

import com.liujiahui.www.dao.impl.TraceFactoryDAO;
import com.liujiahui.www.entity.po.ItemPO;
import com.liujiahui.www.service.QueryByFactorService;

import java.util.List;

/**
 * @author 刘家辉
 * @date 2023/03/26
 */
public class QueryByFactorServiceImpl implements QueryByFactorService {
    private static final QueryByFactorServiceImpl INSTANCE = new QueryByFactorServiceImpl();

    private QueryByFactorServiceImpl() {
    }

    public static QueryByFactorServiceImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public List<ItemPO> queryByPrice(int max, int min, int choice) {
        return TraceFactoryDAO.getItemShowDAO().queryByPrice(max, min, choice == 1 ? "asc" : "desc");
    }

    @Override
    public List<ItemPO> queryByKeyword(String keyword) {
        return TraceFactoryDAO.getItemShowDAO().queryByKeyword(keyword);
    }

    @Override
    public List<ItemPO> queryByType(String type) {
        return TraceFactoryDAO.getItemShowDAO().queryByType(type);
    }

    @Override
    public List<ItemPO> queryBySeller(String seller) {
        return TraceFactoryDAO.getItemShowDAO().queryBySeller(seller);
    }
}
