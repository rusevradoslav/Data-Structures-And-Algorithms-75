package org.example.j_tree.task.binary_search_tree;

import org.example.j_tree.task.TreeNode;

import java.util.Objects;

/**
 * LeetCode 450 — Delete Node in a BST (Medium).
 *
 * <p>Given the root of a BST and a key, delete the node with the given key and return
 * the updated tree's root. The BST property must be preserved after deletion.
 *
 * <p><b>Approach — Recursive BST deletion with in-order successor:</b>
 * <ul>
 *   <li>Navigate the BST using the key to locate the target node</li>
 *   <li>Case 1 — leaf node: return {@code null} to unlink it from its parent</li>
 *   <li>Case 2 — one child: return the non-null child to replace the deleted node</li>
 *   <li>Case 3 — two children: find the in-order successor (minimum of the right subtree),
 *       overwrite the current node's value with it, then recursively delete the successor
 *       from the right subtree</li>
 * </ul>
 *
 * <p>Example:
 * <pre>
 *       5                       5
 *      / \                     / \
 *     3   6    delete(3)  →   4   6
 *    / \    \                /      \
 *   2   4    7              2        7
 *
 * In-order successor of 3 is 4 (min of right subtree {4}).
 * Replace 3 with 4, then remove 4 from right subtree.
 * </pre>
 *
 * <p>Time Complexity: O(h) — O(log n) for a balanced BST, O(n) for a skewed tree.
 *
 * <p>Space Complexity: O(h) — recursion stack depth, where h is the tree height.
 */
public class DeleteNodeInBst {

    public TreeNode deleteNode(TreeNode root, int value) {
        if (Objects.isNull(root)) {
            return null;
        }
        if (value < root.val) {
            root.left = deleteNode(root.left, value);
        } else if (value > root.val) {
            root.right = deleteNode(root.right, value);
        } else {
            if (Objects.isNull(root.left)) {
                return root.right;
            }
            if (Objects.isNull(root.right)) {
                return root.left;
            }
            int minElement = findMinElement(root.right);
            root.val = minElement;
            root.right = deleteNode(root.right, minElement);
        }
        return root;
    }

    private int findMinElement(TreeNode root) {
        if (Objects.isNull(root)) {
            return -1;
        }
        while (Objects.nonNull(root.left)) {
            root = root.left;
        }

        return root.val;
    }
}
