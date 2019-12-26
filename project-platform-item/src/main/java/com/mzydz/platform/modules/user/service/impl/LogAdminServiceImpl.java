package com.mzydz.platform.modules.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mzydz.platform.modules.user.entity.PO.log.LogAdmin;
import com.mzydz.platform.modules.user.mapper.LogAdminMapper;
import com.mzydz.platform.modules.user.service.ILogAdminService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统日志 服务实现类
 * </p>
 *
 * @author kongling
 * @since 2019-09-12
 */
@Service
public class LogAdminServiceImpl extends ServiceImpl<LogAdminMapper, LogAdmin> implements ILogAdminService {

}
