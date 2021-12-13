package com.lee.blog.strategy.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lee.blog.dto.BlogSearchDTO;
import com.lee.blog.entity.Blog;
import com.lee.blog.mapper.BlogMapper;
import com.lee.blog.strategy.SearchStrategy;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.lee.common.comstant.Const.*;
import static com.lee.common.enums.BlogStatusEnum.PUBLIC;

/**
 * mysql搜索策略
 * @author lee
 * @create 2021-09-18 19:04
 **/
@Service("mySqlSearchStrategyImpl")
public class MySqlSearchStrategyImpl implements SearchStrategy {

    @Autowired
    BlogMapper blogMapper;

    @Override
    public List<BlogSearchDTO> searchBlogs(String keywords) {

        // 判空
        if (StringUtils.isBlank(keywords)) {
            return new ArrayList<>();
        }

        // 搜索博客
        List<Blog> blogList = blogMapper.selectList(new LambdaQueryWrapper<Blog>()
                .eq(Blog::getIsDelete, FALSE)
                .eq(Blog::getStatus, PUBLIC.getStatus())
                .and(i -> i.like(Blog::getTitle, keywords)
                        .or()
                        .like(Blog::getContent, keywords)));

        // 高亮处理
        return blogList.stream().map(item -> {
            // 博客内容高亮
            // 获取关键词第一次出现的位置
            String blogContent = null;
            int index = item.getContent().indexOf(keywords);
            if (index != -1) {
                // 获取关键词前面的文字
                int preIndex = index > 25 ? index - 25 : 0;
                String preText = item.getContent().substring(preIndex, index);
                // 获取关键词到后面的文字
                int last = index + keywords.length();
                int postLength = item.getContent().length() - last;
                int postIndex = postLength > 175 ? last + 175 : last + postLength;
                String postText = item.getContent().substring(index, postIndex);
                // 博客内容高亮
                blogContent = (preText + postText).replaceAll(keywords, PRE_TAG + keywords + POST_TAG);
            } else {
                blogContent = item.getContent();
            }

            // 博客标题高亮
            String blogTitle = item.getTitle().replaceAll(keywords, PRE_TAG + keywords + POST_TAG);

            return BlogSearchDTO.builder()
                    .blogId(item.getBlogId())
                    .title(blogTitle)
                    .content(blogContent)
                    .build();
        }).collect(Collectors.toList());
    }
}
