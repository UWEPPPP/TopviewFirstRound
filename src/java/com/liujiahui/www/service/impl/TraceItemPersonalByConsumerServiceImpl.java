package com.liujiahui.www.service.impl;

import com.liujiahui.www.dao.TraceUserDAO;
import com.liujiahui.www.dao.impl.TraceConsumerDAOImpl;
import com.liujiahui.www.dao.impl.TraceFactoryDAO;
import com.liujiahui.www.entity.bo.TraceFeedbackBO;
import com.liujiahui.www.entity.dto.TraceInformationSaveDTO;
import com.liujiahui.www.entity.dto.TraceItemStatusDTO;
import com.liujiahui.www.entity.dto.TraceRealItemDTO;
import com.liujiahui.www.entity.dto.TraceTransactionDTO;
import com.liujiahui.www.entity.po.TraceItemPO;
import com.liujiahui.www.service.TraceItemPersonalService;
import com.liujiahui.www.service.wrapper.ContractStorageService;
import com.liujiahui.www.service.wrapper.ContractTradeService;
import org.fisco.bcos.sdk.abi.datatypes.DynamicArray;
import org.fisco.bcos.sdk.abi.datatypes.generated.tuples.generated.Tuple3;
import org.fisco.bcos.sdk.model.TransactionReceipt;
import org.fisco.bcos.sdk.transaction.model.dto.TransactionResponse;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;
import org.fisco.bcos.sdk.utils.Numeric;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.*;

/**
 * 跟踪项目个人消费者service impl
 *
 * @author 刘家辉
 * @date 2023/03/24
 */
public class TraceItemPersonalByConsumerServiceImpl implements TraceItemPersonalService {
    private static final TraceItemPersonalByConsumerServiceImpl SERVICE = new TraceItemPersonalByConsumerServiceImpl();
    private static final TraceUserDAO USER_ITEM = TraceFactoryDAO.getTraceFactoryDAO(false);

    private TraceItemPersonalByConsumerServiceImpl() {
    }

    public static TraceItemPersonalByConsumerServiceImpl getInstance() {
        return SERVICE;
    }

    @Override
    public Map<String, List<TraceItemPO>> showItem() throws SQLException, IOException, ContractException {
        Map<String, List<TraceItemPO>> map = new HashMap<>(1);
        Map<String, List<TraceItemPO>> stringListMap = USER_ITEM.showItem(TraceInformationSaveDTO.getInstance().getContractAccount());
        map.put("item", stringListMap.get("item"));
        return map;
    }

    public TraceTransactionDTO buyItem(String seller, BigDecimal index) throws ContractException, SQLException, IOException {
        TraceInformationSaveDTO instance = TraceInformationSaveDTO.getInstance();
        ContractTradeService contractTradeServiceSolidity = instance.getItemTradeSolidity();
        BigInteger bigInteger = index.toBigInteger();
        TransactionReceipt transactionReceipt = contractTradeServiceSolidity.buyItem(seller, bigInteger);
        TransactionResponse transactionResponse = instance.getDecoder().decodeReceiptStatus(transactionReceipt);
        TraceTransactionDTO traceTransactionDTO = new TraceTransactionDTO();
        String checkError = "Success";
        if (!Objects.equals(transactionResponse.getReturnMessage(), checkError)) {
            System.out.println("交易失败");
            traceTransactionDTO.setReturnMessage(transactionResponse.getReturnMessage());
            return null;
        }
        List<ContractTradeService.ItemSoldEventResponse> itemSoldEvents = contractTradeServiceSolidity.getItemSoldEvents(transactionReceipt);
        ContractTradeService.ItemSoldEventResponse itemSoldEventResponse = itemSoldEvents.get(0);
        System.out.println(itemSoldEventResponse);
        BigInteger bigInteger1 = index.toBigInteger();
        ((TraceConsumerDAOImpl) USER_ITEM).buyItem(seller, bigInteger1);
        BigInteger balance = contractTradeServiceSolidity.getBalance();
        instance.setBalance(String.valueOf(balance));
        traceTransactionDTO.setItemSoldEventResponse(itemSoldEventResponse);
        traceTransactionDTO.setBalance(String.valueOf(balance));
        return traceTransactionDTO;
    }

