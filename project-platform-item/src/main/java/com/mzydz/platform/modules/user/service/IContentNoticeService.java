package com.mzydz.platform.modules.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.mzydz.platform.modules.user.entity.PO.content.ContentNotice;
import com.util.pageinfoutil.PageUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 系统公告 服务类
 * </p>
 *
 * @author kongling
 * @since 2019-05-08
 */
public interface IContentNoticeService extends IService<ContentNotice> {
    /**
     * 添加文章
     *
     * @param contentNotice
     * @param request
     * @return
     */
    int insert(ContentNotice contentNotice, HttpServletRequest request);

    /**
     * 更新文章
     *
     * @param contentNotice
     * @param request
     * @return
     */
    int update(ContentNotice contentNotice, HttpServletRequest request);

    /**
     * 文章列表
     *
     * @param contentNotice
     * @param page
     * @return
     */
    PageInfo<ContentNotice> findByCondition(ContentNotice contentNotice, PageUtil page);

    /**
     * 通过ID查询实体
     * @param id
     * @return
     */
    ContentNotice findByPrimaryKey(Long id);

    /**
     * 删除文章
     * @param contentNotice
     * @return
     */
    int deleteByPrimaryKey(ContentNotice contentNotice);

    /**
     * 更新操作，针对置顶，是否显示，逻辑删除
     * @param contentNotice
     * @return
     */
    int updateOperation(ContentNotice contentNotice);

    /**
     * app端系统公告
     *
     * @param pageUtil
     * @return
     */
    PageInfo<?> selectByContentNoticeList(PageUtil pageUtil);

    /**
     * 单个列表查询
     *
     * @param id
     * @return
     */
    ContentNotice selectById(Long id);
}
