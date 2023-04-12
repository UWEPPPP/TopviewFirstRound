package com.liujiahui.www.controller;

import com.liujiahui.www.controller.proxy.ProxyFactory;
import com.liujiahui.www.entity.bo.TraceChangePersonalBO;
import com.liujiahui.www.entity.dto.UserSaveDTO;
import com.liujiahui.www.entity.po.FeedbackPO;
import com.liujiahui.www.entity.po.ItemPO;
import com.liujiahui.www.entity.vo.TraceDetailedVO;
import com.liujiahui.www.service.CommonUsedMarketService;
import com.liujiahui.www.service.factory.TraceFactoryService;
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
public class CommonUsedController {
    private static final CommonUsedMarketService CommonUsedMarketService = (CommonUsedMarketService) ProxyFactory.createProxy(TraceFactoryService.getCommonUsedService());


    public List<ItemPO> queryByPrice(int max, int min, int choice) {
        return CommonUsedMarketService.queryByPrice(max, min, choice);
    }

    public List<ItemPO> queryByKeyword(String keyword) {
        return CommonUsedMarketService.queryByKeyword(keyword);
    }


    public List<ItemPO> queryByType(String type) {
        return CommonUsedMarketService.queryByType(type);
    }

    public List<ItemPO> queryBySeller(String seller) {
        return CommonUsedMarketService.queryBySeller(seller);
    }

    /**
     * 条目
     * 进入选择页面
     */
    public void entry(int choice) {
        String identity = "consumer";
        if (identity.equals(UserSaveDTO.getInstance().getIdentity())) {
            consumerEntry(choice);
        } else {
            supplierEntry(choice);
        }
    }

    public void consumerEntry(int choice) {
        ConsumeController consumeController = new ConsumeController();
        switch (choice) {
            case 1:
                showItemList();
                break;
            case 2:
                showUser();
                break;
            case 3:
                consumeController.showUserItem();
                break;
            case 4:
                TraceConsumeView.showAppealResult(showAppealResult());
                break;
            default:
        }
    }


    public void supplierEntry(int choice) {
        SupplyController supplyController = new SupplyController();
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
                supplyController.showSupplierItem();
                break;
            case 5:
                TraceSupplyView.showAllFeedback(supplyController.showSupplierFeedback());
                break;
            case 6:
                supplyController.showToken();
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
        List<ItemPO> traceItems = CommonUsedMarketService.showAllItem();
        String check = "consumer";
        if (check.equals(UserSaveDTO.getInstance().getIdentity())) {
            TraceConsumeView.showAndBuyItemByConsumer(traceItems);
        } else {
            TraceSupplyView.showAndBuyItemBySupplier(traceItems);
        }
    }


    public void showUser() {
        UserSaveDTO information = UserSaveDTO.getInstance();
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
        userChangeServiceBO.setIdentity(UserSaveDTO.getInstance().getIdentity());
        CommonUsedMarketService.updatePersonalMessage(userChangeServiceBO);
        switch (choice) {
            case 1:
                UserSaveDTO.getInstance().setUserName(change);
                break;
            case 2:
                UserSaveDTO.getInstance().setGender(change);
                break;
            case 3:
                UserSaveDTO.getInstance().setPhone(change);
                break;
            default:
        }
    }


    public void showHistory(String name) {
        List<FeedbackPO> history = CommonUsedMarketService.getHistory(name);
        TraceSameView.showHistory(history);
    }

    public List<FeedbackPO> showAppealResult() {
        return CommonUsedMarketService.showAppealResult();
    }

}
