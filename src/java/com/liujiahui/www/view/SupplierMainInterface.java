package com.liujiahui.www.view;

import com.liujiahui.www.entity.vo.UserAfterLoginVO;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;

import java.io.IOException;
import java.sql.SQLException;

/**
 * 供应商主界面
 *
 * @author 刘家辉
 * @date 2023/03/17
 */
public class SupplierMainInterface {
    public static void view(UserAfterLoginVO userAfterLoginVO) throws ContractException, SQLException, IOException {
        if(userAfterLoginVO!=null){
        System.out.println("您好 供应商"+userAfterLoginVO.getName()+"欢迎您来到Topview的产品溯源系统");
        System.out.println("您的余额为"+userAfterLoginVO.getBalance());
    }else {
            System.out.println("用户名或者密码错误");
            System.out.println("即将返回登录界面");
            InitInterface.start();
        }
    }
}
