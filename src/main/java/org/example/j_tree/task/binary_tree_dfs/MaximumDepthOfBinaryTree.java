package org.example.j_tree.task.binary_tree_dfs;

import org.example.j_tree.task.TreeNode;

import java.util.Objects;

/**
 * LeetCode 104 — Maximum Depth of Binary Tree (Easy).
 *
 * <p>Given the {@code root} of a binary tree, returns its maximum depth —
 * the number of nodes along the longest path from the root node down to
 * the farthest leaf node.
 *
 * <p><b>Approach — Recursive DFS:</b>
 * <ul>
 *   <li>Base case: if the node is {@code null}, return 0</li>
 *   <li>Recursively compute the depth of the left and right subtrees</li>
 *   <li>Return {@code Math.max(leftDepth, rightDepth) + 1}</li>
 * </ul>
 *
 * <p>Example:
 * <pre>
 *        3
 *       / \
 *      9   20
 *         /  \
 *        15   7
 *
 * maxDepth → 3  (path: 3 → 20 → 15  or  3 → 20 → 7)
 * </pre>
 *
 * <p>Time Complexity: O(n) — each node is visited exactly once.
 *
 * <p>Space Complexity: O(h) — recursion stack depth, where h is the height
 * of the tree (O(log n) for a balanced tree, O(n) for a skewed tree).
 */
public class MaximumDepthOfBinaryTree {

    /**
     * Returns the maximum depth of the binary tree rooted at {@code root}.
     *
     * @param root the root of the binary tree, may be {@code null}
     * @return the maximum depth (0 if the tree is empty)
     */
    public int maxDepth(TreeNode root) {
        if (Objects.isNull(root)) {
            return 0;
        }

        if (Objects.isNull(root.left) && Objects.isNull(root.right)) {
            return 1;
        }

        return calculateDepth(root);
    }

    /**
     * Recursively computes the depth of the subtree rooted at {@code node}.
     *
     * @param node the current node (may be {@code null})
     * @return the depth of the subtree (0 if {@code node} is {@code null})
     */
    private int calculateDepth(TreeNode node) {
        if (Objects.isNull(node)) {
            return 0;
        }
        int leftDepth = calculateDepth(node.left);
        int rightDepth = calculateDepth(node.right);
        return Math.max(leftDepth, rightDepth) + 1;
    }
}
