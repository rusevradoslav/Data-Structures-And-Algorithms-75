package org.example.j_tree.task.binary_tree_dfs;

import org.example.j_tree.task.TreeNode;

import java.util.Objects;

/**
 * LeetCode 1372 — Longest ZigZag Path in a Binary Tree (Medium).
 *
 * <p>Given a binary tree {@code root}, return the length of the longest zigzag path.
 * A zigzag path alternates directions at each step (left→right or right→left).
 * Length is measured in edges (number of nodes visited - 1).
 *
 * <p><b>Approach — Recursive DFS:</b>
 * <ul>
 *   <li>Pass the arrival direction ({@code isLeft}) and current zigzag length ({@code length}) down</li>
 *   <li>One child continues the zigzag ({@code length + 1}), the other resets ({@code length = 1})</li>
 *   <li>When a null node is reached, the path ended at its parent — return {@code length - 1}</li>
 * </ul>
 *
 * <p>Example:
 * <pre>
 *     1
 *      \
 *       1
 *      / \
 *     1   1
 *
 * longestZigZag → 2  (path: root → right → left)
 * </pre>
 *
 * <p>Time Complexity: O(n) — each node is visited exactly once.
 *
 * <p>Space Complexity: O(h) — recursion stack depth, where h is the tree height
 * (O(log n) for a balanced tree, O(n) for a skewed tree).
 */
public class LongestZigZagPath {

    public int longestZigZag(TreeNode root) {
        if (Objects.isNull(root)) {
            return 0;
        }
        return dfs(root, true, 0);
    }

    private int dfs(TreeNode node, boolean isLeft, int length) {
        if (Objects.isNull(node)) {
            return length - 1;
        }

        int leftLength = isLeft ? 1 : length + 1;
        int rightLength = isLeft ? length + 1 : 1;

        int left = dfs(node.left, true, leftLength);
        int right = dfs(node.right, false, rightLength);

        return Math.max(left, right);
    }
}
