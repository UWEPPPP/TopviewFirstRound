package com.liujiahui.www.service.impl;

import com.liujiahui.www.dao.UserLoginDAO;
import com.liujiahui.www.dao.UserRegisterDAO;
import com.liujiahui.www.entity.bo.UserLoginBO;
import com.liujiahui.www.entity.bo.UserRegisterBO;
import com.liujiahui.www.entity.dto.UserAccountOnJavaDTO;
import com.liujiahui.www.entity.dto.UserInformationSaveDTO;
import com.liujiahui.www.entity.po.Consumer;
import com.liujiahui.www.service.TraceRegisterAndLoginService;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;

import java.io.IOException;
import java.sql.SQLException;

/**
 * 用户注册服务
 *
 * @author 刘家辉
 * @date 2023/03/16
 */
public class TraceRegisterAndLoginByConsumerServiceImpl implements TraceRegisterAndLoginService {
    private static final TraceRegisterAndLoginByConsumerServiceImpl SERVICE = new TraceRegisterAndLoginByConsumerServiceImpl();
    private TraceRegisterAndLoginByConsumerServiceImpl() {
    }
    public static TraceRegisterAndLoginByConsumerServiceImpl getInstance() {
        return SERVICE;
    }

    @Override
    public Boolean register(UserRegisterBO userRegisterBO) throws SQLException, IOException {
        Consumer consumer = new Consumer();
        consumer.setName(userRegisterBO.getName());
        consumer.setGender(userRegisterBO.getGender());
        consumer.setPhoneNumber(userRegisterBO.getPhone());
        consumer.setPassword(userRegisterBO.getPassword());
        UserRegisterDAO userRegisterDAO = new UserRegisterDAO();
        return userRegisterDAO.register(consumer);
    }

    @Override
    public UserInformationSaveDTO login(UserLoginBO userLoginBO) throws ContractException, SQLException, IOException {
        String account = userLoginBO.getAccount();
        String password = userLoginBO.getPassword();
        String identity = userLoginBO.getIdentity();
        UserAccountOnJavaDTO userAccountOnJavaDTO = new UserAccountOnJavaDTO();
        userAccountOnJavaDTO.setAccount(account);
        userAccountOnJavaDTO.setPassword(password);
        userAccountOnJavaDTO.setIdentity("consumer");
        return UserLoginDAO.login(userAccountOnJavaDTO);
    }

}
