package com.mywx.service;

import com.google.gson.Gson;
import com.mywx.other.TranslateResult;
import net.sf.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by wangzy on 2017/6/8.
 */
public class BaiduTranslateService {

    public static String httpRequest(String requestUrl) throws IOException {
        StringBuffer buffer = new StringBuffer();
        try{
            URL url = new URL(requestUrl);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setDoOutput(false);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setUseCaches(false);

            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();

            //将返回的输入流转化成字符串
            InputStream inputStream = httpURLConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            bufferedReader.close();
            inputStreamReader.close();
            // 释放资源
            inputStream.close();
            inputStream = null;
            httpURLConnection.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return buffer.toString();
    }
    /**
     * utf编码
     *
     * @param source
     * @return
     */
    public static String urlEncodeUTF8(String source) {
        String result = source;
        try {
            result = java.net.URLEncoder.encode(source, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 翻译（中->英 英->中 日->中 ）
     *
     * @param source
     * @return
     */
    public static String translate(String source) {
        String dst = null;

        // 组装查询地址
        String requestUrl = "http://api.fanyi.baidu.com/public/2.0/bmt/translate?client_id=AAAAAAAAAAAAAAAAAAAAAAAA&q={keyWord}&from=auto&to=auto";
        // 对参数q的值进行urlEncode utf-8编码
        requestUrl = requestUrl.replace("{keyWord}", urlEncodeUTF8(source));

        // 查询并解析结果
        try {
            // 查询并获取返回结果
            String json = httpRequest(requestUrl);

            // 通过Gson工具将json转换成TranslateResult对象
            TranslateResult translateResult = new Gson().fromJson(json, TranslateResult.class);
            // 取出translateResult中的译文
            dst = translateResult.getTrans_result().get(0).getDst();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (null == dst)
            dst = "翻译系统异常，请稍候尝试！";
        return dst;
    }

    public static void main(String[] args) {
        // 翻译结果：The network really powerful
        System.out.println(translate("网络真强大"));
    }
}
