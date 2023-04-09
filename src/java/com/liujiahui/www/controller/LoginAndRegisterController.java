package com.liujiahui.www.controller;

import com.liujiahui.www.entity.bo.TraceLoginBO;
import com.liujiahui.www.entity.bo.TraceRegisterBO;
import com.liujiahui.www.entity.dto.TraceRegisterDTO;
import com.liujiahui.www.entity.dto.UserSaveDTO;
import com.liujiahui.www.entity.vo.TraceAfterLoginVO;
import com.liujiahui.www.service.RegisterOrLoginService;
import com.liujiahui.www.service.factory.TraceFactoryService;
import com.liujiahui.www.view.TraceEntryView;
import com.liujiahui.www.view.TraceLoginView;
import com.liujiahui.www.view.TraceRegisterView;

/**
 * 用户登录控制器
 *
 * @author 刘家辉
 * @date 2023/03/16
 */
public class LoginAndRegisterController {
    private static String identity;
    private final RegisterOrLoginService registerOrLoginService = TraceFactoryService.getTraceRegisterAndLoginService();

    public void register(TraceRegisterDTO traceRegisterDTO) {
        TraceRegisterBO traceRegisterBO = new TraceRegisterBO();
        traceRegisterBO.setName(traceRegisterDTO.getName());
        traceRegisterBO.setGender(traceRegisterDTO.getGender());
        traceRegisterBO.setPhone(traceRegisterDTO.getPhone());
        traceRegisterBO.setPassword(traceRegisterDTO.getPassword());
        traceRegisterBO.setAddress(traceRegisterDTO.getAddress());
        RegisterOrLoginService registerOrLoginService = TraceFactoryService.getTraceRegisterAndLoginService();
        TraceRegisterView.returnInterface(registerOrLoginService.register(traceRegisterBO));
    }

    public void registerOrderByIdentity(int choice2) {
        if (choice2 == 1) {
            new TraceRegisterView().registerBySupplier();
        } else {
            new TraceRegisterView().registerByConsumer();
        }
    }
    public static void loginBackView(UserSaveDTO userInformationDTO) {
        if (userInformationDTO == null) {
            TraceLoginView.loginReturnInterface();
        }
        TraceAfterLoginVO traceAfterLoginVO = null;
        UserSaveDTO instance = UserSaveDTO.getInstance();
        if (userInformationDTO != null) {
            traceAfterLoginVO = new TraceAfterLoginVO(userInformationDTO.getUserName(), userInformationDTO.getBalance(), identity);
            traceAfterLoginVO.setInformationSize(instance.getInformationSize());
            traceAfterLoginVO.setAppealReusltSize(instance.getAppealResultSize());
        }
        String identityCheck = "consumer";
        if (identity.equals(identityCheck)) {
            TraceEntryView.viewConsumer(traceAfterLoginVO);
        } else {
            TraceEntryView.viewSupplier(traceAfterLoginVO);
        }
    }

    public void login(String account, String password, Boolean choice) {
        if (!choice) {
            TraceLoginBO traceLoginBO = new TraceLoginBO(account, password, "consumer");
            identity = "consumer";
            UserSaveDTO login = registerOrLoginService.login(traceLoginBO);
            loginBackView(login);
        } else {
            TraceLoginBO traceLoginBO = new TraceLoginBO(account, password, "suppliers");
            identity = "suppliers";
            UserSaveDTO login = registerOrLoginService.login(traceLoginBO);
            loginBackView(login);
        }
    }

    /**
     * 登录
     * +
     */
    public void loginOrderByIdentity(int choice1) {
        new TraceLoginView().login(choice1 == 1);
    }


}
