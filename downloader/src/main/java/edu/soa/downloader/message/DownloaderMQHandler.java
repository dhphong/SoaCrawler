package edu.soa.downloader.message;

import edu.soa.common.messages.DataMessage;
import edu.soa.common.messages.UrlMessage;
import edu.soa.downloader.message.queue.DownloaderQueue;
import edu.soa.downloader.services.DownloaderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.context.annotation.Lazy;
import org.springframework.messaging.support.MessageBuilder;

@EnableBinding(DownloaderQueue.class)
public class DownloaderMQHandler {

    private static Logger logger = LoggerFactory.getLogger(DownloaderMQHandler.class);

    @Autowired
    @Lazy
    DownloaderService downloaderService;

    @Autowired
    @Lazy
    DownloaderQueue queue;

    @StreamListener(DownloaderQueue.INPUT)
    public void subscribeUrlMessage(UrlMessage message) {
        logger.info("Received message: " + message + ". Processing...");
        downloaderService.processMessage(message);
    }


    public void publish(DataMessage message) {
        queue.output().send(MessageBuilder.withPayload(message).build());

    }
}
