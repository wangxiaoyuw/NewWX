package com.mywx.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/**
 * Created by wangzy on 2017/6/5.
 */
public class InitServlet extends HttpServlet {

    public void init()throws ServletException{
        //获取web.xml中配置参数
        TokenThread.appid = getInitParameter("appid");
        TokenThread.appsecret = getInitParameter("appsecret");
        //未配置appid时给出提示
        if("".equals(TokenThread.appid)){
            System.out.print("未配置");
        }else{
            //启动定时线程
            new Thread(new TokenThread()).start();
        }

    }
}
