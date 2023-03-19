package com.liujiahui.www.service;

import com.liujiahui.www.dao.UserItemAddAndShowDAO;
import com.liujiahui.www.entity.bo.AddItemBO;
import com.liujiahui.www.entity.dto.UserInformationSaveDTO;
import com.liujiahui.www.entity.dto.UserTransactionDTO;
import com.liujiahui.www.entity.po.Item;
import com.liujiahui.www.solidity.ItemTradeSolidity;
import org.fisco.bcos.sdk.abi.datatypes.generated.tuples.generated.Tuple1;
import org.fisco.bcos.sdk.model.TransactionReceipt;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

/**
 * 添加物品服务
 *
 * @author 刘家辉
 * @date 2023/03/18
 */
public class UserItemService {
    public static void addItem(AddItemBO addItemBO) throws SQLException, IOException {
        UserInformationSaveDTO instance = UserInformationSaveDTO.getInstance();
        ItemTradeSolidity itemTradeSolidity = instance.getItemTradeSolidity();
        TransactionReceipt transactionReceipt = itemTradeSolidity.addItem(addItemBO.getRealName(), addItemBO.getPrice(), addItemBO.getRealDescription());
        Tuple1<BigInteger> addItemOutput = itemTradeSolidity.getAddItemOutput(transactionReceipt);
        UserItemAddAndShowDAO.addItem(addItemBO.getName(),addItemBO.getPrice(),addItemBO.getDescription(),instance.getAccount(),addItemOutput.getValue1());
    }

    public static List<Item> showItem() throws SQLException, IOException {
        return UserItemAddAndShowDAO.showItem();
    }

    public static UserTransactionDTO buyItem(String seller, BigDecimal index) throws ContractException {
        UserInformationSaveDTO instance = UserInformationSaveDTO.getInstance();
        ItemTradeSolidity itemTradeSolidity = instance.getItemTradeSolidity();
        BigInteger bigInteger = index.toBigInteger();
        bigInteger=bigInteger.subtract(BigInteger.ONE);
        TransactionReceipt transactionReceipt = itemTradeSolidity.buyItem(seller, bigInteger);
        List<ItemTradeSolidity.ItemSoldEventResponse> itemSoldEvents = itemTradeSolidity.getItemSoldEvents(transactionReceipt);
        ItemTradeSolidity.ItemSoldEventResponse itemSoldEventResponse = itemSoldEvents.get(0);
        BigInteger balance = itemTradeSolidity.getBalance();
        instance.setBalance(String.valueOf(balance));
        UserTransactionDTO userTransactionDTO = new UserTransactionDTO();
        userTransactionDTO.setItemSoldEventResponse(itemSoldEventResponse);
        userTransactionDTO.setBalance(String.valueOf(balance));
        return userTransactionDTO;
    }
}
