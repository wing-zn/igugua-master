package com.mzydz.platform.modules.user.entity.enums.logintype;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * 登录 方式
 *
 * @author kongling
 * @package com.shop.enums.stockCode
 * @date 2019-05-13  10:21
 * @project suda_cloud
 */
public enum LoginWayEnum {
    /**
     *
     */
    STATUS_0(0, "帐号/密码"),
    /**
     *
     */
    STATUS_1(1, "手机号/验证码"),
    STATUS_2(2, "QQ登录"),
    STATUS_3(3, "微信登录"),
    ;
    private Integer code;
    private String message;
    public static final List<Integer> CODES = Lists.newArrayList();

    static {
        for (LoginWayEnum o : LoginWayEnum.values()) {
            CODES.add(o.getCode());
        }
    }

    LoginWayEnum(Integer code, String message) {

        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public static String getMessage(Integer code) {
        for (LoginWayEnum temp : LoginWayEnum.values()) {
            if (code == temp.code.intValue()) {
                return temp.message;
            }
        }
        return "";
    }

}
