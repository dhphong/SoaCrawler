package edu.soa.main.message.queue;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface MainQueue {
    String INPUT = "main-in";
    String OUTPUT = "main-out";

    @Input(MainQueue.INPUT)
    SubscribableChannel input();

    @Output(MainQueue.OUTPUT)
    MessageChannel output();
}
