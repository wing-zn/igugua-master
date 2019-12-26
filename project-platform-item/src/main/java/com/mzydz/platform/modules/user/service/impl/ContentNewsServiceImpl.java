package com.mzydz.platform.modules.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.mzydz.platform.modules.user.entity.PO.content.ContentNews;
import com.mzydz.platform.modules.user.mapper.ContentNewsMapper;
import com.mzydz.platform.modules.user.service.IContentNewsService;
import com.util.pageinfoutil.PageUtil;
import config.Respons.ResponseMsg;
import config.advice.CommonException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 公司新闻 服务实现类
 * </p>
 *
 * @author kongling
 * @since 2019-05-08
 */
@Service
public class ContentNewsServiceImpl extends ServiceImpl<ContentNewsMapper, ContentNews> implements IContentNewsService {
    @Autowired
    private ContentNewsMapper contentNewsMapper;

    /**
     * 查看新闻分页数据
     *
     * @param news
     * @param pageUtil
     * @return
     */
    @Override
    public PageInfo<ContentNews> findByCondition(ContentNews news, PageUtil pageUtil) {
        PageUtil.page(pageUtil);
        List<ContentNews> list = contentNewsMapper.findByCondition(news);
        return new PageInfo<>(list);
    }

    /**
     * 假删除新闻
     *
     * @param contentNews
     * @return
     */
    @Override
    public Integer deleteByPrimaryKey(ContentNews contentNews) {
        if (contentNews.getId() == null) {
            throw new CommonException(ResponseMsg.MISS_PARAM);
        }
        contentNews.setIsDeleted(true);
        return contentNewsMapper.updateById(contentNews);
    }

    /**
     * 新闻浏览量加一
     * @param id
     */
    @Override
    public void updateViewNumsAddOne(Long id) {
        contentNewsMapper.updateViewNumsAddOne(id);
    }
}
