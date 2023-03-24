package com.liujiahui.www.service;

import com.liujiahui.www.entity.bo.UserLoginBO;
import com.liujiahui.www.entity.bo.UserRegisterBO;
import com.liujiahui.www.entity.dto.UserInformationSaveDTO;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;

import java.io.IOException;
import java.sql.SQLException;

/**
 * 用户注册和登录服务
 *
 * @author 刘家辉
 * @date 2023/03/24
 */
public interface TraceRegisterAndLoginService {
    /**
     * @param userRegisterBO 用户登记薄
     * @return {@link Boolean}
     */
    Boolean register(UserRegisterBO userRegisterBO) throws SQLException, IOException;

    /**
     * @param userLoginBO 用户登录博
     * @return {@link UserInformationSaveDTO}
     */
    UserInformationSaveDTO login(UserLoginBO userLoginBO) throws ContractException, SQLException, IOException;
}
