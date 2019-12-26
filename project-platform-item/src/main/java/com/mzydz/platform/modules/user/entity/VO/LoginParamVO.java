package com.mzydz.platform.modules.user.entity.VO;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

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
public class LoginParamVO extends Model<LoginParamVO> {


    @ApiModelProperty(value = "帐号")
    @TableField("account")
    private String account;

    @ApiModelProperty(value = "密码")
    @TableField("password")
    private String password;

    @ApiModelProperty(value = "imgToken")
    private String imgToken;

    @ApiModelProperty(value = "验证码")
    @TableField(exist = false)
    private String code;


}
