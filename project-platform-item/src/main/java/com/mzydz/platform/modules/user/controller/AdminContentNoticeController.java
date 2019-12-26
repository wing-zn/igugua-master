package com.mzydz.platform.modules.user.controller;


import com.github.pagehelper.PageInfo;
import com.mzydz.platform.modules.user.entity.PO.content.ContentNotice;
import com.mzydz.platform.modules.user.service.IContentNoticeService;
import com.util.pageinfoutil.PageUtil;
import config.Respons.ResponseMsg;
import config.Respons.ResponseUtil;
import config.annotation.LogMenthodName;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
 * 系统公告管理控制类
 *
 * @author yanj
 * @date 2018-6-20 11:07
 */
@Controller
@RequestMapping(value = "admin/contentNotice",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(description = "公告管理", tags = "admin/contentNotice")
public class AdminContentNoticeController {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private IContentNoticeService contentNoticeService;

    /**
     * 查看系统公告 列表
     */
    @RequestMapping(value = "noticeList", method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    @ApiOperation(notes = "系统公告查询 ：开始时间 startDate ；结束时间 endDate ;页数 page 每页数量 pageSize", value = "系统公告查询")
    public Map<String, Object> noticeList(PageUtil page, ContentNotice contentNotice, HttpServletRequest request,
                                          HttpServletResponse response) {
        PageInfo<ContentNotice> list = contentNoticeService.findByCondition(contentNotice, page);
        return ResponseUtil.getSuccessMap(list);
    }

    /**
     * 系统公告查看
     *
     * @param contentNotice
     * @return
     */
    @RequestMapping(value = "view", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(notes = "系统公告查看 ：公告Id id；", value = "系统公告查看")
    public Map<String, Object> view(ContentNotice contentNotice) {
        if (contentNotice.getId() == null) {
            return ResponseUtil.getNotNormalMap(ResponseMsg.EMPTY_PARAM);
        }
        ContentNotice contentNotice1 = contentNoticeService.findByPrimaryKey(contentNotice.getId());
        if (contentNotice1 != null) {
            return ResponseUtil.getSuccessMap(contentNotice1);
        }
        return ResponseUtil.getNotNormalMap();
    }



    /**
     * 系统公告添加
     *
     * @param contentNotice
     * @return
     */
    @RequestMapping(value = "add", method = RequestMethod.POST)
    @ResponseBody
    @LogMenthodName(name = "添加系统公告")
    @ApiOperation(notes = "添加系统公告 ：公告标题 title; 文章内容 details; 发表时间 createTime ；", value = "添加系统公告")
    public Map<String, Object> addNotice(ContentNotice contentNotice) {
        int data = contentNoticeService.insert(contentNotice, request);
        if (data > 0) {
            return ResponseUtil.getSuccessMap();
        }
        return ResponseUtil.getNotNormalMap();
    }

    /**
     * 系统公告编辑
     *
     * @param contentNotice
     * @return
     */
    @RequestMapping(value = "edit", method = RequestMethod.POST)
    @ResponseBody
    @LogMenthodName(name = "编辑系统公告")
    @ApiOperation(notes = "编辑系统公告 ：公告标题 title; 文章内容 details; 发表时间 createTime ；", value = "编辑系统公告")
    public Map<String, Object> editNotice(ContentNotice contentNotice) {
        int data = contentNoticeService.update(contentNotice, request);
        if (data > 0) {
            return ResponseUtil.getSuccessMap();
        }
        return ResponseUtil.getNotNormalMap();
    }

    /**
     * 系统公告删除
     *
     * @param contentNotice
     * @return
     */
    @RequestMapping(value = "del", method = RequestMethod.POST)
    @ResponseBody
    @LogMenthodName(name = "删除系统公告")
    @ApiOperation(notes = "删除系统公告 ：文章Id id；", value = "删除系统公告")
    public Map<String, Object> delNotice(ContentNotice contentNotice) {
        int data = contentNoticeService.deleteByPrimaryKey(contentNotice);
        if (data > 0) {
            return ResponseUtil.getSuccessMap();
        }
        return ResponseUtil.getNotNormalMap();
    }


}
