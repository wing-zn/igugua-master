package com.mzydz.platform.modules.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mzydz.platform.modules.user.entity.PO.content.ContentNotice;

import java.util.List;

/**
 * <p>
 * 系统公告 Mapper 接口
 * </p>
 *
 * @author kongling
 * @since 2019-05-08
 */
public interface ContentNoticeMapper extends BaseMapper<ContentNotice> {
    List<ContentNotice> findByCondition(ContentNotice record);

    /**
     * app端查询通告
     *
     * @return
     */
    List<ContentNotice> selectByContentNoticeList();

}
