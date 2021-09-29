package com.lee.blog.controller;


import com.lee.blog.service.ViewsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  定时任务控制器
 * </p>
 * 定时任务
 *       @EnableScheduling 开启定时任务
 *       @Scheduled 开启一个定时任务
 *       自动配置类：TaskSchedulingAutoConfiguration
 * 异步任务
 *       @EnableAsync 开启异步任务
 *       @Async 给希望异步执行的方法标注
 *       自动配置类：TaskExecutionAutoConfiguration
 *
 *
 * @author lee
 * @since 2021-09-11
 */
@Slf4j
@EnableScheduling
@Component
public class ViewsController {

    @Autowired
    ViewsService viewsService;

    //"0 0 2 * * ?"
    @Scheduled(cron = "0 0 2 * * ?")
    public void redisDataToMySql(){

        viewsService.redisDataToMySql();
        //log.info("定时任务完成");
    }
}
