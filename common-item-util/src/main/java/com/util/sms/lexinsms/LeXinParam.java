package com.util.sms.lexinsms;


/**
 * 乐信短信
 */
public class LeXinParam {
    private String accName =("accName");
    private String accPwd = ("accPwd").toUpperCase();
    //签名验证
    private String sign =("leXin_sign");
    //请求接口
    private String sendAddr =("leXin_sendAddr");
    //消息内容
    private String content = ("leXin_content");

    private String aimcodes;
    private String dataType = "json";

    public String getContent() {
        return content;
    }

    public void setAccName(String accName) {
        this.accName = accName;
    }

    public void setAccPwd(String accPwd) {
        this.accPwd = accPwd;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAccName() {
        return accName;
    }

    public String getAccPwd() {
        return accPwd;
    }

    public String getAimcodes() {
        return aimcodes;
    }

    public void setAimcodes(String aimcodes) {
        this.aimcodes = aimcodes;
    }


    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    @Override
    public String toString() {
        return "LeXinParam{" +
                "aimcodes='" + aimcodes + '\'' +
                ", content='" + content + '\'' +
                ", dataType='" + dataType + '\'' +
                '}';
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getSendAddr() {
        return sendAddr;
    }

    public void setSendAddr(String sendAddr) {
        this.sendAddr = sendAddr;
    }
}
