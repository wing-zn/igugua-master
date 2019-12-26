package com.mzydz.platform.modules.user.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.mzydz.platform.modules.user.entity.PO.AdminDept;
import com.mzydz.platform.modules.user.entity.PO.AdminUser;
import com.mzydz.platform.modules.user.service.IAdminDeptService;
import com.mzydz.platform.modules.user.service.ICommonService;
import com.util.DealDateUtil;
import com.util.StringUtils;
import com.util.pageinfoutil.PageUtil;
import config.Respons.ResponseMsg;
import config.Respons.ResponseUtil;
import config.advice.CommonException;
import config.annotation.LogMenthodName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 部门管理（网点管理）
 *
 * @author kongling
 * @package com.mzydz.platform.modules.user.controller
 * @date 2019-11-22  16:18
 * @project hall-marketing-cloud
 */
@RestController
@RequestMapping(value = "admin/dept", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class AdminDeptController {
    @Autowired
    private IAdminDeptService adminDeptService;
    @Autowired
    private ICommonService commonService;

    /**
     * 部门添加
     */
    @RequestMapping(value = "/addDept", method = RequestMethod.POST)
    @ResponseBody
    @LogMenthodName(name = "部门(网点)添加")
    public Map<String, Object> addRole(AdminDept adminDept) {
        if (StringUtils.isBlank(adminDept.getName()) || adminDept.getDeptNo() == null) {
            throw new CommonException(ResponseMsg.ERROR_PARAM);
        }
        AdminDept d = adminDeptService.getOne(new QueryWrapper<AdminDept>().eq("dept_no", adminDept.getDeptNo()));
        if (d != null) {
            throw new CommonException("该网点编码已经存在");
        }
        adminDept.setCreateTime(DealDateUtil.getNowDate());
        adminDeptService.save(adminDept);
        return ResponseUtil.getSuccessMap();
    }

    /**
     * 部门编辑
     */
    @RequestMapping(value = "/updateDept", method = RequestMethod.POST)
    @ResponseBody
    @LogMenthodName(name = "编辑部门(网点)")
    public Map<String, Object> updateDept(AdminDept adminDept) {
        if (adminDept.getDeptId() == null || StringUtils.isBlank(adminDept.getName(), adminDept.getDeptNo())) {
            throw new CommonException(ResponseMsg.ERROR_PARAM);
        }
        adminDeptService.updateDept(adminDept);
        return ResponseUtil.getSuccessMap();
    }

    /**
     * 部门删除
     */
    @RequestMapping(value = "/delDept", method = RequestMethod.POST)
    @ResponseBody
    @LogMenthodName(name = "删除部门(网点)")
    public Map<String, Object> delDept(AdminDept adminDept) {
        if (adminDept.getDeptId() == null) {
            throw new CommonException(ResponseMsg.ID_IS_EMPTY);
        }
        adminDeptService.delDept(adminDept);
        return ResponseUtil.getSuccessMap();
    }

    /**
     * 部门查看（网点）
     */
    @RequestMapping(value = "/getDepts")
    @ResponseBody
    public Map<String, Object> getDepts(PageUtil pageUtil) {
        AdminUser a = commonService.getAdminUser();
        PageInfo<AdminDept> lists = adminDeptService.selectDepts(a.getAdminDeptId(), pageUtil);
        return ResponseUtil.getSuccessMap(lists);
    }
}
