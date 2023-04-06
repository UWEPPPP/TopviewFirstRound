package com.liujiahui.www.util;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Logger;

/**
 * 加密工具 用于加密私钥
 *
 * @author 刘家辉
 * @date 2023/03/25
 */
public class CryptoUtil {
    private static final Logger logger = Logger.getLogger(CryptoUtil.class.getName());
    private static SecretKey key;

    public static SecretKey generateSecretKey(String password) {
        if (key != null) {
            return key;
        }
        byte[] passwordBytes = password.getBytes(StandardCharsets.UTF_8);
        MessageDigest instance;
        try {
            instance = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        byte[] digest = instance.digest(passwordBytes);
        key = new SecretKeySpec(digest, "AES");
        return key;
    }

    /**
     * 十六进制私钥加密
     * 使用了AES加密算法
     *
     * @param privateKey 私钥
     * @return {@link String}
     * @throws NoSuchAlgorithmException  没有这样算法异常
     * @throws NoSuchPaddingException    没有这样填充例外
     * @throws InvalidKeyException       无效关键例外
     * @throws IllegalBlockSizeException 非法块大小异常
     * @throws BadPaddingException       坏填充例外
     */
    public static String encryptHexPrivateKey(String privateKey, String path) {
        SecretKey aes = generateSecretKey(readPassword(path));
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, aes);
            byte[] encryptedBytes = cipher.doFinal(privateKey.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(encryptedBytes);
        } catch (NoSuchPaddingException | IllegalBlockSizeException | NoSuchAlgorithmException | BadPaddingException |
                 InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }

    public static String decryptHexPrivateKey(String privateKey, String path) {
        SecretKey aes = generateSecretKey(readPassword(path));
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, aes);
            byte[] encryptedBytes = hexToBytes(privateKey);
            byte[] decrypt = cipher.doFinal(encryptedBytes);
            return new String(decrypt, StandardCharsets.UTF_8);
        } catch (NoSuchPaddingException | IllegalBlockSizeException | NoSuchAlgorithmException | BadPaddingException |
                 InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 将字节数组转换为十六进制字符串（hex）
     *
     * @param bytes
     * @return {@link String}
     */
    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(b & 0xff);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    /**
     * 十六进制转换为字节数组
     *
     * @param hex
     * @return {@link byte[]}
     */
    private static byte[] hexToBytes(String hex) {
        byte[] bytes = new byte[hex.length() / 2];
        for (int i = 0; i < hex.length(); i += 2) {
            String sub = hex.substring(i, i + 2);
            bytes[i / 2] = (byte) Integer.parseInt(sub, 16);
        }
        return bytes;
    }

    public static String readPassword(String filePath) {
        File file = new File(filePath);
        try (FileInputStream fis = new FileInputStream(file)) {
            byte[] buffer = new byte[(int) file.length()];
            if (fis.read(buffer) > 0) {
                return new String(buffer);
            } else {
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}

