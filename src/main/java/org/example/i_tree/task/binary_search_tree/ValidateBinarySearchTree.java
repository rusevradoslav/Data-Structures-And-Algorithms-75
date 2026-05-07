package org.example.i_tree.task.binary_search_tree;

import org.example.i_tree.task.TreeNode;

import java.util.Objects;

/**
 * LeetCode 98 — Validate Binary Search Tree (Medium).
 *
 * <p>Given the root of a binary tree, determine if it is a valid BST.
 * A valid BST requires every node's value to be strictly greater than all
 * values in its left subtree and strictly less than all values in its right
 * subtree — not just its immediate children.
 *
 * <p><b>Approach — Recursive bounds propagation:</b>
 * Each node is validated against a valid range {@code (min, max)}. Going left
 * tightens the upper bound to the current node's value; going right tightens
 * the lower bound. A {@code null} bound means no constraint on that side.
 *
 * <p>Example:
 * <pre>
 *       5          valid:   1 &lt; 3 &lt; 5,   5 &lt; 7
 *      / \          invalid: 6 is in left subtree of 5 but 6 &gt; 5
 *     3   7
 *    / \
 *   1   6  ← violates global max (must be &lt; 5)
 * </pre>
 *
 * <p>Time Complexity: O(n) — visits every node exactly once.
 * <p>Space Complexity: O(h) — recursion stack depth.
 */
public class ValidateBinarySearchTree {

    public boolean isValidBST(TreeNode root) {
        if (Objects.isNull(root)) {
            return false;
        }
        return validate(root, null, null);
    }

    private boolean validate(TreeNode node, Integer min, Integer max) {
        if (Objects.isNull(node)) {
            return true;
        }

        if (Objects.nonNull(min) && node.val <= min) {
            return false;
        }

        if (Objects.nonNull(max) && node.val >= max) {
            return false;
        }

        return validate(node.left, min, node.val) && validate(node.right, node.val, max);
    }
}
