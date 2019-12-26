package com.util.sms.alisms;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 短信参数实体类
 *
 * @author 卫星
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class AliParam {

    private String accessKeyId;
    private String accessKeySecret;
    private String signName;
    private String templateCode;
    private String templateParam;
    private String tel;
}
