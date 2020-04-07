package edu.soa.main.controller;

import edu.soa.common.messages.UrlInfo;
import edu.soa.common.messages.UrlMessage;
import edu.soa.main.controller.dto.UrlDTO;
import edu.soa.main.services.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class MainController {

    @Autowired
    MainService mainService;

    @PutMapping(value = "/run")
    public String run(@RequestBody UrlDTO urlForm) {
        List<UrlInfo> urlInfos = urlForm.getUrls().stream().map(UrlInfo::new).collect(Collectors.toList());

        UrlMessage startMessage = new UrlMessage(urlForm.getName(), urlInfos);
        mainService.startCrawl(startMessage);
        return "OK";
    }
}
