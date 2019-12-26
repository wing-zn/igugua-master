package com.mzydz.platform.modules.api.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * 栏目类型
 *
 * @author kongling
 * @package com.mzydz.platform.modules.api.entity
 * @date 2019-12-13  09:29
 * @project hall-marketing-cloud
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ColumnsTypeVO {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "生活类型名字")
    @TableField("type_name")
    private String typeName;

}
