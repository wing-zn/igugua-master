package com.mzydz.platform.modules.user.entity.PO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.mzydz.platform.modules.user.entity.VO.RolePermission.AdminPermissionRecListVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author 张龙飞
 * @since 2019-04-16
 */
@ApiModel(value = "AdminUser对象", description = "")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminUser extends Model<AdminUser> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "帐号")
    @TableField("account")
    private String account;

    @ApiModelProperty(value = "密码")
    @TableField("password")
    private String password;

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

    @ApiModelProperty(value = "1:删除，0:未删除")
    @TableField("is_deleted")
    private Boolean isDeleted;

    /**
     * 用户权限信息
     */
    @TableField(exist = false)
    private List<AdminPermissionRecListVO> permissionFirstMenuVOs;

    @ApiModelProperty(value = "负责人联系方式")
    @TableField("tel")
    private String tel;

    @ApiModelProperty(value = "备注描述")
    @TableField("remarks")
    private String remarks;


    @ApiModelProperty(value = "上级 父级 id")
    @TableField("admin_parent_id")
    private Long adminParentId;


    @ApiModelProperty(value = "子级部门id")
    @TableField("admin_dept_children_id")
    private Long adminDeptChildrenId;

    @ApiModelProperty(value = "根级部门id")
    @TableField("admin_dept_id")
    private Long adminDeptId;

    @ApiModelProperty(value = "管理员类型0、系统管理员（总行）1、网点管理员")
    @TableField("admin_type")
    private Integer adminType;

    /**
     * 用户所属平台角色
     */
    @ApiModelProperty(value = "1：总管理后台用户角色 2:其他用户角色")
    @TableField(exist = false)
    private Integer systemRoleId;

    /**
     * 用户角色 id
     */
    @ApiModelProperty(value = "角色id")
    @TableField(exist = false)
    private Long roleId;

    @TableField(exist = false)
    private String sessionId;


}
