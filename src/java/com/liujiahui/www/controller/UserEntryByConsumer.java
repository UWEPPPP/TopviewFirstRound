package com.liujiahui.www.controller;

import com.liujiahui.www.entity.bo.UserChangePersonalBO;
import com.liujiahui.www.entity.dto.UserInformationSaveDTO;
import com.liujiahui.www.entity.vo.UserDetailedVO;
import com.liujiahui.www.service.UserChangePersonalService;
import com.liujiahui.www.view.PersonalInterface;

import java.io.IOException;
import java.sql.SQLException;

/**
 * 用户输入由消费者
 *
 * @author 刘家辉
 * @date 2023/03/18
 */
public class UserEntryByConsumer {
    public static void entry(int choice) throws SQLException, IOException {
        switch (choice){
            case 1:
            case 2:
                showUser();
            default:
        }
    }
    public static void showUser() throws SQLException, IOException {
        UserInformationSaveDTO information = UserInformationSaveDTO.getInstance();
        UserDetailedVO vo = new UserDetailedVO();
        vo.setUserName(information.getUserName());
        vo.setGender(information.getGender());
        vo.setPhone(information.getPhone());
        vo.setBalance(information.getBalance());
        vo.setIdentify(information.getIdentity());
        PersonalInterface.showPersonalInterface(vo);
    }
    public static void changeUser(int choice,String change) throws SQLException, IOException {
        UserChangePersonalBO userChangeServiceBO = new UserChangePersonalBO();
        userChangeServiceBO.setChange(change);
        userChangeServiceBO.setChoice(choice);
        userChangeServiceBO.setIdentity(UserInformationSaveDTO.getInstance().getIdentity());
        UserChangePersonalService.change(userChangeServiceBO);
        switch (choice){
            case 1:
                UserInformationSaveDTO.getInstance().setUserName(change);
                break;
            case 2:
                UserInformationSaveDTO.getInstance().setGender(change);
                break;
            case 3:
                UserInformationSaveDTO.getInstance().setPhone(change);
                break;
            default:
        }
    }
}
