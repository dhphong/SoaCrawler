package edu.soa.main.message;

import edu.soa.common.messages.DataMessage;
import edu.soa.common.messages.UrlMessage;
import edu.soa.main.message.queue.MainQueue;
import edu.soa.main.services.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.context.annotation.Lazy;
import org.springframework.messaging.support.MessageBuilder;

@EnableBinding(MainQueue.class)
public class MainMQHandler {

    @Autowired
    @Lazy
    MainService mainService;

    @Autowired
    @Lazy
    MainQueue mainQueue;

    @StreamListener(MainQueue.INPUT)
    public void subscribeMain(DataMessage message) {
        mainService.processMessage(message);
    }


    public void publishMessage(UrlMessage message) {
        mainQueue.output().send(MessageBuilder.withPayload(message).build());
    }
}
