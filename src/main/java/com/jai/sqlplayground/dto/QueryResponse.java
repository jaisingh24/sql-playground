package com.jai.sqlplayground.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
public class QueryResponse {

    private long executionTime;

    private int rowCount;
    private boolean explain;

    private List<Map<String, Object>> rows;
}