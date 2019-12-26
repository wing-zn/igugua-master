package com.mzydz.platform.modules.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.mzydz.platform.modules.user.entity.PO.content.ContentNews;
import com.util.pageinfoutil.PageUtil;

/**
 * <p>
 * 公司新闻 服务类
 * </p>
 *
 * @author kongling
 * @since 2019-05-08
 */
public interface IContentNewsService extends IService<ContentNews> {
    /**
     * 查看分页数据
     *
     * @param news
     * @param pageUtil
     * @return
     */
    PageInfo<ContentNews> findByCondition(ContentNews news, PageUtil pageUtil);

    /**
     * 假删除新闻
     *
     * @param contentNews
     * @return
     */
    Integer deleteByPrimaryKey(ContentNews contentNews);

    /**
     * 浏览量加一
     * @param id
     */
    void updateViewNumsAddOne(Long id);
}
