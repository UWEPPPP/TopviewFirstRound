package com.liujiahui.www.service;

import com.liujiahui.www.dao.UserLoginDAO;
import com.liujiahui.www.entity.bo.UserLoginBO;
import com.liujiahui.www.entity.dto.UserInformationDTO;
import com.liujiahui.www.entity.po.*;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

/**
 * 用户登录服务
 *
 * @author 刘家辉
 * @date 2023/03/17
 */
public class UserLoginService {
    public static UserInformationDTO login(UserLoginBO userLoginBO) throws ContractException, SQLException, IOException {
        String account = userLoginBO.getAccount();
        String password = userLoginBO.getPassword();
        String identity = userLoginBO.getIdentity();
        UserAccountOnJavaBO userAccountOnJavaBO = new UserAccountOnJavaBO();
        userAccountOnJavaBO.setAccount(account);
        userAccountOnJavaBO.setPassword(password);
        String checkIdentity="suppliers";
        if(Objects.equals(identity, checkIdentity)){
            userAccountOnJavaBO.setIdentity("suppliers");
            UserAfterLoginBO user = UserLoginDAO.login(userAccountOnJavaBO);
            if(user!=null){
                return new UserInformationDTO(user.getName(),user.getBalance(),user.getAccountAddress());
            }
        }else {
            userAccountOnJavaBO.setIdentity("consumer");
            UserAfterLoginBO user =UserLoginDAO.login(userAccountOnJavaBO);
            if(user!=null){
                return new UserInformationDTO(user.getName(),user.getBalance(),user.getAccountAddress());
            }
        }
        return null;
    }
}
