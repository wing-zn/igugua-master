package com.mzydz.platform.modules.user.service.impl;


import com.mzydz.platform.modules.user.entity.VO.homeCount.HomeCountVO;
import com.mzydz.platform.modules.user.mapper.AdminCountMapper;
import com.mzydz.platform.modules.user.service.IAdminCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author kongling
 * @package com.suda.platform.service.impl
 * @date 2019-06-20  16:18
 * @project suda_cloud
 */
@Service
public class AdminCountServiceImpl implements IAdminCountService {
    @Autowired
    private AdminCountMapper adminCountMapper;

    @Override
    public HomeCountVO getHomePageCountInfo() {
        HomeCountVO vo = adminCountMapper.getHomePageCountInfo();
        return vo;
    }
}
