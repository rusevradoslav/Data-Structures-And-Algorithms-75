package org.example.i_tree.task.binary_tree_dfs;

import org.example.i_tree.task.TreeNode;

import java.util.HashMap;
import java.util.Map;

/**
 * LeetCode 105 — Construct Binary Tree from Preorder and Inorder Traversal (Medium).
 *
 * <p>Given two integer arrays {@code preorder} and {@code inorder} where {@code preorder}
 * is the preorder traversal of a binary tree and {@code inorder} is the inorder traversal
 * of the same tree, construct and return the binary tree.
 *
 * <p><b>Approach — Recursive construction with inorder index map:</b>
 * <ul>
 *   <li>The first element of {@code preorder} is always the root of the current subtree</li>
 *   <li>Store all inorder indices in a {@code HashMap} for O(1) root lookups</li>
 *   <li>Find the root's position in {@code inorder} — everything to its left is the left
 *       subtree, everything to its right is the right subtree</li>
 *   <li>Recurse with updated left/right inorder boundaries, advancing {@code preOrderIndex}
 *       on each call to consume the next root from preorder</li>
 * </ul>
 *
 * <p>Example:
 * <pre>
 *       3
 *      / \
 *     9  20
 *        / \
 *       15   7
 *
 * preorder = [3, 9, 20, 15, 7], inorder = [9, 3, 15, 20, 7] → [3, 9, 20, null, null, 15, 7]
 *
 * preorder[0]=3 is root → inorder splits into [9] (left) and [15, 20, 7] (right)
 * preorder[1]=9 is root of left subtree → inorder [9] has no children
 * preorder[2]=20 is root of right subtree → inorder splits into [15] (left) and [7] (right)
 * </pre>
 *
 * <p>Time Complexity: O(n) — each node is visited once; HashMap gives O(1) index lookup.
 *
 * <p>Space Complexity: O(n) — HashMap stores all inorder indices; recursion stack is O(h).
 */
public class ConstructBinaryTreeFromPreorderAndInorderTraversal {

    private final Map<Integer, Integer> map = new HashMap<>();
    private int preorderIndex = 0;

    public TreeNode buildTree(int[] preorder, int[] inorder) {
        for (int i = 0; i < inorder.length; i++) {
            map.put(inorder[i], i);
        }

        return createTree(preorder, 0, inorder.length - 1);
    }

    private TreeNode createTree(int[] preorder, int left, int right) {
        if (left > right) {
            return null;
        }

        int currentValue = preorder[preorderIndex++];
        TreeNode root = new TreeNode(currentValue);
        Integer currentValueInorderIndex = map.get(currentValue);
        root.left = createTree(preorder, left, currentValueInorderIndex - 1);
        root.right = createTree(preorder, currentValueInorderIndex + 1, right);

        return root;
    }
}
