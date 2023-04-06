package com.liujiahui.www.controller;

import com.liujiahui.www.entity.bo.TraceRegisterBO;
import com.liujiahui.www.entity.dto.TraceRegisterDTO;
import com.liujiahui.www.service.TraceRegisterAndLoginService;
import com.liujiahui.www.service.impl.TraceFactoryService;
import com.liujiahui.www.view.TraceRegisterView;

/**
 * 用户注册控制器
 *
 * @author 刘家辉
 * @date 2023/03/16
 */
public class TraceRegisterController {
    public void register(TraceRegisterDTO traceRegisterDTO) {
        TraceRegisterBO traceRegisterBO = new TraceRegisterBO();
        traceRegisterBO.setName(traceRegisterDTO.getName());
        traceRegisterBO.setGender(traceRegisterDTO.getGender());
        traceRegisterBO.setPhone(traceRegisterDTO.getPhone());
        traceRegisterBO.setPassword(traceRegisterDTO.getPassword());
        traceRegisterBO.setAddress(traceRegisterDTO.getAddress());
        TraceRegisterAndLoginService traceRegisterAndLoginService = TraceFactoryService.getTraceRegisterAndLoginService();
        TraceRegisterView.returnInterface(traceRegisterAndLoginService.register(traceRegisterBO));
    }

    public void registerOrderByIdentity(int choice2) {
        if (choice2 == 1) {
            new TraceRegisterView().registerBySupplier();
        } else {
            new TraceRegisterView().registerByConsumer();
        }
    }
}
