package org.example.i_tree.task.binary_search_tree;

import org.example.i_tree.task.TreeNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MinimumAbsoluteDifferenceInBSTTest {

    private MinimumAbsoluteDifferenceInBST solution;

    @BeforeEach
    public void setUp() {
        solution = new MinimumAbsoluteDifferenceInBST();
    }

    // --- LeetCode examples ---

    /**
     *       4
     *      / \
     *     2   6
     *    / \
     *   1   3
     * Inorder: [1, 2, 3, 4, 6] → min diff = 1
     */
    @Test
    @DisplayName("Example 1: min diff is 1 between adjacent values")
    public void testExample1() {
        TreeNode root = new TreeNode(4,
                new TreeNode(2, new TreeNode(1), new TreeNode(3)),
                new TreeNode(6));

        assertEquals(1, solution.getMinimumDifference(root));
    }

    /**
     *       1
     *        \
     *         3
     *        /
     *       2
     * Inorder: [1, 2, 3] → min diff = 1
     */
    @Test
    @DisplayName("Example 2: min diff is 1 with unbalanced tree")
    public void testExample2() {
        TreeNode root = new TreeNode(1,
                null,
                new TreeNode(3, new TreeNode(2), null));

        assertEquals(1, solution.getMinimumDifference(root));
    }

    // --- Edge cases ---

    @Test
    @DisplayName("Two nodes only: diff is the single pair distance")
    public void testTwoNodes() {
        TreeNode root = new TreeNode(5, new TreeNode(1), null);
        assertEquals(4, solution.getMinimumDifference(root));
    }

    @Test
    @DisplayName("Large gap between all nodes: returns smallest gap")
    public void testLargeGaps() {
        // Inorder: [10, 100, 200, 500] → min diff = 100
        TreeNode root = new TreeNode(100,
                new TreeNode(10),
                new TreeNode(200, null, new TreeNode(500)));

        assertEquals(90, solution.getMinimumDifference(root));
    }

    @Test
    @DisplayName("Right-skewed BST: min diff between consecutive values")
    public void testRightSkewed() {
        // Inorder: [1, 2, 3, 4] → min diff = 1
        TreeNode root = new TreeNode(1,
                null,
                new TreeNode(2,
                        null,
                        new TreeNode(3,
                                null,
                                new TreeNode(4))));

        assertEquals(1, solution.getMinimumDifference(root));
    }

    @Test
    @DisplayName("Left-skewed BST: min diff between consecutive values")
    public void testLeftSkewed() {
        // Inorder: [1, 3, 6, 10] → min diff = 2
        TreeNode root = new TreeNode(10,
                new TreeNode(6,
                        new TreeNode(3,
                                new TreeNode(1), null),
                        null),
                null);

        assertEquals(2, solution.getMinimumDifference(root));
    }

    @Test
    @DisplayName("Min diff found on right side of tree")
    public void testMinDiffOnRightSide() {
        // Inorder: [1, 50, 100, 101] → min diff = 1 (100 vs 101)
        TreeNode root = new TreeNode(50,
                new TreeNode(1),
                new TreeNode(100, null, new TreeNode(101)));

        assertEquals(1, solution.getMinimumDifference(root));
    }
}
