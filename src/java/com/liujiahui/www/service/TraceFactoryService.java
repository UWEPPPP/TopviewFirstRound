package com.liujiahui.www.service;

import com.liujiahui.www.service.impl.TraceItemPersonalByConsumerServiceImpl;
import com.liujiahui.www.service.impl.TraceItemPersonalBySupplierServiceImpl;
import com.liujiahui.www.service.impl.TraceRegisterAndLoginByConsumerServiceImpl;
import com.liujiahui.www.service.impl.TraceRegisterAndLoginBySupplierServiceImpl;

/**
 * 跟踪工厂impl
 *
 * @author 刘家辉
 * @date 2023/03/25
 */
public class TraceFactoryService {
    public static TraceRegisterAndLoginService getTraceRegisterAndLoginService(Boolean choice) {
        if (choice) {
            return TraceRegisterAndLoginBySupplierServiceImpl.getInstance();
        } else {
            return TraceRegisterAndLoginByConsumerServiceImpl.getInstance();
        }
    }
    public static TraceItemPersonalService getTraceItemPersonalService(Boolean choice) {
        if (choice) {
            return TraceItemPersonalBySupplierServiceImpl.getInstance();
        } else {
            return TraceItemPersonalByConsumerServiceImpl.getInstance();
        }
    }

}
