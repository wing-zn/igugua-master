package com.mzydz.platform.modules.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mzydz.platform.modules.user.entity.PO.AdminRolePermission;
import com.mzydz.platform.modules.user.mapper.AdminRolePermissionMapper;
import com.mzydz.platform.modules.user.service.IAdminRolePermissionService;
import config.redis.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 角色菜单关联表 服务实现类
 * </p>
 *
 * @author kongling
 * @since 2019-05-06
 */
@Service
public class AdminRolePermissionServiceImpl extends ServiceImpl<AdminRolePermissionMapper, AdminRolePermission> implements IAdminRolePermissionService {

    @Autowired
    private RedisUtils redisUtils;

    /**
     * 删除缓存中角色权限信息
     *
     * @param ids
     */
    @Override
    @Async("taskExecutor")
    public void delCacheRolePermissions(List<Long> ids) {
        ids.forEach(id -> {
            redisUtils.delStorageRolePermission(id);
        });
    }
}
