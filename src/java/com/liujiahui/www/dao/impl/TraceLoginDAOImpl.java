package com.liujiahui.www.dao.impl;

import com.liujiahui.www.dao.TraceLoginDAO;
import com.liujiahui.www.dao.util.UtilDAO;
import com.liujiahui.www.entity.dto.TraceAccountOnJavaDTO;
import com.liujiahui.www.entity.dto.TraceInformationSaveDTO;
import com.liujiahui.www.service.impl.TraceContractServiceImpl;
import com.liujiahui.www.service.impl.TraceFactoryImplService;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 用户登录持久层
 *
 * @author 刘家辉
 * @date 2023/03/17
 */
public class TraceLoginDAOImpl implements TraceLoginDAO {
    private static final TraceLoginDAOImpl INSTANCE = new TraceLoginDAOImpl();

    private TraceLoginDAOImpl() {
    }

    public static TraceLoginDAOImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public TraceInformationSaveDTO login(TraceAccountOnJavaDTO account) throws SQLException, IOException, ContractException {
        String userAccount = account.getAccount();
        String userPassword = account.getPassword();
        String identity = account.getIdentity();
        try (Connection connection = UtilDAO.getConnection()) {
            PreparedStatement preparedStatement;
            preparedStatement = connection.prepareStatement("select * from user." + identity + "  where user_name=? and password=?");
            preparedStatement.setString(1, userAccount);
            preparedStatement.setString(2, userPassword);
            ResultSet set = preparedStatement.executeQuery();
            if (set.next()) {
                TraceInformationSaveDTO user = TraceInformationSaveDTO.getInstance();
                TraceContractServiceImpl traceContractService = TraceFactoryImplService.getTraceContractService();
                String balance = String.valueOf(traceContractService.getBalance(set.getString("private_key")));
                user.setUserName(set.getString("user_name"));
                user.setBalance(balance);
                user.setGender(set.getString("gender"));
                user.setPhone(set.getString("phone_number"));
                user.setIdentity(identity);
                user.setContractAccount(set.getString("account_address"));
                String inform = "suppliers";
                if (identity.equals(inform)) {
                    String sql1 = "select count(*) from user.consumer_feedback where seller_account=? and is_read=false";
                    PreparedStatement preparedStatement1 = connection.prepareStatement(sql1);
                    preparedStatement1.setString(1, user.getContractAccount());
                    ResultSet set1 = preparedStatement1.executeQuery();
                    if (set1.next()) {
                        user.setInformationSize(set1.getInt(1));
                    }
                }
                return user;
            } else {
                return null;
            }
        } catch (NoSuchPaddingException | IllegalBlockSizeException | NoSuchAlgorithmException | BadPaddingException |
                 InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }
}
