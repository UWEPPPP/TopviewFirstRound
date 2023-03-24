package com.liujiahui.www.controller;

import com.liujiahui.www.entity.bo.UserAddItemBO;
import com.liujiahui.www.entity.bo.UserChangePersonalBO;
import com.liujiahui.www.entity.bo.UserItemUpdateBO;
import com.liujiahui.www.entity.dto.UserInformationSaveDTO;
import com.liujiahui.www.entity.dto.UserItemStatusDTO;
import com.liujiahui.www.entity.dto.UserRealItemDTO;
import com.liujiahui.www.entity.po.Item;
import com.liujiahui.www.entity.vo.UserDetailedVO;
import com.liujiahui.www.entity.vo.UserItemStatusVO;
import com.liujiahui.www.entity.vo.UserTransactionVO;
import com.liujiahui.www.service.TraceItemPersonalService;
import com.liujiahui.www.service.impl.TraceItemPersonalByConsumerServiceImpl;
import com.liujiahui.www.service.impl.TraceItemPersonalBySupplierServiceImpl;
import com.liujiahui.www.view.UserItemInterface;
import com.liujiahui.www.view.UserPersonalInterface;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;

import java.io.IOException;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.List;

/**
 * 用户输入由消费者
 *
 * @author 刘家辉
 * @date 2023/03/18
 */
public class UserEntryController {
     private TraceItemPersonalService traceItemPersonalService;

    public UserEntryController(Boolean choice) {
        if(choice){
         this.traceItemPersonalService=TraceItemPersonalBySupplierServiceImpl.getInstance();
        }else {
            this.traceItemPersonalService=TraceItemPersonalByConsumerServiceImpl.getInstance();
        }
    }


    public  void entry(int choice) throws SQLException, IOException, ContractException {
        String identity="consumer";
        if(identity.equals(UserInformationSaveDTO.getInstance().getIdentity())){
            consumerEntry(choice);
     }else {
            supplierEntry(choice);
        }
    }
    public  void consumerEntry(int choice) throws SQLException, IOException, ContractException {
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

    private  void showUserItem() throws SQLException, IOException, ContractException {
        List<Item> items = traceItemPersonalService.showItem().get("item");
        UserItemInterface.showMyItem(items);
    }

    private  void showItemList() throws SQLException, IOException, ContractException {
        List<Item> items = TraceItemPersonalService.showAllItem();
        String check="consumer";
        if(check.equals(UserInformationSaveDTO.getInstance().getIdentity())){
         UserItemInterface.showAndBuyItemByConsumer(items);
        }else {
            UserItemInterface.showAndBuyItemBySupplier(items);
        }
    }

    public  void supplierEntry(int choice) throws SQLException, IOException, ContractException {
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


    public  void updateItem(int index, List<Item> items, String name, String description, String price) throws SQLException, IOException {
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
        ((TraceItemPersonalBySupplierServiceImpl)traceItemPersonalService).updateItem(updateBO);
    }

    private  void showSupplierItem() throws ContractException, SQLException, IOException {
        UserItemInterface.showSupplierItem(traceItemPersonalService.showItem());
    }

    public  void showUser() throws SQLException, IOException {
        UserInformationSaveDTO information = UserInformationSaveDTO.getInstance();
        UserDetailedVO vo = new UserDetailedVO();
        vo.setUserName(information.getUserName());
        vo.setGender(information.getGender());
        vo.setPhone(information.getPhone());
        vo.setBalance(information.getBalance());
        vo.setIdentify(information.getIdentity());
        UserPersonalInterface.showPersonalInterface(vo);
    }
    public  void changeUser(int choice,String change) throws SQLException, IOException {
        UserChangePersonalBO userChangeServiceBO = new UserChangePersonalBO();
        userChangeServiceBO.setChange(change);
        userChangeServiceBO.setChoice(choice);
        userChangeServiceBO.setIdentity(UserInformationSaveDTO.getInstance().getIdentity());
        TraceItemPersonalService.updatePersonalMessage(userChangeServiceBO);
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

    public  void registerItem(String name, BigInteger price, String description, String realName, String realDescription) throws SQLException, IOException {
        UserAddItemBO userAddItemBO = new UserAddItemBO();
        userAddItemBO.setName(name);
        userAddItemBO.setPrice(price);
        userAddItemBO.setDescription(description);
        userAddItemBO.setRealName(realName);
        userAddItemBO.setRealDescription(realDescription);
        ((TraceItemPersonalBySupplierServiceImpl)traceItemPersonalService).addItem(userAddItemBO);
    }


    public  void updateLogistics(int id, String logistics, int status) {
        ((TraceItemPersonalBySupplierServiceImpl)traceItemPersonalService).updateLogistics(id, logistics, status);
    }

    public UserTransactionVO check(String hash) throws ContractException {
        UserRealItemDTO userRealItemDTO = ((TraceItemPersonalByConsumerServiceImpl)traceItemPersonalService).checkByHash(hash);
        UserTransactionVO userTransactionVO = new UserTransactionVO();
        userTransactionVO.setName(userRealItemDTO.getName());
        userTransactionVO.setHash(hash);
        userTransactionVO.setDescription(userRealItemDTO.getDescription());
        userTransactionVO.setSeller(userRealItemDTO.getSeller());
        return userTransactionVO;
    }

    public UserItemStatusVO checkStatus(String hash1) throws ContractException {
        UserItemStatusDTO userItemStatusDTO = ((TraceItemPersonalByConsumerServiceImpl)traceItemPersonalService).checkStatus(hash1);
        UserItemStatusVO userItemStatusVO = new UserItemStatusVO();
        userItemStatusVO.setDate(userItemStatusDTO.getDate());
        userItemStatusVO.setPlace(userItemStatusDTO.getPlace());
        userItemStatusVO.setStatus(userItemStatusDTO.getStatus());
        return userItemStatusVO;
    }
}
