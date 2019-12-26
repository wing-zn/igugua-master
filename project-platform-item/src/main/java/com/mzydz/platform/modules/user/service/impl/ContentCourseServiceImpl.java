package com.mzydz.platform.modules.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.mzydz.platform.modules.user.entity.PO.content.ContentCourse;
import com.mzydz.platform.modules.user.mapper.ContentCourseMapper;
import com.mzydz.platform.modules.user.service.IContentCourseService;
import com.util.pageinfoutil.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 系统攻略 服务实现类
 * </p>
 *
 * @author kongling
 * @since 2019-05-27
 */
@Service
public class ContentCourseServiceImpl extends ServiceImpl<ContentCourseMapper, ContentCourse> implements IContentCourseService {

    @Autowired
    private ContentCourseMapper contentCourseMapper;
    @Override
    public PageInfo<ContentCourse> findByCondition(ContentCourse contentCourse, PageUtil page) {
        PageUtil.page(page);
        List<ContentCourse> list = contentCourseMapper.findByCondition(contentCourse);
        return new PageInfo<ContentCourse>(list);
    }

    /**
     * 浏览量加一
     * @param id
     */
    @Override
    public void updateViewNumsAddOne(Long id) {
        contentCourseMapper.updateViewNumsAddOne(id);
    }
}
