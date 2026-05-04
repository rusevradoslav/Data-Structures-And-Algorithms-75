package org.example.j_tree.task.binary_search_tree;

import org.example.j_tree.task.TreeNode;

import java.util.Objects;

/**
 * LeetCode 230 — Kth Smallest Element in a BST (Medium).
 *
 * <p>Given the root of a BST and an integer {@code k}, return the k-th smallest
 * value (1-indexed) among all node values in the tree.
 *
 * <p><b>Approach — Inorder traversal with counter:</b>
 * Inorder traversal visits BST nodes in ascending sorted order. A class-level
 * {@code count} is incremented on each visit; when it reaches {@code k} the
 * current node's value is captured in {@code smallest} and recursion unwinds.
 *
 * <p>Example:
 * <pre>
 *       3          Inorder: [1, 2, 3, 4]
 *      / \          k = 1 → 1
 *     1   4         k = 2 → 2
 *      \
 *       2
 * </pre>
 *
 * <p>Time Complexity: O(h + k) — traverses at most h + k nodes.
 * <p>Space Complexity: O(h) — recursion stack depth.
 */
public class KthSmallestElementInBST {
    int smallest = 0;
    int count = 0;

    public int kthSmallest(TreeNode root, int k) {
        count = 0;
        findKthSmallestElement(root, k);
        return smallest;
    }

    private void findKthSmallestElement(TreeNode root, int k) {
        if (Objects.isNull(root)) {
            return;
        }
        findKthSmallestElement(root.left, k);

        count++;
        if (count == k) {
            smallest = root.val;
            return;
        }
        findKthSmallestElement(root.right, k);
    }

}
