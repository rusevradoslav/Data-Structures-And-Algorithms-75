package org.example.j_tree.binary_search_tree;

import org.example.j_tree.binary_tree.BinaryTreeNode;

import java.util.*;

/**
 * Binary Search Tree (BST) operations: search, insert, delete, min/max lookup,
 * BST validation, and inorder traversal.
 *
 * <p>BST property: for every node N, all values in the left subtree are strictly
 * less than {@code N.val}, and all values in the right subtree are strictly greater.
 *
 * <p><b>Operations:</b>
 * <ul>
 *   <li>{@link #search} — walk left if target &lt; current, right if target &gt; current</li>
 *   <li>{@link #insert} — same navigation as search; insert at the first null slot</li>
 *   <li>{@link #delete} — three cases: leaf, one child, two children (replace with inorder successor)</li>
 *   <li>{@link #findMin} — follow left children to the leftmost node</li>
 *   <li>{@link #findMax} — follow right children to the rightmost node</li>
 *   <li>{@link #isValidBST} — DFS with a valid range [min, max] narrowed at each step</li>
 *   <li>{@link #inorder} — Left → Root → Right; produces sorted ascending output for a valid BST</li>
 * </ul>
 *
 * <p>Example tree (insert order: 8, 3, 12, 1, 6, 9, 15):
 * <pre>
 *         8
 *        / \
 *       3   12
 *      / \  / \
 *     1   6 9  15
 *
 * Inorder: [1, 3, 6, 8, 9, 12, 15]
 * </pre>
 *
 * <p>Time Complexity: O(h) per operation — O(log n) for a balanced tree, O(n) worst case.
 *
 * <p>Space Complexity: O(h) — recursion stack depth, where h is the tree height.
 */
public class BinarySearchTree<E extends Comparable<E>> {

    public BinaryTreeNode<E> search(BinaryTreeNode<E> node, E value) {
        if (Objects.isNull(node)) {
            return null;
        }
        int cmp = value.compareTo(node.val);
        if (cmp == 0) {
            return node;
        }
        if (cmp < 0) {
            return search(node.left, value);
        }
        return search(node.right, value);
    }

    public BinaryTreeNode<E> insert(BinaryTreeNode<E> node, E value) {
        if (Objects.isNull(node)) {
            return new BinaryTreeNode<>(value);
        }

        int cmp = value.compareTo(node.val);
        if (cmp < 0) {
            node.left = insert(node.left, value);
        } else {
            node.right = insert(node.right, value);
        }
        return node;
    }

    /**
     * Removes the node with the given value from the BST and returns the updated root.
     *
     * <p><b>Navigation:</b> compare {@code value} to {@code node.val} and recurse left or right
     * until the target node is found. If {@code node} is {@code null}, the value is not in the
     * tree — return {@code null}.
     *
     * <p><b>Case 1 — leaf node (no children):</b>
     * <ul>
     *   <li>Both {@link BinaryTreeNode#left} and {@link BinaryTreeNode#right} are {@code null}</li>
     *   <li>Return {@code null} to detach the node from its parent</li>
     * </ul>
     *
     * <p><b>Case 2 — one child:</b>
     * <ul>
     *   <li>Return the surviving child so the parent links directly to it, skipping the deleted node</li>
     * </ul>
     *
     * <p><b>Case 3 — two children:</b>
     * <ul>
     *   <li>Find the inorder successor: the smallest value in the right subtree ({@link #findMin})</li>
     *   <li>Copy that value into the current node</li>
     *   <li>Recursively delete the inorder successor from the right subtree</li>
     * </ul>
     *
     * <p>Example — delete 3 (case 3, inorder successor is 6):
     * <pre>
     *     Before          After
     *       8               8
     *      / \             / \
     *     3   12    →     6   12
     *    / \  / \        /   / \
     *   1   6 9  15     1   9  15
     * </pre>
     */
    public BinaryTreeNode<E> delete(BinaryTreeNode<E> node, E value) {
        if (Objects.isNull(node)) {
            return null;
        }
        int cmp = value.compareTo(node.val);
        if (cmp < 0) {
            node.left = delete(node.left, value);
        } else if (cmp > 0) {
            node.right = delete(node.right, value);
        } else {
            if (Objects.isNull(node.left)) {
                return node.right;
            }
            if (Objects.isNull(node.right)) {
                return node.left;
            }
            E minElelemntFromRightTree = findMin(node.right);
            node.val = minElelemntFromRightTree;
            node.right = delete(node.right, minElelemntFromRightTree);
        }

        return node;
    }

    public E findMin(BinaryTreeNode<E> node) {
        while (Objects.nonNull(node.left)) {
            node = node.left;
        }
        return node.val;
    }

    public E findMax(BinaryTreeNode<E> root) {
        while (Objects.nonNull(root.right)) {
            root = root.right;
        }
        return root.val;
    }

    public boolean isValidBST(BinaryTreeNode<E> root) {
        List<E> result = inorderRecursively(root);
        for (int i = 0; i < result.size() - 1; i++) {
            if (result.get(i).compareTo(result.get(i + 1)) > 0) {
                return false;
            }

        }
        return true;
    }

    public List<E> inorderRecursively(BinaryTreeNode<E> root) {
        if (Objects.isNull(root)) {
            return Collections.emptyList();
        }
        if (Objects.isNull(root.left) && Objects.isNull(root.right)) {
            return Collections.singletonList(root.val);
        }
        List<E> list = new ArrayList<>();
        collectValues(root, list);
        return list;
    }

    public List<E> inorderIteratively(BinaryTreeNode<E> node) {
        if (Objects.isNull(node)) {
            return Collections.emptyList();
        }
        if (Objects.isNull(node.left) && Objects.isNull(node.right)) {
            return Collections.singletonList(node.val);
        }

        List<E> list = new ArrayList<>();
        Deque<BinaryTreeNode<E>> stack = new ArrayDeque<>();
        BinaryTreeNode<E> tempNode = node;

        while (Objects.nonNull(tempNode) || !stack.isEmpty()) {
            while (Objects.nonNull(tempNode)) {
                stack.push(tempNode);
                tempNode = tempNode.left;
            }

            BinaryTreeNode<E> element = stack.pop();
            list.add(element.val);
            tempNode = element.right;
        }

        return list;
    }

    private void collectValues(BinaryTreeNode<E> root, List<E> list) {
        if (Objects.nonNull(root.left)) {
            collectValues(root.left, list);
        }

        list.add(root.val);

        if (Objects.nonNull(root.right)) {
            collectValues(root.right, list);
        }
    }
}
