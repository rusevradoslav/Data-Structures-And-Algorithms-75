package org.example.i_tree.task.binary_tree_bfs;

import org.example.i_tree.task.TreeNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MaximumLevelSumOfABinaryTreeTest {

    private MaximumLevelSumOfABinaryTree solution;

    @BeforeEach
    public void setUp() {
        solution = new MaximumLevelSumOfABinaryTree();
    }

    // --- LeetCode examples ---

    @Test
    @DisplayName("Example 1: [1,7,0,7,-8] → level 2 has max sum (7+0=7)")
    public void testExample1() {
        TreeNode root = new TreeNode(1,
                new TreeNode(7, new TreeNode(7), new TreeNode(-8)),
                new TreeNode(0));

        assertEquals(2, solution.maxLevelSum(root));
    }

    @Test
    @DisplayName("Example 2: [989,null,10250,98693,-89388,null,-32127] → level 2")
    public void testExample2() {
        TreeNode root = new TreeNode(989,
                null,
                new TreeNode(10250,
                        new TreeNode(98693),
                        new TreeNode(-89388, null, new TreeNode(-32127))));

        assertEquals(2, solution.maxLevelSum(root));
    }

    // --- Edge cases ---

    @Test
    @DisplayName("Single node → level 1")
    public void testSingleNode() {
        assertEquals(1, solution.maxLevelSum(new TreeNode(1)));
    }

    @Test
    @DisplayName("Max sum at root level → level 1")
    public void testMaxSumAtRoot() {
        TreeNode root = new TreeNode(10,
                new TreeNode(1),
                new TreeNode(2));

        assertEquals(1, solution.maxLevelSum(root));
    }

    @Test
    @DisplayName("Max sum at deepest level → last level returned")
    public void testMaxSumAtDeepestLevel() {
        //       1
        //      / \
        //     2   3
        //    / \
        //   5   6
        // Level sums: 1, 5, 11 → level 3
        TreeNode root = new TreeNode(1,
                new TreeNode(2, new TreeNode(5), new TreeNode(6)),
                new TreeNode(3));

        assertEquals(3, solution.maxLevelSum(root));
    }

    @Test
    @DisplayName("All negative values → level with least negative sum")
    public void testAllNegativeValues() {
        // Level sums: -1, -5, -2 → max is -1 at level 1
        TreeNode root = new TreeNode(-1,
                new TreeNode(-2),
                new TreeNode(-3));

        assertEquals(1, solution.maxLevelSum(root));
    }

    @Test
    @DisplayName("Tie between levels → smallest level number returned")
    public void testTieBetweenLevels() {
        // Level sums: 5, 2+3=5 → tie, return level 1
        TreeNode root = new TreeNode(5,
                new TreeNode(2),
                new TreeNode(3));

        assertEquals(1, solution.maxLevelSum(root));
    }

    @Test
    @DisplayName("[1,1,0,7,-8,-7,9] → all levels sum to 1, return level 1")
    public void testAllLevelsTied() {
        // Level sums: 1, 1+0=1, 7+(-8)+(-7)+9=1 → all tied, return level 1
        TreeNode root = new TreeNode(1,
                new TreeNode(1, new TreeNode(7), new TreeNode(-8)),
                new TreeNode(0, new TreeNode(-7), new TreeNode(9)));

        assertEquals(1, solution.maxLevelSum(root));
    }
}
