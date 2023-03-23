package com.liujiahui.www.entity.po;

/**
 * 反馈
 *
 * @author 刘家辉
 * @date 2023/03/23
 */
public class Feedback {
   private String buyer;
   private String comment;
   private String itemHash;
   private Boolean likeOrReport;
   private String seller;
   private String itemName;

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
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

    public String getItemHash() {
        return itemHash;
    }

    public void setItemHash(String itemHash) {
        this.itemHash = itemHash;
    }

    public Boolean getLikeOrReport() {
        return likeOrReport;
    }

    public void setLikeOrReport(Boolean likeOrReport) {
        this.likeOrReport = likeOrReport;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "buyer='" + buyer + '\'' +
                ", comment='" + comment + '\'' +
                ", itemHash='" + itemHash + '\'' +
                ", likeOrReport=" + likeOrReport +
                ", seller='" + seller + '\'' +
                '}';
    }
}
