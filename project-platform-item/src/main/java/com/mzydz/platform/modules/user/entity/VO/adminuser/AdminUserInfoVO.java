package com.mzydz.platform.modules.user.entity.VO.adminuser;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.mzydz.platform.modules.user.entity.VO.RolePermission.AdminPermissionRecListVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author konglingyinxia
 * @since 2019-04-16
 */
@ApiModel(value = "AdminUser对象", description = "")
@Data
public class AdminUserInfoVO extends Model<AdminUserInfoVO> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "帐号")
    @TableField("account")
    private String account;

    @ApiModelProperty(value = "昵称")
    @TableField("nickname")
    private String nickname;

    @ApiModelProperty(value = "用户名")
    @TableField("username")
    private String username;

    @ApiModelProperty(value = "添加时间")
    @TableField("create_time")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @ApiModelProperty(value = "最后登录时间")
    @TableField("last_login_time")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastLoginTime;

    @TableField("timestamp")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date timestamp;

    @ApiModelProperty(value = "0:有效 1:禁止登录")
    @TableField("is_disable")
    private Boolean isDisable;

    /**
     * 用户权限信息
     */
    @TableField(exist = false)
    private List<AdminPermissionRecListVO> permissionFirstMenuVOs;

    /**
     * 用户角色 id
     */
    @ApiModelProperty(value = "角色id")
    @TableField(exist = false)
    private Long roleId;

    @ApiModelProperty(value = "负责人联系方式")
    @TableField("tel")
    private String tel;

    @ApiModelProperty(value = "备注描述")
    @TableField("remarks")
    private String remarks;


    @ApiModelProperty(value = "子级部门id")
    @TableField("admin_dept_children_id")
    private Long adminDeptChildrenId;

    @ApiModelProperty(value = "根级部门id")
    @TableField("admin_dept_id")
    private Long adminDeptId;

    @ApiModelProperty(value = "管理员类型0、系统管理员（总行）1、网点管理员")
    @TableField("admin_type")
    private Integer adminType;


}
