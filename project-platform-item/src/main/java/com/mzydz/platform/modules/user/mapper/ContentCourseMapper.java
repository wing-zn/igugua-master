package com.mzydz.platform.modules.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mzydz.platform.modules.user.entity.PO.content.ContentCourse;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 系统攻略 Mapper 接口
 * </p>
 *
 * @author kongling
 * @since 2019-05-27
 */
public interface ContentCourseMapper extends BaseMapper<ContentCourse> {

    /**
     * 查询
     * @param contentCourse
     * @return
     */
    List<ContentCourse> findByCondition(ContentCourse contentCourse);

    /**
     * 浏览量 加一
     * @param id
     */
    void updateViewNumsAddOne(@Param("id") Long id);
}
