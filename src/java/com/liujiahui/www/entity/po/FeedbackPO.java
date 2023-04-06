package com.liujiahui.www.entity.po;

/**
 * 反馈
 *
 * @author 刘家辉
 * @date 2023/03/23
 */
public class FeedbackPO {
    private String buyer;
    private String comment;
    private String itemHash;
    private Boolean likeOrReport;
    private String seller;
    private String itemName;
    private Boolean isRead;
    private String supplierComplaint;
    private Boolean isAppeal;
    private Boolean appealResult;

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
        return
                "买家='" + buyer + '\'' +
                        ", 评论='" + comment + '\'' +
                        ", 商品名字='" + itemName + '\'' +
                        ",商品hash=" + itemHash +
                        ", 评价类型=" + (likeOrReport ? "好评" : "差评") +
                        (isAppeal ?
                                ", 卖家='" + seller + '\'' +
                                        "/n" + "商家申请申诉：" + supplierComplaint : "");
    }

    public Boolean getRead() {
        return isRead;
    }

    public void setRead(Boolean read) {
        isRead = read;
    }

    public String getSupplierComplaint() {
        return supplierComplaint;
    }

    public void setSupplierComplaint(String supplierComplaint) {
        this.supplierComplaint = supplierComplaint;
    }

    public Boolean getAppeal() {
        return isAppeal;
    }

    public void setAppeal(Boolean appeal) {
        isAppeal = appeal;
    }

    public boolean isAppealResult() {
        return appealResult;
    }

    public void setAppealResult(boolean appealResult) {
        this.appealResult = appealResult;
    }
}
