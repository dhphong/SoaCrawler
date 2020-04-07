package edu.soa.downloader.message.queue;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface DownloaderQueue {
    String INPUT = "downloader-in";
    String OUTPUT = "downloader-out";

    @Input(DownloaderQueue.INPUT)
    SubscribableChannel input();

    @Output(DownloaderQueue.OUTPUT)
    MessageChannel output();
}
