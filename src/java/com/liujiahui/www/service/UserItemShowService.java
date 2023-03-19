package com.liujiahui.www.service;

import com.liujiahui.www.dao.UserItemAddAndShowDAO;
import com.liujiahui.www.entity.po.Item;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * 用户条目显示服务
 *
 * @author 刘家辉
 * @date 2023/03/19
 */
public class UserItemShowService {
    public static List<Item> showItem() throws SQLException, IOException {
           return UserItemAddAndShowDAO.showItem();
    }
}
