package com.example.htmlserializer;

import java.util.List;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {
        HtmlLoader loader = new HtmlLoader();
        System.out.println("insert url or space for example.com");
        String url = System.console().readLine();
        if (url.trim().isEmpty()) {
            url = "http://example.com";
        }
        String html = loader.load(url).exceptionally(e -> {
            System.err.println("Error: " + e.getMessage());
            return null;
        }).join(); // Block until the HTML content is fetched
        List<HtmlTag> tags = HtmlParser.parseTags(html);
        for (HtmlTag tag : tags) {
            System.out.println(tag);
        }
        System.out.println("\nFound " + tags.size() + " HTML tags");

        HtmlTree tree = new HtmlTree(tags);
        System.out.println("\ninsert a selector");
        String selector = System.console().readLine();
        Selector parsedSelector = Selector.parseSelector(selector);
        List<TreeNode<HtmlTag>> selectedTags = tree.findMathesDescendants(parsedSelector);
        for (TreeNode<HtmlTag> tag : selectedTags) {
            System.out.println(tag.getVal());
        }
        System.out.println("\nFound " + selectedTags.size() + " matching tags");

    }
}
