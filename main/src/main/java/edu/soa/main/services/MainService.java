package edu.soa.main.services;

import edu.soa.common.messages.DataMessage;
import edu.soa.common.messages.UrlMessage;

public interface MainService {
    void startCrawl(UrlMessage message);
    void processMessage(DataMessage message);
}
