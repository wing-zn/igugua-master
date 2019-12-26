package com.mzydz.platform.modules.user.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.mzydz.platform.modules.user.entity.PO.AdminUser;
import com.mzydz.platform.modules.user.entity.VO.AdminUserVO;
import com.mzydz.platform.modules.user.entity.VO.adminuser.AdminUserInfoVO;
import com.util.pageinfoutil.PageUtil;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author 张龙飞
 * @since 2019-04-16
 */
public interface IAdminUserService extends IService<AdminUser> {

    /**
     * 登录方法
     *
     * @param account
     * @param password
     * @return
     */
    AdminUser selectByAccountLogin(String account, String password);

    /**
     * 分页查询管理员列表
     *
     * @param adminUserVO
     * @param pageUtil
     * @return
     */
    PageInfo<AdminUserVO> selectByChoice(AdminUserVO adminUserVO, PageUtil pageUtil);

    /**
     * 禁用启用管理员
     *
     * @param adminUserVO
     */
    void adminUserDisable(AdminUserVO adminUserVO);

    /**
     * 角色编辑
     *
     * @param adminUserVO
     */
    void editUserRole(AdminUserVO adminUserVO);

    /**
     * 更新登陆时间
     *
     * @param id
     */
    void updateLoginTime(Long id);

    /**
     * 获取登录后用户 信息
     *
     * @param id
     * @return
     */
    AdminUserInfoVO getAdminUserInfo(Long id);

    /**
     * 实体添加管理员
     *
     * @param vo
     */
    void addAdminUser(AdminUser vo);

    /**
     * 编辑管理员
     *
     * @param vo
     */
    void editAdminUser(AdminUser vo);

    /**
     * 删除 管理员
     *
     * @param id
     */
    void delAdminUser(Long id);
}
