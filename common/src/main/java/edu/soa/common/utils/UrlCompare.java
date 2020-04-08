package edu.soa.common.utils;

import java.net.URI;
import java.net.URISyntaxException;

public class UrlCompare {
    public static boolean compareDomain(String url1, String url2) {
        URI uri1 = null;
        URI uri2 = null;
        try {
            uri1 = new URI(url1);
            uri2 = new URI(url2);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return false;
        }
        if (uri1.getHost() == uri2.getHost()) return false;
        return true;
    }
}
