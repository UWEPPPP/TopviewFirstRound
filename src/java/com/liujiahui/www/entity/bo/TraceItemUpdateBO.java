package com.liujiahui.www.entity.bo;

/**
 * 用户项目更新博
 *
 * @author 刘家辉
 * @date 2023/03/21
 */
public class TraceItemUpdateBO {
    private String name;
    private String price;
    private String description;
    private String oldName;
    private Integer index;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "ItemBO{" +
                "name='" + name + '\'' +
                ", price='" + price + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public void setOldName(String oldName) {
        this.oldName = oldName;
    }

    public String getOldName() {
        return oldName;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Integer getIndex() {
        return index;
    }
}
