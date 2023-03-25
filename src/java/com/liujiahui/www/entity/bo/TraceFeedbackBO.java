package com.liujiahui.www.entity.bo;

/**
 * 用户反馈波
 *
 * @author 刘家辉
 * @date 2023/03/22
 */
public class TraceFeedbackBO {
  private String  seller;
  private String  buyer;
  private String  comment;
  private Integer choice;
  private String  itemHash;

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public String getBuyer() {
        return buyer;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getChoice() {
        return choice;
    }

    public void setChoice(Integer choice) {
        this.choice = choice;
    }

    public String getItemHash() {
        return itemHash;
    }

    public void setItemHash(String itemHash) {
        this.itemHash = itemHash;
    }
    @Override
    public String toString() {
        return "UserFeedbackBO{" +
                "seller='" + seller + '\'' +
                ", buyer='" + buyer + '\'' +
                ", comment='" + comment + '\'' +
                ", choice=" + choice +
                ", itemHash='" + itemHash + '\'' +
                '}';
    }
}
