package org.example.i_tree.task.binary_tree_bfs;

import org.example.i_tree.task.TreeNode;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Objects;

/**
 * LeetCode 1161 — Maximum Level Sum of a Binary Tree (Medium).
 *
 * <p>Given the root of a binary tree, return the smallest level number
 * (1-indexed) whose level sum is maximum. If multiple levels share the
 * same maximum sum, the smallest level number is returned.
 *
 * <p><b>Approach — BFS level-order traversal:</b>
 * <ul>
 *   <li>Process nodes level by level using a queue</li>
 *   <li>Accumulate the sum of all node values at the current level</li>
 *   <li>Update the result only when a strictly greater level sum is found,
 *       ensuring ties keep the smallest level number</li>
 * </ul>
 *
 * <p>Example:
 * <pre>
 *        1          ← sum = 1
 *       / \
 *      7   0        ← sum = 7  ← maximum
 *     / \
 *    7  -8          ← sum = -1
 *
 * Maximum level sum is 7 at level 2 → return 2
 * </pre>
 *
 * <p>Time Complexity: O(n) — each node is visited exactly once.
 *
 * <p>Space Complexity: O(w) — where w is the maximum width of the tree
 * (maximum number of nodes at any single level).
 */
public class MaximumLevelSumOfABinaryTree {
    public int maxLevelSum(TreeNode root) {
        if (Objects.isNull(root)) {
            return 0;
        }

        int result = 0;
        int maxSum = Integer.MIN_VALUE;
        Deque<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);

        int levelCounter = 1;
        while (!queue.isEmpty()) {
            int size = queue.size();
            int tempSum = 0;
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                tempSum += node.val;
                if (Objects.nonNull(node.left)) {
                    queue.offer(node.left);
                }
                if (Objects.nonNull(node.right)) {
                    queue.offer(node.right);
                }
            }

            if (tempSum > maxSum) {
                maxSum = tempSum;
                result = levelCounter;
            }
            levelCounter++;
        }
        return result;
    }
}
