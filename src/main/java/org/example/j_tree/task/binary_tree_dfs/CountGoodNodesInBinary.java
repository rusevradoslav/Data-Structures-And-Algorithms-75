package org.example.j_tree.task.binary_tree_dfs;

import org.example.j_tree.task.TreeNode;

import java.util.Objects;

/**
 * LeetCode 1448 — Count Good Nodes in Binary Tree (Medium).
 *
 * <p>Given a binary tree {@code root}, a node X is named <b>good</b> if in the path
 * from root to X there are no nodes with a value <em>greater than</em> X.
 * Returns the number of good nodes in the tree.
 *
 * <p><b>Approach — Recursive DFS:</b>
 * <ul>
 *   <li>Pass the maximum value seen so far ({@code maxSoFar}) down each path</li>
 *   <li>A node is good if {@code node.val >= maxSoFar}; update {@code maxSoFar} when true</li>
 *   <li>Return the count directly — 1 if the current node is good, 0 otherwise, plus the
 *       counts returned from the left and right subtrees</li>
 * </ul>
 *
 * <p>Example:
 * <pre>
 *        3
 *       / \
 *      1   4
 *     /   / \
 *    3   1   5
 *
 * goodNodes → 4  (nodes: 3, 3, 4, 5)
 * </pre>
 *
 * <p>Time Complexity: O(n) — each node is visited exactly once.
 *
 * <p>Space Complexity: O(h) — recursion stack depth, where h is the tree height
 * (O(log n) for a balanced tree, O(n) for a skewed tree).
 */
public class CountGoodNodesInBinary {
    public int goodNodes(TreeNode root) {
        if (Objects.isNull(root)) {
            return 0;
        }

        if (Objects.isNull(root.left) && Objects.isNull(root.right)) {
            return 1;
        }

        return countGoodNodes(root, root.val);
    }

    private int countGoodNodes(TreeNode node, int maxSoFar) {
        if (Objects.isNull(node)) {
            return 0;
        }
        int count = node.val >= maxSoFar ? 1 : 0;
        maxSoFar = Math.max(maxSoFar, node.val);
        count += countGoodNodes(node.left, maxSoFar);
        count += countGoodNodes(node.right, maxSoFar);
        return count;
    }
}
