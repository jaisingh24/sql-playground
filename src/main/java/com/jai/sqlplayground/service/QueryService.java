package com.jai.sqlplayground.service;

import com.jai.sqlplayground.dto.QueryResponse;
import com.jai.sqlplayground.util.QueryValidator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class QueryService {

    private final JdbcTemplate jdbcTemplate;
    private final QueryHistoryService queryHistoryService;

    public QueryService(
            JdbcTemplate jdbcTemplate,
            QueryHistoryService queryHistoryService) {

        this.jdbcTemplate = jdbcTemplate;
        this.queryHistoryService = queryHistoryService;
    }

    public QueryResponse execute(String query) {

        QueryValidator.validate(query);

        queryHistoryService.addQuery(query);

        long start = System.currentTimeMillis();

        List<Map<String, Object>> rows =
                jdbcTemplate.queryForList(query);

        long end = System.currentTimeMillis();

        boolean explain =
                query.trim()
                        .toUpperCase()
                        .startsWith("EXPLAIN");

        return new QueryResponse(
                end - start,
                rows.size(),
                explain,
                rows
        );
    }
}