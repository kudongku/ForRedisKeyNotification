package com.example.forrediskeynotification.config;

import com.example.forrediskeynotification.redis.EventListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@EnableCaching
@Configuration
public class RedisConfig {

    @Value("${spring.data.redis.host}")
    private String host;
    @Value("${spring.data.redis.port}")
    private int port;

    private final String EXPIRED_EVENT_PATTERN = "__keyEvent@*__:expired";
    private final String EXPIRED_SPACE_PATTERN = "__keySpace@*__:expired";
    private final String SET_EVENT_PATTERN = "__keyEvent@*__:set";

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory(host, port);
    }

    @Bean
    public RedisTemplate<String, String> redisTemplate() {
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>(); // String, String 이유 설명
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        return redisTemplate;
    }

    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(
        RedisConnectionFactory redisConnectionFactory,
        EventListener eventListener
    ) {
        RedisMessageListenerContainer redisMessageListenerContainer = new RedisMessageListenerContainer();
        redisMessageListenerContainer.setConnectionFactory(redisConnectionFactory);
        redisMessageListenerContainer.addMessageListener(eventListener,
            new PatternTopic(SET_EVENT_PATTERN));
        redisMessageListenerContainer.addMessageListener(eventListener,
            new PatternTopic(EXPIRED_EVENT_PATTERN));
        redisMessageListenerContainer.addMessageListener(eventListener,
            new PatternTopic(EXPIRED_SPACE_PATTERN));

        return redisMessageListenerContainer;
    }


}
