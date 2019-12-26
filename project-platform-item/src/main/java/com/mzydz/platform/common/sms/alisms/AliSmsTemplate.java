package com.mzydz.platform.common.sms.alisms;

import com.alibaba.fastjson.JSONObject;
import com.util.sms.alisms.AliParam;
import config.com.MyConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 短信模版读取工具类
 *
 * @author 卫星
 */
@Component
public class AliSmsTemplate {

    @Autowired
    private MyConfiguration myConfiguration;

    /**
     * 登录验证短信模版
     *
     * @param tel
     * @param code
     * @throws IOException
     */
    public AliParam getAliLogin(String code, String tel) throws IOException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", code);
        AliParam ali = AliParam.builder().accessKeyId(myConfiguration.getAccessKeyId())
                .accessKeySecret(myConfiguration.getAccessKeySecret())
                .signName(myConfiguration.getAliSmsSignName()).build();
        ali.setTel(tel);
        ali.setTemplateCode(myConfiguration.getAliSmsTemplateCode());
        ali.setTemplateParam(jsonObject.toJSONString());
        return ali;

    }
}
