package com.mzydz.platform.modules.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Strings;
import com.mzydz.platform.modules.user.entity.PO.content.ContentNotice;
import com.mzydz.platform.modules.user.mapper.ContentNoticeMapper;
import com.mzydz.platform.modules.user.service.IContentNoticeService;
import com.util.DateUtil;
import com.util.pageinfoutil.PageUtil;
import config.Respons.ResponseMsg;
import config.advice.CommonException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 系统公告 服务实现类
 * </p>
 *
 * @author kongling
 * @since 2019-05-08
 */
@Service
public class ContentNoticeServiceImpl extends ServiceImpl<ContentNoticeMapper, ContentNotice> implements IContentNoticeService {

    @Autowired
    private ContentNoticeMapper contentNoticeMapper;

    /**
     * 添加系统公告
     *
     * @param contentNotice
     * @param request
     * @return
     */
    @Override
    public int insert(ContentNotice contentNotice, HttpServletRequest request) {
        if (Strings.isNullOrEmpty(contentNotice.getTitle()) || Strings.isNullOrEmpty(contentNotice.getDetails())) {
            throw new CommonException(ResponseMsg.EMPTY_PARAM);
        }
        if (contentNotice.getCreateTime() == null) {
            contentNotice.setCreateTime(DateUtil.getCurrentDateTime());
        }
        contentNotice.setIsDeleted(false);
        int result = contentNoticeMapper.insert(contentNotice);
        return result;
    }

    /**
     * 更新系统公告
     *
     * @param contentNotice
     * @param request
     * @return
     */
    @Override
    public int update(ContentNotice contentNotice, HttpServletRequest request) {
        if (contentNotice.getId() == null) {
             throw new CommonException(ResponseMsg.MISS_PARAM);
        }
        return contentNoticeMapper.updateById(contentNotice);
    }

    /**
     * 系统公告列表
     *
     * @param contentNotice
     * @param page
     * @return
     */
    @Override
    public PageInfo<ContentNotice> findByCondition(ContentNotice contentNotice, PageUtil page) {
        PageUtil.page(page);
        List<ContentNotice> list = contentNoticeMapper.findByCondition(contentNotice);
        return new PageInfo<ContentNotice>(list);
    }

    /**
     * 通过ID查询实体
     *
     * @param id
     * @return
     */
    @Override
    public ContentNotice findByPrimaryKey(Long id) {
        return contentNoticeMapper.selectById(id);
    }

    /**
     * 删除系统公告
     *
     * @param contentNotice
     * @return
     */
    @Override
    public int deleteByPrimaryKey(ContentNotice contentNotice) {
        if (contentNotice.getId() == null) {
            throw new CommonException(ResponseMsg.MISS_PARAM);
        }
        contentNotice.setIsDeleted(true);
        return contentNoticeMapper.updateById(contentNotice);
    }

    @Override
    public int updateOperation(ContentNotice contentNotice) {
        if (contentNotice.getId() == null) {
            throw new CommonException(ResponseMsg.MISS_PARAM);
        }
        return contentNoticeMapper.updateById(contentNotice);
    }


    @Override
    public PageInfo<?> selectByContentNoticeList(PageUtil pageUtil) {
        PageUtil.page(pageUtil);
        List<ContentNotice> list = contentNoticeMapper.selectByContentNoticeList();
        return new PageInfo<>(list);
    }

    /**
     * 根据查询id
     *
     * @param id
     * @return
     */
    @Override
    public ContentNotice selectById(Long id) {
        return contentNoticeMapper.selectById(id);
    }
}
