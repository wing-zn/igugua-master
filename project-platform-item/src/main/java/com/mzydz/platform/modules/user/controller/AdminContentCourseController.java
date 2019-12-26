package com.mzydz.platform.modules.user.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.mzydz.platform.modules.user.entity.PO.content.ContentCourse;
import com.mzydz.platform.modules.user.service.IContentCourseService;
import com.mzydz.platform.modules.user.service.IContentNoticeService;
import com.util.DealDateUtil;
import com.util.StringUtils;
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
 *
 * 教程管理
 *
 * @author kongling
 * @package com.suda.platform.controller.admin
 * @date 2019-05-27  12:04
 * @project suda_cloud
 */
@Controller
@RequestMapping(value = "admin/contentCourse",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(description = "教程管理", tags = "admin/contentCourse")
public class AdminContentCourseController {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private IContentNoticeService contentNoticeService;

    @Autowired
    private IContentCourseService contentCourseService;

    /**
     * 查看攻略管理 列表
     */
    @RequestMapping(value = "contentCourseList", method = {RequestMethod.GET,RequestMethod.POST})
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



    /**
     * 攻略管理 添加
     *
     * @param contentCourse
     * @return
     */
    @RequestMapping(value = "add", method = RequestMethod.POST)
    @ResponseBody
    @LogMenthodName(name = "添加攻略管理 ")
    @ApiOperation(notes = "添加攻略管理  ：攻略标题 title; 攻略内容 details; 发表时间 createTime ；", value = "添加攻略管理 ")
    public Map<String, Object> addNotice(ContentCourse contentCourse) {
        if(StringUtils.isBlank(contentCourse.getDetails(),contentCourse.getTitle(),
                contentCourse.getImgCover())){
            return  ResponseUtil.getNotNormalMap(ResponseMsg.ERROR_PARAM);
        }
        contentCourse.setCreateTime(DealDateUtil.getNowDate());
        contentCourseService.save(contentCourse);
        return ResponseUtil.getSuccessMap();
    }

    /**
     * 攻略管理编辑
     *
     * @param contentCourse
     * @return
     */
    @RequestMapping(value = "edit", method = RequestMethod.POST)
    @ResponseBody
    @LogMenthodName(name = "编辑攻略管理")
    @ApiOperation(notes = "编辑攻略管理 ：攻略标题 title; 攻略内容 details; 发表时间 createTime ；", value = "编辑攻略管理")
    public Map<String, Object> editNotice(ContentCourse contentCourse) {
        if(contentCourse.getId() == null){
            return  ResponseUtil.getNotNormalMap(ResponseMsg.ERROR_PARAM);
        }
        Boolean data = contentCourseService.updateById(contentCourse);
        if (data) {
            return ResponseUtil.getSuccessMap();
        }
        return ResponseUtil.getNotNormalMap();
    }

    /**
     * 攻略管理删除
     *
     * @param contentCourse
     * @return
     */
    @RequestMapping(value = "del", method = RequestMethod.POST)
    @ResponseBody
    @LogMenthodName(name = "删除攻略管理")
    @ApiOperation(notes = "删除攻略管理 ：文章Id id；", value = "删除攻略管理")
    public Map<String, Object> delNotice(ContentCourse contentCourse) {
        if(contentCourse.getId() == null){
            return  ResponseUtil.getNotNormalMap(ResponseMsg.ERROR_PARAM);
        }
        contentCourse.setIsDeleted(true);
        boolean data = contentCourseService.update(contentCourse,
                new QueryWrapper<ContentCourse>()
        .eq("id",contentCourse.getId()));
        if (data) {
            return ResponseUtil.getSuccessMap();
        }
        return ResponseUtil.getNotNormalMap();
    }


}
