package com.aaron.utils.rsa;


import com.aaron.utils.Base64Utils;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

/**
 * RAS 算法演示
 *
 * @author Aaron
 */
public class MainTest {

    public static void main(String[] args) throws Exception {
        String filepath = "D:/tmp/";
        RSAPublicKey publicKey = RSAEncrypt.loadPublicKeyByStr(RSAEncrypt.loadPublicKeyByFile(filepath));
        RSAPrivateKey privateKey = RSAEncrypt.loadPrivateKeyByStr(RSAEncrypt.loadPrivateKeyByFile(filepath));

        //生成公钥和私钥对
        RSAEncrypt.genKeyPair(filepath);

        System.out.println("--------------公钥加密私钥解密过程-------------------");
        String plainText = "ihep_公钥加密私钥解密";
        // 公钥加密过程
        byte[] cipherData = RSAEncrypt.encrypt(publicKey, plainText.getBytes());
        String cipher = Base64Utils.encode(cipherData);
        // 私钥解密过程
        byte[] res = RSAEncrypt.decrypt(privateKey, Base64Utils.decode(cipher));
        String restr = new String(res);
        System.out.println("原文：" + plainText);
        System.out.println("加密：" + cipher);
        System.out.println("解密：" + restr);
        System.out.println();

        System.out.println("--------------私钥加密公钥解密过程-------------------");
        plainText = "ihep_私钥加密公钥解密";
        // 私钥加密过程
        cipherData = RSAEncrypt.encrypt(privateKey, plainText.getBytes());
        cipher = Base64Utils.encode(cipherData);
        // 公钥解密过程
        res = RSAEncrypt.decrypt(publicKey, Base64Utils.decode(cipher));
        restr = new String(res);
        System.out.println("原文：" + plainText);
        System.out.println("加密：" + cipher);
        System.out.println("解密：" + restr);
        System.out.println();

        System.out.println("---------------私钥签名过程------------------");
        String content = "ihep_这是用于签名的原始数据";
        String signstr = RSASignature.sign(content, RSAEncrypt.loadPrivateKeyByFile(filepath));
        System.out.println("签名原串：" + content);
        System.out.println("签名串：" + signstr);
        System.out.println();

        System.out.println("---------------公钥校验签名------------------");
        System.out.println("签名原串：" + content);
        System.out.println("签名串：" + signstr);

        System.out.println("验签结果：" + RSASignature.doCheck(content, signstr, RSAEncrypt.loadPublicKeyByFile(filepath)));
        System.out.println();
    }

}
