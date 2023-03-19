package com.liujiahui.www.service;

import com.liujiahui.www.dao.UserItemAddAndShowDAO;
import com.liujiahui.www.entity.bo.AddItemBO;
import com.liujiahui.www.entity.dto.UserInformationSaveDTO;
import com.liujiahui.www.solidity.ItemTradeSolidity;

import java.io.IOException;
import java.sql.SQLException;

/**
 * 添加物品服务
 *
 * @author 刘家辉
 * @date 2023/03/18
 */
public class UserItemAddService {
    public static void addItem(AddItemBO addItemBO) throws SQLException, IOException {
        ItemTradeSolidity itemTradeSolidity = UserInformationSaveDTO.getInstance().getItemTradeSolidity();
        itemTradeSolidity.addItem(addItemBO.getRealName(),addItemBO.getPrice(),addItemBO.getRealDescription());
        UserItemAddAndShowDAO.addItem(addItemBO.getName(),addItemBO.getPrice(),addItemBO.getDescription());
    }
}
