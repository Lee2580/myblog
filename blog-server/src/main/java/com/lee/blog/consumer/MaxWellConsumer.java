package com.lee.blog.consumer;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSON;
import com.lee.blog.dto.BlogSearchDTO;
import com.lee.blog.dto.MaxwellDataDTO;
import com.lee.blog.entity.Blog;
import com.lee.blog.mapper.ElasticsearchMapper;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.lee.common.comstant.MQConst.MAXWELL_QUEUE;

/**
 * maxwell
 *  实时监控mysql binlog
 *  将改变的数据写入elasticsearch
 *
 * @author lee
 * @create 2021-12-13 16:51
 **/
@Component
@RabbitListener(queues = MAXWELL_QUEUE)
public class MaxWellConsumer {

    @Autowired
    ElasticsearchMapper elasticsearchMapper;

    @RabbitHandler
    public void process(byte[] data) {
        // 获取监听信息
        MaxwellDataDTO maxwellDataDTO = JSON.parseObject(new String(data), MaxwellDataDTO.class);
        // 获取文章数据
        Blog blog = JSON.parseObject(JSON.toJSONString(maxwellDataDTO.getData()), Blog.class);
        // 判断操作类型
        switch (maxwellDataDTO.getType()) {
            case "insert":
            case "update":
                // 更新es文章
                elasticsearchMapper.save(BeanUtil.copyProperties(blog, BlogSearchDTO.class));
                break;
            case "delete":
                // 删除文章
                elasticsearchMapper.deleteById(blog.getBlogId());
                break;
            default:
                break;
        }
    }
}
