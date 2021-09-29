package com.lee.blog.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.lee.common.comstant.MQConst.EMAIL_EXCHANGE;
import static com.lee.common.comstant.MQConst.EMAIL_QUEUE;

/**
 * Rabbitmq配置
 * @author lee
 * @create 2021-09-18 13:56
 **/
@Configuration
public class RabbitMQConfig {


/*    @Bean
    public Queue blogQueue() {
        return new Queue(MAXWELL_QUEUE, true);
    }

    @Bean
    public FanoutExchange maxWellExchange() {
        return new FanoutExchange(MAXWELL_EXCHANGE, true, false);
    }

    @Bean
    public Binding bindingArticleDirect() {
        return BindingBuilder.bind(blogQueue()).to(maxWellExchange());
    }*/

    /**
     * email队列
     * @return
     */
    @Bean
    public Queue emailQueue() {
        return new Queue(EMAIL_QUEUE, true);
    }

    /**
     * email交换机
     * @return
     */
    @Bean
    public FanoutExchange emailExchange() {
        return new FanoutExchange(EMAIL_EXCHANGE, true, false);
    }

    /**
     * 绑定器
     * @return
     */
    @Bean
    public Binding bindingEmailDirect() {
        return BindingBuilder.bind(emailQueue()).to(emailExchange());
    }
}
