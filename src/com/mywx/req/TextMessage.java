package com.mywx.req;

/**
 * Created by wangzy on 2017/6/2.
 */
public class TextMessage extends BaseMessage{

    //消息内容
    private String Content;

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }
}
