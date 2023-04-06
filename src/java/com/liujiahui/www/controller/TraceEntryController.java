package com.liujiahui.www.controller;

import com.liujiahui.www.entity.bo.TraceChangePersonalBO;
import com.liujiahui.www.entity.dto.TraceInformationSaveDTO;
import com.liujiahui.www.entity.po.TraceFeedbackPO;
import com.liujiahui.www.entity.po.TraceItemPO;
import com.liujiahui.www.entity.vo.TraceDetailedVO;
import com.liujiahui.www.service.CommonUsedMarketService;
import com.liujiahui.www.service.impl.TraceFactoryService;
import com.liujiahui.www.view.TraceConsumeView;
import com.liujiahui.www.view.TraceSameView;
import com.liujiahui.www.view.TraceSupplyView;

import java.util.List;

/**
 * 用户输入由消费者
 *
 * @author 刘家辉
 * @date 2023/03/18
 */
public class TraceEntryController {
    private static final CommonUsedMarketService CommonUsedMarketService = TraceFactoryService.getCommonUsedService();

    /**
     * 条目
     * 进入选择页面
     */
    public void entry(int choice) {
        String identity = "consumer";
        if (identity.equals(TraceInformationSaveDTO.getInstance().getIdentity())) {
            consumerEntry(choice);
        } else {
            supplierEntry(choice);
        }
    }

    public void consumerEntry(int choice) {
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
            case 4:
                TraceConsumeView.showAppealResult(showAppealResult());
                break;
            default:
        }
    }


    public void supplierEntry(int choice) {
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
            case 6:
                traceSupplyController.showToken();
                break;
            case 7:
                TraceSupplyView.showAppealResult(showAppealResult());
                break;
            default:
        }
    }

    /**
     * 显示项目列表
     * 以下均为公共方法
     */
    private void showItemList() {
        List<TraceItemPO> traceItems = CommonUsedMarketService.showAllItem();
        String check = "consumer";
        if (check.equals(TraceInformationSaveDTO.getInstance().getIdentity())) {
            TraceConsumeView.showAndBuyItemByConsumer(traceItems);
        } else {
            TraceSupplyView.showAndBuyItemBySupplier(traceItems);
        }
    }


    public void showUser() {
        TraceInformationSaveDTO information = TraceInformationSaveDTO.getInstance();
        TraceDetailedVO vo = new TraceDetailedVO();
        vo.setUserName(information.getUserName());
        vo.setGender(information.getGender());
        vo.setPhone(information.getPhone());
        vo.setBalance(information.getBalance());
        vo.setIdentify(information.getIdentity());
        TraceSameView.showPersonalInterface(vo);
    }


    public void changeUser(int choice, String change) {
        TraceChangePersonalBO userChangeServiceBO = new TraceChangePersonalBO();
        userChangeServiceBO.setChange(change);
        userChangeServiceBO.setChoice(choice);
        userChangeServiceBO.setIdentity(TraceInformationSaveDTO.getInstance().getIdentity());
        CommonUsedMarketService.updatePersonalMessage(userChangeServiceBO);
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


    public void showHistory(String name) {
        List<TraceFeedbackPO> history = CommonUsedMarketService.getHistory(name);
        TraceSameView.showHistory(history);
    }

    public List<TraceFeedbackPO> showAppealResult() {
        return CommonUsedMarketService.showAppealResult();
    }

}
