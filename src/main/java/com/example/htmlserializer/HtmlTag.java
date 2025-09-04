package com.example.htmlserializer;

import java.util.*;
import java.util.regex.Matcher;

/**
 * Represents an HTML tag with its attributes and content.
 */
public class HtmlTag {
    private String tag; // The raw tag string (e.g., "div class='example'")
    private String name; // The tag name (e.g., "div")
    private String innerHtml; // Inner HTML content
    private HtmlTagType type; // The tag type (SELF_CLOSING, OPENING, CLOSING)
    private Map<String, String> attributes; // Map of attributes (e.g., {"class": "example"})
    private String id; // Extracted "id" attribute
    private List<String> classes; // Extracted "class" attribute
    private boolean isVoid; // Whether the tag is a void element
    // void can be: self closing, opening without closing

    public boolean isVoid() {
        return isVoid;
    }

    public void setVoid(boolean isVoid) {
        this.isVoid = isVoid;
    }

    // Enum for HTML element types
    public enum HtmlTagType {
        SELF_CLOSING,
        OPENING,
        CLOSING,
    }

    /**
     * Constructs an HtmlTag with the specified tag string and type.
     *
     * @param tag  the raw tag string (e.g., "div class='example'")
     * @param type the type of the tag (SELF_CLOSING, OPENING, CLOSING)
     */
    public HtmlTag(String tag, HtmlTagType type) {
        this.tag = tag.trim();
        this.type = type;
        this.name = extractName();
        this.attributes = parseAttributes(tag);
        this.id = getAttribute("id");
        this.classes = extractClasses();
        isVoid = SingletonUtils.getInstance().getHtmlHelper().getHtmlVoidTags().contains(name);
    }

    // Extracts the tag name (first word before any space)
    private String extractName() {
        int spaceIndex = tag.indexOf(" ");
        return (spaceIndex == -1) ? tag : tag.substring(0, spaceIndex).trim();
    }

    // Parses attributes into a map
    private Map<String, String> parseAttributes(String tag) {
        Map<String, String> attributes = new LinkedHashMap<>();
        int spaceIndex = tag.indexOf(" ");
        if (spaceIndex != -1) {// only name
            String attributeString = tag.substring(spaceIndex + 1).trim(); // Everything after the tag name
            Matcher matcher = RegexUtils.ATTRIBUTES_PATTERN.matcher(attributeString);

            while (matcher.find()) {
                String key = matcher.group(1).trim(); // Attribute name
                String value = matcher.group(2); // Attribute value
                if (value != null) {
                    value = value.replaceAll("^['\"]|['\"]$", ""); // Remove surrounding quotes
                }
                attributes.put(key, value);
            }
        }
        return attributes;
    }

    // Get a specific attribute by name
    public String getAttribute(String name) {
        return attributes.getOrDefault(name, null);
    }

    // Get the list of classes from the "class" attribute
    private List<String> extractClasses() {
        String classAttribute = attributes.get("class");
        if (classAttribute != null) {
            return Arrays.asList(classAttribute.split("\\s+"));
        }
        return Collections.emptyList();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getInnerHtml() {
        return innerHtml;
    }

    public void setInnerHtml(String innerHtml) {
        this.innerHtml = innerHtml;
    }

    public HtmlTagType getType() {
        return type;
    }

    public void setType(HtmlTagType type) {
        this.type = type;
    }

    public List<String> getClasses() {
        return classes;
    }

    public void setClasses(List<String> classes) {
        this.classes = classes;
    }

    @Override
    public String toString() {
        return "HtmlTag{" +
                "name='" + name + '\'' +
                ", innerHtml='" + innerHtml + '\'' +
                ", type=" + type +
                ", attributes=" + attributes +
                ", id='" + id + '\'' +
                '}';
    }
}
