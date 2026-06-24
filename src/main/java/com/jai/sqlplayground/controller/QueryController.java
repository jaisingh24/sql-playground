package com.jai.sqlplayground.controller;

import com.jai.sqlplayground.dto.QueryRequest;
import com.jai.sqlplayground.dto.QueryResponse;
import com.jai.sqlplayground.service.QueryHistoryService;
import com.jai.sqlplayground.service.QueryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/query")
@CrossOrigin(origins = "http://localhost:5173")
public class QueryController {
    private final QueryHistoryService queryHistoryService;
    private final QueryService queryService;

    public QueryController(
            QueryService queryService,
            QueryHistoryService queryHistoryService) {

        this.queryService = queryService;
        this.queryHistoryService = queryHistoryService;
    }
    @PostMapping("/execute")
    public QueryResponse execute(
            @RequestBody QueryRequest request) {

        return queryService.execute(request.getQuery());
    }

    @GetMapping("/history")
    public List<String> getHistory() {
        return queryHistoryService.getHistory();
    }
}