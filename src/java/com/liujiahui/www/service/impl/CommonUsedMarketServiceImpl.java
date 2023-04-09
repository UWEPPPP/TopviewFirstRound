package com.liujiahui.www.service.impl;

import com.liujiahui.www.dao.impl.ConsumerFeedbackDAOImpl;
import com.liujiahui.www.dao.impl.ItemShowDAOImpl;
import com.liujiahui.www.dao.impl.SupplierAppealDAOImpl;
import com.liujiahui.www.dao.factory.TraceFactoryDAO;
import com.liujiahui.www.entity.bo.TraceChangePersonalBO;
import com.liujiahui.www.entity.dto.UserSaveDTO;
import com.liujiahui.www.entity.po.FeedbackPO;
import com.liujiahui.www.entity.po.ItemPO;
import com.liujiahui.www.service.CommonUsedMarketService;

import java.sql.SQLException;
import java.util.List;

/**
 * @author 刘家辉
 * @date 2023/03/25
 */
public class CommonUsedMarketServiceImpl implements CommonUsedMarketService {
    private CommonUsedMarketServiceImpl() {
    }

    public static CommonUsedMarketServiceImpl getInstance() {
        return CommonUsedMarketServiceImplHolder.INSTANCE;
    }

    /**
     * 显示所有项
     * 查看所有产品的功能
     */
    @Override
    public List<ItemPO> showAllItem() {
        try {
            return new ItemShowDAOImpl().showAllItem();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 更新个人信息
     */
    @Override
    public void updatePersonalMessage(TraceChangePersonalBO userChangeServiceBO) {
        Integer choice = userChangeServiceBO.getChoice();
        String change = userChangeServiceBO.getChange();
        String identity = userChangeServiceBO.getIdentity();
        String type = null;
        switch (choice) {
            case 1:
                type = "user_name";
                break;
            case 2:
                type = "gender";
                break;
            case 3:
                type = "phone";
                break;
            default:
        }
        String judge = "suppliers";
        Boolean result;
        if (judge.equals(identity)) {
            result = TraceFactoryDAO.getSupplierDAO().updatePersonalInformation(type, change);
        } else {
            result = TraceFactoryDAO.getConsumerDAO().updatePersonalInformation(type, change);
        }
        if (!result) {
            throw new RuntimeException("更新失败");
        }

    }

    /**
     * 输出商家的历史评价
     *
     * @param name 名字
     * @return {@link List}<{@link FeedbackPO}>
     */
    @Override
    public List<FeedbackPO> getHistory(String name) {
        String supplierAccount = TraceFactoryDAO.getSupplierDAO().getSupplierAccount(name);
        try {
            return new ConsumerFeedbackDAOImpl().getSupplierHistory(supplierAccount);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<FeedbackPO> showAppealResult() {
        String contractAccount = UserSaveDTO.getInstance().getContractAccount();
        try {
            return new SupplierAppealDAOImpl().showReportAndAppealResult(contractAccount);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static class CommonUsedMarketServiceImplHolder {
        private static final CommonUsedMarketServiceImpl INSTANCE = new CommonUsedMarketServiceImpl();
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
