package com.mzydz.platform.modules.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mzydz.platform.modules.user.entity.PO.AdminRolePermission;

import java.util.List;

/**
 * <p>
 * 角色菜单关联表 服务类
 * </p>
 *
 * @author kongling
 * @since 2019-05-06
 */
public interface IAdminRolePermissionService extends IService<AdminRolePermission> {

    /**
     * 删除缓存中角色权限信息
     *
     * @param ids
     */
    void delCacheRolePermissions(List<Long> ids);
}
