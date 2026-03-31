package com.watabelabs.chatbot.service.tool;

import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class SqlQueryTool {

    private final JdbcTemplate jdbcTemplate;

    public SqlQueryTool(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Tool("Returns the list of tables and their columns in the database schema")
    public String getSchema() {
        String sql = """
                SELECT table_name, column_name, data_type
                FROM information_schema.columns
                WHERE table_schema = 'public'
                ORDER BY table_name, ordinal_position
                """;
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);

        if (rows.isEmpty()) {
            return "No tables found in the public schema.";
        }

        return rows.stream()
                .collect(Collectors.groupingBy(r -> (String) r.get("table_name")))
                .entrySet().stream()
                .map(e -> {
                    String cols = e.getValue().stream()
                            .map(r -> r.get("column_name") + " (" + r.get("data_type") + ")")
                            .collect(Collectors.joining(", "));
                    return "Table: " + e.getKey() + "\n  Columns: " + cols;
                })
                .collect(Collectors.joining("\n"));
    }

    @Tool("Executes a read-only SQL SELECT query and returns the results as text")
    public String executeQuery(@P("A valid SQL SELECT query") String sql) {
        String trimmed = sql.trim().toLowerCase();
        if (!trimmed.startsWith("select")) {
            return "Error: only SELECT queries are allowed.";
        }
        try {
            List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
            if (rows.isEmpty()) {
                return "Query returned no results.";
            }
            return rows.stream()
                    .map(row -> row.entrySet().stream()
                            .map(e -> e.getKey() + ": " + e.getValue())
                            .collect(Collectors.joining(", ")))
                    .collect(Collectors.joining("\n"));
        } catch (Exception e) {
            return "Query error: " + e.getMessage();
        }
    }
}
