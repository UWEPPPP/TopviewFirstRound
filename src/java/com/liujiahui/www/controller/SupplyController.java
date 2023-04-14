package com.liujiahui.www.controller;

import com.liujiahui.www.controller.proxy.ProxyFactory;
import com.liujiahui.www.entity.bo.TraceFeedbackBO;
import com.liujiahui.www.entity.bo.TraceItemBO;
import com.liujiahui.www.entity.bo.TraceItemUpdateBO;
import com.liujiahui.www.entity.po.FeedbackPO;
import com.liujiahui.www.entity.po.ItemPO;
import com.liujiahui.www.service.SupplierService;
import com.liujiahui.www.service.factory.TraceFactoryService;
import com.liujiahui.www.view.TraceSupplyView;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

/**
 * 用户提供控制器
 *
 * @author 刘家辉
 * @date 2023/03/25
 */
public class SupplyController {
    private static final SupplierService marketService = (SupplierService) ProxyFactory.createProxy(TraceFactoryService.getSupplierUsedService());

    public Boolean registerItem(String name, BigInteger price, String description, String realName, String realDescription, int type, String location, String storage, BigInteger token) {
        Integer integer = marketService.showToken();
        int min = 10;
        if(integer <=min|| integer <token.intValue()){
            return false;
        }
        TraceItemBO traceItemBO = new TraceItemBO();
        traceItemBO.setName(name);
        traceItemBO.setPrice(price);
        traceItemBO.setDescription(description);
        traceItemBO.setRealName(realName);
        traceItemBO.setRealDescription(realDescription);
        traceItemBO.setType(type);
        traceItemBO.setLocation(location);
        traceItemBO.setStorage(storage);
        traceItemBO.setToken(token);
        marketService.addItem(traceItemBO);
        return true;
    }


    public void updateLogistics(int index, String logistics, int status) {
        marketService.updateLogistics(index, logistics, status);
    }

    public void updateItem(int id, List<ItemPO> itemPos, String name, String description, String price) {
        String oldName = null;
        BigDecimal index = null;
        for (ItemPO itemPoOne : itemPos) {
            if (itemPoOne.getId() == id) {
                oldName = itemPoOne.getName();
                index=itemPoOne.getIndex();
                break;
            }
        }
        TraceItemUpdateBO updateBO = new TraceItemUpdateBO();
        if (index != null) {
            updateBO.setIndex(index.intValue());
        }
        updateBO.setOldName(oldName);
        updateBO.setName(name);
        updateBO.setDescription(description);
        updateBO.setPrice(price);
        marketService.updateItem(updateBO);
    }

    public void showSupplierItem() {
        TraceSupplyView.showSupplierItem(marketService.showItem());
    }

    public void removeItem(int index, Boolean choice) {
        marketService.removeItem(index, choice);
    }

    public List<FeedbackPO> showSupplierFeedback() {
        return marketService.showFeedback();
    }

    public void showToken() {
        TraceSupplyView.showToken(marketService.showToken());
    }

    public void appealFeedback(String hash, String comment) {
        TraceFeedbackBO traceFeedbackBO = new TraceFeedbackBO();
        traceFeedbackBO.setItemHash(hash);
        traceFeedbackBO.setComment(comment);
        marketService.appealFeedback(traceFeedbackBO);
    }


}
