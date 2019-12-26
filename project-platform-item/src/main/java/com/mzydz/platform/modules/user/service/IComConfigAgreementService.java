package com.mzydz.platform.modules.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mzydz.platform.modules.sysconfig.entity.PO.ComConfigAgreement;

import java.util.List;

/**
 * <p>
 * 系统协议 服务类
 * </p>
 *
 * @author 薛鹏飞
 * @since 2019-04-20
 */
public interface IComConfigAgreementService extends IService<ComConfigAgreement> {

    /**
     * 获取协议
     * @param comConfigAgreement
     * @return
     */
    List <ComConfigAgreement> getAppUserAgreementAndLegalNotices(ComConfigAgreement comConfigAgreement);

    /**
     * 获取
     * @return
     */
    List<ComConfigAgreement> select();

    /**
     * 获取单个
     * @param comConfigAgreement
     * @return
     */
    ComConfigAgreement get(ComConfigAgreement comConfigAgreement);

    /**
     * 添加
     * @param comConfigAgreement
     * @return
     */
    int insert(ComConfigAgreement comConfigAgreement);

    /**
     * 修改
     * @param comConfigAgreement
     * @return
     */
    int updates(ComConfigAgreement comConfigAgreement);


}
