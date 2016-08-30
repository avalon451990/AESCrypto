package com.born2play.crosspromotion.util;

import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
/**
 * Created on 16/8/29.
 */
public class AESCrypto {
    static final String algorithmStr = "AES/ECB/PKCS5Padding";
    //十六位的秘钥
    public final static String AES_KEY_DEFAULT = "des_default*-$@%";

    private static byte[] encrypt(String content, String password) {
        try {
            byte[] keyStr = password.getBytes();
            SecretKeySpec key = new SecretKeySpec(keyStr, "AES");
            Cipher cipher = Cipher.getInstance(algorithmStr);//algorithmStr
            byte[] byteContent = content.getBytes("utf-8");
            cipher.init(Cipher.ENCRYPT_MODE, key);//   ʼ
            byte[] result = cipher.doFinal(byteContent);
            return result; //
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static byte[] decrypt(byte[] content, String password) {
        try {
            byte[] keyStr = password.getBytes();
            SecretKeySpec key = new SecretKeySpec(keyStr, "AES");
            Cipher cipher = Cipher.getInstance(algorithmStr);//algorithmStr
            cipher.init(Cipher.DECRYPT_MODE, key);//   ʼ
            byte[] result = cipher.doFinal(content);
            return result; //
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 将二进制转换成16进制
     * @param buf
     * @return
     */
    public static String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * 将16进制转换为二进制
     * @param hexStr
     * @return
     */
    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1)
            return null;
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2),
                    16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

    /**
     * 加密
     * @param content
     * @param key 必须是十六位的字符或者null
     * @return
     */
    public static String encode(String content, String key){
        //加密之后的字节数组,转成16进制的字符串形式输出
        String password = key;
        if (password == null || password == "")
            password = AES_KEY_DEFAULT;
        return parseByte2HexStr(encrypt(content, password));
    }
    public static String encode(String content){
        return encode(content, AES_KEY_DEFAULT);
    }

    /**
     * 解密
     * @param content
     * @param key 必须是十六位的字符或者null
     * @return
     */
    public static String decode(String content, String key){
        //解密之前,先将输入的字符串按照16进制转成二进制的字节数组,作为待解密的内容输入
        String password = key;
        if (password == null || password == "")
            password = AES_KEY_DEFAULT;
        byte[] b = decrypt(parseHexStr2Byte(content), password);
        return new String(b);
    }
    public static String decode(String content){
        return decode(content, AES_KEY_DEFAULT);
    }
}
