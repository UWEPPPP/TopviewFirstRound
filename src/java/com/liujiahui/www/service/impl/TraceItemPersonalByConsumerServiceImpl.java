package com.liujiahui.www.service.impl;

import com.liujiahui.www.dao.UserBuyDAO;
import com.liujiahui.www.dao.UserItemDAO;
import com.liujiahui.www.entity.dto.UserInformationSaveDTO;
import com.liujiahui.www.entity.dto.UserItemStatusDTO;
import com.liujiahui.www.entity.dto.UserRealItemDTO;
import com.liujiahui.www.entity.dto.UserTransactionDTO;
import com.liujiahui.www.entity.po.Item;
import com.liujiahui.www.service.ContractTradeService;
import com.liujiahui.www.service.TraceItemPersonalService;
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
    private TraceItemPersonalByConsumerServiceImpl() {
    }
    public static TraceItemPersonalByConsumerServiceImpl getInstance() {
        return SERVICE;
    }

    @Override
    public Map<String, List<Item>> showItem() throws ContractException, SQLException, IOException {
        Map<String, List<Item>> map = new HashMap<>(1);
        map.put("item",UserItemDAO.showMyItem(UserInformationSaveDTO.getInstance().getContractAccount()));
        return map;
    }
    public  UserTransactionDTO buyItem(String seller, BigDecimal index) throws ContractException, SQLException, IOException {
       UserInformationSaveDTO instance = UserInformationSaveDTO.getInstance();
       ContractTradeService contractTradeServiceSolidity = instance.getItemTradeSolidity();
       BigInteger bigInteger = index.toBigInteger();
       bigInteger=bigInteger.subtract(BigInteger.ONE);
       TransactionReceipt transactionReceipt = contractTradeServiceSolidity.buyItem(seller, bigInteger);
       TransactionResponse transactionResponse = instance.getDecoder().decodeReceiptStatus(transactionReceipt);
       UserTransactionDTO userTransactionDTO = new UserTransactionDTO();
       if(!Objects.equals(transactionResponse.getReturnMessage(), "Success")){
           userTransactionDTO.setReturnMessage(transactionResponse.getReturnMessage());
           return null;
       }
       List<ContractTradeService.ItemSoldEventResponse> itemSoldEvents = contractTradeServiceSolidity.getItemSoldEvents(transactionReceipt);
       ContractTradeService.ItemSoldEventResponse itemSoldEventResponse = itemSoldEvents.get(0);
       System.out.println(itemSoldEventResponse);
       BigInteger bigInteger1 = index.toBigInteger();
       UserBuyDAO.buyItem(seller,bigInteger1,itemSoldEventResponse.hash);
       BigInteger balance = contractTradeServiceSolidity.getBalance();
       instance.setBalance(String.valueOf(balance));
       userTransactionDTO.setItemSoldEventResponse(itemSoldEventResponse);
       userTransactionDTO.setBalance(String.valueOf(balance));
       return userTransactionDTO;
   }

     public  UserRealItemDTO checkByHash(String hash) throws ContractException {
         byte[] bytes = Numeric.hexStringToByteArray(hash);
         Tuple3<String, String, String> realItem = UserInformationSaveDTO.getInstance().getItemTradeSolidity().getRealItem(bytes);

         UserRealItemDTO userRealItemDTO = new UserRealItemDTO();
         userRealItemDTO.setName(realItem.getValue1());
         userRealItemDTO.setDescription(realItem.getValue2());
         userRealItemDTO.setSeller(realItem.getValue3());
         return userRealItemDTO;
     }


     public  UserItemStatusDTO checkStatus(String hash1) throws ContractException {
         byte[] bytes = Numeric.hexStringToByteArray(hash1);
         Tuple3<BigInteger, String, BigInteger> status = UserInformationSaveDTO.getInstance().getItemTradeSolidity().checkStatus(bytes);
         UserItemStatusDTO userItemStatusDTO = new UserItemStatusDTO();
         long longValue = status.getValue1().longValue();
         Date date = new Date(longValue * 1000);
         userItemStatusDTO.setDate(date);
         userItemStatusDTO.setPlace(status.getValue2());
         BigInteger value3 = status.getValue3();
         int intValue = value3.intValue();
         if (intValue == 0) {
             userItemStatusDTO.setStatus("未出库，正在准备中");
         } else if (intValue == 1) {
             userItemStatusDTO.setStatus("已出库，正在运送中");
         } else {
             userItemStatusDTO.setStatus("已送达");
         }
         return userItemStatusDTO;
     }
}
