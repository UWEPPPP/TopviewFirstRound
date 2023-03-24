package com.liujiahui.www.service.impl;

import com.liujiahui.www.dao.UserLoginDAO;
import com.liujiahui.www.dao.UserRegisterDAO;
import com.liujiahui.www.entity.bo.UserLoginBO;
import com.liujiahui.www.entity.bo.UserRegisterBO;
import com.liujiahui.www.entity.dto.UserAccountOnJavaDTO;
import com.liujiahui.www.entity.dto.UserInformationSaveDTO;
import com.liujiahui.www.entity.po.Supplier;
import com.liujiahui.www.service.TraceRegisterAndLoginService;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;

import java.io.IOException;
import java.sql.SQLException;

/**
 * @author 刘家辉
 * @date 2023/03/25
 */
public class TraceRegisterAndLoginBySupplierServiceImpl implements TraceRegisterAndLoginService {
    private static final TraceRegisterAndLoginBySupplierServiceImpl SERVICE = new TraceRegisterAndLoginBySupplierServiceImpl();
    private TraceRegisterAndLoginBySupplierServiceImpl() {
    }
    public static TraceRegisterAndLoginBySupplierServiceImpl getInstance() {
        return SERVICE;
    }
    @Override
    public Boolean register(UserRegisterBO userRegisterBO) throws SQLException, IOException {
        Supplier supplier = new Supplier();
        supplier.setName(userRegisterBO.getName());
        supplier.setGender(userRegisterBO.getGender());
        supplier.setPhoneNumber(userRegisterBO.getPhone());
        supplier.setPassword(userRegisterBO.getPassword());
        supplier.setAddress(userRegisterBO.getAddress());
        UserRegisterDAO userRegisterDAO = new UserRegisterDAO();
        return userRegisterDAO.register(supplier);
    }

    @Override
    public UserInformationSaveDTO login(UserLoginBO userLoginBO) throws ContractException, SQLException, IOException {
        String account = userLoginBO.getAccount();
        String password = userLoginBO.getPassword();
        String identity = userLoginBO.getIdentity();
        UserAccountOnJavaDTO userAccountOnJavaDTO = new UserAccountOnJavaDTO();
        userAccountOnJavaDTO.setAccount(account);
        userAccountOnJavaDTO.setPassword(password);
        userAccountOnJavaDTO.setIdentity("suppliers");
        return UserLoginDAO.login(userAccountOnJavaDTO);
    }
}
