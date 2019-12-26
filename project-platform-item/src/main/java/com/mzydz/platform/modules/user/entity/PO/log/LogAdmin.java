package com.mzydz.platform.modules.user.entity.PO.log;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 系统日志
 * </p>
 *
 * @author kongling
 * @since 2019-09-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "LogAdmin对象", description = "系统日志")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LogAdmin extends Model<LogAdmin> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "请求地址")
    @TableField("request_url")
    private String requestUrl;

    @ApiModelProperty(value = "请求方式")
    @TableField("request_way")
    private String requestWay;

    @ApiModelProperty(value = "IP")
    @TableField("ip")
    private String ip;

    @ApiModelProperty(value = "方法地址")
    @TableField("method_url")
    private String methodUrl;

    @ApiModelProperty(value = "方法别名")
    @TableField("method_name")
    private String methodName;

    @ApiModelProperty(value = "请求参数")
    @TableField("request_param")
    private String requestParam;

    @ApiModelProperty(value = "返回参数")
    @TableField("return_param")
    private String returnParam;

    @ApiModelProperty(value = "耗时")
    @TableField("time")
    private String time;

    @ApiModelProperty(value = "创建时间")
    @TableField("create_time")
    private Date createTime;

    @ApiModelProperty(value = "操作人id")
    @TableField("operator_id")
    private Long operatorId;

    @ApiModelProperty(value = "登录设备")
    @TableField("login_facility")
    private String loginFacility;

    @ApiModelProperty(value = "日志类型 0：未分类1：登录 ")
    @TableField("type")
    private Integer type;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
