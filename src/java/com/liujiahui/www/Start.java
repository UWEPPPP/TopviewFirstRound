package com.liujiahui.www;

import com.liujiahui.www.view.InitInterface;

import java.io.IOException;
import java.sql.SQLException;

/**
 * 启动器
 *
 * @author 刘家辉
 * @date 2023/03/17
 */
public class Start {
    public static void main(String[] args) throws SQLException, IOException {
        InitInterface.start();
    }
}
