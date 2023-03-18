package com.liujiahui.www.service;

import com.liujiahui.www.dao.UserChangePersonalDAO;
import com.liujiahui.www.entity.bo.UserChangePersonalBO;

import java.io.IOException;
import java.sql.SQLException;

/**
 * 用户改变个人服务
 *
 * @author 刘家辉
 * @date 2023/03/18
 */
public class UserChangePersonalService {
    public static void change(UserChangePersonalBO userChangeServiceBO) throws SQLException, IOException {
        Integer choice = userChangeServiceBO.getChoice();
        String change = userChangeServiceBO.getChange();
        String identity = userChangeServiceBO.getIdentity();
        String type = null;
        switch (choice){
            case 1:
                type = "user_name";
                break;
            case 2:
                type = "gender";
                break;
            case 3:
                type = "phone";
                break;
            default:
        }
        UserChangePersonalDAO.change(type,change,identity);
    }
}
