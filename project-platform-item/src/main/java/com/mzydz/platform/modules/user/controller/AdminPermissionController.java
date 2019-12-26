package com.mzydz.platform.modules.user.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.mzydz.platform.modules.user.entity.PO.AdminPermission;
import com.mzydz.platform.modules.user.entity.PO.AdminUser;
import com.mzydz.platform.modules.user.entity.VO.RolePermission.AdminPermissionRecListVO;
import com.mzydz.platform.modules.user.entity.VO.RolePermission.AdminPermissionVO;
import com.mzydz.platform.modules.user.entity.VO.RolePermission.ReactRoleMenuVO;
import com.mzydz.platform.modules.user.entity.VO.RolePermission.RoleMenuVO;
import com.mzydz.platform.modules.user.service.IAdminPermissionService;
import com.util.StringUtils;
import com.util.auth.AuthSign;
import com.util.pageinfoutil.PageUtil;
import config.Respons.ResponseMsg;
import config.Respons.ResponseUtil;
import config.advice.CommonException;
import config.annotation.LogMenthodName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 权限列表管理
 *
 * @author kongling
 * @package com.suda.server.service.admin.controller
 * @date 2019-05-06  15:06
 * @project suda_cloud
 */
@RestController
@RequestMapping(value = "admin/permission", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class AdminPermissionController {
    @Autowired
    private HttpServletRequest request;

    @Autowired
    private IAdminPermissionService adminPermissionService;

    /**
     * 权限列表
     *
     * @param adminPermissionVO
     * @param pageUtil
     * @return
     */
    @RequestMapping(value = "/getPermissions")
    @ResponseBody
    public Map<String, Object> getPermissions(AdminPermissionVO adminPermissionVO, PageUtil pageUtil) {
        PageInfo<AdminPermission> lists = adminPermissionService.selectPermissionsBy(adminPermissionVO, pageUtil);
        return ResponseUtil.getSuccessMap(lists);
    }

    /**
     * 权限资源 递归查询
     */
    @RequestMapping(value = "/getPermissionsRecAll")
    @ResponseBody
    public Map<String, Object> getPermissionsRecAll() {
        List<AdminPermissionRecListVO> lists = adminPermissionService.getPermissionsRecAll(0L);
        return ResponseUtil.getSuccessMap(lists);
    }


    /**
     * 编辑权限（权限url,权限排序）
     */
    @RequestMapping(value = "/updatePermissions")
    @ResponseBody
    @LogMenthodName(name = "编辑后台权限列表")
    public Map<String, Object> getPermissions(AdminPermission adminPermission) {
        if (adminPermission.getId() == null || adminPermission.getMenuOrder() == null) {
            throw new CommonException(ResponseMsg.EMPTY_PARAM);
        }
        adminPermissionService.updateById(adminPermission);
        return ResponseUtil.getSuccessMap();
    }

    /**
     * 删除权限
     */
    @RequestMapping(value = "/delPermissions")
    @ResponseBody
    @LogMenthodName(name = "删除权限")
    public Map<String, Object> delPermissions(AdminPermission adminPermission) {
        if (adminPermission.getId() == null) {
            throw new CommonException(ResponseMsg.ID_IS_EMPTY);
        }
        if (adminPermissionService.listObjs(new QueryWrapper<AdminPermission>()
                .eq("parent_id", adminPermission.getId())).size() > 0) {
            throw new CommonException(ResponseMsg.FIRST_REMOVE_LOWER_PERMISSION);
        }
        adminPermissionService.delPermissionsById(adminPermission);
        return ResponseUtil.getSuccessMap();
    }

    /**
     * 添加权限列表
     */
    @RequestMapping(value = "/addPermission")
    @ResponseBody
    @LogMenthodName(name = "添加权限列表")
    public Map<String, Object> addPermission(AdminPermission adminPermission) {
        if (StringUtils.isBlank(adminPermission.getMenuUrl(), adminPermission.getMenuName()) || adminPermission.getMenuType() == null) {
            return ResponseUtil.getNotNormalMap(ResponseMsg.EMPTY_PARAM);
        }
        if (adminPermission.getParentId() != null && adminPermission.getParentId() != 0) {
            AdminPermission permission = adminPermissionService.getById(adminPermission.getParentId());
            if (permission == null) {
                return ResponseUtil.getNotNormalMap(ResponseMsg.PERMISSION_NO_HAVE_PARENT_ID);
            }
        }
        adminPermission.setCreateTime(new Date());
        adminPermissionService.save(adminPermission);
        return ResponseUtil.getSuccessMap();
    }

    /**
     * 用户获取权限路由菜单
     */
    @RequestMapping("/getRouters")
    @ResponseBody
    public Map<String, Object> getRouters() {
        String token = request.getHeader(AuthSign.token);
        AdminUser adminUser = AuthSign.getUserObject(token, AdminUser.class);
        if (adminUser == null) {
            throw new CommonException("token效验异常");
        }
        Long roleId = adminUser.getRoleId();
        List<AdminPermissionRecListVO> lists = adminPermissionService.getPermissionsRecAll(roleId);
        List<RoleMenuVO> roleMenuVOS = adminPermissionService.selectRouters(new ArrayList<RoleMenuVO>(), lists, roleId);
        return ResponseUtil.getSuccessMap(roleMenuVOS);
    }

    /**
     * 获取react 权限菜单
     *
     * @return
     */
    @RequestMapping("/getReactRouters")
    @ResponseBody
    public Map<String, Object> getReactRouters() {
        String token = request.getHeader(AuthSign.token);
        AdminUser adminUser = AuthSign.getUserObject(token, AdminUser.class);
        if (adminUser == null) {
            throw new CommonException("token效验异常");
        }
        Long roleId = adminUser.getRoleId();
        List<AdminPermissionRecListVO> lists = adminPermissionService.getPermissionsRecAll(roleId);
        List<ReactRoleMenuVO> roleMenuVOS = adminPermissionService.selectReactRouters(new ArrayList<ReactRoleMenuVO>(), lists, roleId);
        return ResponseUtil.getSuccessMap(roleMenuVOS);
    }

}
