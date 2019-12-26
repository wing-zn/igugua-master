package com.mzydz.platform.modules.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mzydz.platform.modules.sysconfig.entity.PO.ComConfigAgreement;
import com.mzydz.platform.modules.user.mapper.ComConfigAgreementMapper;
import com.mzydz.platform.modules.user.service.IComConfigAgreementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 系统协议 服务实现类
 * </p>
 *
 * @author 薛鹏飞
 * @since 2019-04-20
 */
@Service
public class ComConfigAgreementServiceImpl extends ServiceImpl<ComConfigAgreementMapper, ComConfigAgreement> implements IComConfigAgreementService {

    @Autowired
    private ComConfigAgreementMapper comConfigAgreementMapper;

    /**
     * 获取多个
     * @param comConfigAgreement
     * @return
     */
    @Override
    public List <ComConfigAgreement> getAppUserAgreementAndLegalNotices(ComConfigAgreement comConfigAgreement) {


        return comConfigAgreementMapper.getAppUserAgreementAndLegalNotices(comConfigAgreement);
    }

    /**
     * 获取多个
     * @return
     */
    @Override
    public List<ComConfigAgreement> select() {

        return comConfigAgreementMapper.selectList(new QueryWrapper<ComConfigAgreement>());
    }

    /**
     * 获取单个
     * @param comConfigAgreement
     * @return
     */
    @Override
    public ComConfigAgreement get(ComConfigAgreement comConfigAgreement) {
        return comConfigAgreementMapper.selectOne(new QueryWrapper<ComConfigAgreement>()
        .eq(comConfigAgreement.getType()!=null, "type",comConfigAgreement.getType()));
    }

    /**
     * 添加
     * @param comConfigAgreement
     * @return
     */
    @Override
    public int insert(ComConfigAgreement comConfigAgreement) {
        comConfigAgreement.setCreateTime(new Date());
        return comConfigAgreementMapper.insert(comConfigAgreement);
    }

    /**
     * 修改
     * @param comConfigAgreement
     * @return
     */
    @Override
    public int updates(ComConfigAgreement comConfigAgreement) {
        return comConfigAgreementMapper.updateByPrimaryKeySelective(comConfigAgreement);
    }


}
