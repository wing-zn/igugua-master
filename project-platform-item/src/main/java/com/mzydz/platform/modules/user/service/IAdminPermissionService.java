package com.mzydz.platform.modules.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.mzydz.platform.modules.user.entity.PO.AdminPermission;
import com.mzydz.platform.modules.user.entity.VO.RolePermission.AdminPermissionRecListVO;
import com.mzydz.platform.modules.user.entity.VO.RolePermission.AdminPermissionVO;
import com.mzydz.platform.modules.user.entity.VO.RolePermission.ReactRoleMenuVO;
import com.mzydz.platform.modules.user.entity.VO.RolePermission.RoleMenuVO;
import com.util.pageinfoutil.PageUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * <p>
 * 菜单资源表 服务类
 * </p>
 *
 * @author kongling
 * @since 2019-05-06
 */
public interface IAdminPermissionService extends IService<AdminPermission> {

    /**
     * 操作性权限查看
     *
     * @return
     */
    public Set<String> getOperationPermissions();

    /**
     * 菜单权限列表
     *
     * @param adminPermissionVO
     * @param pageUtil
     * @return
     */
    PageInfo<AdminPermission> selectPermissionsBy(AdminPermissionVO adminPermissionVO, PageUtil pageUtil);

    /**
     * 删除权限资源
     *
     * @param adminPermission
     */
    void delPermissionsById(AdminPermission adminPermission);


    /**
     * 权限资源 递归查询
     *
     * @return
     */
    List<AdminPermissionRecListVO> getPermissionsRecAll(Long roleId);

    /**
     * 获取vue 菜单路由信息
     *
     * @param roleMenuVOS
     * @param lists
     * @return
     */
    List<RoleMenuVO> selectRouters(ArrayList<RoleMenuVO> roleMenuVOS, List<AdminPermissionRecListVO> lists, Long roleId);

    /**
     * 获取react 权限路由菜单
     *
     * @param reactRoleMenuVOS
     * @param lists
     * @param roleId
     * @return
     */
    List<ReactRoleMenuVO> selectReactRouters(ArrayList<ReactRoleMenuVO> reactRoleMenuVOS, List<AdminPermissionRecListVO> lists, Long roleId);


    /**
     * 重新构造 各级管理员给下级分配的权限
     *
     * @param lists            上级拥有的权限 菜单信息
     * @param allocationRoleId 需要分配 权限的 角色 信息
     * @return
     */
    CopyOnWriteArrayList<AdminPermissionRecListVO> reCreatePermission(CopyOnWriteArrayList<AdminPermissionRecListVO> lists, Long allocationRoleId);

   }
