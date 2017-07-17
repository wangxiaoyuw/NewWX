package com.mywx.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Created by wangzy on 2017/6/7.
 */
public class RequestHandler {

    // Token获取网关地址地址
    private String tokenUrl;
    //预支付网关url地址
    private String gateUrl;
    // 查询支付通知网关URL
    private String notifyUrl;
    // 商户参数
    private String appid;
    private String appkey;
    private String partnerkey;
    private String appsecret;
    private String key;
    // 请求的参数
    private SortedMap parameters;
    //Token
    private String Token;
    private String charset;
    //debug信息
    private String debugInfo;
    private String last_errcode;

    private HttpServletRequest request;

    private HttpServletResponse response;

    /**
     * 初始化构造函数
     * @param request
     * @param response
     */
    public RequestHandler(HttpServletRequest request,
                          HttpServletResponse response) {
        this.last_errcode = "0";
        this.request = request;
        this.response = response;
        this.charset = "UTF-8";
        this.parameters = new TreeMap();
        // 验证notify支付订单网关
        notifyUrl = "http://www.weixin.qq.com/wxpay/pay.php";

    }

    /**
     * 初始化    将partnerkey赋值给key
     * @param appid
     * @param appsecret
     * @param partnerkey
     */
    public void init(String appid, String appsecret, String partnerkey) {
        this.last_errcode = "0";
        this.Token = "token_";
        this.debugInfo = "";
        this.appid = appid;
        this.partnerkey = partnerkey;
        this.appsecret = appsecret;
        this.key = partnerkey;
    }

    /**
     * 生成签名
     * @param packageParams 所有参数
     * @return
     */
    public String createSign(SortedMap<String, String> packageParams) {
         StringBuffer sb = new StringBuffer();
         Set es = packageParams.entrySet();
         Iterator it = es.iterator();
         while (it.hasNext()){
             Map.Entry entry = (Map.Entry) it.next();
             String k = (String) entry.getKey();
             String v = (String) entry.getValue();
             if (null!=v&&"".equals(v)&&!"sign".equals(k)&&!"key".equals(k)){
                 sb.append(k+"="+v+"&");
             }
         }
         sb.append("key="+this.getKey());
         String sign = MD5Util.MD5Encode(sb.toString(),this.charset).toUpperCase();
         return sign;
    }

    public String getKey() {
        return key;
    }
}
