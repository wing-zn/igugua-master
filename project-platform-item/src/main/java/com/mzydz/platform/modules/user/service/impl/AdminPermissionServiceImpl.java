package com.mzydz.platform.modules.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mzydz.platform.modules.user.entity.PO.AdminPermission;
import com.mzydz.platform.modules.user.entity.PO.AdminRolePermission;
import com.mzydz.platform.modules.user.entity.VO.RolePermission.*;
import com.mzydz.platform.modules.user.entity.enums.rolepermission.PermissionsMenuTypeEnum;
import com.mzydz.platform.modules.user.mapper.AdminPermissionMapper;
import com.mzydz.platform.modules.user.mapper.AdminRolePermissionMapper;
import com.mzydz.platform.modules.user.service.IAdminPermissionService;
import com.util.pageinfoutil.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;


/**
 * <p>
 * 菜单资源表 服务实现类
 * </p>
 *
 * @author kongling
 * @since 2019-05-06
 */
@Service
public class AdminPermissionServiceImpl extends ServiceImpl<AdminPermissionMapper, AdminPermission> implements IAdminPermissionService {

    @Autowired
    private AdminPermissionMapper adminPermissionMapper;
    @Autowired
    private AdminRolePermissionMapper adminRolePermissionMapper;

    private static Set<String> operationPermissions = Sets.newHashSet();

    //初始化权限记录
    @PostConstruct
    public void init() {
        operationPermissions.addAll(baseMapper.selectObjs(new QueryWrapper<AdminPermission>()
                .eq("menu_type", PermissionsMenuTypeEnum.MENU_TYPE_2.getCode()).select("menu_url")).stream().map(e -> e.toString()).collect(Collectors.toList()));
    }

    @Override
    public Set<String> getOperationPermissions() {
        if (operationPermissions.isEmpty()) {
            init();
        }
        return operationPermissions;
    }


    /**
     * 菜单权限列表
     *
     * @param adminPermissionVO
     * @param pageUtil
     * @return
     */
    @Override
    public PageInfo<AdminPermission> selectPermissionsBy(AdminPermissionVO adminPermissionVO, PageUtil pageUtil) {
        PageUtil.page(pageUtil);
        List<AdminPermission> lists = adminPermissionMapper.selectPermissionsBy(adminPermissionVO);
        return new PageInfo<>(lists);
    }

    /**
     * 删除权限资源
     *
     * @param adminPermission
     */
    @Override
    @Transactional(rollbackFor = {})
    public void delPermissionsById(AdminPermission adminPermission) {
        Map<String, Object> resultMap = Maps.newHashMap();
        resultMap.put("permission_id", adminPermission.getId());
        //删除权限角色表中的内容
        adminRolePermissionMapper.deleteByMap(resultMap);
        //删除权限表资源
        adminPermissionMapper.deleteById(adminPermission.getId());
    }

    //============================优化后的权限资源管理===========================================

    /**
     * 权限资源 递归查询
     *
     * @return
     */
    @Override
    public List<AdminPermissionRecListVO> getPermissionsRecAll(Long roleId) {
        List<AdminPermissionRecListVO> vos = adminPermissionMapper.getPermissionsRecAll(roleId, 0L);
        //递归查询子级
        recursionSelPermissions(vos, roleId);
        return vos;
    }

    /**
     * 递归查询子级
     *
     * @param vos
     * @param roleId
     */
    private List<AdminPermissionRecListVO> recursionSelPermissions(List<AdminPermissionRecListVO> vos, Long roleId) {
        vos.forEach((permission) -> {
            List<AdminPermissionRecListVO> temps = adminPermissionMapper.getPermissionsRecAll(roleId, permission.getId());
            if (roleId == 1) {
                permission.setChecked(true);
            }
            permission.setChildren(recursionSelPermissions(temps, roleId));
        });
        return vos;
    }

