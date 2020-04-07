package edu.soa.common.messages;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UrlMessage extends CommonMessage{
    private List<UrlInfo> urls;

    public UrlMessage(String name, List<UrlInfo> urls) {
        super(name);
        this.urls = urls;
    }
}
