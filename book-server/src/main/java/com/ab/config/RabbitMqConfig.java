package com.ab.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author cattleYuan
 * @date 2024/1/27
 */
@Configuration
public class RabbitMqConfig {
    @Bean
    public MessageConverter jsonMessageConverter(){
        return new Jackson2JsonMessageConverter();
    }


    @Bean
    public Queue deadQueue(){
        return new Queue("queue.dead");
    }
    @Bean
    public DirectExchange deadDirectExchange(){
        return new DirectExchange("exchange.dead");
    }
    @Bean
    Binding bindingQueueToExchangewithDead(){
        return BindingBuilder.bind(deadQueue()).to(deadDirectExchange()).with("blue");
    }
    @Bean
    public Queue activityDelayQueue(){
        return QueueBuilder.durable("activity.queue")
                .deadLetterExchange("exchange.dead")
                .deadLetterRoutingKey("blue").build();
    }

    @Bean
    public DirectExchange activityExchange(){
        return new DirectExchange("activity.exchange");
    }
    @Bean
    public Binding bindingQueueToActivityExchange(){
        return BindingBuilder.bind(activityDelayQueue()).to(activityExchange()).with("red");
    }

}
