package edu.soa.main.controller.dto;

import lombok.Data;

import java.util.List;

@Data
public class UrlDTO {
    private List<String> urls;
    private String name;
}
