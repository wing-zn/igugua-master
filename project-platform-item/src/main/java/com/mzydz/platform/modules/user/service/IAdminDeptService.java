package com.mzydz.platform.modules.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.mzydz.platform.modules.user.entity.PO.AdminDept;
import com.util.pageinfoutil.PageUtil;

/**
 * <p>
 * 部门管理 服务类
 * </p>
 *
 * @author konglingyinxia
 * @since 2019-11-22
 */
public interface IAdminDeptService extends IService<AdminDept> {

    /**
     * 编辑部门（网点）
     *
     * @param adminDept
     */
    void updateDept(AdminDept adminDept);

    /**
     * 删除部门（网点）
     *
     * @param adminDept
     */
    void delDept(AdminDept adminDept);

    /**
     * 部门查看（网点）
     *
     * @param adminDeptId
     * @param pageUtil
     * @return
     */
    PageInfo<AdminDept> selectDepts(Long adminDeptId, PageUtil pageUtil);
}
