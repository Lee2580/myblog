package com.lee.blog.strategy;

import com.lee.blog.dto.BlogSearchDTO;

import java.util.List;

/**
 * 搜索策略
 * @author lee
 * @create 2021-09-18 19:02
 **/
public interface SearchStrategy {

    List<BlogSearchDTO> searchBlogs(String keywords);
}
