package com.lee.blog.strategy.impl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.lee.blog.dto.BlogSearchDTO;
import com.lee.blog.strategy.SearchStrategy;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.lee.common.comstant.Const.*;
import static com.lee.common.enums.BlogStatusEnum.PUBLIC;

/**
 * es搜索策略
 * @author lee
 * @create 2021-12-13 16:06
 **/
@Slf4j
@Service("elasticSearchStrategyImpl")
public class ElasticSearchStrategyImpl implements SearchStrategy {

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Override
    public List<BlogSearchDTO> searchBlogs(String keywords) {
        if (StringUtils.isBlank(keywords)) {
            return new ArrayList<>();
        }
        return search(buildQuery(keywords));
    }

    /**
     * 搜索博客构造
     *
     * @param keywords 关键字
     * @return es条件构造器
     */
    private NativeSearchQueryBuilder buildQuery(String keywords) {

        // 条件构造器
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

        // 根据关键词搜索博客标题或内容
        boolQueryBuilder.must(QueryBuilders.boolQuery().should(QueryBuilders.matchQuery("title", keywords))
                .should(QueryBuilders.matchQuery("content", keywords)))
                .must(QueryBuilders.termQuery("isDelete", FALSE))
                .must(QueryBuilders.termQuery("status", PUBLIC.getStatus()));
        nativeSearchQueryBuilder.withQuery(boolQueryBuilder);
        return nativeSearchQueryBuilder;
    }

    /**
     * 博客搜索结果高亮
     *
     * @param nativeSearchQueryBuilder es条件构造器
     * @return 搜索结果
     */
    private List<BlogSearchDTO> search(NativeSearchQueryBuilder nativeSearchQueryBuilder) {

        // 添加博客标题高亮
        HighlightBuilder.Field titleField = new HighlightBuilder.Field("title");
        titleField.preTags(PRE_TAG);
        titleField.postTags(POST_TAG);

        // 添加博客内容高亮
        HighlightBuilder.Field contentField = new HighlightBuilder.Field("content");
        contentField.preTags(PRE_TAG);
        contentField.postTags(POST_TAG);
        contentField.fragmentSize(200);
        nativeSearchQueryBuilder.withHighlightFields(titleField, contentField);

        // 搜索
        try {
            SearchHits<BlogSearchDTO> search = elasticsearchRestTemplate.search(nativeSearchQueryBuilder.build(), BlogSearchDTO.class);
            return search.getSearchHits().stream().map(hit -> {
                BlogSearchDTO blog = hit.getContent();
                // 获取博客标题高亮数据
                List<String> titleHighLightList = hit.getHighlightFields().get("title");
                if (CollectionUtils.isNotEmpty(titleHighLightList)) {
                    // 替换标题数据
                    blog.setTitle(titleHighLightList.get(0));
                }
                // 获取博客内容高亮数据
                List<String> contentHighLightList = hit.getHighlightFields().get("content");
                if (CollectionUtils.isNotEmpty(contentHighLightList)) {
                    // 替换内容数据
                    blog.setContent(contentHighLightList.get(contentHighLightList.size() - 1));
                }
                return blog;
            }).collect(Collectors.toList());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return new ArrayList<>();
    }
}
