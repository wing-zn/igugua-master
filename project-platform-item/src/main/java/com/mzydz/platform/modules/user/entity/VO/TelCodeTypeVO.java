package com.mzydz.platform.modules.user.entity.VO;

import lombok.Data;
import lombok.ToString;

/**
 * 验证码类型
 */
@Data
@ToString
public class TelCodeTypeVO {
    private Byte codeType;// 1 注册 2 重置密码
    private String account; //手机号验证码
    private String imgToken;//图片验证码值
    private Long agentUserId;//登录用户id


}
