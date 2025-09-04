package com.example.htmlserializer;

import java.util.regex.*;

public class RegexUtils {
    // Pattern for extracting tag name from <tag>
    public static final Pattern TAGS_FROM_RAW_HTML = Pattern.compile("<(\\/?\\w+[^>]*)>|([^<]+)");

    // Matches key-value pairs in attributes (e.g., key="value" or key='value')
    public static final Pattern ATTRIBUTES_PATTERN = Pattern.compile(
            "(\\w+)=(\"[^\"]*\"|'[^']*'|\\S+)");

}
