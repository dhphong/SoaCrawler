package edu.soa.common.messages;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UrlInfo {
    private String url;
    private String title;

    public UrlInfo(String url) {
        this.url = url;
    }
}
