package org.example.i_tree.task.binary_search_tree;

import org.example.i_tree.task.TreeNode;


import java.util.Objects;

/**
 * LeetCode 530 — Minimum Absolute Difference in BST (Easy).
 *
 * <p>Given the root of a BST, return the minimum absolute difference between
 * the values of any two different nodes.
 *
 * <p><b>Approach — Inorder traversal with running previous node:</b>
 * Inorder traversal of a BST visits nodes in ascending sorted order, so the
 * minimum difference is always between two adjacent values in that sequence.
 * A class-level {@code previous} pointer tracks the last visited node; after
 * each comparison it is updated to the current node before descending right.
 *
 * <p>Example:
 * <pre>
 *       4          Inorder: [1, 2, 3, 4, 6]
 *      / \         Differences: 1, 1, 1, 2
 *     2   6        → minimum = 1
 *    / \
 *   1   3
 * </pre>
 *
 * <p>Time Complexity: O(n) — visits every node once.
 * <p>Space Complexity: O(h) — recursion stack depth.
 */
public class MinimumAbsoluteDifferenceInBST {

    private int minDiff = Integer.MAX_VALUE;
    private TreeNode previous;

    public int getMinimumDifference(TreeNode root) {
        if (Objects.isNull(root)) {
            return -1;
        }

        evaluateMinNumber(root);
        return minDiff;
    }

    private void evaluateMinNumber(TreeNode current) {
        if (Objects.isNull(current)) {
            return;
        }
        evaluateMinNumber(current.left);
        if (Objects.nonNull(previous)) {
            minDiff = Math.min(minDiff, Math.abs(previous.val - current.val));
        }
        previous = current;
        evaluateMinNumber(current.right);
    }

}