    public TraceRealItemDTO checkByHash(String hash) throws ContractException {
        byte[] bytes = Numeric.hexStringToByteArray(hash);
        Tuple3<String, String, String> realItem = TraceInformationSaveDTO.getInstance().getItemTradeSolidity().getRealItem(bytes);

        TraceRealItemDTO traceRealItemDTO = new TraceRealItemDTO();
        traceRealItemDTO.setName(realItem.getValue1());
        traceRealItemDTO.setDescription(realItem.getValue2());
        traceRealItemDTO.setSeller(realItem.getValue3());
        return traceRealItemDTO;
    }


    public TraceItemStatusDTO checkStatus(String hash1) throws ContractException {
        byte[] bytes = Numeric.hexStringToByteArray(hash1);
        Tuple3<BigInteger, String, BigInteger> status = TraceInformationSaveDTO.getInstance().getItemTradeSolidity().getStatus(bytes);
        TraceItemStatusDTO traceItemStatusDTO = new TraceItemStatusDTO();
        long longValue = status.getValue1().longValue();
        Date date = new Date(longValue * 1000);
        traceItemStatusDTO.setDate(date);
        traceItemStatusDTO.setPlace(status.getValue2());
        BigInteger value3 = status.getValue3();
        int intValue = value3.intValue();
        stuffingStatus(traceItemStatusDTO, intValue);
        return traceItemStatusDTO;
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

    public void supplierWriteDownService(TraceFeedbackBO traceFeedbackBO) throws SQLException, IOException {
        String buyer = traceFeedbackBO.getBuyer();
        String seller = traceFeedbackBO.getSeller();
        String itemHash = traceFeedbackBO.getItemHash();
        String comment = traceFeedbackBO.getComment();
        int choice = traceFeedbackBO.getChoice();
        ContractTradeService itemTradeSolidity = TraceInformationSaveDTO.getInstance().getItemTradeSolidity();
        itemTradeSolidity.handing_feedback(choice == 1, Numeric.hexStringToByteArray(itemHash));
        ((TraceConsumerDAOImpl) USER_ITEM).writeDown(seller, buyer, comment, choice, itemHash);
    }

    public void returnItem(String hash2) throws SQLException, IOException {
        ((TraceConsumerDAOImpl) USER_ITEM).returnItem(hash2);
    }

    public List<TraceItemStatusDTO> checkLife(String hash3) throws ContractException {
        TraceInformationSaveDTO instance = TraceInformationSaveDTO.getInstance();
        ContractTradeService contractTradeServiceSolidity = instance.getItemTradeSolidity();
        byte[] bytes = Numeric.hexStringToByteArray(hash3);
        DynamicArray<ContractStorageService.ItemLife> itemLifeDynamicArray = contractTradeServiceSolidity.showWholeLife(bytes);
        List<ContractStorageService.ItemLife> itemLifeList = itemLifeDynamicArray.getValue();
        List<TraceItemStatusDTO> itemStauts = new ArrayList<>();
        for (ContractStorageService.ItemLife itemLife : itemLifeList) {
            TraceItemStatusDTO traceItemStatusDTO = new TraceItemStatusDTO();
            long time = itemLife.date.longValue();
            Date date = new Date(time * 1000);
            traceItemStatusDTO.setDate(date);
            traceItemStatusDTO.setPlace(itemLife.place);
            int status = itemLife.status.intValue();
            stuffingStatus(traceItemStatusDTO, status);
            itemStauts.add(traceItemStatusDTO);
        }
        return itemStauts;
    }
}
