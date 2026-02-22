package org.example.j_tree;


import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Objects;

public class Tree<E> implements AbstractTree<E> {
    private E value;
    private Tree<E> parent;
    private List<Tree<E>> children;

    @SafeVarargs
    public Tree(E value, Tree<E>... children) {
        this.value = value;
        this.children = new ArrayList<>();
        for (Tree<E> child : children) {
            this.children.add(child);
            child.parent = this;
        }
    }


    @Override
    public List<E> orderBfs() {
        List<E> result = new ArrayList<>();
        Deque<Tree<E>> queue = new ArrayDeque<>();

        queue.add(this);
        while (!queue.isEmpty()) {
            Tree<E> tree = queue.poll();
            result.add(tree.value);
            for (Tree<E> child : tree.children) {
                queue.offer(child);
            }
        }

        return result;
    }

    @Override
    public List<E> postorderDfs() {
        List<E> result = new ArrayList<>();
        executePostorderDfs(this, result);
        return result;
    }

    private void executePostorderDfs(Tree<E> tree, List<E> result) {
        for (Tree<E> child : tree.children) {
            executePostorderDfs(child, result);
        }
        result.add(tree.value);
    }

    @Override
    public List<E> preorderDfs() {
        List<E> result = new ArrayList<>();
        executePreorderDfs(this, result);
        return result;

    }

    private void executePreorderDfs(Tree<E> tree, List<E> result) {
        result.add(tree.value);
        for (Tree<E> child : tree.children) {
            executePreorderDfs(child, result);
        }
    }

    @Override
    public void addChild(E parentKey, Tree<E> child) {
        Tree<E> parentNode = findNodeByKey(parentKey, this);
        if (Objects.isNull(parentNode)) {
            throw new IllegalArgumentException(String.format("The parent node is null for key: %s", parentKey));
        }
        child.parent = parentNode;
        parentNode.children.add(child);
    }

    @Override
    public void removeNode(E nodeKey) {
        Tree<E> node = findNodeByKey(nodeKey, this);
        if (Objects.isNull(node)) {
            throw new IllegalArgumentException(String.format("The node is null for key: %s", nodeKey));
        }

        node.parent.children.remove(node);
        node.children.clear();
        node.value = null;
    }

    @Override
    public void swap(E firstKey, E secondKey) {
        Tree<E> firstNode = findNodeByKey(firstKey, this);
        Tree<E> secondNode = findNodeByKey(secondKey, this);
        if (Objects.isNull(firstNode)) {
            throw new IllegalArgumentException(String.format("The first node is null for key: %s", firstKey));
        }
        if (Objects.isNull(secondNode)) {
            throw new IllegalArgumentException(String.format("The second node is null for key: %s", secondKey));
        }
        Tree<E> firstNodeParent = firstNode.parent;
        Tree<E> secondNodeParent = secondNode.parent;
        if (Objects.isNull(firstNodeParent)) {
            swapRoot(secondNode);
            return;
        } else if (Objects.isNull(secondNodeParent)) {
            swapRoot(firstNode);
            return;
        }

        if (secondNodeParent == firstNode) {
            swapParentChild(firstNode, secondNode, firstNodeParent);
        } else if (firstNodeParent == secondNode) {
            swapParentChild(secondNode, firstNode, secondNodeParent);
        } else {
            int firstIndex = firstNodeParent.children.indexOf(firstNode);
            int secondIndex = secondNodeParent.children.indexOf(secondNode);
            firstNodeParent.children.set(firstIndex, secondNode);
            secondNodeParent.children.set(secondIndex, firstNode);
            firstNode.parent = secondNodeParent;
            secondNode.parent = firstNodeParent;
        }


    }

    private Tree<E> findNodeByKey(E parentKey, Tree<E> node) {
        if (Objects.isNull(node.value)) {
            return null;
        }
        if (node.value.equals(parentKey)) {
            return node;
        }
        for (Tree<E> child : node.children) {
            Tree<E> found = findNodeByKey(parentKey, child);
            if (Objects.nonNull(found)) {
                return found;
            }
        }

        return null;
    }

    private void swapParentChild(Tree<E> parentNode, Tree<E> childNode, Tree<E> grandParent) {
        int parentIndex = grandParent.children.indexOf(parentNode);
        int childIndex = parentNode.children.indexOf(childNode);

        grandParent.children.set(parentIndex, childNode);
        parentNode.children.set(childIndex, parentNode);

        List<Tree<E>> parentChildren = new ArrayList<>(parentNode.children);
        parentNode.children = new ArrayList<>(childNode.children);
        childNode.children = parentChildren;

        childNode.parent = grandParent;
        parentNode.parent = childNode;
        updateParentRefs(childNode);
        updateParentRefs(parentNode);
    }

    private void updateParentRefs(Tree<E> node) {
        for (Tree<E> child : node.children) {
            child.parent = node;
        }
    }

    private void swapRoot(Tree<E> node) {
        this.parent = null;
        this.value = node.value;
        this.children = node.children;
    }
}



