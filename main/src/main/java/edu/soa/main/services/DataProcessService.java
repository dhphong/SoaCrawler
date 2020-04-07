package edu.soa.main.services;

import edu.soa.common.messages.DataMessage;

import java.util.List;

public interface DataProcessService {
    List<String> extract(DataMessage message);
    List<String> filter(List<String> allowRegex, List<String> denyRegex, List<String> urls);
    void save(byte[] data, String name, String dir);
    void save(byte[] data, String name);
}
