package com.example.htmlserializer;

import java.util.List;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Queue;

public class HtmlTree {

    private TreeNode<HtmlTag> root;

    public HtmlTree(HtmlTag root) {
        this.root = new TreeNode<HtmlTag>(root);
    }

    public HtmlTree(List<HtmlTag> tags) {
        // skip <!doctype html>
        int i = 0;
        if (tags.get(0).getName().equals("!doctype")) {
            root = new TreeNode<HtmlTag>(tags.get(1));
            i = 2;
        } else {
            root = new TreeNode<HtmlTag>(tags.get(0));
            i = 1;
        }
        TreeNode<HtmlTag> currentParent = root;
        for (; i < tags.size(); i++) {
            if (tags.get(i).getType() == HtmlTag.HtmlTagType.CLOSING && currentParent.getParent() != null) {
                currentParent = currentParent.getParent();
                continue;
                // im closing current parent, continue with uncle's children
            }
            TreeNode<HtmlTag> currentNode = new TreeNode<>(tags.get(i));
            currentParent.addChild(currentNode);

            if (tags.get(i).getType() == HtmlTag.HtmlTagType.SELF_CLOSING) {
                continue;
                // next is brother, because it's a self-closing tag

            } else if (tags.get(i).isVoid()) {
                continue;
                // next is brother, because it's a void tag
            } else {
                currentParent = currentNode;
                // next is my child
            }

        }
        root.setHeights();
    }

    public List<TreeNode<HtmlTag>> findMathesDescendants(Selector selector) {
        HashSet<TreeNode<HtmlTag>> resultSet = new HashSet<>();
        findMathesDescendants(root, selector, resultSet);
        return new ArrayList<>(resultSet);
    }

    public static void findMathesDescendants(TreeNode<HtmlTag> rootNode, Selector selector,
            HashSet<TreeNode<HtmlTag>> result) {
        if (selector == null || rootNode == null) {
            return; // No more selectors or no more nodes
        }

        // Process all current subtree from rootnode + descendants
        for (TreeNode<HtmlTag> child : rootNode.getNodeAndDescendants()) {
            if (selector.matches(child.getVal())) {// found match
                if (selector.getChild() == null) {// last selector
                    result.add(child);
                } else {// more selectors to come
                    findMathesDescendants(child, selector.getChild(), result);
                }
            }
        }
    }

}
