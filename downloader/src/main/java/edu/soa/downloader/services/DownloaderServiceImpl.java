package edu.soa.downloader.services;

import edu.soa.common.messages.DataMessage;
import edu.soa.common.messages.UrlInfo;
import edu.soa.common.messages.UrlMessage;
import edu.soa.downloader.message.DownloaderMQHandler;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class DownloaderServiceImpl implements DownloaderService {
    private static Logger logger = LoggerFactory.getLogger(DownloaderService.class);

    @Autowired
    DownloaderMQHandler downloaderMQHandler;

    @Override
    public void processMessage(UrlMessage message) {
        List<UrlInfo> urlInfos = message.getUrls();

        urlInfos.forEach(url -> {
            DataMessage returnMessage = generateBinaryDataMessage(url);

            if (returnMessage != null) {
                sendMessage(returnMessage);
            } else {
                logger.error("Can not generate message!");
            }
        });
    }

    private DataMessage generateBinaryDataMessage(UrlInfo urlInfo) {
        String url = urlInfo.getUrl();

        System.out.println("Download [" + url + "]");
        try {
            boolean isRedirect = false;
            HttpURLConnection connection;
            do {
                isRedirect = false;
                connection = (HttpURLConnection) new URL(url).openConnection();

                int status = connection.getResponseCode();
                if (status != HttpURLConnection.HTTP_OK) {
                    if (status == HttpURLConnection.HTTP_MOVED_TEMP
                            || status == HttpURLConnection.HTTP_MOVED_PERM
                            || status == HttpURLConnection.HTTP_SEE_OTHER) {
                        isRedirect = true;
                    }
                }

                if (isRedirect) {

                    url = connection.getHeaderField("Location");
                    System.out.println("Redirect to URL : " + url);

                }
            } while (isRedirect);

            String contentType = connection.getContentType();
            byte[] data = IOUtils.toByteArray(connection.getInputStream());
           {
                String title = "";
                if (contentType.startsWith("text/html")) {
                    title = getTitle(data);
                }
                if (title.isEmpty()) {
                    String[] urlParts = url.split("/");
                    title = urlParts[urlParts.length - 1];
                }
                urlInfo.setTitle(title);
            }

            DataMessage message = new DataMessage(urlInfo, data, contentType);
            return message;
        } catch (IOException e) {
            logger.error("Download Error: " + e.getMessage());
            return null;
        }
    }


    private String getTitle(byte[] content) {
        Pattern TITLE_TAG =
                Pattern.compile("<title>(.*?)</title>", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
        String stringContent = new String(content);
        Matcher matcher = TITLE_TAG.matcher(stringContent);
        if (matcher.find()) {
            return matcher.group(1).replaceAll("[\\s<>]+", "-").trim();
        } else {
            return "";
        }
    }

    private void sendMessage(DataMessage message) {
        downloaderMQHandler.publish(message);
    }
}
