package com.liujiahui.www.service;

import com.liujiahui.www.dao.TraceUserDAO;
import com.liujiahui.www.entity.bo.TraceChangePersonalBO;
import com.liujiahui.www.entity.dto.TraceInformationSaveDTO;
import com.liujiahui.www.entity.po.TraceFeedbackPO;
import com.liujiahui.www.entity.po.TraceItemPO;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @author 刘家辉
 * @date 2023/03/25
 */
public interface TraceUserMarketService {
    /**
     * 显示所有项
     * 查看所有产品的功能
     */
    static List<TraceItemPO> showAllItem() throws SQLException, IOException {
        return TraceUserDAO.showAllItem();
    }

    /**
     * 更新个人信息
     */
    static void updatePersonalMessage(TraceChangePersonalBO userChangeServiceBO) throws SQLException, IOException {
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
        TraceUserDAO.updatePersonalInformation(type, change, identity);
    }

    /**
     * 输出商家的历史评价
     *
     * @param name 名字
     * @return {@link List}<{@link TraceFeedbackPO}>
     * @throws SQLException sqlexception异常
     * @throws IOException  ioexception
     */
    static List<TraceFeedbackPO> getHistory(String name) throws SQLException, IOException {
        return TraceUserDAO.getHistory(name);
    }

    static List<TraceFeedbackPO> showAppealResult() throws SQLException, IOException {
        String contractAccount = TraceInformationSaveDTO.getInstance().getContractAccount();
        return TraceUserDAO.showReportAndAppealResult(contractAccount);
    }

    /**
     * 显示项
     * 不同类型用户显示不同的产品
     */
    Map<String, List<TraceItemPO>> showItem() throws ContractException, SQLException, IOException;
}
