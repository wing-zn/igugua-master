package com.mzydz.platform.modules.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mzydz.platform.modules.sysconfig.entity.PO.ComConfigAgreement;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 系统协议 Mapper 接口
 * </p>
 *
 * @author 薛鹏飞
 * @since 2019-04-20
 */
public interface ComConfigAgreementMapper extends BaseMapper<ComConfigAgreement> {

    List<ComConfigAgreement> getAppUserAgreementAndLegalNotices(@Param("comConfigAgreement") ComConfigAgreement comConfigAgreement);

    int updateByPrimaryKeySelective(ComConfigAgreement comConfigAgreement);
}
