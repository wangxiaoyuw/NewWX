package com.mywx.req;

/**
 * Created by wangzy on 2017/6/2.
 */
public class BaseMessage {

    /**
     * 开发者微信号
     */
    private String ToUserName;

    //发送方账号（唯一一个OpenID）
    private String FromUserName;

    //创建时间
    private long CreateTime;

    //消息类型（text/image/location/link）
    private String MsgType;

    //消息id,64位整形
    private long MsgId;

    public String getToUserName() {
        return ToUserName;
    }

    public void setToUserName(String toUserName) {
        ToUserName = toUserName;
    }

    public String getFromUserName() {
        return FromUserName;
    }

    public void setFromUserName(String fromUserName) {
        FromUserName = fromUserName;
    }

    public long getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(long createTime) {
        CreateTime = createTime;
    }

    public String getMsgType() {
        return MsgType;
    }

    public void setMsgType(String msgType) {
        MsgType = msgType;
    }

    public long getMsgId() {
        return MsgId;
    }

    public void setMsgId(long msgId) {
        MsgId = msgId;
    }
}
