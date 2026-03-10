package org.example.j_tree.task.binary_tree_dfs;

import org.example.j_tree.task.TreeNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CountGoodNodesInBinaryTest {

    private CountGoodNodesInBinary solution;

    @BeforeEach
    public void setUp() {
        solution = new CountGoodNodesInBinary();
    }

    // --- LeetCode examples ---

    @Test
    @DisplayName("Example 1: [3,1,4,3,null,1,5] → 4")
    public void testExample1() {
        TreeNode root = new TreeNode(3,
                new TreeNode(1,
                        new TreeNode(3), null),
                new TreeNode(4,
                        new TreeNode(1),
                        new TreeNode(5)));

        assertEquals(4, solution.goodNodes(root));
    }

    @Test
    @DisplayName("Example 2: [3,3,null,4,2] → 3")
    public void testExample2() {
        TreeNode root = new TreeNode(3,
                new TreeNode(3,
                        new TreeNode(4),
                        new TreeNode(2)),
                null);

        assertEquals(3, solution.goodNodes(root));
    }

    @Test
    @DisplayName("Example 3: [1] → 1")
    public void testExample3() {
        assertEquals(1, solution.goodNodes(new TreeNode(1)));
    }

    // --- Edge cases ---

    @Test
    @DisplayName("Null root → 0")
    public void testNullRoot() {
        assertEquals(0, solution.goodNodes(null));
    }

    @Test
    @DisplayName("Single node → 1 (root is always good)")
    public void testSingleNode() {
        assertEquals(1, solution.goodNodes(new TreeNode(5)));
    }

    @Test
    @DisplayName("Two-node tree: left child smaller than root → only root is good")
    public void testTwoNodeLeftChildSmaller() {
        TreeNode root = new TreeNode(5, new TreeNode(3), null);
        assertEquals(1, solution.goodNodes(root));
    }

    @Test
    @DisplayName("Two-node tree: right child smaller than root → only root is good")
    public void testTwoNodeRightChildSmaller() {
        TreeNode root = new TreeNode(5, null, new TreeNode(3));
        assertEquals(1, solution.goodNodes(root));
    }

    @Test
    @DisplayName("All nodes good: strictly increasing path → all count")
    public void testAllNodesGood() {
        // Path values strictly increase: 1 → 2 → 3 → 4
        TreeNode root = new TreeNode(1,
                new TreeNode(2,
                        new TreeNode(3,
                                new TreeNode(4), null),
                        null),
                null);

        assertEquals(4, solution.goodNodes(root));
    }

    @Test
    @DisplayName("Only root is good: all descendants smaller than root")
    public void testOnlyRootGood() {
        TreeNode root = new TreeNode(10,
                new TreeNode(5,
                        new TreeNode(1), null),
                new TreeNode(3));

        assertEquals(1, solution.goodNodes(root));
    }

    @Test
    @DisplayName("Equal values on path count as good")
    public void testEqualValuesAreGood() {
        // All nodes have value 2, so each is >= the max on its path
        TreeNode root = new TreeNode(2,
                new TreeNode(2,
                        new TreeNode(2), null),
                new TreeNode(2));

        assertEquals(4, solution.goodNodes(root));
    }

    @Test
    @DisplayName("Negative values: good node still counted when >= path max")
    public void testNegativeValues() {
        // Root (-1), left child (-2): -2 < -1 → not good
        // Right child (-1): -1 >= -1 → good
        TreeNode root = new TreeNode(-1,
                new TreeNode(-2),
                new TreeNode(-1));

        assertEquals(2, solution.goodNodes(root));
    }
}
