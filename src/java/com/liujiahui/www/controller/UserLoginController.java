package com.liujiahui.www.controller;

import com.liujiahui.www.entity.bo.UserLoginBO;
import com.liujiahui.www.entity.dto.UserInformationSaveDTO;
import com.liujiahui.www.entity.vo.UserAfterLoginVO;
import com.liujiahui.www.service.TraceFactoryService;
import com.liujiahui.www.service.TraceRegisterAndLoginService;
import com.liujiahui.www.view.UserMainInterface;
import com.liujiahui.www.view.UserLoginInterface;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

/**
 * 用户登录控制器
 *
 * @author 刘家辉
 * @date 2023/03/16
 */
public class UserLoginController {
      private static String identity;

    public  void login(String account, String password, Boolean choice) throws SQLException, IOException, ContractException, NoSuchAlgorithmException {
        TraceRegisterAndLoginService registerAndLoginService = TraceFactoryService.getTraceRegisterAndLoginService(choice);
        if(!choice) {
            UserLoginBO userLoginBO = new UserLoginBO(account,password,"consumer");
            identity = "consumer";
            UserInformationSaveDTO login = registerAndLoginService.login(userLoginBO);
            loginBackView(login);
         }else {
            UserLoginBO userLoginBO = new UserLoginBO(account,password,"suppliers");
            identity="suppliers";
            UserInformationSaveDTO login = registerAndLoginService.login(userLoginBO);
            loginBackView(login);
        }
    }


    public static void loginBackView(UserInformationSaveDTO userInformationDTO) throws ContractException, SQLException, IOException, NoSuchAlgorithmException {
        if(userInformationDTO == null){
            UserLoginInterface.loginReturnInterface();
        }
        UserAfterLoginVO userAfterLoginVO = null;
        if (userInformationDTO != null) {
            userAfterLoginVO = new UserAfterLoginVO(userInformationDTO.getUserName(),userInformationDTO.getBalance(),identity);
        }
        String identityCheck ="consumer";
        if(identity.equals(identityCheck)) {
            UserMainInterface.viewConsumer(userAfterLoginVO);
        }else {
            UserMainInterface.viewSupplier(userAfterLoginVO);
        }
    }
    /**
     * 登录
     +*/
    public void loginOrderByIdentity(int choice1) throws SQLException, IOException, ContractException, NoSuchAlgorithmException {
        if(choice1 == 1){
            new UserLoginInterface().loginBySupplier();
        }else {
            new UserLoginInterface().loginByConsumer();
        }
    }


}
