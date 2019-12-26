package com.mzydz.platform.modules.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.mzydz.platform.modules.user.entity.PO.content.ContentCourse;
import com.util.pageinfoutil.PageUtil;

/**
 * <p>
 * 系统公告 服务类
 * </p>
 *
 * @author kongling
 * @since 2019-05-27
 */
public interface IContentCourseService extends IService<ContentCourse> {


    PageInfo<ContentCourse> findByCondition(ContentCourse contentCourse, PageUtil page);

    /**
     * 浏览量加一
     * @param id
     */
    void updateViewNumsAddOne(Long id);
}
