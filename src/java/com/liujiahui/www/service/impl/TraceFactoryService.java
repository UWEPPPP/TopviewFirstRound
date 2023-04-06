package com.liujiahui.www.service.impl;

import com.liujiahui.www.service.*;

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

    public static AdminService getTraceAdminService() {
        return AdminServiceImpl.getInstance();
    }

    public static ConsumerService getConsumeUsedService() {
        return ConsumerServiceImpl.getInstance();
    }

    public static SupplierService getSupplierUsedService() {
        return SupplierServiceImpl.getInstance();
    }

    static public CommonUsedMarketService getCommonUsedService() {
        return CommonUsedMarketServiceImpl.getInstance();
    }

    static public TraceContractServiceImpl getTraceContractService() {
        return new TraceContractServiceImpl();
    }
}
