package com.liujiahui.www.entity.dto;

/**
 * 真正项dto
 *
 * @author 刘家辉
 * @date 2023/03/21
 */
public class TraceRealItemDTO {
    private String name;
    private String description;
    private String seller;

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "RealItemDTO{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
