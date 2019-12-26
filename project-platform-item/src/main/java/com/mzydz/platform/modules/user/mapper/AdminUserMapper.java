package com.mzydz.platform.modules.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mzydz.platform.modules.user.entity.PO.AdminUser;
import com.mzydz.platform.modules.user.entity.VO.AdminUserVO;
import com.mzydz.platform.modules.user.entity.VO.adminuser.AdminUserInfoVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author 张龙飞
 * @since 2019-04-16
 */
public interface AdminUserMapper extends BaseMapper<AdminUser> {

    /**
     * 管理员查询
     *
     * @param adminUserVO
     * @return
     */
    List<AdminUserVO> selectByChoice(AdminUserVO adminUserVO);

    /**
     * 获取登录后用户信息
     *
     * @param id
     * @return
     */
    AdminUserInfoVO getAdminUserInfo(@Param("id") Long id);
}
