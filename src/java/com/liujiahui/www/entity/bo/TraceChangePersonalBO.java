package com.liujiahui.www.entity.bo;

/**
 * 用于修改个人信息的BO
 *
 * @author 刘家辉
 * @date 2023/03/18
 */
public class TraceChangePersonalBO {
    private Integer choice;
    private String change;
    private String identity;

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public Integer getChoice() {
        return choice;
    }

    public void setChoice(Integer choice) {
        this.choice = choice;
    }

    public String getChange() {
        return change;
    }

    public void setChange(String change) {
        this.change = change;
    }

    @Override
    public String toString() {
        return "UserChangeBO{" +
                "choice=" + choice +
                ", change='" + change + '\'' +
                '}';
    }
}
