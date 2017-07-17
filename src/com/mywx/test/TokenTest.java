package com.mywx.test;

import com.mywx.pojo.Token;
import com.mywx.utils.CommonUtil;
import com.mywx.utils.MyX509TrustManager;
import org.junit.Test;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Created by wangzy on 2017/6/2.
 */
public class TokenTest {


    @Test
    public void testGetToken1() throws Exception {
        String tokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=appID&secret=appsecret";

        //建立连接
        URL url = new URL(tokenUrl);
        HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();

        //创建SSLContext对象，并使用我们指定的信任管理器初始化
        TrustManager[] tm = {new MyX509TrustManager()};
        SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
        sslContext.init(null,tm,new java.security.SecureRandom());
        //从上述SSLContext对象中得到SSLSocketFactory对象
        SSLSocketFactory ssf = sslContext.getSocketFactory();

        httpsURLConnection.setSSLSocketFactory(ssf);
        httpsURLConnection.setDoOutput(true);
        httpsURLConnection.setDoInput(true);

        //设置请求方式
        httpsURLConnection.setRequestMethod("GET");
        //取得输入流
        InputStream inputStream = httpsURLConnection.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream,"utf-8");
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        //读取响应内容
        StringBuffer buffer = new StringBuffer();
        String str = null;
        while((str = bufferedReader.readLine())!=null){
            buffer.append(str);
        }
        bufferedReader.close();
        inputStreamReader.close();
        //释放资源
        inputStream.close();
        httpsURLConnection.disconnect();
        //输出返回结果
        System.out.print(buffer);
    }

    @Test
    public void testGetToken2() {

        Token token = CommonUtil.getToken("appID","appsecret");
        System.out.println("access_token:"+token.getAccessToken());
        System.out.println("expires_in:"+token.getExpiresIn());
    }

}
