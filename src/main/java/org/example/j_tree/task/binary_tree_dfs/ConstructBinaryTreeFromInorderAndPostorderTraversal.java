package org.example.j_tree.task.binary_tree_dfs;

import org.example.j_tree.task.TreeNode;

import java.util.HashMap;
import java.util.Map;


/**
 * LeetCode 106 — Construct Binary Tree from Inorder and Postorder Traversal (Medium).
 *
 * <p>Given two integer arrays {@code inorder} and {@code postorder} where {@code inorder}
 * is the inorder traversal of a binary tree and {@code postorder} is the postorder traversal
 * of the same tree, construct and return the binary tree.
 *
 * <p><b>Approach — Recursive construction with inorder index map:</b>
 * <ul>
 *   <li>The last element of {@code postorder} is always the root of the current subtree</li>
 *   <li>Store all inorder indices in a {@code HashMap} for O(1) root lookups</li>
 *   <li>Find the root's position in {@code inorder} — everything to its left is the left
 *       subtree, everything to its right is the right subtree</li>
 *   <li>Recurse <b>right before left</b>, decrementing {@code postOrderIndex} each call —
 *       postorder read from the back gives root → right → left, opposite of preorder</li>
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
 * inorder = [9, 3, 15, 20, 7], postorder = [9, 15, 7, 20, 3] → [3, 9, 20, null, null, 15, 7]
 *
 * postorder read backwards: 3, 20, 7, 15, 9
 * postorder[4]=3 is root → inorder splits into [9] (left) and [15, 20, 7] (right)
 * postorder[3]=20 is root of right subtree → inorder splits into [15] (left) and [7] (right)
 * postorder[0]=9 is root of left subtree → inorder [9] has no children
 * </pre>
 *
 * <p>Time Complexity: O(n) — each node is visited once; HashMap gives O(1) index lookup.
 *
 * <p>Space Complexity: O(n) — HashMap stores all inorder indices; recursion stack is O(h).
 */
public class ConstructBinaryTreeFromInorderAndPostorderTraversal {

    private final Map<Integer, Integer> map = new HashMap<>();
    private int postOrderIndex;

    public TreeNode buildTree(int[] inorder, int[] postorder) {
        for (int i = 0; i < inorder.length; i++) {
            map.put(inorder[i], i);
        }

        postOrderIndex = postorder.length - 1;

        return createTree(postorder, 0, inorder.length - 1);
    }

    private TreeNode createTree(int[] postorder, int left, int right) {

        if (left > right) {
            return null;
        }

        int nodeValue = postorder[postOrderIndex--];
        TreeNode root = new TreeNode(nodeValue);
        Integer postorderNodeIndex = map.get(nodeValue);
        root.right = createTree(postorder, postorderNodeIndex + 1, right);
        root.left = createTree(postorder, left, postorderNodeIndex - 1);

        return root;
    }
}
