package org.example.j_tree.task.binary_tree_dfs;

import org.example.j_tree.task.TreeNode;

import java.util.Objects;

/**
 * LeetCode 236 — Lowest Common Ancestor of a Binary Tree (Medium).
 *
 * <p>Given a binary tree and two nodes {@code p} and {@code q}, return their lowest
 * common ancestor (LCA) — the deepest node that has both {@code p} and {@code q}
 * as descendants (a node is considered a descendant of itself).
 *
 * <p><b>Approach — Post-order DFS:</b>
 * <ul>
 *   <li>If the current node is {@code null}, return {@code null}</li>
 *   <li>If the current node is {@code p} or {@code q}, return it immediately</li>
 *   <li>Recurse into left and right subtrees</li>
 *   <li>If both sides return non-null, the current node is the LCA</li>
 *   <li>Otherwise bubble up whichever side found something</li>
 * </ul>
 *
 * <p>Example:
 * <pre>
 *         3
 *        / \
 *       5   1
 *      / \ / \
 *     6  2 0  8
 *       / \
 *      7   4
 *
 * p=5, q=1 → LCA is 3  (on opposite sides of root)
 * p=5, q=4 → LCA is 5  (4 is a descendant of 5)
 * </pre>
 *
 * <p>Time Complexity: O(n) — each node is visited at most once.
 *
 * <p>Space Complexity: O(h) — recursion stack depth, where h is the tree height
 * (O(log n) for a balanced tree, O(n) for a skewed tree).
 */
public class LowestCommonAncestor {

    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        return dfs(root, p, q);
    }

    private TreeNode dfs(TreeNode node, TreeNode p, TreeNode q) {
        if (Objects.isNull(node)) {
            return null;
        }

        if (Objects.equals(node.val, p.val) || Objects.equals(node.val, q.val)) {
            return node;
        }

        TreeNode left = dfs(node.left, p, q);
        TreeNode right = dfs(node.right, p, q);

        if (Objects.nonNull(left) && Objects.nonNull(right)) {
            return node;
        }
        if (Objects.nonNull(left)) {
            return left;
        }
        if (Objects.nonNull(right)) {
            return right;
        }
        return null;
    }
}
