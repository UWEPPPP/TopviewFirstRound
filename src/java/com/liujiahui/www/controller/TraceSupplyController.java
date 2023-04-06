package com.liujiahui.www.controller;

import com.liujiahui.www.entity.bo.TraceFeedbackBO;
import com.liujiahui.www.entity.bo.TraceItemBO;
import com.liujiahui.www.entity.bo.TraceItemUpdateBO;
import com.liujiahui.www.entity.po.TraceFeedbackPO;
import com.liujiahui.www.entity.po.TraceItemPO;
import com.liujiahui.www.service.SupplierService;
import com.liujiahui.www.service.impl.TraceFactoryService;
import com.liujiahui.www.view.TraceSupplyView;

import java.math.BigInteger;
import java.util.List;

/**
 * 用户提供控制器
 *
 * @author 刘家辉
 * @date 2023/03/25
 */
public class TraceSupplyController {
    private static final SupplierService marketService = TraceFactoryService.getSupplierUsedService();

    public void registerItem(String name, BigInteger price, String description, String realName, String realDescription, int type, String location, String storage, BigInteger token) {
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
    }


    public void updateLogistics(int id, String logistics, int status) {
        marketService.updateLogistics(id, logistics, status);
    }

    public void updateItem(int index, List<TraceItemPO> traceItemPos, String name, String description, String price) {
        String oldName = null;
        for (TraceItemPO traceItemPoOne : traceItemPos) {
            if (traceItemPoOne.getIndex().intValue() == index) {
                oldName = traceItemPoOne.getName();
            }
        }
        TraceItemUpdateBO updateBO = new TraceItemUpdateBO();
        updateBO.setIndex(index);
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

    public List<TraceFeedbackPO> showSupplierFeedback() {
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
