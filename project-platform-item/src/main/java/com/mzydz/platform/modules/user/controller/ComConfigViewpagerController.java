package com.mzydz.platform.modules.user.controller;


import com.github.pagehelper.PageInfo;
import com.mzydz.platform.modules.user.entity.PO.AdminUser;
import com.mzydz.platform.modules.user.entity.PO.ComConfigViewpager;
import com.mzydz.platform.modules.user.service.IComConfigViewpagerService;
import com.mzydz.platform.modules.user.service.ICommonService;
import com.util.DealDateUtil;
import com.util.pageinfoutil.PageUtil;
import config.Respons.ResponseMsg;
import config.Respons.ResponseUtil;
import config.annotation.LogMenthodName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * <p>
 * 轮播图配置 前端控制器
 * </p>
 *
 * @author kongling
 * @since 2019-04-20
 */
@Controller
@RequestMapping(value = "admin/comConfigViewpager", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ComConfigViewpagerController {

    @Autowired
    private IComConfigViewpagerService comConfigViewpagerService;

    @Autowired
    ICommonService commonService;

    /**
     * 添加
     *
     * @param comViewpager
     * @return type:图片类型,1.app 2.pc; sort:排序字段; imgUrl:图片
     */
    @LogMenthodName(name = "轮播图添加")
    @RequestMapping(value = "add", method = RequestMethod.POST)
    @ResponseBody
    public Map add(ComConfigViewpager comViewpager) {
        try {
            AdminUser a = commonService.getAdminUser();
            comViewpager.setAddUserId(a.getId());
            comViewpager.setAdminDeptId(a.getAdminDeptId());
            comViewpager.setCreateTime(DealDateUtil.getNowDate());
            comConfigViewpagerService.add(comViewpager);
            return ResponseUtil.getSuccessMap();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtil.getNotNormalMap();
        }

    }

    /**
     * 删除
     *
     * @param comViewpager
     * @return 轮播图Id id
     */
    @RequestMapping(value = "del", method = RequestMethod.POST)
    @ResponseBody
    @LogMenthodName(name = "轮播图删除")
    public Map del(ComConfigViewpager comViewpager) {

        int del = comConfigViewpagerService.del(comViewpager);
        if (del > 0) {
            return ResponseUtil.getSuccessMap();
        }
        return ResponseUtil.getNotNormalMap();
    }

    /**
     * 查看轮播图 列表和单个
     */
    @RequestMapping(value = "viewPagerList", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> viewPagerList(PageUtil page, ComConfigViewpager comViewpager, HttpServletRequest request,
                                             HttpServletResponse response) {
        AdminUser a = commonService.getAdminUser();
        comViewpager.setAdminDeptId(a.getAdminDeptId());
        PageInfo<ComConfigViewpager> list = comConfigViewpagerService.selectComViewpager(comViewpager, page);
        return ResponseUtil.getSuccessMap(list);
    }

    /**
     * 轮播图编辑
     */
    @RequestMapping(value = "editViewPager", method = RequestMethod.POST)
    @ResponseBody
    @LogMenthodName(name = "轮播图编辑")
    public Map<String, Object> editViewPager(ComConfigViewpager comViewpager) {
        if (comViewpager.getId() == null || comViewpager.getSort() == null) {
            return ResponseUtil.getNotNormalMap(ResponseMsg.ERROR_PARAM);
        }
        comConfigViewpagerService.updateById(comViewpager);
        return ResponseUtil.getSuccessMap();
    }
}
