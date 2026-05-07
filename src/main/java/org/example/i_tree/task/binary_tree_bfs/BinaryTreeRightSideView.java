package org.example.i_tree.task.binary_tree_bfs;

import org.example.i_tree.task.TreeNode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Objects;

/**
 * LeetCode 199 — Binary Tree Right Side View (Medium).
 *
 * <p>Given the root of a binary tree, return the values of the nodes you can see
 * when looking at the tree from the right side, ordered from top to bottom.
 * A node is visible from the right side if it is the last (rightmost) node on its level.
 *
 * <p><b>Approach — BFS level-order traversal:</b>
 * <ul>
 *   <li>Use a queue to process nodes level by level</li>
 *   <li>Track the number of nodes on the current level ({@code size})</li>
 *   <li>The last node processed in each level ({@code i == size - 1}) is the rightmost
 *       visible node — add its value to the result</li>
 * </ul>
 *
 * <p>Example:
 * <pre>
 *       1          ← visible: 1
 *      / \
 *     2   3        ← visible: 3
 *      \   \
 *       5   4      ← visible: 4
 *
 * Right side view: [1, 3, 4]
 * </pre>
 *
 * <p>Time Complexity: O(n) — each node is visited exactly once.
 *
 * <p>Space Complexity: O(w) — where w is the maximum width of the tree
 * (maximum number of nodes at any single level).
 */
public class BinaryTreeRightSideView {

    public List<Integer> rightSideView(TreeNode root) {
        List<Integer> res = new ArrayList<>();

        if (Objects.isNull(root)) {
            return res;
        }

        Deque<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();

                if (i == size - 1) {
                    res.add(node.val);
                }
                if (Objects.nonNull(node.left)) {
                    queue.offer(node.left);
                }
                if (Objects.nonNull(node.right)) {
                    queue.offer(node.right);
                }
            }
        }
        return res;
    }
}
