/* 
 * RSAHelperTest.java  
 * 
 * version TODO
 *
 * 2015年11月12日 
 * 
 * Copyright (c) 2015,zlebank.All rights reserved.
 * 
 */
package org.zplatform.cmbc;

import com.zlebank.zplatform.trade.cmbc.security.RSAHelper;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2015年11月12日 上午9:13:15
 * @since 
 */
public class RSAHelperTest {
    public static final String privKey = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCpV0wBTtwnDYWF"+
            "+3NSYHvFkCKCJDJF25yTccQL/vUbyhutEqeX7HAylXnrH4TrXaPa75DioPNGlCfj"+
            "HAGcRVjY4zgwIl9RYhyFjZMwiWf3AUvHqfG47mDndXn/aA0kVseB6cfua9E1VtVw"+
            "ldK+KeaQiDm1dxfOZNMbqR9b/86kqW2t3/4MXx3xMU3i//I3GtLizVvtETmty4As"+
            "TEWMnYaCJgpm4fNcO0sWASbWm0tR+oEA5gWuNcP0yHPlnZ9ZGA15M4DhaN0MKJNV"+
            "DZnQNSQONJOnxBAcAc+qYhum/Qa7oDHaSHI5UaCw4qB37V2AaHiBYiE6OqWPNdAq"+
            "gLPHGfK5AgMBAAECggEBAIVMVmPhM6L6HdTtrkV19Xf6kW9oFrquuH3H286lj0Ok"+
            "wExolswcLeVjjGjskrDDMSwx0BbVIyq3jP5VGV9tpxZzJdOd+vIRDOqEGwzl3JSu"+
            "zHwK5l/wJQ+Nhc7qlQ250aoN4HdpH3GhPLo29YEDcp7q3KJfcLni29q2Mh01K/Co"+
            "tgO0QHz4IXqBOug/JE4cIcMPCauqwEX96QQk/PL8dr24adEJLdfpd30sRabvPJyb"+
            "teoGYSHW0ceyi1sN8za3Y4E+y4bHd2XfIBM3jYd9LyiogOjQDdK8lYVb6U/vtwEE"+
            "4S0umh1IbDPbE7Bn9KNevfW0yuOW9UHMASeRtWcY6gECgYEA0dzrnFUXEB5tpq8I"+
            "ZC8CN2lurSSXBYK2xPN0WuFbMOnAPql5BV19eiywDNN7tpQr9f/8/5L20ouy2FHF"+
            "oHtk9phd8cK4VUF15WxpoP3xDrCQ3st3yxeT59ZSepUSSAf5eBkD7wHDBMIXVmJN"+
            "fCc0wqZdIChkdGmmxX4fkiOtvSkCgYEAzpHNRu7MM0lFzMBGBHfEhnAESL4sX7bu"+
            "Jg/nVZlJygq8Q055v3FB0ZfQzL3iNVhaoPfqvC9+IEhe5aXwqEdiui12RvqMacrd"+
            "NRKfzMKSQf+S8hcHLhPC1zRiShcDYi3iszvrVPfTzCBNcgEpUEVvVPewQ2zHPbUv"+
            "HSfKNdJGqxECgYBi73Njz/l/b9jhq8KY+FBjLyEuHwHTZzwivWMtNPB4rroi0GJt"+
            "VRXCcy7L3SeFe3T+FCCBnBcgSxuVP3eboJCIcGxZfSLJjoK2FdX2P8FU24hwgBuJ"+
            "cyQXN8LKZ1ym+JtzMtfX5Uy7AYVfBBjjXthQq31bm68/8k7c7AiWLJvLgQKBgBTh"+
            "tk+iWjAhCxZMpYdUGCDC+BvmZZPzaRWd6/PCQCxVc3yWVUZeC5pFb40S8OgVm2vE"+
            "RA1r+6MFzdoHMVHBjaBmchtwvnr/T+dKj67iSGi1jkQ8wbQNuVeJ6xm0Q9pL6yvV"+
            "Nc/sEZQD+/A3X9bGfh+nvkDCoU2CoFWhLRbNv+kRAoGAN9DuCZZlJq4y/FxeLUq8"+
            "EBn9m97sCWNapqTgyGevSDiIeMHl0HPEZmj4eCC1U1rJ+iDfp6+tdVjutSKZkuyO"+
            "Rsm7Gs+7y9xF9KmnwWIxIbqm9fiODQFpHeVszHz65tsRyY94tKujWUhXbNVQQyz5"+
            "3URaV+YfGG83bud2XE9TMH0=";
;

    public static final String pubKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAqWWk6BGwjlvR4PYXp+YrkXLTXR1jO5qzHIHtkUizRnbstdk+mIq/gHm/hR9yAEfGBukRoabGPDa2pR502+U9+ifnjIKFmxISg/nx8+8ZzYe9WkiPEglEeBly4l057Z71XKZ49wigQ7wNxlTlh3FHH20vmGhbu4UPwIZRlJi9QaYWB8kco9NDRxqNvzHIKkIlko5r+PtyXTr7HjZxPbH34ZMEc5eRUS/BI/cGw8VzdSXB7Zk4Sk5DNtNm+RU5JMnRDCfZrHrsHj8eKzX5dPM0wDqYuaYszNiqJuOIVYn1hSzt3SqDjmy3LF4NE6tv/2rU/xontGOY+o5TYYGZjfzKmwIDAQAB";

    public static final String plaintext = "你好，测试";

    public static void main(String[] args) throws Exception {
System.out.println(privKey);
        System.out.println("=====> init <=====");
        RSAHelper cipher = new RSAHelper();
        cipher.initKey(privKey, pubKey, 2048);

        System.out.println("=====> sign & verify <=====");

        // 签名
       // byte[] signBytes = cipher.signRSA(plaintext.getBytes("UTF-8"), false,
         //       "UTF-8");

        // 验证签名
        //boolean isValid = cipher.verifyRSA(plaintext.getBytes("UTF-8"),
         //       signBytes, false, "UTF-8");
        //System.out.println("isValid: " + isValid);

        // 加密和解密
        System.out.println("=====> encrypt & decrypt <=====");
        // 对明文加密
        byte[] cryptedBytes = cipher.encryptRSA(plaintext.getBytes("UTF-8"),
                false, "UTF-8");

        // 对密文解密
        byte[] decryptedBytes = cipher.decryptRSA(cryptedBytes, false, "UTF-8");
        System.out.println("decrypted: " + new String(decryptedBytes, "UTF-8"));
    }
}
