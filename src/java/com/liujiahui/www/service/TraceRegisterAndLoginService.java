package com.liujiahui.www.service;

import com.liujiahui.www.entity.bo.TraceLoginBO;
import com.liujiahui.www.entity.bo.TraceRegisterBO;
import com.liujiahui.www.entity.dto.TraceInformationSaveDTO;

/**
 * 用户注册和登录服务
 *
 * @author 刘家辉
 * @date 2023/03/24
 */
public interface TraceRegisterAndLoginService {
    /**
     * @param traceLoginBO 用户登录博
     * @return {@link TraceInformationSaveDTO}
     * 用于用户登录
     */
    TraceInformationSaveDTO login(TraceLoginBO traceLoginBO);

    /**
     * @param traceRegisterBO 用户登记薄
     * @return {@link Boolean}
     * 用于用户注册
     */
    Boolean register(TraceRegisterBO traceRegisterBO);
}
