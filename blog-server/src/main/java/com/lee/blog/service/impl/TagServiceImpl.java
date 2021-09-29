package com.lee.blog.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lee.blog.dto.TagDTO;
import com.lee.blog.dto.TagListDTO;
import com.lee.blog.entity.BlogTag;
import com.lee.blog.entity.Tag;
import com.lee.blog.mapper.BlogTagMapper;
import com.lee.blog.mapper.TagMapper;
import com.lee.blog.service.TagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lee.blog.util.BeanCopyUtil;
import com.lee.blog.util.PageUtil;
import com.lee.blog.vo.ConditionVo;
import com.lee.blog.vo.PageResult;
import com.lee.blog.vo.TagVo;
import com.lee.common.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lee
 * @since 2021-09-11
 */
@Slf4j
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

    @Autowired
    TagMapper tagMapper;

    @Autowired
    BlogTagMapper blogTagMapper;

    /**
     * 获取博客标签
     * @param conditionVo
     * @return
     */
    @Override
    public List<TagDTO> tagListBySearch(ConditionVo conditionVo) {

        // 搜索标签
        List<Tag> tagList = tagMapper.selectList(new LambdaQueryWrapper<Tag>()
                .like(StringUtils.isNotBlank(conditionVo.getKeywords()), Tag::getTagName, conditionVo.getKeywords())
                .orderByDesc(Tag::getTagId));
        return BeanCopyUtil.copyList(tagList, TagDTO.class);
    }

    /**
     * 查询标签列表
     * @param conditionVo
     * @return
     */
    @Override
    public PageResult<TagListDTO> getTagListDTO(ConditionVo conditionVo) {
        // 查询标签数量
        Integer count = tagMapper.selectCount(new LambdaQueryWrapper<Tag>()
                .like(StringUtils.isNotBlank(conditionVo.getKeywords()), Tag::getTagName, conditionVo.getKeywords()));
        if (count == 0) {
            return new PageResult<>();
        }
        // 分页查询标签列表
        List<TagListDTO> tagList = tagMapper.getTagListDTO(
                PageUtil.getLimitCurrent(), PageUtil.getSize(), conditionVo);
        return new PageResult<>(tagList, count);
    }

    /**
     * 删除标签
     * @param tagIdList
     */
    @Override
    public void deleteTag(List<Long> tagIdList) {
        // 查询标签下是否有文章
        Integer count = blogTagMapper.selectCount(new LambdaQueryWrapper<BlogTag>()
                .in(BlogTag::getTagId, tagIdList));
        if (count > 0) {
            throw new BizException("删除失败，该标签下存在文章");
        }
        tagMapper.deleteBatchIds(tagIdList);
    }

    /**
     * 添加或修改标签
     * @param tagVo
     */
    @Transactional
    @Override
    public void saveOrUpdateTag(TagVo tagVo) {
        // 查询标签名是否存在
        Tag existTag = tagMapper.selectOne(new LambdaQueryWrapper<Tag>()
                .select(Tag::getTagId)
                .eq(Tag::getTagName, tagVo.getTagName()));
        if (Objects.nonNull(existTag) && !existTag.getTagId().equals(tagVo.getTagId())) {
            throw new BizException("标签名已存在");
        }

        Tag tag = BeanUtil.copyProperties(tagVo, Tag.class);
        //log.info("tag ===>"+tag);
        this.saveOrUpdate(tag);
    }

    /**
     * 前台查询标签列表
     * @return
     */
    @Override
    public PageResult<TagDTO> listTags() {

        // 查询标签列表
        List<Tag> tagList = tagMapper.selectList(null);
        // 转换DTO
        List<TagDTO> tagDTOList = BeanCopyUtil.copyList(tagList, TagDTO.class);
        // 查询标签数量
        Integer count = tagMapper.selectCount(null);

        return new PageResult<>(tagDTOList, count);
    }
}
