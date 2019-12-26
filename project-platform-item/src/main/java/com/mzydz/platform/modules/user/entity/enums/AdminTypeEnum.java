package com.mzydz.platform.modules.user.entity.enums;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * 管理员类型 0、系统管理员（总行）1、网点管理员
 *
 * @author kongling
 * @package com.mzydz.platform.modules.user.entity.enums
 * @date 2019-11-22  14:01
 * @project hall-marketing-cloud
 */
public enum AdminTypeEnum {

    STATUS_0(0, "系统管理员"),
    STATUS_1(1, "网点管理员"),
    ;
    private Integer code;
    private String message;
    public static final List<Integer> CODES = Lists.newArrayList();

    static {
        for (AdminTypeEnum o : AdminTypeEnum.values()) {
            CODES.add(o.getCode());
        }
    }

    AdminTypeEnum(Integer code, String message) {

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
        for (AdminTypeEnum temp : AdminTypeEnum.values()) {
            if (code == temp.code.intValue()) {
                return temp.message;
            }
        }
        return "";
    }

}
