package com.liujiahui.www.service.impl;

import com.liujiahui.www.service.TraceAdminService;
import com.liujiahui.www.service.TraceItemPersonalService;
import com.liujiahui.www.service.TraceQueryService;
import com.liujiahui.www.service.TraceRegisterAndLoginService;

/**
 * 跟踪工厂impl
 *
 * @author 刘家辉
 * @date 2023/03/25
 */
public class TraceFactoryImplService {
    static public TraceQueryService getTraceQueryService() {
        return TraceQueryServiceImpl.getInstance();
    }

    static public TraceRegisterAndLoginService getTraceRegisterAndLoginService(Boolean choice) {
        if (choice) {
            return TraceRegisterAndLoginBySupplierServiceImpl.getInstance();
        } else {
            return TraceRegisterAndLoginByConsumerServiceImpl.getInstance();
        }
    }

    public static TraceAdminService getTraceAdminService() {
        return TraceAdminServiceImpl.getInstance();
    }


    static public TraceItemPersonalService getTraceItemPersonalService(Boolean choice) {
        if (choice) {
            return TraceItemPersonalBySupplierServiceImpl.getInstance();
        } else {
            return TraceItemPersonalByConsumerServiceImpl.getInstance();
        }
    }

    static public TraceContractServiceImpl getTraceContractService() {
        return new TraceContractServiceImpl();
    }
}
