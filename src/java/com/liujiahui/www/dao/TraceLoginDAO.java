package com.liujiahui.www.dao;

import com.liujiahui.www.entity.dto.TraceAccountOnJavaDTO;
import com.liujiahui.www.entity.dto.TraceInformationSaveDTO;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;

import java.io.IOException;
import java.sql.SQLException;

/**
 * 跟踪登录DAO层
 *
 * @author 刘家辉
 * @date 2023/03/25
 */
public interface TraceLoginDAO {
    /**
     * 登录功能
     * * @param account 账户
     * @return {@link TraceInformationSaveDTO}
     * @throws SQLException      sqlexception异常
     * @throws IOException       ioexception
     * @throws ContractException 合同例外
     */
    TraceInformationSaveDTO login(TraceAccountOnJavaDTO account) throws SQLException, IOException, ContractException;
}
