package com.liujiahui.www.service.impl;

import com.liujiahui.www.dao.impl.*;
import com.liujiahui.www.entity.bo.TraceFeedbackBO;
import com.liujiahui.www.entity.bo.TraceItemBO;
import com.liujiahui.www.entity.bo.TraceItemUpdateBO;
import com.liujiahui.www.entity.dto.TraceInformationSaveDTO;
import com.liujiahui.www.entity.po.TraceFeedbackPO;
import com.liujiahui.www.entity.po.TraceItemPO;
import com.liujiahui.www.service.SupplierService;
import com.liujiahui.www.service.wrapper.ContractStorageService;
import com.liujiahui.www.service.wrapper.ContractTradeService;
import org.fisco.bcos.sdk.abi.datatypes.DynamicArray;
import org.fisco.bcos.sdk.abi.datatypes.generated.tuples.generated.Tuple2;
import org.fisco.bcos.sdk.model.TransactionReceipt;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;
import org.fisco.bcos.sdk.utils.Numeric;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 添加物品服务
 *
 * @author 刘家辉
 * @date 2023/03/18
 */
public class SupplierServiceImpl implements SupplierService {
    private static class SupplierMarketServiceImplHolder {
        private static final SupplierServiceImpl INSTANCE = new SupplierServiceImpl();
    }


    private SupplierServiceImpl() {
    }

    public static SupplierServiceImpl getInstance() {
        return SupplierMarketServiceImplHolder.INSTANCE;
    }

    @Override
    public void addItem(TraceItemBO traceItemBO) throws SQLException, IOException {
        TraceInformationSaveDTO instance = TraceInformationSaveDTO.getInstance();
        ContractTradeService contractTradeServiceSolidity = instance.getItemTradeSolidity();
        TransactionReceipt transactionReceipt = contractTradeServiceSolidity.addItem(traceItemBO.getRealName(), traceItemBO.getPrice(), traceItemBO.getRealDescription(), BigInteger.valueOf(traceItemBO.getType()), traceItemBO.getToken());
        Tuple2<BigInteger, byte[]> addItemOutput = contractTradeServiceSolidity.getAddItemOutput(transactionReceipt);
        contractTradeServiceSolidity.updateStatus(addItemOutput.getValue1(), traceItemBO.getLocation(), BigInteger.valueOf(3));
        contractTradeServiceSolidity.updateStatus(addItemOutput.getValue1(), traceItemBO.getStorage(), BigInteger.valueOf(4));
        String hash = Numeric.toHexString(addItemOutput.getValue2());
        new ItemShowDAOImpl().insert(traceItemBO.getName(),traceItemBO.getPrice(),traceItemBO.getDescription(), instance.getUserName(),traceItemBO.getType(),hash);
        new ItemBehindDAOImpl().insert(traceItemBO.getName(),instance.getContractAccount(),addItemOutput.getValue1(),hash,traceItemBO.getToken());
        }


    @Override
    public void updateItem(TraceItemUpdateBO traceItemUpdateBO) throws SQLException, IOException {
        String oldName = traceItemUpdateBO.getOldName();
        String name = traceItemUpdateBO.getName();
        String description = traceItemUpdateBO.getDescription();
        String price = traceItemUpdateBO.getPrice();
        TraceFactoryDAO.getItemShowDAO().updateItem(oldName, name, description, price);
        TraceInformationSaveDTO.getInstance().getItemTradeSolidity().updateItem(new BigInteger(String.valueOf(traceItemUpdateBO.getIndex())), new BigInteger(price));
    }

    @Override
    public void updateLogistics(int index, String logistics, int status) {
        TraceInformationSaveDTO.getInstance().getItemTradeSolidity().updateStatus(BigInteger.valueOf(index), logistics, BigInteger.valueOf(status));
    }

    @Override
    public Map<String, List<TraceItemPO>> showItem() throws ContractException, SQLException, IOException {
        Map<String, List<TraceItemPO>> allItems=new HashMap<>(2);
        List<TraceItemPO> allItem = new ItemBehindDAOImpl().getAllItem(TraceInformationSaveDTO.getInstance().getUserAddress());
        allItems.put("outside",allItem);
        TraceInformationSaveDTO instance = TraceInformationSaveDTO.getInstance();
        DynamicArray<ContractStorageService.Item> soldItem = instance.getItemTradeSolidity().getSoldItems();
        List<TraceItemPO> list1 = new ArrayList<>();
        int index = 0;
        for (ContractStorageService.Item item : soldItem.getValue()) {
            TraceItemPO traceItemPoOne = new TraceItemPO(item.name, item.price, item.description);
            traceItemPoOne.setIndex(new BigDecimal(index));
            traceItemPoOne.setSold(item.isSold);
            list1.add(traceItemPoOne);
            index++;
        }
        allItems.put("real",list1);
        return allItems;
    }

    @Override
    public void removeItem(int index, Boolean choice) throws SQLException, IOException {
        ContractTradeService itemTradeSolidity = TraceInformationSaveDTO.getInstance().getItemTradeSolidity();
        itemTradeSolidity.removeItem(BigInteger.valueOf(index), choice);
        new ItemBehindDAOImpl().removeOrRestoredItem(index,choice);
    }

    @Override
    public List<TraceFeedbackPO> showFeedback() throws SQLException, IOException {
        String contractAccount = TraceInformationSaveDTO.getInstance().getContractAccount();
        return new ItemBehindDAOImpl().showAndUpdateFeedback(contractAccount);
    }

    @Override
    public Integer showToken() throws ContractException {
        TraceInformationSaveDTO instance = TraceInformationSaveDTO.getInstance();
        String contractAccount = instance.getContractAccount();
        return instance.getItemTradeSolidity().showSupplierToken(contractAccount).intValue();
    }

    @Override
    public void appealFeedback(TraceFeedbackBO traceFeedbackBO) throws SQLException, IOException {
         new SupplierAppealDAOImpl().insert(traceFeedbackBO.getItemHash(),traceFeedbackBO.getComment());
         new ConsumerFeedbackDAOImpl().updateFeedback(traceFeedbackBO.getItemHash());
    }


}
