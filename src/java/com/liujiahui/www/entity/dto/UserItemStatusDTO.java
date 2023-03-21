package com.liujiahui.www.entity.dto;

import java.util.Date;

/**
 * 用户项状态
 *
 * @author 刘家辉
 * @date 2023/03/21
 */
public class UserItemStatusDTO {
         private   Date    date;
         private   String    place;
         private   String     status;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "UserItemStatus{" +
                "date='" + date + '\'' +
                ", place='" + place + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
