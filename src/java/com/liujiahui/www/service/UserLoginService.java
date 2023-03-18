package com.liujiahui.www.service;

import com.liujiahui.www.dao.UserLoginDAO;
import com.liujiahui.www.entity.bo.UserLoginBO;
import com.liujiahui.www.entity.dto.UserAccountOnJavaDTO;
import com.liujiahui.www.entity.dto.UserInformationSaveDTO;
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
    public static UserInformationSaveDTO login(UserLoginBO userLoginBO) throws ContractException, SQLException, IOException {
        String account = userLoginBO.getAccount();
        String password = userLoginBO.getPassword();
        String identity = userLoginBO.getIdentity();
        UserAccountOnJavaDTO userAccountOnJavaDTO = new UserAccountOnJavaDTO();
        userAccountOnJavaDTO.setAccount(account);
        userAccountOnJavaDTO.setPassword(password);
        String checkIdentity="suppliers";
        if(Objects.equals(identity, checkIdentity)){
            userAccountOnJavaDTO.setIdentity("suppliers");
        }else {
            userAccountOnJavaDTO.setIdentity("consumer");
        }
        return UserLoginDAO.login(userAccountOnJavaDTO);
    }
}
