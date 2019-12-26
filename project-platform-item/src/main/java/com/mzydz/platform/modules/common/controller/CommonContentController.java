package com.mzydz.platform.modules.common.controller;


import com.github.pagehelper.PageInfo;
import com.mzydz.platform.modules.sysconfig.entity.PO.ComConfigAgreement;
import com.mzydz.platform.modules.user.entity.PO.content.ContentCourse;
import com.mzydz.platform.modules.user.entity.PO.content.ContentNews;
import com.mzydz.platform.modules.user.entity.PO.content.ContentNotice;
import com.mzydz.platform.modules.user.service.IComConfigAgreementService;
import com.mzydz.platform.modules.user.service.IContentCourseService;
import com.mzydz.platform.modules.user.service.IContentNewsService;
import com.mzydz.platform.modules.user.service.IContentNoticeService;
import com.util.pageinfoutil.PageUtil;
import config.Respons.ResponseMsg;
import config.Respons.ResponseUtil;
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
 * 内容查看 管理
 *
 * @author yanj
 * @date 2018-6-20 11:07
 */
@Controller
@RequestMapping(value = "common/content", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class CommonContentController {

    @Autowired
    private IContentNewsService contentNewsService;


    @Autowired
    private IContentCourseService contentCourseService;

    @Autowired
    private IComConfigAgreementService comConfigAgreementService;


    //===============================新闻资讯查看=======================================================

    /**
     * 单个新闻查看
     *
     * @return
     * @ResponseBody * @param contentNews
     */
    @RequestMapping(value = "view", method = RequestMethod.GET)
    @ApiOperation(notes = "id:新闻列表id", value = "文章查询")
    @ResponseBody
    public Map<String, Object> view(ContentNews contentNews) {
        if (contentNews.getId() == null) {
            return ResponseUtil.getNotNormalMap(ResponseMsg.EMPTY_PARAM);
        }
        ContentNews news = contentNewsService.getById(contentNews.getId());
        if (news != null) {
            return ResponseUtil.getSuccessMap(news);
        }
        return ResponseUtil.getNotNormalMap();
    }

    /**
     * 分页查看数据
     */
    @RequestMapping(value = "newsList", method = RequestMethod.GET)
    @ApiOperation(notes = "文章查询 ：开始时间 startDate ；结束时间 endDate ;页数 page 每页数量 pageSize", value = "文章查询")
    @ResponseBody
    public Map<String, Object> newsList(ContentNews contentNews, PageUtil pageUtil) {
        // 时间过滤参数
        PageInfo<ContentNews> list = contentNewsService.findByCondition(contentNews, pageUtil);
        return ResponseUtil.getSuccessMap(list);
    }

    // ================================教程攻略查看=================================================

    /**
     * 查看攻略管理 列表
     */
    @RequestMapping(value = "contentCourseList", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    @ApiOperation(notes = "查看攻略管理 列表 ：开始时间 startDate ；结束时间 endDate ;页数 page 每页数量 pageSize", value = "查看攻略管理 列表")
    public Map<String, Object> noticeList(PageUtil page, ContentCourse contentCourse, HttpServletRequest request,
                                          HttpServletResponse response) {
        PageInfo<ContentCourse> list = contentCourseService.findByCondition(contentCourse, page);
        return ResponseUtil.getSuccessMap(list);
    }

    /**
     * 攻略管理 查看单个
     *
     * @param contentCourse
     * @return
     */
    @RequestMapping(value = "contentCourse", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(notes = "攻略管理 查看 ：公告Id id；", value = "系统公告查看")
    public Map<String, Object> contentCourse(ContentCourse contentCourse) {
        if (contentCourse.getId() == null) {
            return ResponseUtil.getNotNormalMap(ResponseMsg.EMPTY_PARAM);
        }
        ContentCourse contentCourse1 = contentCourseService.getById(contentCourse.getId());
        if (contentCourse1 != null) {
            return ResponseUtil.getSuccessMap(contentCourse1);
        }
        return ResponseUtil.getNotNormalMap();
    }


    // ====================================公告查看 ========================

    @Autowired
    private IContentNoticeService contentNoticeService;

    /**
     * 查看系统公告 列表
     */
    @RequestMapping(value = "noticeList", method = {RequestMethod.GET, RequestMethod.POST})
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
    @RequestMapping(value = "noticeView", method = RequestMethod.GET)
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


    /*************************************系统协议****************************/
    @RequestMapping(value = "getAgreement", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(notes = "获取系统协议 ", value = "系统公告查看")
    public Map<String, Object> getAgreement(ComConfigAgreement agreement) {
        if(agreement.getType() == null){
            return ResponseUtil.getNotNormalMap(ResponseMsg.EMPTY_PARAM);
        }
        agreement = comConfigAgreementService.get(agreement);
        return ResponseUtil.getSuccessMap(agreement);
    }



}
