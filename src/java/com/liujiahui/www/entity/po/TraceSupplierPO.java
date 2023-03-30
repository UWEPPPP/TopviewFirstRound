package com.liujiahui.www.entity.po;

/**
 * 供应商
 *
 * @author 刘家辉
 * @date 2023/03/16
 */
public class TraceSupplierPO extends TraceUserPO {
    protected String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Supplier{" +
                "address='" + address + '\'' +
                ", name='" + name + '\'' +
                ", phoneNumber=" + phoneNumber +
                ", password='" + password + '\'' +
                ", gender='" + gender + '\'' +
                '}';
    }
}
