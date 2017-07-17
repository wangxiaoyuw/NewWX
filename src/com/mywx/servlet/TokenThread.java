package com.mywx.servlet;

import com.mywx.pojo.Token;
import com.mywx.utils.CommonUtil;
import com.mywx.utils.TokenUtil;

/**
 * Created by wangzy on 2017/6/5.
 */
public class TokenThread implements Runnable {

    public static String appid = "";
    // 第三方用户唯一凭证密钥
    public static String appsecret = "";
    public static Token accessToken = null;
    @Override
    public void run() {
        while (true){
            try {
                accessToken = CommonUtil.getToken(appid,appsecret);
                if (null!=accessToken){
                    TokenUtil.saveToken(accessToken);
                    Thread.sleep((accessToken.getExpiresIn()-200)*1000);
                }else {
                    Thread.sleep(60*1000);
                }
            } catch (InterruptedException e) {
                try {
                    Thread.sleep(60*1000);
                }catch (InterruptedException e1){
                    e1.printStackTrace();
                }
            }
        }
    }
}
