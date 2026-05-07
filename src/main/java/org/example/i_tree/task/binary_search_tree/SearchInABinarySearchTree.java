package org.example.i_tree.task.binary_search_tree;

import org.example.i_tree.task.TreeNode;

import java.util.Objects;

/**
 * LeetCode 700 — Search in a Binary Search Tree (Easy).
 *
 * <p>Given the root of a BST and an integer {@code val}, find the node whose value equals
 * {@code val} and return the subtree rooted at that node. If no such node exists, return
 * {@code null}.
 *
 * <p><b>Approach — Iterative BST navigation:</b>
 * <ul>
 *   <li>If {@code root} is {@code null}, the value is not in the tree — return {@code null}</li>
 *   <li>If {@code root.val == val}, return {@code root} (the entire subtree rooted here)</li>
 *   <li>If {@code val < root.val}, move to the left child</li>
 *   <li>If {@code val > root.val}, move to the right child</li>
 * </ul>
 *
 * <p>Example:
 * <pre>
 *       4
 *      / \
 *     2   7      search(root, 2) → returns subtree rooted at 2
 *    / \
 *   1   3
 *
 * Output subtree: [2, 1, 3]
 * </pre>
 *
 * <p>Time Complexity: O(h) — O(log n) for a balanced BST, O(n) for a skewed tree.
 *
 * <p>Space Complexity: O(h) — recursion stack depth, where h is the tree height.
 */
public class SearchInABinarySearchTree {

    public TreeNode searchBST(TreeNode root, int val) {
        TreeNode resultNode = null;
        while (Objects.nonNull(root)) {
            if (Objects.equals(root.val, val)) {
                resultNode = root;
                break;
            }
            if (val < root.val) {
                root = root.left;
            } else {
                root = root.right;
            }
        }
        return resultNode;
    }
}
