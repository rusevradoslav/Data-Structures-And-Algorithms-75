package org.example.i_tree.task.binary_tree_dfs;

import org.example.i_tree.task.TreeNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MaximumDepthOfBinaryTreeTest {

    private MaximumDepthOfBinaryTree solution;

    @BeforeEach
    public void setUp() {
        solution = new MaximumDepthOfBinaryTree();
    }

    // --- LeetCode examples ---

    @Test
    @DisplayName("Example 1: [3, 9, 20, null, null, 15, 7] → 3")
    public void testExample1() {
        TreeNode root = new TreeNode(3,
                new TreeNode(9),
                new TreeNode(20,
                        new TreeNode(15),
                        new TreeNode(7)));
        assertEquals(3, solution.maxDepth(root));
    }

    @Test
    @DisplayName("Example 2: [1, null, 2] → 2")
    public void testExample2() {
        TreeNode root = new TreeNode(1, null, new TreeNode(2));
        assertEquals(2, solution.maxDepth(root));
    }

    // --- Edge cases ---

    @Test
    @DisplayName("Null root → 0")
    public void testNullRoot() {
        assertEquals(0, solution.maxDepth(null));
    }

    @Test
    @DisplayName("Single node → 1")
    public void testSingleNode() {
        assertEquals(1, solution.maxDepth(new TreeNode(1)));
    }

    @Test
    @DisplayName("Left-skewed: 1 → 2 → 3 → depth 3")
    public void testLeftSkewed() {
        TreeNode root = new TreeNode(1,
                new TreeNode(2,
                        new TreeNode(3), null),
                null);
        assertEquals(3, solution.maxDepth(root));
    }

    @Test
    @DisplayName("Right-skewed: 1 → 2 → 3 → depth 3")
    public void testRightSkewed() {
        TreeNode root = new TreeNode(1,
                null,
                new TreeNode(2,
                        null, new TreeNode(3)));
        assertEquals(3, solution.maxDepth(root));
    }

    @Test
    @DisplayName("Balanced full tree of depth 3")
    public void testBalancedFullTree() {
        TreeNode root = new TreeNode(1,
                new TreeNode(2,
                        new TreeNode(4),
                        new TreeNode(5)),
                new TreeNode(3,
                        new TreeNode(6),
                        new TreeNode(7)));
        assertEquals(3, solution.maxDepth(root));
    }
}
