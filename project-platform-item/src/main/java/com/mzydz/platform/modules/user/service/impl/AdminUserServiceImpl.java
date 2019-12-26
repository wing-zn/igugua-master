package com.mzydz.platform.modules.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.mzydz.platform.modules.user.entity.PO.*;
import com.mzydz.platform.modules.user.entity.VO.AdminUserVO;
import com.mzydz.platform.modules.user.entity.VO.adminuser.AdminUserInfoVO;
import com.mzydz.platform.modules.user.entity.enums.AdminTypeEnum;
import com.mzydz.platform.modules.user.mapper.AdminUserMapper;
import com.mzydz.platform.modules.user.mapper.AdminUserRoleMapper;
import com.mzydz.platform.modules.user.service.*;
import com.util.DealDateUtil;
import com.util.StringUtils;
import com.util.pageinfoutil.PageUtil;
import config.Respons.ResponseMsg;
import config.advice.CommonException;
import config.redis.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 张龙飞
 * @since 2019-04-16
 */
@Service
@Slf4j
public class AdminUserServiceImpl extends ServiceImpl<AdminUserMapper, AdminUser> implements IAdminUserService {
    @Autowired
    private AdminUserMapper adminUserMapper;
    @Autowired
    private AdminUserRoleMapper adminUserRoleMapper;
    @Autowired
    private IAdminRoleService adminRoleService;
    @Autowired
    private IAdminRolePermissionService adminRolePermissionService;
    @Autowired
    private ICommonService commonService;
    @Autowired
    private IAdminDeptService adminDeptService;

    @Autowired
    private RedisUtils redisUtils;


    @Override
    public AdminUser selectByAccountLogin(String account, String password) {
        AdminUser vo = adminUserMapper.selectOne(new QueryWrapper<AdminUser>()
                .eq("account", account)
                .eq("password", password)
                .eq("is_deleted", 0));
        if (vo == null) {
            throw new CommonException("用户账号或密码错误");
        }
        if (vo.getIsDisable()) {
            throw new CommonException(ResponseMsg.DISABLE);
        }
        return vo;
    }

    @Override
    public PageInfo<AdminUserVO> selectByChoice(AdminUserVO adminUserVO, PageUtil pageUtil) {
        PageUtil.page(pageUtil);
        List<AdminUserVO> lists = adminUserMapper.selectByChoice(adminUserVO);
        return new PageInfo<>(lists);
    }

    @Override
    public void adminUserDisable(AdminUserVO adminUserVO) {
        AdminUser adminUser = new AdminUser();
        adminUser.setId(adminUserVO.getId());
        adminUser.setIsDisable(adminUserVO.getIsDisable());
        adminUserMapper.updateById(adminUser);
    }

    /**
     * 角色编辑
     *
     * @param adminUserVO
     */
    @Override
    @Transactional(rollbackFor = {})
    public void editUserRole(AdminUserVO adminUserVO) {
        //删除原有用户角色
        Map<String, Object> resultMap = Maps.newHashMap();
        resultMap.put("user_id", adminUserVO.getId());
        adminUserRoleMapper.deleteByMap(resultMap);
        adminUserRoleMapper.insert(new AdminUserRole(adminUserVO.getRoleId(), adminUserVO.getId()));
    }

    /**
     * 更新 用户登陆时间
     *
     * @param id
     */
    @Override
    @Async
    public void updateLoginTime(Long id) {
        AdminUser adminUser = new AdminUser();
        adminUser.setId(id);
        adminUser.setLastLoginTime(DealDateUtil.getNowDate());
        baseMapper.updateById(adminUser);
    }


    /**
     * 获取登录后用户信息
     *
     * @param id
     * @return
     */
    @Override
    public AdminUserInfoVO getAdminUserInfo(Long id) {
        AdminUserInfoVO infoVO = baseMapper.getAdminUserInfo(id);
        return infoVO;
    }

    /**
     * 添加管理员
     *
     * @param vo
     */
    @Transactional(rollbackFor = {})
    @Override
    public void addAdminUser(AdminUser vo) {
        //当前管理员信息
        AdminUser a = commonService.getAdminUser();
        vo.setCreateTime(DealDateUtil.getNowDate());
        vo.setAdminParentId(a.getId());
        //系统管理员添加管理员
        if (a.getAdminType() == AdminTypeEnum.STATUS_0.getCode().intValue()) {
            //添加系统管理员
            if (vo.getAdminType() == AdminTypeEnum.STATUS_0.getCode().intValue()) {
                //添加网点管理员
            } else {
                //创建支行部门信息
                if (StringUtils.isBlank(vo.getAdminDeptId())) {
                    throw new CommonException(ResponseMsg.EMPTY_PARAM);
                }
                AdminDept t = adminDeptService.getById(vo.getAdminDeptId());
                vo.setAdminDeptId(t.getDeptId());
                vo.setAdminDeptChildrenId(t.getDeptId());
            }
            //网点管理员添加管理员
        } else if (a.getAdminType() == AdminTypeEnum.STATUS_1.getCode().intValue()) {
            if (vo.getAdminType() == AdminTypeEnum.STATUS_0.getCode().intValue()) {
                throw new CommonException("当前账号不能添加系统管理员！");
            }
            vo.setAdminDeptId(a.getAdminDeptId());
            vo.setAdminType(AdminTypeEnum.STATUS_1.getCode());
        } else {
            log.error("管理员信息错误！" + a.toString());
            throw new CommonException("管理员信息错误！");
        }
        save(vo);
        //创建角色
        AdminRole role = AdminRole.builder().id(vo.getId()).roleName(vo.getUsername())
                .createTime(DealDateUtil.getNowDate()).roleValue(StringUtils.getUUID())
                .build();
        //创建 用户角色 记录
        AdminUserRole userRole = AdminUserRole.builder().roleId(vo.getId()).userId(vo.getId()).build();
        //数据保存
        adminRoleService.save(role);
        adminUserRoleMapper.insert(userRole);
    }

    /**
     * 管理员编辑
     *
     * @param vo
     */
    @Override
    public void editAdminUser(AdminUser vo) {
        vo.setAccount(null);
        vo.setPassword(null);
        updateById(vo);
    }

    /**
     * 删除管理员
     *
     * @param id
     */
    @Override
    public void delAdminUser(Long id) {
        remove(new QueryWrapper<AdminUser>()
                .eq("id", id)
                .ne("id", 1));
        adminRoleService.remove(new QueryWrapper<AdminRole>()
                .eq("id", id)
                .ne("id", 1));
        adminUserRoleMapper.delete(new QueryWrapper<AdminUserRole>()
                .eq("user_id", id)
                .ne("user_id", 1));
        adminRolePermissionService.remove(new QueryWrapper<AdminRolePermission>()
                .eq("role_id", id)
                .ne("role_id", 1));
        //删除角色权限信息
        redisUtils.delStorageRolePermission(id);
    }
}
