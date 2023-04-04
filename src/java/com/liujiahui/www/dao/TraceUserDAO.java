package com.liujiahui.www.dao;

import com.liujiahui.www.entity.dto.TraceInformationSaveDTO;
import com.liujiahui.www.entity.po.TraceFeedbackPO;
import com.liujiahui.www.entity.po.TraceItemPO;
import com.liujiahui.www.util.UtilDAO;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;

import java.io.IOException;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 跟踪用户刀
 *
 * @author 刘家辉
 * @date 2023/03/25
 */
public interface TraceUserDAO {




    /**
     * @param accountAddress 账户地址
     * @return {@link List}<{@link TraceFeedbackPO}>
     * @throws SQLException sqlexception异常
     * @throws IOException  ioexception
     */

    /**
     * 显示商品
     *
     * @param accountAddress 账户地址
     * @return {@link Map}<{@link String}, {@link List}<{@link TraceItemPO}>>
     * @throws ContractException 合同例外
     * @throws SQLException      sqlexception异常
     * @throws IOException       ioexception
     */
    Map<String, List<TraceItemPO>> showItem(String accountAddress) throws ContractException, SQLException, IOException;
}
