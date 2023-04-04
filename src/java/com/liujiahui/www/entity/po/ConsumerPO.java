package com.liujiahui.www.entity.po;

/**
 * 消费者
 *
 * @author 刘家辉
 * @date 2023/03/16
 */
public class ConsumerPO extends UserPO {
    @Override
    public String toString() {
        return "Consumer{" +
                "name='" + name + '\'' +
                ", phoneNumber=" + phoneNumber +
                ", password='" + password + '\'' +
                ", gender='" + gender + '\'' +
                '}';
    }
}
