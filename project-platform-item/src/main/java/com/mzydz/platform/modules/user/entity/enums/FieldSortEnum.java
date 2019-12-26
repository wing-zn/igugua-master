package com.mzydz.platform.modules.user.entity.enums;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * 数据排序
 *
 * @author kongling
 * @package com.shop.enums.stockCode
 * @date 2019-05-13  10:21
 * @project suda_cloud
 */
public enum FieldSortEnum {
    STATUS_0(0, "ASC"),
    STATUS_1(1, "DESC"),
    ;
    private Integer code;
    private String message;
    public static final List<Integer> CODES = Lists.newArrayList();

    static {
        for (FieldSortEnum o : FieldSortEnum.values()) {
            CODES.add(o.getCode());
        }
    }

    FieldSortEnum(Integer code, String message) {

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
        for (FieldSortEnum temp : FieldSortEnum.values()) {
            if (code == temp.code.intValue()) {
                return temp.message;
            }
        }
        return "";
    }

}
