package com.liujiahui.www;

import com.liujiahui.www.view.TraceInitView;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

/**
 * 启动器
 *
 * @author 刘家辉
 * @date 2023/03/17
 */
public class Start {
    public static void main(String[] args) throws Exception {
        TraceInitView.start();
    }
}
