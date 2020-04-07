package edu.soa.downloader.services;

import edu.soa.common.messages.UrlMessage;

public interface DownloaderService {
    void processMessage(UrlMessage message);
}
