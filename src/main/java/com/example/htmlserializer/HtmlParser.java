package com.example.htmlserializer;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

public class HtmlParser {

    public static List<HtmlTag> parseTags(String html) {
        Matcher matcher = RegexUtils.TAGS_FROM_RAW_HTML.matcher(html);
        List<HtmlTag> tags = new ArrayList<>();
        HtmlTag lastOpeningTag = null; // Tracks the last valid opening tag

        while (matcher.find()) {
            if (matcher.group(1) != null) { // Match a tag
                String tag = matcher.group(1).trim(); // Extract the full tag text (e.g., "p class="intro"")
                HtmlTag htmlTag;
                // Track the current opening tag for potential innerHTML assignment
                if (tag.startsWith("/")) { // It's a closing tag
                    htmlTag = new HtmlTag(tag.substring(1),
                            HtmlTag.HtmlTagType.CLOSING);
                    lastOpeningTag = null; // Reset the current tag
                } else if (tag.endsWith("/")) { // It's a self closing tag
                    htmlTag = new HtmlTag(tag.substring(0, tag.length() - 1),
                            HtmlTag.HtmlTagType.SELF_CLOSING);
                } else {
                    htmlTag = new HtmlTag(tag, HtmlTag.HtmlTagType.OPENING);
                    lastOpeningTag = htmlTag;
                }
                tags.add(htmlTag);
            } else if (matcher.group(2) != null) { // Match plain text
                String textContent = matcher.group(2).trim();

                if (!textContent.isEmpty() && lastOpeningTag != null) {
                    // Assign the plain text as innerHTML for the current tag
                    lastOpeningTag.setInnerHtml(textContent);
                    lastOpeningTag = null; // Reset after assigning innerHTML
                }
            }
        }

        return tags;
    }
}
