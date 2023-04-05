package com.liujiahui.www.service.impl;

import com.liujiahui.www.dao.impl.*;
import com.liujiahui.www.entity.bo.TraceChangePersonalBO;
import com.liujiahui.www.entity.dto.TraceInformationSaveDTO;
import com.liujiahui.www.entity.po.TraceFeedbackPO;
import com.liujiahui.www.entity.po.TraceItemPO;
import com.liujiahui.www.service.CommonUsedMarketService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * @author 刘家辉
 * @date 2023/03/25
 */
public class CommonUsedMarketServiceImpl implements CommonUsedMarketService {
    private CommonUsedMarketServiceImpl() {
    }
    private static class CommonUsedMarketServiceImplHolder{
        private static final CommonUsedMarketServiceImpl INSTANCE = new CommonUsedMarketServiceImpl();
    }
    public static CommonUsedMarketServiceImpl getInstance(){
        return CommonUsedMarketServiceImplHolder.INSTANCE;
    }

    /**
     * 显示所有项
     * 查看所有产品的功能
     */
    @Override
    public List<TraceItemPO> showAllItem() throws SQLException, IOException {
        return new ItemShowDAOImpl().showAllItem();
    }

    /**
     * 更新个人信息
     */
    @Override
    public void updatePersonalMessage(TraceChangePersonalBO userChangeServiceBO) throws SQLException, IOException {
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
        if("suppliers".equals(identity)){
            new SupplierAccountDAOImpl().updatePersonalInformation(type, change);
        }
           new ConsumerAccountDAOImpl().updatePersonalInformation(type, change);
        }

    /**
     * 输出商家的历史评价
     *
     * @param name 名字
     * @return {@link List}<{@link TraceFeedbackPO}>
     * @throws SQLException sqlexception异常
     * @throws IOException  ioexception
     */
    @Override
    public List<TraceFeedbackPO> getHistory(String name) throws SQLException, IOException {
        String supplierAccount = new SupplierAccountDAOImpl().getSupplierAccount(name);
        return new ConsumerFeedbackDAOImpl().getSupplierHistory(supplierAccount);
    }

    @Override
    public List<TraceFeedbackPO> showAppealResult() throws SQLException, IOException {
        String contractAccount = TraceInformationSaveDTO.getInstance().getContractAccount();
        return new SupplierAppealDAOImpl().showReportAndAppealResult(contractAccount);
    }

}
