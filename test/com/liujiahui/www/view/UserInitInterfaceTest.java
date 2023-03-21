package com.liujiahui.www.view;

import junit.framework.TestCase;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class UserInitInterfaceTest extends TestCase {

    public void testStart() throws ContractException, SQLException, IOException, NoSuchAlgorithmException {
        UserInitInterface.start();
    }
}