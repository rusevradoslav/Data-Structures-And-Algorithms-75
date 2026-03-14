package org.example.j_tree.task.binary_tree_dfs;

import org.example.j_tree.task.TreeNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ConstructBinaryTreeFromPreorderAndInorderTraversalTest {

    private ConstructBinaryTreeFromPreorderAndInorderTraversal solution;

    /**
     *       3
     *      / \
     *     9  20
     *        / \
     *       15   7
     */
    @BeforeEach
    public void setUp() {
        solution = new ConstructBinaryTreeFromPreorderAndInorderTraversal();
    }

    // --- LeetCode examples ---

    @Test
    @DisplayName("Example 1: preorder=[3,9,20,15,7], inorder=[9,3,15,20,7] → tree rooted at 3")
    public void testExample1() {
        TreeNode result = solution.buildTree(
                new int[]{3, 9, 20, 15, 7},
                new int[]{9, 3, 15, 20, 7});

        assertEquals(3, result.val);
        assertEquals(9, result.left.val);
        assertEquals(20, result.right.val);
        assertNull(result.left.left);
        assertNull(result.left.right);
        assertEquals(15, result.right.left.val);
        assertEquals(7, result.right.right.val);
    }

    @Test
    @DisplayName("Example 2: preorder=[-1], inorder=[-1] → single node tree")
    public void testExample2() {
        TreeNode result = solution.buildTree(new int[]{-1}, new int[]{-1});

        assertEquals(-1, result.val);
        assertNull(result.left);
        assertNull(result.right);
    }

    // --- Edge cases ---

    @Test
    @DisplayName("Left-skewed tree: preorder=[3,2,1], inorder=[1,2,3] → each node has only left child")
    public void testLeftSkewedTree() {
        TreeNode result = solution.buildTree(
                new int[]{3, 2, 1},
                new int[]{1, 2, 3});

        assertEquals(3, result.val);
        assertEquals(2, result.left.val);
        assertEquals(1, result.left.left.val);
        assertNull(result.right);
    }

    @Test
    @DisplayName("Right-skewed tree: preorder=[1,2,3], inorder=[1,2,3] → each node has only right child")
    public void testRightSkewedTree() {
        TreeNode result = solution.buildTree(
                new int[]{1, 2, 3},
                new int[]{1, 2, 3});

        assertEquals(1, result.val);
        assertEquals(2, result.right.val);
        assertEquals(3, result.right.right.val);
        assertNull(result.left);
    }
}
