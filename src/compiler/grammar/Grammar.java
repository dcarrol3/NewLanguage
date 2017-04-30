package compiler.grammar;

public class Grammar{
    public static final String GRAMMAR = "[\n" +
            "    {\n" +
            "        \"token\":\"if\",\n" +
            "            \"type\":\"keyword\",\n" +
            "            \"delimit\": false\n" +
            "    },\n" +
            "    {\n" +
            "        \"token\":\"else\",\n" +
            "            \"type\":\"keyword\",\n" +
            "            \"delimit\": false\n" +
            "    },\n" +
            "    {\n" +
            "        \"token\": \"loop\",\n" +
            "            \"type\": \"keyword\",\n" +
            "            \"delimit\": false\n" +
            "    },\n" +
            "    {\n" +
            "        \"token\": \"print\",\n" +
            "            \"type\": \"keyword\",\n" +
            "            \"delimit\": false\n" +
            "    },\n" +
            "\n" +
            "\n" +
            "    {\n" +
            "        \"token\": \"#/\",\n" +
            "            \"type\": \"ml_start_comment\",\n" +
            "            \"delimit\": true\n" +
            "    },\n" +
            "    {\n" +
            "        \"token\": \"/#\",\n" +
            "            \"type\": \"ml_end_comment\",\n" +
            "            \"delimit\": true\n" +
            "    },\n" +
            "    {\n" +
            "        \"token\": \"#\",\n" +
            "            \"type\": \"comment\",\n" +
            "            \"delimit\": true\n" +
            "    },\n" +
            "\n" +
            "\n" +
            "    {\n" +
            "        \"token\":\"=\",\n" +
            "            \"type\":\"assignment\",\n" +
            "            \"delimit\": true\n" +
            "    },\n" +
            "\n" +
            "    {\n" +
            "        \"token\": \"\\t\",\n" +
            "            \"type\": \"whitespace\",\n" +
            "            \"delimit\": true\n" +
            "    },\n" +
            "    {\n" +
            "        \"token\": \"\\n\",\n" +
            "            \"type\": \"new_line\",\n" +
            "            \"delimit\": true\n" +
            "    },\n" +
            "\n" +
            "    {\n" +
            "        \"token\": \"{\",\n" +
            "            \"type\": \"open_bracket\",\n" +
            "            \"delimit\": true\n" +
            "    },\n" +
            "    {\n" +
            "        \"token\": \"}\",\n" +
            "            \"type\": \"close_bracket\",\n" +
            "            \"delimit\": true\n" +
            "    },\n" +
            "\n" +
            "    {\n" +
            "        \"token\": \"(\",\n" +
            "            \"type\": \"open_paren\",\n" +
            "            \"delimit\": true\n" +
            "    },\n" +
            "    {\n" +
            "        \"token\": \")\",\n" +
            "            \"type\": \"close_paren\",\n" +
            "            \"delimit\": true\n" +
            "    },\n" +
            "\n" +
            "    {\n" +
            "        \"token\": \",\",\n" +
            "            \"type\": \"comma\",\n" +
            "            \"delimit\": true\n" +
            "    },\n" +
            "\n" +
            "\n" +
            "    {\n" +
            "        \"token\": \";\",\n" +
            "            \"type\": \"semi-colon\",\n" +
            "            \"delimit\": true\n" +
            "    },\n" +
            "\n" +
            "\n" +
            "    {\n" +
            "        \"token\": \"+\",\n" +
            "            \"type\": \"operator\",\n" +
            "            \"delimit\": true\n" +
            "    },\n" +
            "    {\n" +
            "        \"token\": \"-\",\n" +
            "            \"type\": \"operator\",\n" +
            "            \"delimit\": true\n" +
            "    },\n" +
            "    {\n" +
            "        \"token\": \"*\",\n" +
            "            \"type\": \"operator\",\n" +
            "            \"delimit\": true\n" +
            "    },\n" +
            "    {\n" +
            "        \"token\": \"/\",\n" +
            "            \"type\": \"operator\",\n" +
            "            \"delimit\": true\n" +
            "    },\n" +
            "    {\n" +
            "        \"token\": \"%\",\n" +
            "            \"type\": \"operator\",\n" +
            "            \"delimit\": true\n" +
            "    },\n" +
            "    {\n" +
            "        \"token\": \"==\",\n" +
            "            \"type\": \"compares\",\n" +
            "            \"delimit\": true\n" +
            "    },\n" +
            "    {\n" +
            "        \"token\": \"!=\",\n" +
            "            \"type\": \"compares\",\n" +
            "            \"delimit\": true\n" +
            "    },\n" +
            "    {\n" +
            "        \"token\": \">\",\n" +
            "            \"type\": \"compares\",\n" +
            "            \"delimit\": true\n" +
            "    },\n" +
            "    {\n" +
            "        \"token\": \"<\",\n" +
            "            \"type\": \"compares\",\n" +
            "            \"delimit\": true\n" +
            "    },\n" +
            "    {\n" +
            "        \"token\": \">=\",\n" +
            "            \"type\": \"compares\",\n" +
            "            \"delimit\": true\n" +
            "    },\n" +
            "    {\n" +
            "        \"token\": \"<=\",\n" +
            "            \"type\": \"compares\",\n" +
            "            \"delimit\": true\n" +
            "    },\n" +
            "    {\n" +
            "        \"token\": \"true\",\n" +
            "            \"type\": \"compares\",\n" +
            "            \"delimit\": false\n" +
            "    },\n" +
            "    {\n" +
            "        \"token\": \"false\",\n" +
            "            \"type\": \"compares\",\n" +
            "            \"delimit\": false\n" +
            "    },\n" +
            "    {\n" +
            "        \"token\": \"and\",\n" +
            "            \"type\": \"compares\",\n" +
            "            \"delimit\": false\n" +
            "    },\n" +
            "    {\n" +
            "        \"token\": \"or\",\n" +
            "            \"type\": \"compares\",\n" +
            "            \"delimit\": false\n" +
            "    }\n" +
            "]";
}
