package com.example.forrediskeynotification.redis;

import com.example.forrediskeynotification.dto.ExpiredAuction;
import com.example.forrediskeynotification.service.RedisService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

@Component
public class RedisKeyExpiredListener extends KeyExpirationEventMessageListener {

    private final ApplicationEventPublisher publisher;

    /**
     * Creates new {@link MessageListener} for {@code __keyEvent@*__:expired} messages.
     *
     * @param listenerContainer must not be {@literal null}.
     */
    public RedisKeyExpiredListener(
        RedisMessageListenerContainer listenerContainer,
        ApplicationEventPublisher publisher
    ) {
        super(listenerContainer);
        this.publisher = publisher;
    }

    /**
     * @param message redis key
     * @param pattern __keyEvent@*__:expired
     */
    @Override
    public void onMessage(Message message, byte[] pattern) {
        System.out.println("########## 시간이 만료된 키 : " + message);
        publisher.publishEvent(new ExpiredAuction(message.toString()));
    }
}
