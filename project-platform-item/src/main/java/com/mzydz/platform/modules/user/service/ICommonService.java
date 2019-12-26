package com.mzydz.platform.modules.user.service;

import com.mzydz.platform.modules.user.entity.PO.AdminDept;
import com.mzydz.platform.modules.user.entity.PO.AdminUser;

/**
 * 公共业务层
 *
 * @author kongling
 * @package com.mzydz.platform.service
 * @date 2019-08-27  15:37
 * @project ad-publish-cloud
 */
public interface ICommonService {
    /**
     * 检测短验证码正确性
     *
     * @param account
     * @param code
     */
    void checkTelToken(String account, String code);

    /**
     * 检测图片验证码正确性
     *
     * @param imgToken
     * @param code
     */
    void checkImgCodeToken(String imgToken, String code);


    /**
     * 获取管理员用户信息
     */
    public AdminUser getAdminUser();


    /**
     * 根据网点编码获取网点
     *
     * @param deptNo
     * @return
     */
    AdminDept getAdminDept(String deptNo);
}
