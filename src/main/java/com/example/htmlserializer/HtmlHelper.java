package com.example.htmlserializer;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;

public class HtmlHelper {
    private List<String> htmlTags;
    private List<String> htmlVoidTags;

    private static final String CONFIG_FILE = "application.properties";

    public HtmlHelper() {
        // Load properties
        Properties properties = new Properties();
        try (FileInputStream fis = new FileInputStream(CONFIG_FILE)) {
            properties.load(fis);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load configuration file", e);
        }

        // Get paths from environment variables or fall back to properties
        String htmlTagsPath = System.getenv("HTML_TAGS_PATH");
        if (htmlTagsPath == null || htmlTagsPath.isEmpty()) {
            htmlTagsPath = properties.getProperty("html.tags.path", "JSON-Files/HtmlTags.json");
        }

        String htmlVoidTagsPath = System.getenv("HTML_VOID_TAGS_PATH");
        if (htmlVoidTagsPath == null || htmlVoidTagsPath.isEmpty()) {
            htmlVoidTagsPath = properties.getProperty("html.void.tags.path", "JSON-Files/HtmlVoidTags.json");
        }

        // Load JSON files
        this.htmlTags = readJsonFile(htmlTagsPath);
        this.htmlVoidTags = readJsonFile(htmlVoidTagsPath);
    }

    private List<String> readJsonFile(String filePath) {
        try {
            String content = Files.readString(Paths.get(filePath));
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(content, List.class);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read JSON file: " + filePath, e);
        }
    }

    public List<String> getHtmlTags() {
        return htmlTags;
    }

    public List<String> getHtmlVoidTags() {
        return htmlVoidTags;
    }

    public boolean isValidTagName(String name) {
        return htmlTags.contains(name) || htmlVoidTags.contains(name);
    }
}
