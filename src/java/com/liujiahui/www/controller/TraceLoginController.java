package com.liujiahui.www.controller;

import com.liujiahui.www.entity.bo.TraceLoginBO;
import com.liujiahui.www.entity.dto.TraceInformationSaveDTO;
import com.liujiahui.www.entity.vo.TraceAfterLoginVO;
import com.liujiahui.www.service.TraceRegisterAndLoginService;
import com.liujiahui.www.view.TraceEntryView;
import com.liujiahui.www.view.TraceLoginView;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

/**
 * 用户登录控制器
 *
 * @author 刘家辉
 * @date 2023/03/16
 */
public class TraceLoginController {
      private static String identity;

    public  void login(String account, String password, Boolean choice) throws SQLException, IOException, ContractException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        if(!choice) {
            TraceLoginBO traceLoginBO = new TraceLoginBO(account,password,"consumer");
            identity = "consumer";
            TraceInformationSaveDTO login = TraceRegisterAndLoginService.login(traceLoginBO);
            loginBackView(login);
         }else {
            TraceLoginBO traceLoginBO = new TraceLoginBO(account,password,"suppliers");
            identity="suppliers";
            TraceInformationSaveDTO login = TraceRegisterAndLoginService.login(traceLoginBO);
            loginBackView(login);
        }
    }


    public static void loginBackView(TraceInformationSaveDTO userInformationDTO) throws ContractException, SQLException, IOException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        if(userInformationDTO == null){
            TraceLoginView.loginReturnInterface();
        }
        TraceAfterLoginVO traceAfterLoginVO = null;
        TraceInformationSaveDTO instance = TraceInformationSaveDTO.getInstance();
        if (userInformationDTO != null) {
            traceAfterLoginVO = new TraceAfterLoginVO(userInformationDTO.getUserName(),userInformationDTO.getBalance(),identity);
            traceAfterLoginVO.setInformationSize(instance.getInformationSize());
        }
        String identityCheck ="consumer";
        if(identity.equals(identityCheck)) {
            TraceEntryView.viewConsumer(traceAfterLoginVO);
        }else {
            TraceEntryView.viewSupplier(traceAfterLoginVO);
        }
    }
    /**
     * 登录
     +*/
    public void loginOrderByIdentity(int choice1) throws SQLException, IOException, ContractException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        if(choice1 == 1){
            new TraceLoginView().loginBySupplier();
        }else {
            new TraceLoginView().loginByConsumer();
        }
    }


}
