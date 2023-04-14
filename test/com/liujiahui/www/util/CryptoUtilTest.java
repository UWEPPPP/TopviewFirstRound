package com.liujiahui.www.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CryptoUtilTest {

    @Test
    void encryptHexPrivateKey() {
        String s = CryptoUtil.encryptHexPrivateKey("eca8130fab663bf3260debf7a4d19be1ba79a3c1d4417e718d7316b60c905d3f", "src/resource/password.txt");
        System.out.println(s);
        String s1 = CryptoUtil.decryptHexPrivateKey(s, "src/resource/password.txt");
        assertEquals("eca8130fab663bf3260debf7a4d19be1ba79a3c1d4417e718d7316b60c905d3f", s1);

    }

    @Test
    void decryptHexPrivateKey() {
    }
}