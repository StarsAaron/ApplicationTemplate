package com.aaron.utils.encrypt.rsa;

import com.aaron.utils.Base64Utils;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * RSA签名验签类
 */
public class RSASignature {

    /**
     * 签名算法
     */
    public static final String SIGN_ALGORITHMS = "SHA1WithRSA";

    /**
     * RSA签名
     *
     * @param content    待签名数据
     * @param privateKey 商户私钥
     * @param encode     字符集编码
     * @return 签名值
     */
    public static String sign(String content, String privateKey, String encode) {
        try {
            //PKCS8EncodedKeySpec类使用PKCS#8标准作为密钥规范管理的编码格式
            PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64Utils.decode(privateKey));

            KeyFactory keyf = KeyFactory.getInstance("RSA");
            PrivateKey priKey = keyf.generatePrivate(priPKCS8);

            java.security.Signature signature = java.security.Signature.getInstance(SIGN_ALGORITHMS);

            signature.initSign(priKey);//使用私钥签名
            if (encode == null) {
                signature.update(content.getBytes());
            } else {
                signature.update(content.getBytes(encode));
            }
            byte[] signed = signature.sign();
            return Base64Utils.encode(signed);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * RSA签名(默认编码)
     *
     * @param content    待签名数据
     * @param privateKey 商户私钥
     * @return
     */
    public static String sign(String content, String privateKey) {
        sign(content, privateKey, null);
        return null;
    }

    /**
     * RSA验签名检查
     *
     * @param content   待签名数据
     * @param sign      签名值
     * @param publicKey 分配给开发商公钥
     * @param encode    字符集编码
     * @return 布尔值
     */
    public static boolean doCheck(String content, String sign, String publicKey, String encode) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            byte[] encodedKey = Base64Utils.decode(publicKey);
            PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));

            java.security.Signature signature = java.security.Signature.getInstance(SIGN_ALGORITHMS);

            signature.initVerify(pubKey);
            if (encode == null) {
                signature.update(content.getBytes());
            } else {
                signature.update(content.getBytes(encode));
            }
            boolean bverify = signature.verify(Base64Utils.decode(sign));
            return bverify;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * RSA验签名检查(默认编码)
     *
     * @param content   待签名数据
     * @param sign      签名值
     * @param publicKey 分配给开发商公钥
     * @return 布尔值
     */
    public static boolean doCheck(String content, String sign, String publicKey) {
        return doCheck(content, sign, publicKey, null);
    }

}
