package com.jai.sqlplayground.util;

import java.util.List;

public class QueryValidator {

    private static final List<String> BLOCKED_KEYWORDS = List.of(
            "INSERT",
            "UPDATE",
            "DELETE",
            "DROP",
            "ALTER",
            "TRUNCATE",
            "CREATE",
            "EXEC",
            "MERGE"
    );

    public static void validate(String query) {

        if (query == null || query.isBlank()) {
            throw new RuntimeException("Query cannot be empty");
        }

        // Remove extra spaces
        query = query.trim();

        // Allow users to write SELECT ...;
        if (query.endsWith(";")) {
            query = query.substring(0, query.length() - 1);
        }

        String upperQuery = query.toUpperCase();

        // Allow only SELECT, WITH and EXPLAIN
        if (!(upperQuery.startsWith("SELECT")
                || upperQuery.startsWith("WITH")
                || upperQuery.startsWith("EXPLAIN"))) {

            throw new RuntimeException(
                    "Only SELECT, WITH and EXPLAIN queries are allowed");
        }

        // Block dangerous keywords
        for (String keyword : BLOCKED_KEYWORDS) {

            if (upperQuery.contains(keyword)) {

                throw new RuntimeException(
                        "Query contains restricted keyword: " + keyword);
            }
        }

        // Prevent multiple statements
        if (upperQuery.contains(";")) {

            throw new RuntimeException(
                    "Multiple SQL statements are not allowed");
        }
    }
}