package com.example.forrediskeynotification.service;

import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RedisService {

    private final RedisTemplate<String, String> redisTemplate;

    public void keyNotificationTest(String testKey, String testValue) {
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        operations.set(testKey, testValue);
        System.out.println("########## redis 등록, key : " + testKey + ", value :" + testValue);
        redisTemplate.expire(testKey, 20, TimeUnit.SECONDS);
    }

    public String getValueByKey(String testKey) {
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        String cacheValue = operations.get(testKey);
        System.out.println("########## redis 조회, key : " + testKey + ", value :" + cacheValue);
        return cacheValue;
    }

    public static void getNotification(String message) {
        System.out.println("########## 시간이 만료된 키 : " + message);
    }
}
