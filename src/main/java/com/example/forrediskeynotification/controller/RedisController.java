package com.example.forrediskeynotification.controller;

import com.example.forrediskeynotification.service.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class RedisController {

    private final RedisService redisService;

    @GetMapping("redis/{testKey}/{testValue}")
    public void keyNotificationTest(
        @PathVariable String testKey,
        @PathVariable String testValue
    ){
        redisService.keyNotificationTest(testKey, testValue);
    }

    @GetMapping("redis/{testKey}")
    public String getValueByKey(
        @PathVariable String testKey
    ){
        return redisService.getValueByKey(testKey);
    }

}
