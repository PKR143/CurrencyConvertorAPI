package com.demo.dto;

import lombok.Data;

import java.util.Map;

@Data
public class ApiResponse {
    private MetaInfo meta;
    private Map<String, DataInfo> data;
}
