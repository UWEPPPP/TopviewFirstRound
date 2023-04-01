package com.liujiahui.www.entity.dto;

/**
 * 真正项dto
 *
 * @author 刘家辉
 * @date 2023/03/21
 */
public class TraceRealAndOutItemDTO {
    private String realName;
    private String realDescription;
    private String outName;
    private String outDescription;
    private String seller;

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getRealDescription() {
        return realDescription;
    }

    public void setRealDescription(String realDescription) {
        this.realDescription = realDescription;
    }

    @Override
    public String toString() {
        return "RealItemDTO{" +
                "name='" + realName + '\'' +
                ", description='" + realDescription + '\'' +
                '}';
    }

    public String getOutName() {
        return outName;
    }

    public void setOutName(String outName) {
        this.outName = outName;
    }

    public String getOutDescription() {
        return outDescription;
    }

    public void setOutDescription(String outDescription) {
        this.outDescription = outDescription;
    }
}
