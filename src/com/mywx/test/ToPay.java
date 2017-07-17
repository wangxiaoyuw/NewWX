package com.mywx.test;

import com.mywx.topay.WxPay;
import com.mywx.utils.*;
import net.sf.json.JSONObject;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Created by wangzy on 2017/6/7.
 */
public class ToPay {

    //微信支付商户开通后 微信会提供appid和appsecret和商户号partner
    private static String appid = "wx2530049f3fbc04dc";

    private static String appsecret = "162ba840637e4f4ba571639a12bdfe36";

    private static String partner = "1354198602";

    //这个参数partnerkey是在商户后台配置的一个32位的key,微信商户平台-账户设置-安全设置-api安全
    private static String partnerkey = "dTkus4q8cjmy9ffUsE5ZQzjpC99FjJgC";

    //openId 是微信用户针对公众号的标识，授权的部分这里不解释
    private static String openId = "ohPpYs4VyR51JoYcgM3V9y0w3fd0";

    //微信支付成功后通知地址 必须要求80端口并且地址不能带参数
    private static String notifyurl = "http://www.weixin.qq.com/wxpay/pay.php";

    public static void main(String[] args){

        //微信支付jsApi
        WxPay tpWxPay = new WxPay();
        tpWxPay.setOpenId(openId);
        tpWxPay.setBody("shangpinxinxi");
        tpWxPay.setOrderId(getNonceStr());
        tpWxPay.setSpbillCreateIp("127.0.0.93");
        tpWxPay.setTotalFee("0.01");
        getPackage(tpWxPay);
    }

    //获取预支付订单，生成签名
    private static void getPackage(WxPay tpWxPay) {

        //获取必要参数
        String openId = tpWxPay.getOpenId();
        String attach = "";//非必须，可以为空，但不参与签名
        String totalFee = getMoney(tpWxPay.getTotalFee());//转化为分
        String spbill_create_ip =tpWxPay.getSpbillCreateIp();
        String notify_url = notifyurl;//已给出
        String trade_type = "JSAPI";
        String mch_id = partner;
        String nonce_str = getNonceStr();
        String body = tpWxPay.getBody();
        String out_trade_no = tpWxPay.getOrderId();

        SortedMap<String,String> packageParams = new TreeMap<String,String>();
         packageParams.put("appid",appid);
         packageParams.put("openid",openId);
         packageParams.put("attach",attach);
         packageParams.put("total_fee",totalFee);
         packageParams.put("spbill_create_ip",spbill_create_ip);
         packageParams.put("trade_type", trade_type);
         packageParams.put("notify_url",notify_url);
         packageParams.put("mch_id",mch_id);
         packageParams.put("nonce_str",nonce_str);
         packageParams.put("body",body);
         packageParams.put("out_trade_no",out_trade_no);

        RequestHandler requestHandler = new RequestHandler(null,null);
        requestHandler.init(appid, appsecret, partnerkey);
        //生成签名
        String sign = requestHandler.createSign(packageParams);

        //封装为xml
        String xml = "<xml>" + "<appid>" + appid + "</appid>" + "<mch_id>"
                + mch_id + "</mch_id>" + "<nonce_str>" + nonce_str
                + "</nonce_str>" + "<sign>" + sign + "</sign>"
                + "<body><![CDATA[" + body + "]]></body>"
                + "<out_trade_no>" + out_trade_no
                + "</out_trade_no>" + "<attach>" + attach + "</attach>"
                + "<total_fee>" + totalFee + "</total_fee>"
                + "<spbill_create_ip>" + spbill_create_ip
                + "</spbill_create_ip>" + "<notify_url>" + notify_url
                + "</notify_url>" + "<trade_type>" + trade_type
                + "</trade_type>" + "<openid>" + openId + "</openid>"
                + "</xml>";

        String prepay_id = "";
        //调用接口
        String createOrderURL = "https://api.mch.weixin.qq.com/pay/unifiedorder";

        //将封装为xml的数据发送给微信，返回prepay_id
         prepay_id = new GetWxOrderno().getPayNo(createOrderURL, xml);
        System.out.print(prepay_id);

//获取prepay_id后，拼接最后请求支付所需要的package

        SortedMap<String, String> finalpackage = new TreeMap<String, String>();
        String timestamp = SHA1.getTimeStamp();
        String packages = "prepay_id="+prepay_id;
        finalpackage.put("appId", appid);
        finalpackage.put("timeStamp", timestamp);
        finalpackage.put("nonceStr", nonce_str);
        finalpackage.put("package", packages);
        finalpackage.put("signType", "MD5");
        //要签名
        String finalsign = requestHandler.createSign(finalpackage);

        String finaPackage = "\"appId\":\"" + appid + "\",\"timeStamp\":\"" + timestamp
                + "\",\"nonceStr\":\"" + nonce_str + "\",\"package\":\""
                + packages + "\",\"signType\" : \"MD5" + "\",\"paySign\":\""
                + finalsign + "\"";
        String appid2 = appid;
        String timestamp2 = SHA1.getTimeStamp();
        String nonceStr2 = nonce_str;
        String prepay_id2 = "prepay_id="+prepay_id;
        String packages2 = prepay_id2;

        //向前台的页面输出结果

       /* Map<String, Object> map = new HashMap<String, Object>();
        map.put("appId1",appid2);
        map.put("timeStamp1", timestamp2 );
        map.put("nonceStr1",nonceStr2);
        map.put("package1", packages);
        map.put("signType1", "MD5");
        map.put("finalsign1",finalsign);
        PrintWriter out=response.getWriter();
        out.println(JSONObject.fromObject(map).toString());
        out.close();*/

    }

    /**
     * 元转化分
     * @param totalFee  总钱数
     * @return
     */
    private static String getMoney(String totalFee) {

        if(totalFee==null){
            return "";
        }
        // 金额转化为分为单位
        String currency =  totalFee.replaceAll("\\$|\\￥|\\,", "");  //处理包含, ￥ 或者$的金额
        int index = currency.indexOf(".");
        int length = currency.length();
        Long amLong = 0l;
        if(index == -1){
            amLong = Long.valueOf(currency+"00");
        }else if(length - index >= 3){
            amLong = Long.valueOf((currency.substring(0, index+3)).replace(".", ""));
        }else if(length - index == 2){
            amLong = Long.valueOf((currency.substring(0, index+2)).replace(".", "")+0);
        }else{
            amLong = Long.valueOf((currency.substring(0, index+1)).replace(".", "")+"00");
        }
        return amLong.toString();
    }

    /**
     * 获取随机字符串
     * @return
     */
    public static String getNonceStr() {

        String currTime = TenpayUtil.getCurrTime();
        String strTime = currTime.substring(8,currTime.length());
        String strRandom = TenpayUtil.buildRandom(4)+"";
        String nonceStr = strTime+strRandom;
        return nonceStr;
    }
}
