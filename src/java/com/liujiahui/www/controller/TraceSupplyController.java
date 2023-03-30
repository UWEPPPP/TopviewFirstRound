package com.liujiahui.www.controller;

import com.liujiahui.www.entity.bo.TraceItemBO;
import com.liujiahui.www.entity.bo.TraceItemUpdateBO;
import com.liujiahui.www.entity.po.TraceFeedbackPO;
import com.liujiahui.www.entity.po.TraceItemPO;
import com.liujiahui.www.service.TraceItemPersonalService;
import com.liujiahui.www.service.impl.TraceFactoryImplService;
import com.liujiahui.www.service.impl.TraceItemPersonalBySupplierServiceImpl;
import com.liujiahui.www.view.TraceSupplyView;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;

import java.io.IOException;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.List;

/**
 * 用户提供控制器
 *
 * @author 刘家辉
 * @date 2023/03/25
 */
public class TraceSupplyController {
    private final TraceItemPersonalService traceItemPersonalService = TraceFactoryImplService.getTraceItemPersonalService(true);

    public void registerItem(String name, BigInteger price, String description, String realName, String realDescription, int type, String location, String storage) throws SQLException, IOException {
        TraceItemBO traceItemBO = new TraceItemBO();
        traceItemBO.setName(name);
        traceItemBO.setPrice(price);
        traceItemBO.setDescription(description);
        traceItemBO.setRealName(realName);
        traceItemBO.setRealDescription(realDescription);
        traceItemBO.setType(type);
        traceItemBO.setLocation(location);
        traceItemBO.setStorage(storage);
        ((TraceItemPersonalBySupplierServiceImpl) traceItemPersonalService).addItem(traceItemBO);
    }


    public void updateLogistics(int id, String logistics, int status) {
        ((TraceItemPersonalBySupplierServiceImpl) traceItemPersonalService).updateLogistics(id, logistics, status);
    }

    public void updateItem(int index, List<TraceItemPO> traceItemPos, String name, String description, String price) throws SQLException, IOException {
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
        ((TraceItemPersonalBySupplierServiceImpl) traceItemPersonalService).updateItem(updateBO);
    }

    public void showSupplierItem() throws ContractException, SQLException, IOException {
        TraceSupplyView.showSupplierItem(traceItemPersonalService.showItem());
    }

    public void removeItem(int index, Boolean choice) throws SQLException, IOException {
        ((TraceItemPersonalBySupplierServiceImpl) traceItemPersonalService).removeItem(index, choice);
    }

    public List<TraceFeedbackPO> showSupplierFeedback() throws SQLException, IOException {
        return ((TraceItemPersonalBySupplierServiceImpl) traceItemPersonalService).showFeedback();
    }

}
