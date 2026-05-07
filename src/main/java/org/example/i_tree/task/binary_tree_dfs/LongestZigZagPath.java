package org.example.i_tree.task.binary_tree_dfs;

import org.example.i_tree.task.TreeNode;

import java.util.Objects;

/**
 * LeetCode 1372 — Longest ZigZag Path in a Binary Tree (Medium).
 *
 * <p>Given a binary tree {@code root}, return the length of the longest zigzag path.
 * A zigzag path alternates directions at each step (left→right or right→left).
 * Length is measured in edges (number of nodes visited - 1).
 *
 * <p><b>Approach 1 — Recursive DFS with direction flag:</b>
 * <ul>
 *   <li>Pass the arrival direction ({@code isLeft}) and current zigzag length ({@code length}) down</li>
 *   <li>One child continues the zigzag ({@code length + 1}), the other resets ({@code length = 1})</li>
 *   <li>When a null node is reached, the path ended at its parent — return {@code length - 1}</li>
 * </ul>
 *
 * <p><b>Approach 2 — Recursive DFS with dual lengths:</b>
 * <ul>
 *   <li>Pass two lengths to every node: {@code leftLen} (zigzag length if we arrived going left)
 *       and {@code rightLen} (zigzag length if we arrived going right)</li>
 *   <li>Going left always passes {@code rightLen + 1} as the child's {@code leftLen} and resets
 *       {@code rightLen} to 0; going right does the mirror</li>
 *   <li>A global {@code result} field is updated at every node — no direction flag, no ternary,
 *       no {@code length - 1} at null</li>
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
        if (Objects.isNull(root)) return 0;
        return maxZigZagV1(root, true, 0);
    }

    private int maxZigZagV1(TreeNode node, boolean isLeft, int length) {
        if (Objects.isNull(node)) return length - 1;
        int leftLength = isLeft ? 1 : length + 1;
        int rightLength = isLeft ? length + 1 : 1;

        int leftResult = maxZigZagV1(node.left, true, leftLength);
        int rightResult = maxZigZagV1(node.right, false, rightLength);
        return Math.max(leftResult, rightResult);
    }


    private int result = 0;

    public int longestZigZagV2(TreeNode root) {
        maxZigZagV2(root, 0, 0);
        return result;
    }

    private void maxZigZagV2(TreeNode node, int leftLen, int rightLen) {
        if (Objects.isNull(node)) {
            return;
        }
        result = Math.max(result, Math.max(leftLen, rightLen));
        int nextLeftLen = rightLen + 1;
        int nextRightLen = leftLen + 1;
        maxZigZagV2(node.left, nextLeftLen, 0);
        maxZigZagV2(node.right, 0, nextRightLen);
    }
}
