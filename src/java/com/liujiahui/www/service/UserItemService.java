package com.liujiahui.www.service;

import com.liujiahui.www.dao.UserBuyDAO;
import com.liujiahui.www.dao.UserItemAddAndShowDAO;
import com.liujiahui.www.entity.bo.UserAddItemBO;
import com.liujiahui.www.entity.dto.UserRealItemDTO;
import com.liujiahui.www.entity.dto.UserInformationSaveDTO;
import com.liujiahui.www.entity.dto.UserTransactionDTO;
import com.liujiahui.www.entity.po.Item;
import com.liujiahui.www.solidity.ItemTrade;
import org.fisco.bcos.sdk.abi.datatypes.generated.tuples.generated.Tuple1;
import org.fisco.bcos.sdk.abi.datatypes.generated.tuples.generated.Tuple2;
import org.fisco.bcos.sdk.model.TransactionReceipt;
import org.fisco.bcos.sdk.transaction.model.dto.TransactionResponse;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;
import org.fisco.bcos.sdk.utils.Numeric;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

/**
 * 添加物品服务
 *
 * @author 刘家辉
 * @date 2023/03/18
 */
public class UserItemService {
    public static void addItem(UserAddItemBO userAddItemBO) throws SQLException, IOException {
        UserInformationSaveDTO instance = UserInformationSaveDTO.getInstance();
        ItemTrade itemTradeSolidity = instance.getItemTradeSolidity();
        TransactionReceipt transactionReceipt = itemTradeSolidity.addItem(userAddItemBO.getRealName(), userAddItemBO.getPrice(), userAddItemBO.getRealDescription());
        Tuple1<BigInteger> addItemOutput = itemTradeSolidity.getAddItemOutput(transactionReceipt);
        System.out.println(addItemOutput);
        System.out.println(addItemOutput.getValue1());
        UserItemAddAndShowDAO.addItem(userAddItemBO.getName(), userAddItemBO.getPrice(), userAddItemBO.getDescription(),instance.getAccount(),addItemOutput.getValue1());
        System.out.println(addItemOutput.getValue1());
    }

    public static List<Item> showAllItem() throws SQLException, IOException {
        return UserItemAddAndShowDAO.showAllItem();
    }

    public static List<Item> showMyItem() throws SQLException, IOException {
        return UserItemAddAndShowDAO.showMyItem(UserInformationSaveDTO.getInstance().getAccount());
    }

    public static List<Item> showSoldItem() throws SQLException, IOException, ContractException {
        return UserItemAddAndShowDAO.showSoldItem();
    }
    public static UserTransactionDTO buyItem(String seller, BigDecimal index) throws ContractException, SQLException, IOException {
        UserInformationSaveDTO instance = UserInformationSaveDTO.getInstance();
        ItemTrade itemTradeSolidity = instance.getItemTradeSolidity();
        BigInteger bigInteger = index.toBigInteger();
        bigInteger=bigInteger.subtract(BigInteger.ONE);
        TransactionReceipt transactionReceipt = itemTradeSolidity.buyItem(seller, bigInteger);
        TransactionResponse transactionResponse = instance.getDecoder().decodeReceiptStatus(transactionReceipt);
        UserTransactionDTO userTransactionDTO = new UserTransactionDTO();
        if(Objects.equals(transactionResponse.getReturnMessage(), "")){
            System.out.println("yeah");
            return null;
        }
        System.out.println(transactionResponse.getReturnMessage());
        if(!Objects.equals(transactionResponse.getReturnMessage(), "Success")){
            userTransactionDTO.setReturnMessage(transactionResponse.getReturnMessage());
            return null;
        }
        BigInteger bigInteger1 = index.toBigInteger();
        UserBuyDAO.buyItem(seller,bigInteger1);
        List<ItemTrade.ItemSoldEventResponse> itemSoldEvents = itemTradeSolidity.getItemSoldEvents(transactionReceipt);
        ItemTrade.ItemSoldEventResponse itemSoldEventResponse = itemSoldEvents.get(0);
        BigInteger balance = itemTradeSolidity.getBalance();
        instance.setBalance(String.valueOf(balance));
        userTransactionDTO.setItemSoldEventResponse(itemSoldEventResponse);
        userTransactionDTO.setBalance(String.valueOf(balance));
        return userTransactionDTO;
    }

    public static UserRealItemDTO checkByHash(String hash) throws ContractException {
        byte[] bytes = Numeric.hexStringToByteArray(hash);
        Tuple2<String, String> realItem = UserInformationSaveDTO.getInstance().getItemTradeSolidity().getRealItem(bytes);
        UserRealItemDTO userRealItemDTO = new UserRealItemDTO();
        userRealItemDTO.setName(realItem.getValue1());
        userRealItemDTO.setDescription(realItem.getValue2());
        return userRealItemDTO;
    }
}
