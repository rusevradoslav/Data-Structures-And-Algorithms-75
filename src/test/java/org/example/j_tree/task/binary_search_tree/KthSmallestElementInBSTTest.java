package org.example.j_tree.task.binary_search_tree;

import org.example.j_tree.task.TreeNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class KthSmallestElementInBSTTest {

    private KthSmallestElementInBST solution;

    /**
     *       5
     *      / \
     *     3   6
     *    / \    \
     *   2   4    7
     *  /
     * 1
     */
    @BeforeEach
    public void setUp() {
        solution = new KthSmallestElementInBST();
    }

    // --- LeetCode examples ---

    @Test
    @DisplayName("Example 1: root = [3,1,4,null,2], k = 1 → 1")
    public void testExample1() {
        TreeNode root = new TreeNode(3,
                new TreeNode(1, null, new TreeNode(2)),
                new TreeNode(4));

        assertEquals(1, solution.kthSmallest(root, 1));
    }

    @Test
    @DisplayName("Example 2: root = [5,3,6,2,4,null,null,1], k = 3 → 3")
    public void testExample2() {
        TreeNode root = new TreeNode(5,
                new TreeNode(3, new TreeNode(2, new TreeNode(1), null), new TreeNode(4)),
                new TreeNode(6));

        assertEquals(3, solution.kthSmallest(root, 3));
    }

    // --- Core Functionality ---

    @Test
    @DisplayName("Find the smallest element (k=1) in a balanced tree")
    public void testSmallestElement() {
        TreeNode root = new TreeNode(5,
                new TreeNode(3, new TreeNode(2), new TreeNode(4)),
                new TreeNode(6, null, new TreeNode(7)));

        assertEquals(2, solution.kthSmallest(root, 1));
    }

    @Test
    @DisplayName("Find the largest element (k=N) in a balanced tree")
    public void testLargestElement() {
        TreeNode root = new TreeNode(5,
                new TreeNode(3, new TreeNode(2), new TreeNode(4)),
                new TreeNode(6, null, new TreeNode(7)));

        // Tree has 6 nodes: 2, 3, 4, 5, 6, 7
        assertEquals(7, solution.kthSmallest(root, 6));
    }

    // --- Edge cases ---

    @Test
    @DisplayName("Single node tree, k = 1 → node value")
    public void testSingleNode() {
        TreeNode root = new TreeNode(10);
        assertEquals(10, solution.kthSmallest(root, 1));
    }

    @Test
    @DisplayName("Left-skewed tree: find k-th element")
    public void testLeftSkewed() {
        // 3 -> 2 -> 1
        TreeNode root = new TreeNode(3,
                new TreeNode(2, new TreeNode(1), null),
                null);

        assertEquals(1, solution.kthSmallest(root, 1));
        assertEquals(2, solution.kthSmallest(root, 2));
        assertEquals(3, solution.kthSmallest(root, 3));
    }

    @Test
    @DisplayName("Right-skewed tree: find k-th element")
    public void testRightSkewed() {
        // 1 -> 2 -> 3
        TreeNode root = new TreeNode(1,
                null,
                new TreeNode(2, null, new TreeNode(3)));

        assertEquals(1, solution.kthSmallest(root, 1));
        assertEquals(2, solution.kthSmallest(root, 2));
        assertEquals(3, solution.kthSmallest(root, 3));
    }
}