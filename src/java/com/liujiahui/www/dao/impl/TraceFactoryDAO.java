package com.liujiahui.www.dao.impl;

import com.liujiahui.www.dao.ConsumerAccountDAO;
import com.liujiahui.www.dao.ConsumerFeedbackDAO;

/**
 * dao工厂
 *
 * @author 刘家辉
 * @date 2023/03/25
 */
public class TraceFactoryDAO {

    private static final ConsumerAccountDAOImpl CONSUMER_DAO = new ConsumerAccountDAOImpl();
    private static final ConsumerFeedbackDAOImpl CONSUMER_FEEDBACK_DAO = new ConsumerFeedbackDAOImpl();
    private static final ItemShowDAOImpl ITEM_SHOW_DAO = new ItemShowDAOImpl();
    private static final ItemBehindDAOImpl ITEM_BEHIND_DAO = new ItemBehindDAOImpl();
    private static final SupplierAccountDAOImpl SUPPLIER_DAO = new SupplierAccountDAOImpl();
    private static final SupplierAppealDAOImpl SUPPLIER_APPEAL_DAO = new SupplierAppealDAOImpl();
    public static ConsumerAccountDAO getConsumerDAO() {
        return CONSUMER_DAO;
    }
    public static ConsumerFeedbackDAO getConsumerFeedbackDAO() {
        return CONSUMER_FEEDBACK_DAO;
    }
    public static ItemShowDAOImpl getItemShowDAO() {
        return ITEM_SHOW_DAO;
    }
    public static ItemBehindDAOImpl getItemBehindDAO() {
        return ITEM_BEHIND_DAO;
    }
    public static SupplierAccountDAOImpl getSupplierDAO() {
        return SUPPLIER_DAO;
    }
    public static SupplierAppealDAOImpl getSupplierAppealDAO() {
        return SUPPLIER_APPEAL_DAO;
    }

}