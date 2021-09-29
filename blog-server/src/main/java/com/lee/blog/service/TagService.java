package com.lee.blog.service;

import com.lee.blog.dto.TagDTO;
import com.lee.blog.dto.TagListDTO;
import com.lee.blog.entity.Tag;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lee.blog.vo.ConditionVo;
import com.lee.blog.vo.PageResult;
import com.lee.blog.vo.TagVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lee
 * @since 2021-09-11
 */
public interface TagService extends IService<Tag> {

    List<TagDTO> tagListBySearch(ConditionVo conditionVo);

    PageResult<TagListDTO> getTagListDTO(ConditionVo conditionVo);

    void deleteTag(List<Long> tagIdList);

    void saveOrUpdateTag(TagVo tagVo);

    PageResult<TagDTO> listTags();
}
