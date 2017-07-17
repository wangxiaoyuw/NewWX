package com.mywx.utils;

import java.util.Arrays;

/**
 * Created by wangzy on 2017/6/2.校验
 */

public class CheckUtil {

    private static final String token = "myweixin";
    public static boolean checkSignature(String signature,String timestamp,String nonce){
        String[] arr = new String[] {token, timestamp, nonce};

        //字典排序
        Arrays.sort(arr);
        //生成字符串
        StringBuilder content = new StringBuilder();
        for (int i = 0;i<arr.length;i++){
            content.append(arr[i]);
        }
        String digest = new SHA1().getDigestOfString(content.toString().getBytes());
        return digest.equalsIgnoreCase(signature);
    }

}
