package com.liujiahui.www.controller;

import com.liujiahui.www.entity.bo.TraceLoginBO;
import com.liujiahui.www.entity.dto.TraceInformationSaveDTO;
import com.liujiahui.www.entity.vo.TraceAfterLoginVO;
import com.liujiahui.www.service.TraceRegisterAndLoginService;
import com.liujiahui.www.service.impl.TraceFactoryService;
import com.liujiahui.www.view.TraceEntryView;
import com.liujiahui.www.view.TraceLoginView;

/**
 * 用户登录控制器
 *
 * @author 刘家辉
 * @date 2023/03/16
 */
public class TraceLoginController {
    private static String identity;
    private final TraceRegisterAndLoginService traceRegisterAndLoginService = TraceFactoryService.getTraceRegisterAndLoginService();

    public static void loginBackView(TraceInformationSaveDTO userInformationDTO) {
        if (userInformationDTO == null) {
            TraceLoginView.loginReturnInterface();
        }
        TraceAfterLoginVO traceAfterLoginVO = null;
        TraceInformationSaveDTO instance = TraceInformationSaveDTO.getInstance();
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
            TraceInformationSaveDTO login = traceRegisterAndLoginService.login(traceLoginBO);
            loginBackView(login);
        } else {
            TraceLoginBO traceLoginBO = new TraceLoginBO(account, password, "suppliers");
            identity = "suppliers";
            TraceInformationSaveDTO login = traceRegisterAndLoginService.login(traceLoginBO);
            loginBackView(login);
        }
    }

    /**
     * 登录
     * +
     */
    public void loginOrderByIdentity(int choice1) {
        if (choice1 == 1) {
            new TraceLoginView().loginBySupplier();
        } else {
            new TraceLoginView().loginByConsumer();
        }
    }


}
