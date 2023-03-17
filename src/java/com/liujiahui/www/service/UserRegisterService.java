package com.liujiahui.www.service;

import com.liujiahui.www.dao.UserRegisterDAO;
import com.liujiahui.www.entity.bo.UserRegisterBO;
import com.liujiahui.www.entity.po.Consumer;
import com.liujiahui.www.entity.po.Supplier;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;

import java.io.IOException;
import java.sql.SQLException;

/**
 * 用户注册服务
 *
 * @author 刘家辉
 * @date 2023/03/16
 */
public class UserRegisterService {
    public static Boolean registerByConsumer(UserRegisterBO userRegisterBO) throws SQLException, IOException, ContractException {
        Consumer consumer = new Consumer();
        consumer.setName(userRegisterBO.getName());
        consumer.setGender(userRegisterBO.getGender());
        consumer.setPhoneNumber(userRegisterBO.getPhone());
        consumer.setPassword(userRegisterBO.getPassword());
        UserRegisterDAO userRegisterDAO = new UserRegisterDAO();
        return userRegisterDAO.register(consumer);
    }
    public static Boolean registerBySupplier(UserRegisterBO userRegisterBO) throws SQLException, IOException, ContractException {
        Supplier supplier = new Supplier();
        supplier.setName(userRegisterBO.getName());
        supplier.setGender(userRegisterBO.getGender());
        supplier.setPhoneNumber(userRegisterBO.getPhone());
        supplier.setPassword(userRegisterBO.getPassword());
        supplier.setAddress(userRegisterBO.getAddress());
        UserRegisterDAO userRegisterDAO = new UserRegisterDAO();
        return userRegisterDAO.register(supplier);
    }

}
