package com.mzydz.platform.modules.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mzydz.platform.modules.user.entity.PO.content.ContentNews;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 公司新闻 Mapper 接口
 * </p>
 *
 * @author kongling
 * @since 2019-05-08
 */
public interface ContentNewsMapper extends BaseMapper<ContentNews> {
    /**
     * 新闻列表
     *
     * @param news
     * @return
     */
    List<ContentNews> findByCondition(@Param("news") ContentNews news);


    void updateViewNumsAddOne(@Param("id") Long id);
}
