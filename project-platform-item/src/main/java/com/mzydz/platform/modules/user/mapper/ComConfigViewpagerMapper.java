package com.mzydz.platform.modules.user.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mzydz.platform.modules.user.entity.PO.ComConfigViewpager;
import com.mzydz.platform.modules.user.entity.VO.comconfig.ViewpagerVO;

import java.util.List;

/**
 * <p>
 * 轮播图配置 Mapper 接口
 * </p>
 *
 * @author
 * @since 2019-04-20
 */
public interface ComConfigViewpagerMapper extends BaseMapper<ComConfigViewpager> {

    /**
     * 轮播图移动端查看
     * @return
     */
    List<ViewpagerVO> getViewpagerList();
}
