package com.liujiahui.www.controller;

import com.liujiahui.www.entity.bo.AddItemBO;
import com.liujiahui.www.entity.bo.UserChangePersonalBO;
import com.liujiahui.www.entity.dto.UserInformationSaveDTO;
import com.liujiahui.www.entity.vo.UserDetailedVO;
import com.liujiahui.www.service.AddItemService;
import com.liujiahui.www.service.UserChangePersonalService;
import com.liujiahui.www.view.PersonalInterface;
import com.liujiahui.www.view.RegisterItemInterface;

import java.io.IOException;
import java.sql.SQLException;

/**
 * 用户输入由消费者
 *
 * @author 刘家辉
 * @date 2023/03/18
 */
public class UserEntryController {
    public static void entry(int choice) throws SQLException, IOException {
        if(UserInformationSaveDTO.getInstance().getIdentity().equals("consumer")){
            consumerEntry(choice);
     }else {
            supplierEntry(choice);
        }
    }
    public static void consumerEntry(int choice) throws SQLException, IOException {
        switch (choice){
            case 1:
                break;
            case 2:
                showUser();
                break;
            case 3:
                RegisterItemInterface.registerItem();
                break;
            case 4:

                break;
            case 5:

                break;
            default:
        }
    }
    public static void supplierEntry(int choice) throws SQLException, IOException {
        switch (choice){
            case 1:
                break;
            case 2:
                showUser();
                break;
            case 3:
                break;
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

    public static void registerItem(String name, String price, String description) {
        AddItemBO addItemBO = new AddItemBO();
        addItemBO.setName(name);
        addItemBO.setPrice(price);
        addItemBO.setDescription(description);
        AddItemService.addItem(addItemBO);
    }
}
