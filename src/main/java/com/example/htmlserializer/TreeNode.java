package com.example.htmlserializer;

import java.util.*;

public class TreeNode<T> {
    private T val;
    private TreeNode<T> parent;
    private List<TreeNode<T>> children;
    private int height;

    public TreeNode(T val) {
        this.val = val;
        this.children = new ArrayList<>();
    }

    public void addChild(TreeNode<T> child) {
        child.parent = this;
        this.children.add(child);
    }

    public T getVal() {
        return val;
    }

    public TreeNode<T> getParent() {
        return parent;
    }

    public List<TreeNode<T>> getChildren() {
        return children;
    }

    public int getHeight() {
        return height;
    }

    private void setHeight(int height) {
        this.height = height;
    }

    // traverse subtree of a node iteratively in preorder
    // this method returns custom implementation of iterable
    // ( as if i created new class), with :
    // properties: queue (to track state)
    // methods: hasNext, next for my custom traversal logic
    public Iterable<TreeNode<T>> getNodeAndDescendants() {
        return () -> new Iterator<TreeNode<T>>() {
            private Queue<TreeNode<T>> queue = new ArrayDeque<>();

            {
                queue.offer(TreeNode.this); // Initialize the queue with the current node
            }

            @Override
            public boolean hasNext() {
                return !queue.isEmpty();
            }

            @Override
            public TreeNode<T> next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                TreeNode<T> current = queue.poll(); // Retrieve the next node
                queue.addAll(current.getChildren()); // Add children to the queue
                return current; // Lazily return the current node
            }
        };
    }

    public Iterable<TreeNode<T>> getNodeAndAncestors() {
        return () -> new Iterator<TreeNode<T>>() {
            private TreeNode<T> current = TreeNode.this.parent;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public TreeNode<T> next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                TreeNode<T> next = current;
                current = current.parent;
                return next;
            }
        };
    }

    public void setHeights() {
        Queue<TreeNode<T>> queue = new ArrayDeque<>();
        queue.offer(this);
        this.height = 0;

        while (!queue.isEmpty()) {
            TreeNode<T> current = queue.poll();
            int currentHeight = current.getHeight();

            for (TreeNode<T> child : current.getChildren()) {
                child.setHeight(currentHeight + 1);
                queue.offer(child);
            }
        }
    }
}
