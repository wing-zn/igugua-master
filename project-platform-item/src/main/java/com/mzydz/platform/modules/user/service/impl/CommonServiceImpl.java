package com.mzydz.platform.modules.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mzydz.platform.modules.user.entity.PO.AdminDept;
import com.mzydz.platform.modules.user.entity.PO.AdminUser;
import com.mzydz.platform.modules.user.entity.enums.AdminTypeEnum;
import com.mzydz.platform.modules.user.service.IAdminDeptService;
import com.mzydz.platform.modules.user.service.ICommonService;
import com.util.StringUtils;
import com.util.auth.AuthSign;
import config.advice.CommonException;
import config.redis.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * @author kongling
 * @package com.mzydz.platform.modules.user.service.impl
 * @date 2019-08-27  15:37
 * @project ad-publish-cloud
 */
@Service
public class CommonServiceImpl implements ICommonService {

    @Autowired
    RedisUtils redisUtils;
    @Autowired
    private IAdminDeptService adminDeptService;

    /**
     * 检测短验证码正确性
     *
     * @param account
     * @param code
     */
    @Override
    public void checkTelToken(String account, String code) {
        // 数据库中查出帐号验证码
        Object saveCode = redisUtils.getSmsRedisMessage(account, code);
        if (null == saveCode) {
            throw new CommonException("请先获取验证码");
        }
        if (!saveCode.toString().equalsIgnoreCase(code)) {
            throw new CommonException("验证码错误");
        }
    }

    /**
     * 检测图片验证码正确性
     *
     * @param imgToken
     * @param code
     */
    @Override
    public void checkImgCodeToken(String imgToken, String code) {
        // 数据库中查出帐号验证码
        Object saveCode = redisUtils.getImgRedisMessage(imgToken, code);

        if (null == saveCode) {
            throw new CommonException("请先获取图片验证码");
        }
        if (!saveCode.toString().equalsIgnoreCase(code)) {
            throw new CommonException("图片验证码错误");
        }
    }

    @Autowired
    HttpServletRequest request;

    /**
     * 获取用户登陆后的信息
     *
     * @return
     */
    @Override
    public AdminUser getAdminUser() {
        String token = request.getHeader(AuthSign.token);
        if (StringUtils.isBlank(token)) {
            token = request.getParameter(AuthSign.token);
        }
        AdminUser adminUser = AuthSign.getUserObject(token, AdminUser.class);
        if (adminUser == null) {
            return AdminUser.builder().build();
            //throw new CommonException("token 信息错误");
        }
        if (adminUser.getAdminType() == AdminTypeEnum.STATUS_0.getCode().intValue()) {
            adminUser.setAdminDeptId(null);
            adminUser.setAdminDeptChildrenId(null);
        }
        return adminUser;
    }

    /**
     * 根据编号获取网点 id
     *
     * @param deptNo
     * @return
     */
    @Override
    public AdminDept getAdminDept(String deptNo) {
        AdminDept d = adminDeptService.getOne(new QueryWrapper<AdminDept>()
                .eq("dept_no", deptNo));
        if (d == null) {
            throw new CommonException("网点编码错误！");
        }
        return d;
    }


}
