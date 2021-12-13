package com.lee.blog.strategy;

import com.lee.blog.dto.BlogSearchDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static com.lee.common.enums.SearchModeEnum.getStrategy;


/**
 * 搜索策略上下文
 * @author lee
 * @create 2021-12-13 16:26
 **/
@Service
public class SearchStrategyContext {

    /**
     * 搜索模式
     */
    @Value("${search.mode}")
    private String searchMode;

    @Autowired
    private Map<String, SearchStrategy> searchStrategyMap;

    /**
     * 搜索策略
     * @param keywords
     * @return
     */
    public List<BlogSearchDTO> executeSearchStrategy(String keywords) {
        return searchStrategyMap.get(getStrategy(searchMode)).searchBlogs(keywords);
    }
}
