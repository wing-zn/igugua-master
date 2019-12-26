package com.mzydz.platform.modules.user.entity.enums.logintype;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * 登录 设备
 *
 * @author kongling
 * @package com.shop.enums.stockCode
 * @date 2019-05-13  10:21
 * @project suda_cloud
 */
public enum LoginFacilityEnum {
    /**
     * 登录移动端
     */
    STATUS_0(0, "app"),
    /**
     * 登录pc 端
     */
    STATUS_1(1, "pc"),
    ;
    private Integer code;
    private String message;
    public static final List<Integer> CODES = Lists.newArrayList();

    static {
        for (LoginFacilityEnum o : LoginFacilityEnum.values()) {
            CODES.add(o.getCode());
        }
    }

    LoginFacilityEnum(Integer code, String message) {

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
        for (LoginFacilityEnum temp : LoginFacilityEnum.values()) {
            if (code == temp.code.intValue()) {
                return temp.message;
            }
        }
        return "";
    }

}
