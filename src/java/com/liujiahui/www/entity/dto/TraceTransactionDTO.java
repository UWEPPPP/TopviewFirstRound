package com.liujiahui.www.entity.dto;

import com.liujiahui.www.service.wrapper.ContractTradeService;

/**
 * @author LiuJiaHui
 */
public class TraceTransactionDTO {
  private ContractTradeService.ItemSoldEventResponse itemSoldEventResponse;
  private String balance;
  private String returnMessage;

  public ContractTradeService.ItemSoldEventResponse getItemSoldEventResponse() {
    return itemSoldEventResponse;
  }

  public void setItemSoldEventResponse(ContractTradeService.ItemSoldEventResponse itemSoldEventResponse) {
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

    public void setReturnMessage(String returnMessage) {
        this.returnMessage = returnMessage;
    }

    public String getReturnMessage() {
        return returnMessage;
    }
}
