package com.liujiahui.www.service.impl;

import com.liujiahui.www.service.TraceAdminService;
import com.liujiahui.www.service.TraceUserMarketService;
import com.liujiahui.www.service.TraceQueryService;
import com.liujiahui.www.service.TraceRegisterAndLoginService;

/**
 * 跟踪工厂impl
 *
 * @author 刘家辉
 * @date 2023/03/25
 */
public class TraceFactoryService {
    static public TraceQueryService getTraceQueryService() {
        return TraceQueryServiceImpl.getInstance();
    }

    static public TraceRegisterAndLoginService getTraceRegisterAndLoginService() {
        return TraceRegisterOrLoginServiceImpl.getInstance();
    }

    public static TraceAdminService getTraceAdminService() {
        return TraceAdminServiceImpl.getInstance();
    }


    static public TraceUserMarketService getTraceItemPersonalService(Boolean choice) {
        if (choice) {
            return TraceUserMarketBySupplierServiceImpl.getInstance();
        } else {
            return TraceUserMarketByConsumerServiceImpl.getInstance();
        }
    }

    static public TraceContractServiceImpl getTraceContractService() {
        return new TraceContractServiceImpl();
    }
}
