package com.mzydz.platform.modules.user.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.mzydz.platform.modules.user.entity.PO.ComConfigViewpager;
import com.mzydz.platform.modules.user.entity.VO.comconfig.ViewpagerVO;
import com.util.pageinfoutil.PageUtil;

import java.util.List;

/**
 * <p>
 * 轮播图配置 服务类
 * </p>
 *
 * @author kongling
 * @since 2019-04-20
 */
public interface IComConfigViewpagerService extends IService<ComConfigViewpager> {

    /**
     * 添加
     *
     * @param comViewpager
     * @return
     */
    public int add(ComConfigViewpager comViewpager);
    /**
     * 删除
     *
     * @param comViewpager
     * @return
     */
    public int del(ComConfigViewpager comViewpager);
    /**
     * 查询轮播图列表和单个
     *
     * @param comViewpager
     * @param page
     * @return
     */
    public PageInfo<ComConfigViewpager> selectComViewpager(ComConfigViewpager comViewpager, PageUtil page);

    /**
     * 轮播图移动端查看
     * @param vo
     * @return
     */
    List<ViewpagerVO> getViewpagerList(ViewpagerVO vo);
}
