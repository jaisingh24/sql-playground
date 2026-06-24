package com.jai.sqlplayground.service;

import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class QueryHistoryService {

    private final LinkedList<String> history = new LinkedList<>();

    public void addQuery(String query) {

        if (history.size() >= 10) {
            history.removeFirst();
        }

        history.addLast(query);
    }

    public List<String> getHistory() {
        return history;
    }
}