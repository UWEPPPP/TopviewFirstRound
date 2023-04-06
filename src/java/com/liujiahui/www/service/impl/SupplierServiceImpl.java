package com.liujiahui.www.service.impl;

import com.liujiahui.www.dao.impl.*;
import com.liujiahui.www.entity.bo.TraceFeedbackBO;
import com.liujiahui.www.entity.bo.TraceItemBO;
import com.liujiahui.www.entity.bo.TraceItemUpdateBO;
import com.liujiahui.www.entity.dto.UserSaveDTO;
import com.liujiahui.www.entity.po.FeedbackPO;
import com.liujiahui.www.entity.po.ItemPO;
import com.liujiahui.www.service.SupplierService;
import com.liujiahui.www.service.wrapper.ContractStorageService;
import com.liujiahui.www.service.wrapper.ContractTradeService;
import org.fisco.bcos.sdk.abi.datatypes.DynamicArray;
import org.fisco.bcos.sdk.abi.datatypes.generated.tuples.generated.Tuple2;
import org.fisco.bcos.sdk.model.TransactionReceipt;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;
import org.fisco.bcos.sdk.utils.Numeric;

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
    private SupplierServiceImpl() {
    }

    public static SupplierServiceImpl getInstance() {
        return SupplierMarketServiceImplHolder.INSTANCE;
    }

    @Override
    public void addItem(TraceItemBO traceItemBO) {
        UserSaveDTO instance = UserSaveDTO.getInstance();
        ContractTradeService contractTradeServiceSolidity = instance.getItemTradeSolidity();
        TransactionReceipt transactionReceipt = contractTradeServiceSolidity.addItem(traceItemBO.getRealName(), traceItemBO.getPrice(), traceItemBO.getRealDescription(), BigInteger.valueOf(traceItemBO.getType()), traceItemBO.getToken());
        Tuple2<BigInteger, byte[]> addItemOutput = contractTradeServiceSolidity.getAddItemOutput(transactionReceipt);
        contractTradeServiceSolidity.updateStatus(addItemOutput.getValue1(), traceItemBO.getLocation(), BigInteger.valueOf(3));
        contractTradeServiceSolidity.updateStatus(addItemOutput.getValue1(), traceItemBO.getStorage(), BigInteger.valueOf(4));
        String hash = Numeric.toHexString(addItemOutput.getValue2());
        try {
            new ItemShowDAOImpl().insert(traceItemBO.getName(), traceItemBO.getPrice(), traceItemBO.getDescription(), instance.getUserName(), traceItemBO.getType(), hash);
            new ItemBehindDAOImpl().insert(traceItemBO.getName(), instance.getContractAccount(), addItemOutput.getValue1(), hash, traceItemBO.getToken());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateItem(TraceItemUpdateBO traceItemUpdateBO) {
        String oldName = traceItemUpdateBO.getOldName();
        String name = traceItemUpdateBO.getName();
        String description = traceItemUpdateBO.getDescription();
        String price = traceItemUpdateBO.getPrice();
        try {
            TraceFactoryDAO.getItemShowDAO().updateItem(oldName, name, description, price);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        UserSaveDTO.getInstance().getItemTradeSolidity().updateItem(new BigInteger(String.valueOf(traceItemUpdateBO.getIndex())), new BigInteger(price));
    }

    @Override
    public void updateLogistics(int index, String logistics, int status) {
        UserSaveDTO.getInstance().getItemTradeSolidity().updateStatus(BigInteger.valueOf(index), logistics, BigInteger.valueOf(status));
    }

    @Override
    public Map<String, List<ItemPO>> showItem() {
        Map<String, List<ItemPO>> allItems = new HashMap<>(2);
        try {
            List<ItemPO> allItem = TraceFactoryDAO.getItemBehindDAO().getAllItem(UserSaveDTO.getInstance().getUserAddress());
            allItems.put("outside", allItem);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        UserSaveDTO instance = UserSaveDTO.getInstance();
        try {
            DynamicArray<ContractStorageService.Item> soldItem = instance.getItemTradeSolidity().getSoldItems();
            List<ItemPO> list1 = new ArrayList<>();
            int index = 0;
            for (ContractStorageService.Item item : soldItem.getValue()) {
                ItemPO itemPoOne = new ItemPO(item.name, item.price, item.description);
                itemPoOne.setIndex(new BigDecimal(index));
                itemPoOne.setSold(item.isSold);
                list1.add(itemPoOne);
                index++;
            }
            allItems.put("real", list1);
        } catch (ContractException e) {
            e.printStackTrace();
        }
        return allItems;
    }

    @Override
    public void removeItem(int index, Boolean choice) {
        ContractTradeService itemTradeSolidity = UserSaveDTO.getInstance().getItemTradeSolidity();
        itemTradeSolidity.removeItem(BigInteger.valueOf(index), choice);
        try {
            new ItemBehindDAOImpl().removeOrRestoredItem(index, choice);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<FeedbackPO> showFeedback() {
        String contractAccount = UserSaveDTO.getInstance().getContractAccount();
        try {
            return new ItemBehindDAOImpl().showAndUpdateFeedback(contractAccount);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Integer showToken() {
        UserSaveDTO instance = UserSaveDTO.getInstance();
        String contractAccount = instance.getContractAccount();
        try {
            return instance.getItemTradeSolidity().showSupplierToken(contractAccount).intValue();
        } catch (ContractException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void appealFeedback(TraceFeedbackBO traceFeedbackBO) {
        try {
            new SupplierAppealDAOImpl().insert(traceFeedbackBO.getItemHash(), traceFeedbackBO.getComment());
            new ConsumerFeedbackDAOImpl().updateFeedback(traceFeedbackBO.getItemHash());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static class SupplierMarketServiceImplHolder {
        private static final SupplierServiceImpl INSTANCE = new SupplierServiceImpl();
    }


}
