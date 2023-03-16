package com.liujiahui.www.service;

import com.liujiahui.www.dao.UserDAO;
import com.liujiahui.www.entity.bo.UserRegisterBO;
import com.liujiahui.www.entity.po.Consumer;
import com.liujiahui.www.entity.po.Supplier;

import java.io.IOException;
import java.sql.SQLException;

/**
 * 用户注册服务
 *
 * @author 刘家辉
 * @date 2023/03/16
 */
public class UserRegisterService {
    public static Boolean registerByConsumer(UserRegisterBO userRegisterBO) throws SQLException, IOException {
        Consumer consumer = new Consumer();
        consumer.setName(userRegisterBO.getName());
        consumer.setGender(userRegisterBO.getGender());
        consumer.setPhoneNumber(userRegisterBO.getPhone());
        consumer.setPassword(userRegisterBO.getPassword());
        UserDAO userDAO = new UserDAO();
        if (userDAO.register(consumer)) {
            //成功
            return true;
        } else {
            //失败
            return false;
        }
    }
    public static Boolean registerBySupplier(UserRegisterBO userRegisterBO) throws SQLException, IOException {
        Supplier supplier = new Supplier();
        supplier.setName(userRegisterBO.getName());
        supplier.setGender(userRegisterBO.getGender());
        supplier.setPhoneNumber(userRegisterBO.getPhone());
        supplier.setPassword(userRegisterBO.getPassword());
        supplier.setAddress(userRegisterBO.getAddress());
        UserDAO userDAO = new UserDAO();
        if (userDAO.register(supplier)) {
            return true;
        } else {
            return false;
        }
    }

}
