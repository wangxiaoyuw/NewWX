package com.mywx.servlet;

import com.mywx.service.CoreService;
import com.mywx.utils.CheckUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by wangzy on 2017/6/2.
 */

public class WeixinServlet extends HttpServlet {
    private static final long serialVersionUID = 4323197796926899691L;
    //处理微信服务器发来的消息
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      //消息的接受，处理，响应。设置格式
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        //调用核心业务接受消息，处理消息
        String respXml = CoreService.processRequest(request);
        //响应消息
        PrintWriter out = response.getWriter();
        out.print(respXml);
        out.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //接受微信服务器Get发送的4个参数
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");

        PrintWriter out = response.getWriter();
        if (CheckUtil.checkSignature(signature, timestamp, nonce)){
              out.println(echostr);// 校验通过，原样返回echostr参数内容
        }

        out.close();
    }

}
