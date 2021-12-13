package com.lee.blog.mapper;

import com.lee.blog.dto.BlogSearchDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author lee
 * @create 2021-12-13 16:54
 **/
@Mapper
public interface ElasticsearchMapper extends ElasticsearchRepository<BlogSearchDTO,Long> {
}
