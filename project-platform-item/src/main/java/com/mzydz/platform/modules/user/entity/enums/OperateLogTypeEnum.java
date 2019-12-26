package com.mzydz.platform.modules.user.entity.enums;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;

/**
 * 操作日志 类型
 *
 * @author kongling
 * @package com.mzydz.platform.modules.user.entity.enums
 * @date 2019-09-12  16:01
 * @project ad-publish-cloud
 */
public enum OperateLogTypeEnum {
    STATUS_0(0, "未分类"),
    STATUS_1(1, "登录日志"),
    ;

    private Integer code;
    private String message;
    public static final List<Integer> CODES = Lists.newArrayList();

    private static final Map<Integer, OperateLogTypeEnum> instances = Maps.newHashMap();

    static {
        for (OperateLogTypeEnum typeEnum : OperateLogTypeEnum.values()) {
            instances.put(typeEnum.getCode(), typeEnum);
        }
        for (OperateLogTypeEnum o : OperateLogTypeEnum.values()) {
            CODES.add(o.getCode());
        }
    }

    OperateLogTypeEnum(Integer code, String message) {

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

    public static OperateLogTypeEnum getOperateLogTypeEnumByCode(byte code) {
        if (instances.containsKey(code)) {
            return instances.get(code);
        }
        return null;
    }

    public static String getMessage(Integer code) {
        for (OperateLogTypeEnum temp : OperateLogTypeEnum.values()) {
            if (code == temp.code.intValue()) {
                return temp.message;
            }
        }
        return null;
    }
}
