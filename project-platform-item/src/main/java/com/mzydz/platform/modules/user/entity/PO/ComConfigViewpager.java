package com.mzydz.platform.modules.user.entity.PO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * <p>
 * 轮播图配置
 * </p>
 *
 * @author 张龙飞
 * @since 2019-04-16
 */
@ApiModel(value = "ComConfigViewpager对象", description = "轮播图配置")
@Data
public class ComConfigViewpager extends Model<ComConfigViewpager> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("img_url")
    private String imgUrl;

    @ApiModelProperty(value = "图片类型：1.app 2.pc")
    @TableField("type")
    private Integer type;

    @ApiModelProperty(value = "排序字段")
    @TableField("sort")
    private Integer sort;

    @ApiModelProperty(value = "是否展示 默认为1 展示")
    @TableField("is_show")
    private Boolean isShow;

    @TableField("create_time")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @TableField("timestamp")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date timestamp;

    /**
     * 轮播图跳转地址
     */
    @TableField("skip_url")
    private String skipUrl;


    @ApiModelProperty(value = "根级部门企业id")
    @TableField("admin_dept_id")
    private Long adminDeptId;

    @ApiModelProperty(value = "添加人id")
    @TableField("add_user_id")
    private Long addUserId;


}
