package org.example.j_tree.task.binary_tree_dfs;

import org.example.j_tree.task.TreeNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PathSum3Test {

    private PathSum3 solution;

    @BeforeEach
    public void setUp() {
        solution = new PathSum3();
    }

    // --- LeetCode examples ---

    @Test
    @DisplayName("Example 1: [10,5,-3,3,2,null,11,3,-2,null,1], targetSum=8 → 3")
    public void testExample1() {
        TreeNode root = new TreeNode(10,
                new TreeNode(5,
                        new TreeNode(3,
                                new TreeNode(3),
                                new TreeNode(-2)),
                        new TreeNode(2,
                                null,
                                new TreeNode(1))),
                new TreeNode(-3,
                        null,
                        new TreeNode(11)));

        assertEquals(3, solution.pathSum(root, 8));
        assertEquals(3, solution.pathSumBruteForce(root, 8));
    }

    @Test
    @DisplayName("Example 2: [5,4,8,11,null,13,4,7,2,null,null,5,1], targetSum=22 → 3")
    public void testExample2() {
        TreeNode root = new TreeNode(5,
                new TreeNode(4,
                        new TreeNode(11,
                                new TreeNode(7),
                                new TreeNode(2)),
                        null),
                new TreeNode(8,
                        new TreeNode(13),
                        new TreeNode(4,
                                new TreeNode(5),
                                new TreeNode(1))));

        assertEquals(3, solution.pathSum(root, 22));
        assertEquals(3, solution.pathSumBruteForce(root, 22));
    }

    // --- Edge cases ---

    @Test
    @DisplayName("Null root → 0")
    public void testNullRoot() {
        assertEquals(0, solution.pathSum(null, 8));
        assertEquals(0, solution.pathSumBruteForce(null, 8));
    }

    @Test
    @DisplayName("Single node equals targetSum → 1")
    public void testSingleNodeMatch() {
        assertEquals(1, solution.pathSum(new TreeNode(5), 5));
        assertEquals(1, solution.pathSumBruteForce(new TreeNode(5), 5));
    }

    @Test
    @DisplayName("Single node does not equal targetSum → 0")
    public void testSingleNodeNoMatch() {
        assertEquals(0, solution.pathSum(new TreeNode(5), 4));
        assertEquals(0, solution.pathSumBruteForce(new TreeNode(5), 4));
    }

    @Test
    @DisplayName("Path does not start at root → still counted")
    public void testPathNotStartingAtRoot() {
        // Target = 3, valid path: 3 (just the leaf, not from root)
        TreeNode root = new TreeNode(10,
                new TreeNode(5,
                        new TreeNode(3), null),
                null);

        assertEquals(1, solution.pathSum(root, 3));
        assertEquals(1, solution.pathSumBruteForce(root, 3));
    }

    @Test
    @DisplayName("Path does not end at leaf → still counted")
    public void testPathNotEndingAtLeaf() {
        // Target = 15, valid path: 10→5 (does not end at leaf 3)
        TreeNode root = new TreeNode(10,
                new TreeNode(5,
                        new TreeNode(3), null),
                null);

        assertEquals(1, solution.pathSum(root, 15));
        assertEquals(1, solution.pathSumBruteForce(root, 15));
    }

    @Test
    @DisplayName("Negative values: path with negatives sums to target")
    public void testNegativeValues() {
        // targetSum = 0 → 2 paths: 1→(-1) and (-1)→1
        TreeNode root = new TreeNode(1,
                new TreeNode(-1,
                        new TreeNode(1), null),
                null);

        assertEquals(2, solution.pathSum(root, 0));
        assertEquals(2, solution.pathSumBruteForce(root, 0));
    }

    @Test
    @DisplayName("Zero-value node extends already-matched path → both paths counted")
    public void testZeroValueNode() {
        // Path "1" sums to 1, path "1→0" also sums to 1 — both valid
        TreeNode root = new TreeNode(1, new TreeNode(0), null);
        assertEquals(2, solution.pathSum(root, 1));
        assertEquals(2, solution.pathSumBruteForce(root, 1));
    }

    @Test
    @DisplayName("No valid path → 0")
    public void testNoValidPath() {
        TreeNode root = new TreeNode(1,
                new TreeNode(2),
                new TreeNode(3));

        assertEquals(0, solution.pathSum(root, 100));
        assertEquals(0, solution.pathSumBruteForce(root, 100));
    }
}
