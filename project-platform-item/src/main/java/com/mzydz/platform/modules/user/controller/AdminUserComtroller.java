package com.mzydz.platform.modules.user.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.mzydz.platform.modules.user.entity.PO.AdminUser;
import com.mzydz.platform.modules.user.entity.PO.AdminUserRole;
import com.mzydz.platform.modules.user.entity.VO.AdminUserVO;
import com.mzydz.platform.modules.user.entity.VO.LoginParamVO;
import com.mzydz.platform.modules.user.entity.VO.RolePermission.AdminPermissionRecListVO;
import com.mzydz.platform.modules.user.entity.VO.adminuser.AdminUserInfoVO;
import com.mzydz.platform.modules.user.entity.VO.adminuser.AdminUserLoginInfo;
import com.mzydz.platform.modules.user.entity.VO.adminuser.AdminUserPwdVO;
import com.mzydz.platform.modules.user.entity.enums.AdminTypeEnum;
import com.mzydz.platform.modules.user.entity.enums.LogAdminAgentAppEnum;
import com.mzydz.platform.modules.user.service.IAdminPermissionService;
import com.mzydz.platform.modules.user.service.IAdminUserRoleService;
import com.mzydz.platform.modules.user.service.IAdminUserService;
import com.mzydz.platform.modules.user.service.ICommonService;
import com.util.StringUtils;
import com.util.auth.AuthSign;
import com.util.cache.UserCacheUtil;
import com.util.pageinfoutil.PageUtil;
import config.Respons.ResponseMsg;
import config.Respons.ResponseUtil;
import config.annotation.LogMenthodName;
import config.redis.RedisUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

/**
 * @author 卫星
 * @package com.suda.server.service.admin.controller
 * @date 2019-04-19  23:51
 * @project niuwan_cloud
 */
