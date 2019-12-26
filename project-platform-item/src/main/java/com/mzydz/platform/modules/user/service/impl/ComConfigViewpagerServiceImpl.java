package com.mzydz.platform.modules.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.mzydz.platform.modules.user.entity.PO.ComConfigViewpager;
import com.mzydz.platform.modules.user.entity.VO.comconfig.ViewpagerVO;
import com.mzydz.platform.modules.user.mapper.ComConfigViewpagerMapper;
import com.mzydz.platform.modules.user.service.IComConfigViewpagerService;
import com.util.pageinfoutil.PageUtil;
import config.Respons.ResponseMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 轮播图配置 服务实现类
 * </p>
 *
 * @author kongling
 * @since 2019-04-20
 */
@Service
public class ComConfigViewpagerServiceImpl extends ServiceImpl<ComConfigViewpagerMapper, ComConfigViewpager> implements IComConfigViewpagerService {

    @Autowired
    private ComConfigViewpagerMapper comConfigViewpagerMapper;

    /**
     * 添加
     *
     * @param comViewpager
     * @return
     */
    @Override
    public int add(ComConfigViewpager comViewpager) {
        comViewpager.setCreateTime(new Date());
        int i = comConfigViewpagerMapper.insert(comViewpager);
        return i;
    }

    /**
     * 删除
     *
     * @param comViewpager
     * @return
     */
    @Override
    public int del(ComConfigViewpager comViewpager) {
        if (comViewpager.getId() == null) {
            throw new RuntimeException(ResponseMsg.MISS_PARAM);
        }
        return comConfigViewpagerMapper.deleteById(comViewpager.getId());
    }


    /**
     * 查询轮播图列表和单个
     *
     * @param comViewpager
     * @param page
     * @return
     */
    @Override
    public PageInfo<ComConfigViewpager> selectComViewpager(ComConfigViewpager comViewpager, PageUtil page) {
        PageUtil.page(page);
        List<ComConfigViewpager> list = comConfigViewpagerMapper.selectList(new QueryWrapper<ComConfigViewpager>()
                .eq(comViewpager.getId() != null, "id", comViewpager.getId())
                .eq(comViewpager.getType() != null, "type", comViewpager.getType())
                .eq(comViewpager.getAdminDeptId() != null, "admin_dept_id", comViewpager.getAdminDeptId())
                .eq(comViewpager.getIsShow() != null, "is_show", comViewpager.getIsShow())
                .or().eq("admin_dept_id", 0)
                .orderByDesc("sort"));
        return new PageInfo<ComConfigViewpager>(list);
    }

    /**
     * 轮播图移动端查看
     *
     * @param vo
     * @return
     */
    @Override
    public List<ViewpagerVO> getViewpagerList(ViewpagerVO vo) {
        List<ViewpagerVO> lists = comConfigViewpagerMapper.getViewpagerList();
        return lists;
    }
}
