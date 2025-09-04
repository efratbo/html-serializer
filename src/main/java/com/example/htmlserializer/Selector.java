package com.example.htmlserializer;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Represents a CSS selector used to match HTML tags.
 */
public class Selector {
    private String tagName;
    private String id;
    private List<String> classes;
    private Selector parent;
    private Selector child;
    private int height;

    /**
     * Constructs a Selector with the specified tag name, id, and classes.
     *
     * @param tagName the name of the tag
     * @param id      the id of the tag
     * @param classes the list of classes of the tag
     */
    public Selector(String tagName, String id, List<String> classes) {
        this.tagName = tagName;
        this.id = id;
        this.classes = classes != null ? classes : new ArrayList<>();
    }

    public boolean matches(HtmlTag tag) {
        // match all if exists in selector & in htmlTag: tagname, id, classes
        if (tagName != null && !tagName.equals(tag.getName())) {
            return false;
        }
        if (id != null && !id.equals(tag.getAttribute("id"))) {
            return false;
        }
        for (String className : classes) {
            if (!tag.getClasses().contains(className)) {
                return false;
            }
        }
        return true;
    }

    // Getters and Setters
    public String getTagName() {
        return tagName;
    }

    public String getId() {
        return id;
    }

    public List<String> getClasses() {
        return classes;
    }

    public Selector getParent() {
        return parent;
    }

    public void setParent(Selector parent) {
        this.parent = parent;
    }

    public Selector getChild() {
        return child;
    }

    public void setChild(Selector child) {
        this.child = child;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    // Static method to parse selector string
    public static Selector parseSelector(String selectorString) {
        String[] parts = selectorString.split(" ");
        Selector root = null;
        Selector current = null;

        for (String part : parts) {// each part is a selector
            String tagName = null;
            String id = null;
            List<String> classes = new ArrayList<>();

            String[] segments = part.split("(?=[#.])");
            for (String segment : segments) {
                if (segment.startsWith("#")) {
                    id = segment.substring(1);
                } else if (segment.startsWith(".")) {
                    classes.add(segment.substring(1));
                } else if (SingletonUtils.getInstance().getHtmlHelper().isValidTagName(segment)) {
                    tagName = segment;
                }
            }

            Selector newSelector = new Selector(tagName, id, classes);
            if (root == null) {
                root = newSelector;
            } else {
                current.setChild(newSelector);
                newSelector.setParent(current);
            }
            current = newSelector;
        }

        return root;
    }

    /**
     * Sets heights to all selectors descending from this selector iteratively.
     */
    public void setHeights() {
        Stack<Selector> stack = new Stack<>();
        stack.push(this);
        int currentHeight = 0;

        while (!stack.isEmpty()) {
            Selector selector = stack.pop();
            selector.setHeight(currentHeight);

            if (selector.getChild() != null) {
                stack.push(selector.getChild());
                currentHeight++;
            }
        }
    }

    @Override
    public String toString() {
        return "Selector{" +
                "tagName='" + tagName + '\'' +
                ", id='" + id + '\'' +
                ", classes=" + classes +
                ", height=" + height +
                '}';
    }
}
