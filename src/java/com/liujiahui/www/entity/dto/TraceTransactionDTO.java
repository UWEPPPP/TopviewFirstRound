package com.liujiahui.www.entity.dto;

import org.fisco.bcos.sdk.abi.datatypes.generated.tuples.generated.Tuple3;

/**
 * @author LiuJiaHui
 */
public class TraceTransactionDTO {
    private Tuple3<String, String, byte[]> itemSoldEventResponse;
    private String balance;
    private String returnMessage;

    public Tuple3<String, String, byte[]> getItemSoldEventResponse() {
        return itemSoldEventResponse;
    }

    public void setItemSoldEventResponse(Tuple3<String, String, byte[]> itemSoldEventResponse) {
        this.itemSoldEventResponse = itemSoldEventResponse;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "UserTransactionDTO{" +
                "itemSoldEventResponse=" + itemSoldEventResponse +
                ", balance='" + balance + '\'' +
                '}';
    }

    public String getReturnMessage() {
        return returnMessage;
    }

    public void setReturnMessage(String returnMessage) {
        this.returnMessage = returnMessage;
    }
}
