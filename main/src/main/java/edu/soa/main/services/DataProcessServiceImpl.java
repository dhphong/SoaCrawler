package edu.soa.main.services;

import edu.soa.common.messages.DataMessage;
import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DataProcessServiceImpl implements DataProcessService {
    private static Logger logger = LoggerFactory.getLogger(DataProcessService.class);

    @Override
    public List<String> extract(DataMessage message) {
        logger.info("extracting....");
        // TODO: Implement new Extractor

        String contentType = message.getType();
        byte[] data = message.getData();

        List<String> ret = new ArrayList<>();
        if (contentType.startsWith("text/xml")) {
            Document document = Jsoup.parse(new String(data), "", Parser.xmlParser());
            Elements links = document.select("loc");

            for (Element link : links) {
                boolean isIgnore = false;
                ret.add(link.text());
            }
        } else if (contentType.startsWith("text/html")) {
            Document document = Jsoup.parse(new String(data));
            Elements linkElements = document.select("a[href]");
            List<String> links = linkElements.stream().map(e -> e.attr("abs:href"))
                    .collect(Collectors.toList());
            ret.addAll(links);
        }
        return ret;
    }

    @Override
    public List<String> filter(List<String> allowRegex, List<String> denyRegex, List<String> urls) {
        logger.info("filtering....");
        // TODO: implement Filter(Check null, ....)
        return urls;
    }

    @Override
    public void save(byte[] data, String name, String dir) {
        logger.info("Saving [" + name + "]");
        try {
//            File dirFile = new File(dir);
//            if (!dirFile.exists()) {
//                if (dirFile.mkdirs()) {
//                    logger.info("Created " + dir);
//                } else {
//                    logger.error("Can not create " + dir);
//                    return;
//                }
//            }
            FileUtils.writeByteArrayToFile(new File(dir + File.separator + name), data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void save(byte[] data, String name) {
        save(data, name, "data");

    }
}
