package com.liujiahui.www.controller;

import com.liujiahui.www.entity.bo.UserAddItemBO;
import com.liujiahui.www.entity.bo.UserChangePersonalBO;
import com.liujiahui.www.entity.bo.UserItemUpdateBO;
import com.liujiahui.www.entity.dto.UserInformationSaveDTO;
import com.liujiahui.www.entity.po.Item;
import com.liujiahui.www.entity.vo.UserDetailedVO;
import com.liujiahui.www.service.UserItemService;
import com.liujiahui.www.service.UserChangePersonalService;
import com.liujiahui.www.view.UserPersonalInterface;
import com.liujiahui.www.view.UserItemInterface;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;

import java.io.IOException;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;

/**
 * 用户输入由消费者
 *
 * @author 刘家辉
 * @date 2023/03/18
 */
public class UserEntryController {
    public static void entry(int choice) throws SQLException, IOException, ContractException, NoSuchAlgorithmException {
        String identity="consumer";
        if(identity.equals(UserInformationSaveDTO.getInstance().getIdentity())){
            consumerEntry(choice);
     }else {
            supplierEntry(choice);
        }
    }
    public static void consumerEntry(int choice) throws SQLException, IOException, ContractException, NoSuchAlgorithmException {
        switch (choice){
            case 1:
                showItemList();
                break;
            case 2:
                showUser();
                break;
            case 3:
                showUserItem();
                break;
            case 4:

                break;
            case 5:

                break;
            default:
        }
    }

    private static void showUserItem() throws SQLException, IOException, ContractException {
        List<Item> items = UserItemService.showMyItem();
        UserItemInterface.showMyItem(items);
    }

    private static void showItemList() throws SQLException, IOException, ContractException {
        List<Item> items = UserItemService.showAllItem();
        UserItemInterface.showAndBuyItem(items);
    }

    public static void supplierEntry(int choice) throws SQLException, IOException, ContractException {
        switch (choice){
            case 1:
                showItemList();
                break;
            case 2:
                showUser();
                break;
            case 3:
                UserItemInterface.registerItem();
                break;
            case 4:
                showSupplierItem();
                break;
            default:
        }
    }


    public static void updateItem(int index, List<Item> items, String name, String description, String price) throws SQLException, IOException {
        String oldName = null;
        for ( Item item:items) {
            if(item.getIndex().intValue()==index){
                oldName=item.getName();
            }
        }
        UserItemUpdateBO updateBO = new UserItemUpdateBO();
        updateBO.setIndex(index);
        updateBO.setOldName(oldName);
        updateBO.setName(name);
        updateBO.setDescription(description);
        updateBO.setPrice(price);
        UserItemService.updateItem(updateBO);
    }

    private static void showSupplierItem() throws ContractException, SQLException, IOException {
        UserItemInterface.showSupplierItem(UserItemService.showRealItem(),UserItemService.showOutsideItem());
    }

    public static void showUser() throws SQLException, IOException {
        UserInformationSaveDTO information = UserInformationSaveDTO.getInstance();
        UserDetailedVO vo = new UserDetailedVO();
        vo.setUserName(information.getUserName());
        vo.setGender(information.getGender());
        vo.setPhone(information.getPhone());
        vo.setBalance(information.getBalance());
        vo.setIdentify(information.getIdentity());
        UserPersonalInterface.showPersonalInterface(vo);
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

    public static void registerItem(String name, BigInteger price, String description, String realName, String realDescription) throws SQLException, IOException {
        UserAddItemBO userAddItemBO = new UserAddItemBO();
        userAddItemBO.setName(name);
        userAddItemBO.setPrice(price);
        userAddItemBO.setDescription(description);
        userAddItemBO.setRealName(realName);
        userAddItemBO.setRealDescription(realDescription);
        UserItemService.addItem(userAddItemBO);
    }
}
