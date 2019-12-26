package com.mzydz.platform.modules.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mzydz.platform.modules.user.entity.PO.AdminUserRole;
import com.mzydz.platform.modules.user.mapper.AdminUserRoleMapper;
import com.mzydz.platform.modules.user.service.IAdminUserRoleService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色用户关联表 服务实现类
 * </p>
 *
 * @author kongling
 * @since 2019-05-06
 */
@Service
public class AdminUserRoleServiceImpl extends ServiceImpl<AdminUserRoleMapper, AdminUserRole> implements IAdminUserRoleService {

}
