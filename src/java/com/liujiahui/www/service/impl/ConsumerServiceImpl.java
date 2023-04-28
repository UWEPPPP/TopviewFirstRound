package com.liujiahui.www.service.impl;

import com.liujiahui.www.dao.factory.TraceFactoryDAO;
import com.liujiahui.www.dao.impl.ItemBehindDAOImpl;
import com.liujiahui.www.entity.bo.TraceFeedbackBO;
import com.liujiahui.www.entity.dto.TraceItemStatusDTO;
import com.liujiahui.www.entity.dto.TraceRealAndOutItemDTO;
import com.liujiahui.www.entity.dto.TraceTransactionDTO;
import com.liujiahui.www.entity.dto.UserSaveDTO;
import com.liujiahui.www.entity.po.ItemPO;
import com.liujiahui.www.service.ConsumerService;
import com.liujiahui.www.service.wrapper.ContractMarketService;
import com.liujiahui.www.service.wrapper.ContractStorageService;
import org.fisco.bcos.sdk.abi.datatypes.DynamicArray;
import org.fisco.bcos.sdk.abi.datatypes.generated.tuples.generated.Tuple3;
import org.fisco.bcos.sdk.model.TransactionReceipt;
import org.fisco.bcos.sdk.transaction.model.dto.TransactionResponse;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;
import org.fisco.bcos.sdk.utils.Numeric;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 跟踪项目个人消费者service impl
 *
 * @author 刘家辉
 * @date 2023/03/24
 */
public class ConsumerServiceImpl implements ConsumerService {
    private ConsumerServiceImpl() {
    }

    public  static class ConsumerServiceImplInstanceHolder{
        public static final ConsumerServiceImpl INSTANCE = new ConsumerServiceImpl();
    }
    public static ConsumerService getInstance() {
        return ConsumerServiceImplInstanceHolder.INSTANCE ;
    }

