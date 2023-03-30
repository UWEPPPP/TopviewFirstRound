package com.liujiahui.www.controller;

import com.liujiahui.www.entity.bo.TraceChangePersonalBO;
import com.liujiahui.www.entity.dto.TraceInformationSaveDTO;
import com.liujiahui.www.entity.po.TraceFeedbackPO;
import com.liujiahui.www.entity.po.TraceItemPO;
import com.liujiahui.www.entity.vo.TraceDetailedVO;
import com.liujiahui.www.service.TraceItemPersonalService;
import com.liujiahui.www.view.TraceConsumeView;
import com.liujiahui.www.view.TraceSameView;
import com.liujiahui.www.view.TraceSupplyView;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * 用户输入由消费者
 *
 * @author 刘家辉
 * @date 2023/03/18
 */
public class TraceEntryController {

    /**
     * 条目
     * 进入选择页面
     */
    public void entry(int choice) throws SQLException, IOException, ContractException {
        String identity = "consumer";
        if (identity.equals(TraceInformationSaveDTO.getInstance().getIdentity())) {
            consumerEntry(choice);
        } else {
            supplierEntry(choice);
        }
    }

    public void consumerEntry(int choice) throws SQLException, IOException, ContractException {
        TraceConsumeController traceConsumeController = new TraceConsumeController();
        switch (choice) {
            case 1:
                showItemList();
                break;
            case 2:
                showUser();
                break;
            case 3:
                traceConsumeController.showUserItem();
                break;
            default:
        }
    }


    public void supplierEntry(int choice) throws SQLException, IOException, ContractException {
        TraceSupplyController traceSupplyController = new TraceSupplyController();
        switch (choice) {
            case 1:
                showItemList();
                break;
            case 2:
                showUser();
                break;
            case 3:
                TraceSupplyView.registerItem();
                break;
            case 4:
                traceSupplyController.showSupplierItem();
                break;
            case 5:
                TraceSupplyView.showAllFeedback(traceSupplyController.showSupplierFeedback());
                break;
            default:
        }
    }

    /**
     * 显示项目列表
     * 以下均为公共方法
     */
    private void showItemList() throws SQLException, IOException, ContractException {
        List<TraceItemPO> traceItems = TraceItemPersonalService.showAllItem();
        String check = "consumer";
        if (check.equals(TraceInformationSaveDTO.getInstance().getIdentity())) {
            TraceConsumeView.showAndBuyItemByConsumer(traceItems);
        } else {
            TraceSupplyView.showAndBuyItemBySupplier(traceItems);
        }
    }


    public void showUser() throws SQLException, IOException {
        TraceInformationSaveDTO information = TraceInformationSaveDTO.getInstance();
        TraceDetailedVO vo = new TraceDetailedVO();
        vo.setUserName(information.getUserName());
        vo.setGender(information.getGender());
        vo.setPhone(information.getPhone());
        vo.setBalance(information.getBalance());
        vo.setIdentify(information.getIdentity());
        TraceSameView.showPersonalInterface(vo);
    }


    public void changeUser(int choice, String change) throws SQLException, IOException {
        TraceChangePersonalBO userChangeServiceBO = new TraceChangePersonalBO();
        userChangeServiceBO.setChange(change);
        userChangeServiceBO.setChoice(choice);
        userChangeServiceBO.setIdentity(TraceInformationSaveDTO.getInstance().getIdentity());
        TraceItemPersonalService.updatePersonalMessage(userChangeServiceBO);
        switch (choice) {
            case 1:
                TraceInformationSaveDTO.getInstance().setUserName(change);
                break;
            case 2:
                TraceInformationSaveDTO.getInstance().setGender(change);
                break;
            case 3:
                TraceInformationSaveDTO.getInstance().setPhone(change);
                break;
            default:
        }
    }


    public void showHistory(String name) throws SQLException, IOException {
        List<TraceFeedbackPO> history = TraceItemPersonalService.getHistory(name);
        TraceSameView.showHistory(history);
    }

}
