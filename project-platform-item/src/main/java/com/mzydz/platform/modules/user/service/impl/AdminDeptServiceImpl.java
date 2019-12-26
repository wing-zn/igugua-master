package com.mzydz.platform.modules.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.mzydz.platform.modules.user.entity.PO.AdminDept;
import com.mzydz.platform.modules.user.entity.PO.AdminUser;
import com.mzydz.platform.modules.user.mapper.AdminDeptMapper;
import com.mzydz.platform.modules.user.service.IAdminDeptService;
import com.mzydz.platform.modules.user.service.IAdminUserService;
import com.util.pageinfoutil.PageUtil;
import config.advice.CommonException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 部门管理 服务实现类
 * </p>
 *
 * @author konglingyinxia
 * @since 2019-11-22
 */
@Service
public class AdminDeptServiceImpl extends ServiceImpl<AdminDeptMapper, AdminDept> implements IAdminDeptService {

    @Autowired
    @Lazy
    private IAdminUserService adminUserService;

    /**
     * 编辑部门（网点）
     *
     * @param adminDept
     */
    @Override
    public void updateDept(AdminDept adminDept) {
        //查询是否有重复的编码
        AdminDept a = getOne(new QueryWrapper<AdminDept>()
                .eq("dept_no", adminDept.getDeptNo())
                .ne("dept_id", adminDept.getDeptId()));
        if (a != null) {
            throw new CommonException("编码重复了！");
        }
        updateById(adminDept);
    }

    /**
     * 删除部门（网点）
     *
     * @param adminDept
     */
    @Override
    public void delDept(AdminDept adminDept) {
        List<Object> l = adminUserService.listObjs(new QueryWrapper<AdminUser>()
                .eq("admin_dept_id", adminDept.getDeptId()));
        if (!l.isEmpty()) {
            throw new CommonException("拥有所属管理员，暂不能删除！");
        }
        removeById(adminDept.getDeptId());
    }

    /**
     * 部门（网点）查看
     *
     * @param adminDeptId
     * @param pageUtil
     * @return
     */
    @Override
    public PageInfo<AdminDept> selectDepts(Long adminDeptId, PageUtil pageUtil) {
        PageUtil.page(pageUtil);
        List<AdminDept> adminDepts = baseMapper.selectList(new QueryWrapper<AdminDept>()
                .eq(adminDeptId != null, "dept_id", adminDeptId));
        return new PageInfo<>(adminDepts);
    }
}