    @Override
    public Map<String, List<ItemPO>> showItem() {
        Map<String, List<ItemPO>> map = new HashMap<>(1);
        Map<String, List<ItemPO>> stringListMap;
        try {
            stringListMap = TraceFactoryDAO.getItemShowDAO().showConsumerItem(UserSaveDTO.getInstance().getContractAccount());
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        map.put("item", stringListMap.get("item"));
        return map;
    }

    @Override
    public TraceTransactionDTO buyItem(String seller, BigDecimal index) {
        UserSaveDTO instance = UserSaveDTO.getInstance();
        ContractMarketService contractTradeServiceSolidity = instance.getItemTradeSolidity();
        BigInteger bigInteger = index.toBigInteger();
        TransactionReceipt transactionReceipt = contractTradeServiceSolidity.buyItem(seller, bigInteger);
        TransactionResponse transactionResponse = instance.getDecoder().decodeReceiptStatus(transactionReceipt);
        TraceTransactionDTO traceTransactionDTO = new TraceTransactionDTO();
        String checkError = "Success";
        if (!Objects.equals(transactionResponse.getReturnMessage(), checkError)) {
            System.out.println("交易失败");
            return null;
        }
        Tuple3<String, String, byte[]> buyItemOutput = contractTradeServiceSolidity.getBuyItemOutput(transactionReceipt);
        BigInteger bigInteger1 = index.toBigInteger();
        try {
            TraceFactoryDAO.getItemBehindDAO().buyItem(seller, bigInteger1);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        BigInteger balance;
        try {
            balance = contractTradeServiceSolidity.getBalance();
        } catch (ContractException e) {
            throw new RuntimeException(e);
        }

        instance.setBalance(String.valueOf(balance));
        traceTransactionDTO.setItemSoldEventResponse(buyItemOutput);
        traceTransactionDTO.setBalance(String.valueOf(balance));
        return traceTransactionDTO;
    }

    @Override
    public TraceRealAndOutItemDTO checkByHash(String hash) {
        byte[] bytes = Numeric.hexStringToByteArray(hash);
        try {
            Tuple3<String, String, String> realItem = UserSaveDTO.getInstance().getItemTradeSolidity().getRealItem(bytes);
            TraceRealAndOutItemDTO traceRealAndOutItemDTO = new TraceRealAndOutItemDTO();
            traceRealAndOutItemDTO.setRealName(realItem.getValue1());
            traceRealAndOutItemDTO.setRealDescription(realItem.getValue2());
            traceRealAndOutItemDTO.setSeller(realItem.getValue3());
            return traceRealAndOutItemDTO;
        } catch (ContractException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public TraceItemStatusDTO checkStatus(String hash1) {
        byte[] bytes = Numeric.hexStringToByteArray(hash1);
        try {
            Tuple3<BigInteger, String, BigInteger> status = UserSaveDTO.getInstance().getItemTradeSolidity().getStatus(bytes);
            TraceItemStatusDTO traceItemStatusDTO = new TraceItemStatusDTO();
            long longValue = status.getValue1().longValue();
            Date date = new Date(longValue * 1000);
            traceItemStatusDTO.setDate(date);
            traceItemStatusDTO.setPlace(status.getValue2());
            BigInteger value3 = status.getValue3();
            int intValue = value3.intValue();
            stuffingStatus(traceItemStatusDTO, intValue);
            return traceItemStatusDTO;
        } catch (ContractException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void stuffingStatus(TraceItemStatusDTO traceItemStatusDTO, int intValue) {
        if (intValue == 0) {
            traceItemStatusDTO.setStatus("未出库，正在准备中");
        } else if (intValue == 1) {
            traceItemStatusDTO.setStatus("已出库，正在运送中");
        } else if (intValue == 2) {
            traceItemStatusDTO.setStatus("已送达");
        } else if (intValue == 3) {
            traceItemStatusDTO.setStatus("生产中");
        } else {
            traceItemStatusDTO.setStatus("已完成,存储于仓库中");
        }
    }

    @Override
    public void supplierWriteDownService(TraceFeedbackBO traceFeedbackBO) {
        String buyer = traceFeedbackBO.getBuyer();
        String seller = traceFeedbackBO.getSeller();
        String itemHash = traceFeedbackBO.getItemHash();
        String comment = traceFeedbackBO.getComment();
        String itemName = traceFeedbackBO.getItemName();
        int choice = traceFeedbackBO.getChoice();
        ContractMarketService itemTradeSolidity = UserSaveDTO.getInstance().getItemTradeSolidity();
        itemTradeSolidity.handing_feedback(seller, choice == 1, Numeric.hexStringToByteArray(itemHash));
        try {
            TraceFactoryDAO.getConsumerFeedbackDAO().writeDown(seller, buyer, comment, choice == 1 ? "likes" : "reports", itemHash, itemName);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("写入数据库异常");
        }
    }

    @Override
    public void returnItem(String hash2) {
        try {
            ItemPO item = new ItemBehindDAOImpl().returnItem(hash2);
            ContractMarketService itemTradeSolidity = UserSaveDTO.getInstance().getItemTradeSolidity();
            itemTradeSolidity.refundItem(Numeric.hexStringToByteArray(hash2), item.getIndex().toBigInteger());
            UserSaveDTO.getInstance().setBalance(String.valueOf(itemTradeSolidity.getBalance()));
        } catch (SQLException e) {
            throw new RuntimeException("退货异常");
        } catch (ContractException e) {
            throw new RuntimeException("合约异常");
        }
    }

    @Override
    public List<TraceItemStatusDTO> checkLife(String hash3) {
        UserSaveDTO instance = UserSaveDTO.getInstance();
        ContractMarketService contractTradeServiceSolidity = instance.getItemTradeSolidity();
        byte[] bytes = Numeric.hexStringToByteArray(hash3);
        try {
            DynamicArray<ContractStorageService.ItemLife> itemLifeDynamicArray = contractTradeServiceSolidity.showWholeLife(bytes);
            List<ContractStorageService.ItemLife> itemLifeList = itemLifeDynamicArray.getValue();
            List<TraceItemStatusDTO> itemStatus;
            itemStatus = itemLifeList.stream()
                    .map(itemLife -> {
                        TraceItemStatusDTO traceItemStatusDTO = new TraceItemStatusDTO();
                        long time = itemLife.date.longValue();
                        Date date = new Date(time * 1000);
                        traceItemStatusDTO.setDate(date);
                        traceItemStatusDTO.setPlace(itemLife.place);
                        int status = itemLife.status.intValue();
                        stuffingStatus(traceItemStatusDTO, status);
                        return traceItemStatusDTO;
                    })
                    .collect(Collectors.toList());
            return itemStatus;
        } catch (ContractException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static class CommonUsedMarketByConsumerServiceImplImplHolder {
        private static final ConsumerServiceImpl INSTANCE = new ConsumerServiceImpl();
    }
}
