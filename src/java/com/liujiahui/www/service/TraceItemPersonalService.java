package com.liujiahui.www.service;

import com.liujiahui.www.dao.UserChangePersonalDAO;
import com.liujiahui.www.dao.UserItemDAO;
import com.liujiahui.www.entity.bo.UserChangePersonalBO;
import com.liujiahui.www.entity.po.Item;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface TraceItemPersonalService {
    Map<String, List<Item>> showItem() throws ContractException, SQLException, IOException;
    static List<Item> showAllItem() throws SQLException, IOException {
        return UserItemDAO.showAllItem();
    }

    static void updatePersonalMessage(UserChangePersonalBO userChangeServiceBO) throws SQLException, IOException {
        Integer choice = userChangeServiceBO.getChoice();
        String change = userChangeServiceBO.getChange();
        String identity = userChangeServiceBO.getIdentity();
        String type = null;
        switch (choice) {
            case 1:
                type = "user_name";
                break;
            case 2:
                type = "gender";
                break;
            case 3:
                type = "phone";
                break;
            default:
        }
        UserChangePersonalDAO.change(type, change, identity);
    }
}