@RestController
@RequestMapping(value = "admin/user", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class AdminUserComtroller {
    @Autowired
    private IAdminUserService adminUserService;
    @Autowired
    private UserCacheUtil userCacheUtil;
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private IAdminPermissionService adminPermissionService;
    @Autowired
    private IAdminUserRoleService adminUserRoleService;
    @Autowired
    private HttpServletRequest request;

    @Autowired
    ICommonService commonService;


    /**
     * 登陆请求
     *
     * @param paramVO
     * @return
     */
    @LogMenthodName(name = "AOP环绕登陆请求", logType = 1)
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> doLogin(LoginParamVO paramVO) throws UnsupportedEncodingException {
        if (StringUtils.isBlank(paramVO.getAccount(), paramVO.getPassword())) {
            return ResponseUtil.getNotNormalMap(ResponseMsg.ERROR_PARAM);
        }
        AdminUser adminUserVO = adminUserService.selectByAccountLogin(paramVO.getAccount(), paramVO.getPassword());
        Long id = adminUserVO.getId();
        /**
         * 查询用户角色
         */
        AdminUserRole role = adminUserRoleService.getOne(new QueryWrapper<AdminUserRole>()
                .eq("user_id", id));
        Long roleId = 0L;
        if (role != null) {
            roleId = role.getRoleId();
        }
        adminUserVO.setRoleId(roleId);
        adminUserVO.setSystemRoleId(LogAdminAgentAppEnum.ADMIN_SYS.getSystem());
        String token = AuthSign.tokenSign(id, JSONObject.parseObject(JSONObject.toJSON(adminUserVO).toString()), adminUserVO.getSystemRoleId());
        //存储登陆token信息
        userCacheUtil.storeAdminUserLoginInfo(id, token);
        //存储登陆用户信息
        redisUtils.setStorageAdminUser(id, JSONObject.toJSON(adminUserVO).toString());
        /**
         * 设置sessionId
         */
        adminUserService.updateLoginTime(adminUserVO.getId());
        adminUserVO.setSessionId(token);
        AdminUserLoginInfo userLoginInfo = new AdminUserLoginInfo();
        BeanUtils.copyProperties(adminUserVO, userLoginInfo);
        return ResponseUtil.getSuccessMap(userLoginInfo);
    }

    /**
     * 获取用户信息
     */
    @RequestMapping(value = "/getAdminUserInfo", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getAdminUserInfo(Long id) {
        if (id == null) {
            return ResponseUtil.getNotNormalMap(ResponseMsg.ERROR_PARAM);
        }
        AdminUserInfoVO info = adminUserService.getAdminUserInfo(id);
        /**
         * 查询用户角色
         */
        AdminUserRole role = adminUserRoleService.getOne(new QueryWrapper<AdminUserRole>()
                .eq("user_id", id));
        Long roleId = 0L;
        if (role != null) {
            roleId = role.getRoleId();
        }
        info.setRoleId(roleId);
        /*
         *
         *用户权限信息
         */
        List<AdminPermissionRecListVO> permissions = adminPermissionService.getPermissionsRecAll(roleId);
        info.setPermissionFirstMenuVOs(permissions);
        return ResponseUtil.getSuccessMap(info);
    }


    /**
     * 管理员列表
     */
    @RequestMapping(value = "/getAdminUsers", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getAdminUsers(AdminUserVO adminUserVO, HttpServletRequest req, PageUtil pageUtil) throws UnsupportedEncodingException {
        AdminUser user = commonService.getAdminUser();
        //系统管理员
        if (user.getAdminType() == AdminTypeEnum.STATUS_0.getCode().intValue()) {
            if (adminUserVO.getAdminDeptId() == null) {
                adminUserVO.setAdminDeptId(user.getAdminDeptId());
            }
        } else {
            adminUserVO.setAdminDeptId(user.getAdminDeptId());
        }
        adminUserVO.setLoginId(user.getId());
        PageInfo<AdminUserVO> lists = adminUserService.selectByChoice(adminUserVO, pageUtil);
        return ResponseUtil.getSuccessMap(lists);
    }


    /**
     * 管理员禁用启用
     */
    @RequestMapping(value = "/adminUserDisable", method = RequestMethod.POST)
    @LogMenthodName(name = "禁用/启用管理员")
    @ResponseBody
    public Map<String, Object> adminUserDisable(AdminUserVO adminUserVO) throws UnsupportedEncodingException {
        if (adminUserVO.getId() == null
                || adminUserVO.getId() == 1
                || adminUserVO.getIsDisable() == null) {
            return ResponseUtil.getNotNormalMap(ResponseMsg.ERROR_PARAM);
        }
        adminUserService.adminUserDisable(adminUserVO);
        return ResponseUtil.getSuccessMap();
    }

    /**
     * 更改管理员角色
     */
    @RequestMapping(value = "/editUserRole", method = RequestMethod.POST)
    @LogMenthodName(name = "更改管理员角色")
    @ResponseBody
    public Map<String, Object> editUserRole(AdminUserVO adminUserVO) throws UnsupportedEncodingException {
        if (adminUserVO.getId() == null
                || adminUserVO.getId() == 1
                || adminUserVO.getRoleId() == null) {
            return ResponseUtil.getNotNormalMap(ResponseMsg.ERROR_PARAM);
        }
        adminUserService.editUserRole(adminUserVO);
        return ResponseUtil.getSuccessMap();
    }

    /**
     * 更改登陆账户账户密码
     */
    @RequestMapping(value = "/updateAdminPassword", method = RequestMethod.POST)
    @LogMenthodName(name = "更改账户密码")
    @ResponseBody
    public Map<String, Object> updateAdminPassword(AdminUserPwdVO adminUserVO) throws UnsupportedEncodingException {
        if (adminUserVO.getId() == null
        ) {
            return ResponseUtil.getNotNormalMap(ResponseMsg.ERROR_PARAM);
        }
        AdminUser adminUserSel = adminUserService.getById(adminUserVO.getId());
        if (adminUserSel == null) {
            return ResponseUtil.getNotNormalMap(ResponseMsg.NOUSER);
        }
        if (adminUserSel.getPassword().equalsIgnoreCase(adminUserVO.getNewPassword())) {
            return ResponseUtil.getNotNormalMap(ResponseMsg.OLD_NEW_PASSWORD_NOT_EQUAL);
        }
        if (!adminUserSel.getPassword().equalsIgnoreCase(adminUserVO.getOldPassword())) {
            return ResponseUtil.getNotNormalMap(ResponseMsg.OLD_PASSWORD_IS_ERROR);
        }
        AdminUser adminUser = new AdminUser();
        adminUser.setPassword(adminUserVO.getNewPassword());
        adminUser.setId(adminUserVO.getId());
        adminUserService.updateById(adminUser);
        return ResponseUtil.getSuccessMap();
    }

    /**
     * 修改下属管理员密码
     */
    @RequestMapping(value = "/editAdminPassword", method = RequestMethod.POST)
    @LogMenthodName(name = "修改下属管理员密码")
    @ResponseBody
    public Map<String, Object> editAdminPassword(AdminUser adminUserVO) throws UnsupportedEncodingException {
        if (adminUserVO.getId() == null
                || StringUtils.isBlank(adminUserVO.getPassword())) {
            return ResponseUtil.getNotNormalMap(ResponseMsg.ERROR_PARAM);
        }
        AdminUser adminUser = new AdminUser();
        adminUser.setId(adminUserVO.getId());
        adminUser.setPassword(adminUserVO.getPassword());
        adminUserService.updateById(adminUser);
        return ResponseUtil.getSuccessMap();
    }

    /**
     * 添加管理员
     */
    @RequestMapping(value = "/addAdminUser", method = RequestMethod.POST)
    @LogMenthodName(name = "添加管理员")
    @ResponseBody
    public Map<String, Object> addAdminUser(AdminUser vo) {
        if (StringUtils.isBlank(vo.getPassword(), vo.getAccount(), vo.getTel(), vo.getAdminType())) {
            return ResponseUtil.getNotNormalMap(ResponseMsg.ERROR_PARAM);
        }
        AdminUser user = adminUserService.getOne(new QueryWrapper<AdminUser>()
                .eq("account", vo.getAccount())
                .eq("is_deleted", 0));
        if (user != null) {
            return ResponseUtil.getNotNormalMap(ResponseMsg.USER_HAS_EXIST);
        }
        adminUserService.addAdminUser(vo);
        return ResponseUtil.getSuccessMap();
    }

    @RequestMapping(value = "/editAdminUser", method = RequestMethod.POST)
    @LogMenthodName(name = "编辑管理员")
    @ResponseBody
    public Map<String, Object> editAdminUser(AdminUser vo) {
        if (StringUtils.isBlank(vo.getUsername(), vo.getId())) {
            return ResponseUtil.getNotNormalMap(ResponseMsg.ERROR_PARAM);
        }
        adminUserService.editAdminUser(vo);
        return ResponseUtil.getSuccessMap();
    }


    /**
     * 删除管理员
     */
    @RequestMapping(value = "/delAdminUser", method = RequestMethod.POST)
    @LogMenthodName(name = "删除管理员")
    @ResponseBody
    public Map<String, Object> delAdminUser(AdminUser vo) throws UnsupportedEncodingException {
        if (vo.getId() == null) {
            return ResponseUtil.getNotNormalMap(ResponseMsg.ID_IS_EMPTY);
        }
        adminUserService.delAdminUser(vo.getId());
        return ResponseUtil.getSuccessMap();
    }
}
