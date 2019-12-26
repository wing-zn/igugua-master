package com.mzydz.platform.modules.user.entity.VO.RolePermission;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * react ant 配置 权限路由菜单
 *
 * @author kongling
 * @package com.mzydz.platform.modules.user.entity.VO.RolePermission
 * @date 2019-12-12  09:15
 * @project hall-marketing-cloud
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReactRoleMenuVO {
    //唯一key
    private String key;
    //用于关联父级
    private String parentKey;
    //菜单对应的路由地址
    private String path;
    //菜单标题
    private String text;
    //图标
    private String icon;
    //父级id
    private Long parentId;
    //排序
    private Integer order;
}
