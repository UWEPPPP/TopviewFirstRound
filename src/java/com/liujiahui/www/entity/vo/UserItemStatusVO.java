package com.liujiahui.www.entity.vo;

import java.util.Date;

public class UserItemStatusVO {
         private Date    date;
         private String      place;
         private String      status;

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
        return "UserItemStatusVO{" +
                "date='" + date + '\'' +
                ", place='" + place + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
