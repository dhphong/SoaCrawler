package edu.soa.main.services;

import edu.soa.common.messages.DataMessage;
import edu.soa.common.messages.UrlInfo;
import edu.soa.common.messages.UrlMessage;
import edu.soa.main.message.MainMQHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MainServiceImpl implements MainService {

    @Autowired
    MainMQHandler mainMQHandler;

    @Autowired
    DataProcessService dataProcessService;

    @Override
    public void startCrawl(UrlMessage message) {
        sendMessage(message);
    }

    @Override
    public void processMessage(DataMessage message) {
        String saveName = message.getUrl().getTitle().replaceAll("[\\s<>]+", " ").trim();
        if (message.getType().startsWith("application/pdf"))
            saveName += ".pdf";
        else if (message.getType().startsWith("text/html"))
            saveName += ".html";
        else if (message.getType().startsWith("text/xml"))
            saveName += ".xml";
        dataProcessService.save(message.getData(), saveName);

        List<String> newUrls = dataProcessService.extract(message);
        List<String> filteredUrls = dataProcessService.filter(
                Arrays.asList("https://js.vnu.edu.vn/"),
                null,
                newUrls
        );

        List<UrlInfo> urlInfos = filteredUrls.stream().map(UrlInfo::new).collect(Collectors.toList());

        UrlMessage urlMessage = new UrlMessage(message.getName(), urlInfos);
        sendMessage(urlMessage);
    }



    private void sendMessage(UrlMessage message) {
        mainMQHandler.publishMessage(message);
    }
}
