package com.liujiahui.www.controller;

import com.liujiahui.www.entity.bo.UserLoginBO;
import com.liujiahui.www.entity.dto.UserInformationDTO;
import com.liujiahui.www.entity.vo.UserAfterLoginVO;
import com.liujiahui.www.service.UserLoginService;
import com.liujiahui.www.view.ConsumerMainInterface;
import com.liujiahui.www.view.LoginInterface;
import com.liujiahui.www.view.ReturnInterface;
import com.liujiahui.www.view.SupplierMainInterface;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;

import java.io.IOException;
import java.sql.SQLException;

/**
 * 用户登录控制器
 *
 * @author 刘家辉
 * @date 2023/03/16
 */
public class UserLoginController {
      public static String identity;
    public static void loginByConsumer(String account, String password) throws SQLException, IOException, ContractException {
        UserLoginBO userLoginBO = new UserLoginBO(account,password,"consumer");
        identity="consumer";
        UserInformationDTO login=UserLoginService.login(userLoginBO);
        loginBackView(login);
    }

    public static void loginBySupplier(String account, String password) throws ContractException, SQLException, IOException {
        UserLoginBO userLoginBO = new UserLoginBO(account,password,"suppliers");
        identity="suppliers";
        UserInformationDTO login = UserLoginService.login(userLoginBO);
        loginBackView(login);
    }

    public static void loginBackView(UserInformationDTO userInformationDTO) throws ContractException, SQLException, IOException {
        if(userInformationDTO == null){
            ReturnInterface.loginReturnInterface();
        }
        UserAfterLoginVO userAfterLoginVO = new UserAfterLoginVO(userInformationDTO.getUserName(),userInformationDTO.getBalance(),identity);
        String identityCheck ="consumer";
        if(identity.equals(identityCheck)) {
            ConsumerMainInterface.view(userAfterLoginVO);
        }else {
            SupplierMainInterface.view(userAfterLoginVO);
        }
    }


    /**
     * 登录
     */
    public void loginOrderByIdentity(int choice1) throws SQLException, IOException, ContractException {
        if(choice1 == 1){
            new LoginInterface().loginBySupplier();
        }else {
            new LoginInterface().loginByConsumer();
        }
    }

}
