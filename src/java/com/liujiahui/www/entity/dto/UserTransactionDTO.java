package com.liujiahui.www.entity.dto;

import com.liujiahui.www.solidity.ItemTradeSolidity;

/**
 * @author LiuJiaHui
 */
public class UserTransactionDTO {
  private ItemTradeSolidity.ItemSoldEventResponse itemSoldEventResponse;
  private String balance;

  public ItemTradeSolidity.ItemSoldEventResponse getItemSoldEventResponse() {
    return itemSoldEventResponse;
  }

  public void setItemSoldEventResponse(ItemTradeSolidity.ItemSoldEventResponse itemSoldEventResponse) {
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
}