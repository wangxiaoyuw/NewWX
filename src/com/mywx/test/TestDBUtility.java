package com.mywx.test;

import com.mywx.pojo.Token;
import com.mywx.pojo.WeixinUserInfo;
import com.mywx.utils.CommonUtil;
import com.mywx.utils.DBUtility;

import com.mywx.utils.TokenUtil;
import org.junit.Test;

import java.sql.SQLException;
import java.util.Map;

import static com.mywx.pojo.WeixinUserInfo.getUserInfo;

/**
 * Created by wangzy on 2017/6/5.
 */
public class TestDBUtility {


    public void testConnect()throws SQLException{
        DBUtility db = new DBUtility();
        System.out.print(db.getConnection());
    }


    public void testGetToken3() {
        Map<String, Object> token= TokenUtil.getToken();
        System.out.println(token.get("access_token"));
        System.out.println(token.get("expires_in"));
    }



    public void testSaveToken4() {
        Token token=CommonUtil.getToken("appID", "appsecret");
        TokenUtil.saveToken(token);
    }


    @Test
    public void aa(){
        // 获取接口访问凭证
        String accessToken = CommonUtil.getToken("wxa8f4325fb8e78f07", "bd289e2ee6e4b8790277a58572866d42").getAccessToken();
        /**
         * 获取用户信息
         */
        WeixinUserInfo user = getUserInfo(accessToken, "oyPuaw2Q9BT3RKUIxZ-0ZDZfJvw8");
        System.out.println("OpenID：" + user.getOpenId());
        System.out.println("关注状态：" + user.getSubscribe());
        System.out.println("关注时间：" + user.getSubscribeTime());
        System.out.println("昵称：" + user.getNickname());
        System.out.println("性别：" + user.getSex());
        System.out.println("国家：" + user.getCountry());
        System.out.println("省份：" + user.getProvince());
        System.out.println("城市：" + user.getCity());
        System.out.println("语言：" + user.getLanguage());
        System.out.println("头像：" + user.getHeadImgUrl());
    }
}
