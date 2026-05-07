package org.example.i_tree.task.binary_tree_dfs;

import org.example.i_tree.task.TreeNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * LeetCode 872 — Leaf-Similar Trees (Easy).
 *
 * <p>Given the roots of two binary trees, returns {@code true} if the two trees
 * have the same leaf value sequence — the values of all leaf nodes read left to right.
 *
 * <p><b>Approach — Recursive DFS:</b>
 * <ul>
 *   <li>Traverse each tree with DFS and collect leaf values into a list</li>
 *   <li>A node is a leaf when both its left and right children are {@code null}</li>
 *   <li>Return {@code true} if the two leaf lists are equal</li>
 * </ul>
 *
 * <p>Example:
 * <pre>
 *     Tree 1:          Tree 2:
 *         3                3
 *        / \              / \
 *       5   1            5   1
 *      / \ / \          / \ / \
 *     6  2 9  8        6  7 4  2
 *       / \                   / \
 *      7   4                 9   8
 *
 * leafSimilar → true  (both sequences: [6, 7, 4, 9, 8])
 * </pre>
 *
 * <p>Time Complexity: O(n1 + n2) — each node in both trees is visited exactly once.
 *
 * <p>Space Complexity: O(h1 + h2 + L) — recursion stack depths plus the leaf lists,
 * where h is the height of each tree and L is the number of leaves.
 */
public class LeafSimilarTrees {
    public boolean leafSimilar(TreeNode root1, TreeNode root2) {
        if (Objects.isNull(root1) && Objects.isNull(root2)) {
            return true;
        }
        List<Integer> firstList = new ArrayList<>();
        collectLeafs(root1, firstList);

        List<Integer> secondList = new ArrayList<>();
        collectLeafs(root2, secondList);

        return firstList.equals(secondList);
    }

    private void collectLeafs(TreeNode treeNode, List<Integer> list) {
        if (Objects.isNull(treeNode)) {
            return;
        }
        if (Objects.isNull(treeNode.left) && Objects.isNull(treeNode.right)) {
            list.add(treeNode.val);
        }
        if (Objects.nonNull(treeNode.left)) {
            collectLeafs(treeNode.left, list);
        }
        if (Objects.nonNull(treeNode.right)) {
            collectLeafs(treeNode.right, list);
        }
    }
}
