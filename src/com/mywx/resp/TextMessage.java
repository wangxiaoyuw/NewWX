package com.mywx.resp;

import com.mywx.event.BaseEvent;

/**
 * Created by wangzy on 2017/6/2.
 */
public class TextMessage extends BaseEvent {
    //回复的消息内容
    private String Content;

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }


}
