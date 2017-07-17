package com.mywx.service;


import com.mywx.resp.Article;
import com.mywx.resp.NewsMessage;
import com.mywx.resp.TextMessage;
import com.mywx.utils.MessageUtil;
import com.mywx.utils.XiaoqUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by wangzy on 2017/6/2.
 */
public class CoreService {

     /* * 处理微信发来的请求
     * @param request
     * @return xml
     */
    public static String processRequest(HttpServletRequest request) {
        // xml格式的消息数据
        String respXml = null;
        // 默认返回的文本消息内容
        String respContent = "未知的消息类型！";
        try {
            // 调用parseXml方法解析请求消息
            Map<String, String> requestMap = MessageUtil.parseXml(request);
            // 发送方帐号
            String fromUserName = requestMap.get("FromUserName");
            // 开发者微信号
            String toUserName = requestMap.get("ToUserName");
            // 消息类型
            String msgType = requestMap.get("MsgType");


            // 回复文本消息
            TextMessage textMessage = new TextMessage();
            textMessage.setToUserName(fromUserName);
            textMessage.setFromUserName(toUserName);
            textMessage.setCreateTime(new Date().getTime());
            textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
System.out.print(textMessage.getFromUserName()+"~~~"+textMessage.getToUserName());
            // 文本消息
            if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
                //回复消息链接
                respContent = "<a href=\"http://localhost:8080/Myjsp.jsp\">点击进入</a>  ";
               /* String content = requestMap.get("Content");
                // 判断用户发送的是否是单个QQ表情
                if(XiaoqUtil.isQqFace(content)) {
                    respContent =content;
                }else {
                    respContent = "自行车" + emoji(0x1F6B2) + " 男性" + emoji(0x1F6B9) + " 钱袋" + emoji(0x1F4B0);
                }*/
               //图文消息
               /* String content = requestMap.get("Content");
                NewsMessage newsMessage = new NewsMessage();
                newsMessage.setToUserName(fromUserName);
                newsMessage.setFromUserName(toUserName);
                newsMessage.setCreateTime(new Date().getTime());
                newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
                List<Article> articleList = new ArrayList<Article>();
                if ("1".equals(content)){
                    Article article = new Article();
                    article.setTitle("微信公众账号开发");
                    article.setDescription("图文消息描述");
                    article.setPicUrl("http://wangzy.ngrok.cc/image/Chrysanthemum.jpg");
                    article.setUrl("http://blog.csdn.net/lyq8479");
                    articleList.add(article);
                    //设置图文消息个数
                    newsMessage.setArticleCount(articleList.size());
                    //设置图文消息包含的图文集合
                    newsMessage.setArticles(articleList);

                    respXml = MessageUtil.messageToXml(newsMessage);
                }else if ("2".equals(content)){
                    Article article1 = new Article();
                    article1.setTitle("微信公众帐号开发教程\n引言");
                    article1.setDescription("");
                    article1.setPicUrl("http://wangzy.ngrok.cc/image/Chrysanthemum.jpg");
                    article1.setUrl("http://blog.csdn.net/lyq8479/article/details/8937622");

                    Article article2 = new Article();
                    article2.setTitle("第2篇\n微信公众帐号的类型");
                    article2.setDescription("");
                    article2.setPicUrl("http://wangzy.ngrok.cc/image/Chrysanthemum.jpg");
                    article2.setUrl("http://blog.csdn.net/lyq8479/article/details/8941577");

                    Article article3 = new Article();
                    article3.setTitle("第3篇\n开发模式启用及接口配置");
                    article3.setDescription("");
                    article3.setPicUrl("http://wangzy.ngrok.cc/image/Chrysanthemum.jpg");
                    article3.setUrl("http://blog.csdn.net/lyq8479/article/details/8944988");

                    articleList.add(article1);
                    articleList.add(article2);
                    articleList.add(article3);
                    newsMessage.setArticleCount(articleList.size());
                    newsMessage.setArticles(articleList);
                    respXml = MessageUtil.messageToXml(newsMessage);
                }*/
            }
            // 图片消息
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {
                respContent = "您发送的是图片消息！";
            }
            // 语音消息
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) {
                respContent = "您发送的是语音消息！";
            }
            // 视频消息
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VIDEO)) {
                respContent = "您发送的是视频消息！";
            }
            // 视频消息
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_SHORTVIDEO)) {
                respContent = "您发送的是小视频消息！";
            }
            // 地理位置消息
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {
                respContent = "您发送的是地理位置消息！";
            }
            // 链接消息
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) {
                respContent = "您发送的是链接消息<a href=http://localhost:8080/Myjsp.jsp>点击</a>！";
            }
            // 事件推送
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
                // 事件类型
                String eventType = requestMap.get("Event");
                // 关注
                if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
                    respContent = "谢谢您的关注！";
                }
                // 取消关注
                else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {
                    // TODO 取消订阅后用户不会再收到公众账号发送的消息，因此不需要回复
                }
                // 扫描带参数二维码
                else if (eventType.equals(MessageUtil.EVENT_TYPE_SCAN)) {
                    // TODO 处理扫描带参数二维码事件
                }
                // 上报地理位置
                else if (eventType.equals(MessageUtil.EVENT_TYPE_LOCATION)) {
                    // TODO 处理上报地理位置事件
                }
                // 自定义菜单
                else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {
                    // TODO 处理菜单点击事件
                    String eventKey = requestMap.get("EventKey");
                    if (eventKey.equals("11")) {
                        respContent = "天气预报菜单项被点击！";
                    } else if (eventKey.equals("12")) {
                        respContent = "公交查询菜单项被点击！";
                    } else if (eventKey.equals("13")) {
                        respContent = "周边搜索菜单项被点击！";
                    } else if (eventKey.equals("14")) {
                        respContent = "历史上的今天菜单项被点击！";
                    } else if (eventKey.equals("21")) {
                        respContent = "歌曲点播菜单项被点击！";
                    } else if (eventKey.equals("22")) {
                        respContent = "经典游戏菜单项被点击！";
                    } else if (eventKey.equals("23")) {
                        respContent = "美女电台菜单项被点击！";
                    } else if (eventKey.equals("24")) {
                        respContent = "人脸识别菜单项被点击！";
                    } else if (eventKey.equals("25")) {
                        respContent = "聊天唠嗑菜单项被点击！";
                    } else if (eventKey.equals("31")) {
                        respContent = "Q友圈菜单项被点击！";
                    } else if (eventKey.equals("32")) {
                        respContent = "电影排行榜菜单项被点击！";
                    } else if (eventKey.equals("33")) {
                        respContent = "幽默笑话菜单项被点击！";
                    }
                }
            }
            // 设置文本消息的内容
            textMessage.setContent(respContent);
            // 将文本消息对象转换成xml
            respXml = MessageUtil.messageToXml(textMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return respXml;
    }

    /**
     * 表情转换
     * @param hexEmoji
     * @return
     */
    public static String emoji(int hexEmoji){
        return String.valueOf(Character.toChars(hexEmoji));
    }
}
