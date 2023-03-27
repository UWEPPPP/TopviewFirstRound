package com.liujiahui.www.dao.impl;

import com.liujiahui.www.dao.TraceQueryDAO;
import com.liujiahui.www.dao.TraceUserDAO;

/**
 * dao工厂
 *
 * @author 刘家辉
 * @date 2023/03/25
 */
public class TraceFactoryDAO {
    public static TraceUserDAO getTraceFactoryDAO(Boolean choice) {
        if (choice) {
            return TraceSupplierDAOImpl.getInstance();
        } else {
            return TraceConsumerDAOImpl.getInstance();
        }
    }
    public static TraceQueryDAO getTraceQueryDAO() {
        return TraceQueryDAOImpl.getInstance();
    }

    public static TraceRegisterDAOImpl getTraceRegisterDAO() {
            return TraceRegisterDAOImpl.getInstance();
    }

    public static TraceLoginDAOImpl getTraceLoginDAO() {
        return TraceLoginDAOImpl.getInstance();
    }
}