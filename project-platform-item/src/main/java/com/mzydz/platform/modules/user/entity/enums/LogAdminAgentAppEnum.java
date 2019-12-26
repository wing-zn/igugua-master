package com.mzydz.platform.modules.user.entity.enums;

/**
 * 日志记录设置系统类型
 *
 * @author 卫星
 * @package com.fegin.common.comenum
 * @date 2019-04-11  13:34
 * @project cloud_template
 */
public enum LogAdminAgentAppEnum {
    /**
     * 后台
     */
    ADMIN_SYS(1, "总后台管理员"),
    /**
     * 代理后台
     */
    AGENT_SYS(2, "广告机用户"),
    ;
    private Integer system;
    private String message;



    LogAdminAgentAppEnum(Integer code, String message) {

        this.system = code;
        this.message = message;
    }


    public Integer getSystem() {
        return system;
    }

    public void setSystem(Integer system) {
        this.system = system;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}