    /**
     * 获取vue 菜单路由信息
     *
     * @param roleMenuVOS
     * @param lists
     * @return
     */
    @Override
    public List<RoleMenuVO> selectRouters(ArrayList<RoleMenuVO> roleMenuVOS, List<AdminPermissionRecListVO> lists, Long roleId) {
        //处理一级菜单
        lists.forEach(perm -> {
            if (perm.getChecked() || roleId == 1L) {
                roleMenuVOS.add(RoleMenuVO.builder().redirect("noRedirect")
                        .path(perm.getMenuUrl())
                        .name(perm.getMenuName())
                        .meta(MenuMetaVO.builder().title(perm.getMenuName())
                                .icon(perm.getMenuClass()).build())
                        .alwaysShow(false)
                        .component("Layout").children(findChildren(new ArrayList<RoleMenuVO>(), perm.getChildren(), roleId)).build());
            }
        });
        return roleMenuVOS;
    }

    /**
     * 递归子菜单
     */
    private List<RoleMenuVO> findChildren(ArrayList<RoleMenuVO> roleMenuVOS, List<AdminPermissionRecListVO> lists, Long roleId) {
        lists.forEach(perm -> {
            if ((perm.getChecked() || roleId == 1L) && perm.getMenuType() == PermissionsMenuTypeEnum.MENU_TYPE_1.getCode().byteValue()) {
                roleMenuVOS.add(RoleMenuVO.builder()
                        .path(perm.getMenuUrl())
                        .name(perm.getMenuName())
                        .meta(MenuMetaVO.builder().title(perm.getMenuName())
                                .icon(perm.getMenuClass()).build())
                        .alwaysShow(false)
                        .component(perm.getMenuComponent()).children(findChildren(new ArrayList<RoleMenuVO>(), perm.getChildren(), roleId)).build());
            }
        });
        return roleMenuVOS;
    }

    /**
     * 获取react 权限路由菜单
     *
     * @param reactRoleMenuVOS
     * @param lists
     * @param roleId
     * @return
     */
    @Override
    public List<ReactRoleMenuVO> selectReactRouters(ArrayList<ReactRoleMenuVO> reactRoleMenuVOS, List<AdminPermissionRecListVO> lists, Long roleId) {
        lists.forEach(perm -> {
            if (perm.getChecked() || roleId == 1L) {
                findChildrenReactRouters(reactRoleMenuVOS, perm, perm, roleId);
            }
        });
        return reactRoleMenuVOS;
    }

    /**
     * 获取 react 路由信息
     *
     * @param reactRoleMenuVOS
     * @param current
     */
    private void findChildrenReactRouters(ArrayList<ReactRoleMenuVO> reactRoleMenuVOS, AdminPermissionRecListVO current, AdminPermissionRecListVO parent, Long roleId) {
        ReactRoleMenuVO part = ReactRoleMenuVO.builder()
                .icon(current.getMenuClass())
                .key(current.getMenuUrl())
                .text(current.getMenuName())
                .path(current.getMenuUrl())
                .parentKey(parent.getMenuUrl())
                .parentId(current.getParentId())
                .order(current.getMenuOrder())
                .build();
        reactRoleMenuVOS.add(part);
        if (!current.getChildren().isEmpty()) {
            current.getChildren().forEach(item -> {
                if ((item.getChecked() || roleId == 1L) && item.getMenuType() == PermissionsMenuTypeEnum.MENU_TYPE_1.getCode().byteValue()) {
                    findChildrenReactRouters(reactRoleMenuVOS, item, current, roleId);
                }
            });
        }
    }


    /**
     * 重新构造 各级管理员给下级分配的权限
     *
     * @param lists            上级拥有的权限 菜单信息
     * @param allocationRoleId 需要分配 权限的 角色 信息
     * @return
     */
    @Override
    public CopyOnWriteArrayList<AdminPermissionRecListVO> reCreatePermission(CopyOnWriteArrayList<AdminPermissionRecListVO> lists, Long allocationRoleId) {
        lists.forEach(e -> {
            if (!e.getChecked()) {
                lists.remove(e);
            } else if (allocationRoleId != 1) {
                AdminRolePermission p = adminRolePermissionMapper.selectOne(new QueryWrapper<AdminRolePermission>()
                        .eq("permission_id", e.getId())
                        .eq("role_id", allocationRoleId));
                if (p != null) {
                    e.setChecked(true);
                } else {
                    e.setChecked(false);
                }
                e.setChildren(reCreatePermission(Lists.newCopyOnWriteArrayList(e.getChildren()), allocationRoleId));
            }
        });
        return lists;
    }
}
