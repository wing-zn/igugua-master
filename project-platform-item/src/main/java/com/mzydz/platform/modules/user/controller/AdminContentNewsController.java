package com.mzydz.platform.modules.user.controller;


import com.github.pagehelper.PageInfo;
import com.mzydz.platform.modules.user.entity.PO.content.ContentNews;
import com.mzydz.platform.modules.user.service.IContentNewsService;
import com.util.DealDateUtil;
import com.util.pageinfoutil.PageUtil;
import config.Respons.ResponseMsg;
import config.Respons.ResponseUtil;
import config.annotation.LogMenthodName;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 新闻管理控制类
 *
 * @author yanj
 * @date 2018-6-20 11:07
 */
@Controller
@Scope(value = "prototype")
@RequestMapping(value="admin/contentNews",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(description = "新闻管理", tags = "admin/contentNews")
public class AdminContentNewsController {

    @Autowired
   private IContentNewsService contentNewsService;

    @Autowired
    private HttpServletRequest request;


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

    /**
     * 新闻删除
     *
     * @param contentNews
     * @return
     */
    @RequestMapping(value = "del", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(notes = "删除文章 ：文章Id id；", value = "删除文章")
    @LogMenthodName(name = "删除新闻")
    public Map<String, Object> delNews(ContentNews contentNews) {
        Map<String, Object> resultMap = ResponseUtil.getNotNormalMap(ResponseMsg.EMPTY_PARAM);
        if (contentNews.getId() != null) {
            ContentNews contentNews1 = new ContentNews();
            contentNews1.setId(contentNews.getId());
            int data = contentNewsService.deleteByPrimaryKey(contentNews);
            if (data > 0) {
                return ResponseUtil.getSuccessMap();
            }
            return ResponseUtil.getNotNormalMap();
        }
        return resultMap;
    }

    /**
     * 新闻编辑
     *
     * @param contentNews
     * @return
     */
    @RequestMapping(value = "edit", method = RequestMethod.POST)
    @ResponseBody
    @LogMenthodName(name = "编辑新闻")
    @ApiOperation(notes = "编辑文章 ：文章标题 title; 文章内容 details; 缩略图 imgCover ；", value = "编辑文章")
    public Map<String, Object> editNews(ContentNews contentNews) {
        if (contentNews.getId() == null) {
            return ResponseUtil.getNotNormalMap(ResponseMsg.ID_IS_EMPTY);
        }
        boolean data = contentNewsService.updateById(contentNews);
        if (data) {
            return ResponseUtil.getSuccessMap();
        }
        return ResponseUtil.getNotNormalMap();
    }

    /**
     * 新闻添加
     *
     * @param contentNews
     * @return
     */
    @RequestMapping(value = "add", method = RequestMethod.POST)
    @ResponseBody
    @LogMenthodName(name = "添加新闻")
    @ApiOperation(notes = "添加文章 ：文章标题 title; 文章内容 details; 缩略图 imgCover;", value = "添加文章")
    public Map<String, Object> addNews(ContentNews contentNews) {
        contentNews.setIsShow(true);
        contentNews.setIsTop(false);
        contentNews.setIsDeleted(false);
        contentNews.setCreateTime(DealDateUtil.getNowDate());
        Boolean data = contentNewsService.save(contentNews);
        if (data) {
            return ResponseUtil.getSuccessMap();
        }
        return ResponseUtil.getNotNormalMap();
    }


    /**
     * 是否显示操作
     *
     * @param contentNews
     * @return
     */
    @RequestMapping(value = "isShow", method = RequestMethod.POST)
    @ResponseBody
    @LogMenthodName(name = "显示/隐藏")
    @ApiOperation(notes = "是否显示操作 ：文章Id id；显示状态 isShow：0：不显示；1：显示 ", value = "是否显示操作")
    public Map<String, Object> isShow(ContentNews contentNews) {
        if(contentNews.getId()==null || contentNews.getIsShow() ==null){
            return  ResponseUtil.getNotNormalMap(ResponseMsg.ID_IS_EMPTY);
        }
        ContentNews news = new ContentNews();
        news.setId(contentNews.getId());
        news.setIsShow(contentNews.getIsShow());
        Boolean data = contentNewsService.updateById(news);
        if (data) {
            return ResponseUtil.getSuccessMap();
        }
        return ResponseUtil.getNotNormalMap();
    }

    /**
     * 是否置顶操作
     *
     * @param contentNews
     * @return
     */
    @RequestMapping(value = "isTop", method = RequestMethod.POST)
    @ResponseBody
    @LogMenthodName(name = "置顶/取消")
    @ApiOperation(notes = "是否置顶操作 ：文章Id id；是否置顶 isTop：0：不置顶；1：置顶 ", value = "是否置顶操作")
    public Map<String, Object> isTop(ContentNews contentNews) {
        if(contentNews.getId()==null || contentNews.getIsTop()==null){
            return  ResponseUtil.getNotNormalMap(ResponseMsg.ID_IS_EMPTY);
        }
        ContentNews news = new ContentNews();
        news.setId(contentNews.getId());
        news.setIsTop(contentNews.getIsTop());
        Boolean data = contentNewsService.updateById(news);
        if (data) {
            return ResponseUtil.getSuccessMap();
        }
        return ResponseUtil.getNotNormalMap();
    }

}
