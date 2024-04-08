package com.example.forrediskeynotification.redis;

import com.example.forrediskeynotification.service.RedisService;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

@Component
public class RedisKeyExpiredListener extends KeyExpirationEventMessageListener {

    /**
     * Creates new {@link MessageListener} for {@code __keyEvent@*__:expired} messages.
     *
     * @param listenerContainer must not be {@literal null}.
     */
    public RedisKeyExpiredListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    /**
     * @param message redis key
     * @param pattern __keyEvent@*__:expired
     */
    @Override
    public void onMessage(Message message, byte[] pattern) {
        RedisService.getNotification(message.toString());
        System.out.println("########## onMessage pattern " + new String(pattern) + " | " + message);
    }
}
