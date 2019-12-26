package com.mzydz.platform.common.interceptors;

import com.alibaba.fastjson.JSONObject;
import com.mzydz.platform.modules.user.entity.PO.AdminUser;
import com.mzydz.platform.modules.user.entity.enums.LogAdminAgentAppEnum;
import com.mzydz.platform.modules.user.service.IAdminPermissionService;
import com.util.StringUtils;
import com.util.auth.AuthSign;
import com.util.cache.UserCacheUtil;
import config.Respons.ResponseUtil;
import config.redis.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;

/**
 * Created by Zhang on 2018/8/26.
 *
 * @author kongling
 */
@Configuration
@Slf4j
public class SudaPlatformInterceptor implements HandlerInterceptor {
    @Autowired
    IAdminPermissionService adminPermissionService;

    @Autowired
    UserCacheUtil userCacheUtil;

    @Autowired
    RedisUtils redisUtils;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        boolean isNoInterceptor = false;
        String token = request.getHeader(AuthSign.token);
        String URI = request.getRequestURI();
        if (true) {
            return true;
        }
        if (StringUtils.isBlank(token)) {
            token = request.getParameter(AuthSign.token);
        }
        if (StringUtils.isBlank(token)) {
            ResponseUtil.out(response, ResponseUtil.getNoLoginResponseMap());
        } else {
            Integer systemRoleId = AuthSign.getSystemRoleId(token);
            if (systemRoleId == LogAdminAgentAppEnum.ADMIN_SYS.getSystem().intValue()) {
                isNoInterceptor = processAdmin(request, response, URI, token);
            } else if (systemRoleId == LogAdminAgentAppEnum.AGENT_SYS.getSystem().intValue()) {
                isNoInterceptor = processAgent(request, response, URI, token);
            }
        }
        return isNoInterceptor;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {

    }

    /**
     * 总管理后台 权限控制
     *
     * @param request
     * @param response
     * @param path
     * @param token
     * @return
     */
    private Boolean processAdmin(HttpServletRequest request, HttpServletResponse response, String path, String token) {
        Long id = AuthSign.getUserId(token);
        //获取存储sessionId
        String sessionId = userCacheUtil.getStoreAdminUser(id);
        if (sessionId == null) {
            ResponseUtil.out(response, ResponseUtil.getLoginOutTimeMap());
            return false;
        }
        if (!token.equalsIgnoreCase(sessionId)) {
            ResponseUtil.out(response, ResponseUtil.getLoginOutResponseMap());
            return false;
        }
        //权限验证
        //用户禁用启用状态
        String jsonUser = redisUtils.getStorageAdminUser(id);
        if (jsonUser != null) {
            AdminUser adminUser = JSONObject.toJavaObject(JSONObject.parseObject(jsonUser), AdminUser.class);
            if (adminUser.getIsDisable()) {
                ResponseUtil.out(response, ResponseUtil.getDisableResponseMap());
                return false;
            }
            Long roleId = adminUser.getRoleId();
            if (roleId != 1) {
                //判断系统内需要效验权限的接口
                Set<String> permissions = adminPermissionService.getOperationPermissions();
                if (permissions.contains(path)) {
                    if (!redisUtils.rolePermissionBooleanIs(roleId, path)) {
                        ResponseUtil.out(response, ResponseUtil.NoAuth());
                        return false;
                    }
                }
            }
        }
        userCacheUtil.storeAdminUserRefreshExpire(id);
        return true;
    }

    /**
     * 广告机管理用户权限控制
     *
     * @param request
     * @param response
     * @param uri
     * @param token
     * @return
     */
    private boolean processAgent(HttpServletRequest request, HttpServletResponse response, String uri, String token) {
        return true;
    }

    /**
     * 其他用户权限控制
     *
     * @param request
     * @param response
     * @param path
     * @param token
     * @return
     */
    private Boolean processApp(HttpServletRequest request, HttpServletResponse response, String path, String token) {
        Long id = AuthSign.getUserId(token);
        //获取存储sessionId
        String sessionId = userCacheUtil.getStoreAppStockUser(id);
        if (sessionId == null) {
            ResponseUtil.out(response, ResponseUtil.getLoginOutTimeMap());
            return false;
        }
        if (!token.equalsIgnoreCase(sessionId)) {
            ResponseUtil.out(response, ResponseUtil.getLoginOutResponseMap());
            return false;
        }
        //用户禁用启用状态
        String jsonStr = redisUtils.getStorageStockUser(id);
        if (jsonStr != null) {

        }
        userCacheUtil.storeAppStockUserRefreshExpire(id);
        return true;
    }


}
