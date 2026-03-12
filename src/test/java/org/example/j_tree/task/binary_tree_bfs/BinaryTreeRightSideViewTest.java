package org.example.j_tree.task.binary_tree_bfs;

import org.example.j_tree.task.TreeNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BinaryTreeRightSideViewTest {

    private BinaryTreeRightSideView solution;

    @BeforeEach
    public void setUp() {
        solution = new BinaryTreeRightSideView();
    }

    // --- LeetCode examples ---

    @Test
    @DisplayName("Example 1: [1,2,3,null,5,null,4] → [1, 3, 4]")
    public void testExample1() {
        TreeNode root = new TreeNode(1,
                new TreeNode(2, null, new TreeNode(5)),
                new TreeNode(3, null, new TreeNode(4)));

        assertEquals(List.of(1, 3, 4), solution.rightSideView(root));
    }

    @Test
    @DisplayName("Example 2: [1,null,3] → [1, 3]")
    public void testExample2() {
        TreeNode root = new TreeNode(1, null, new TreeNode(3));

        assertEquals(List.of(1, 3), solution.rightSideView(root));
    }

    @Test
    @DisplayName("Example 3: [] → []")
    public void testExample3() {
        assertEquals(List.of(), solution.rightSideView(null));
    }

    // --- Edge cases ---

    @Test
    @DisplayName("Single node → [1]")
    public void testSingleNode() {
        assertEquals(List.of(1), solution.rightSideView(new TreeNode(1)));
    }

    @Test
    @DisplayName("Left-skewed tree → each node is visible from right side")
    public void testLeftSkewedTree() {
        TreeNode root = new TreeNode(1,
                new TreeNode(2,
                        new TreeNode(3), null),
                null);

        assertEquals(List.of(1, 2, 3), solution.rightSideView(root));
    }

    @Test
    @DisplayName("Right-skewed tree → each node is visible from right side")
    public void testRightSkewedTree() {
        TreeNode root = new TreeNode(1,
                null,
                new TreeNode(2,
                        null,
                        new TreeNode(3)));

        assertEquals(List.of(1, 2, 3), solution.rightSideView(root));
    }

    @Test
    @DisplayName("Left subtree deeper than right → leftmost deep node visible")
    public void testLeftSubtreeDeeperThanRight() {
        //       1
        //      / \
        //     2   3
        //    /
        //   4
        // Level 3 has only node 4 (left side) → it is the rightmost at that level
        TreeNode root = new TreeNode(1,
                new TreeNode(2, new TreeNode(4), null),
                new TreeNode(3));

        assertEquals(List.of(1, 3, 4), solution.rightSideView(root));
    }

    @Test
    @DisplayName("Complete binary tree → rightmost node per level")
    public void testCompleteBinaryTree() {
        //        1
        //       / \
        //      2   3
        //     / \ / \
        //    4  5 6  7
        TreeNode root = new TreeNode(1,
                new TreeNode(2, new TreeNode(4), new TreeNode(5)),
                new TreeNode(3, new TreeNode(6), new TreeNode(7)));

        assertEquals(List.of(1, 3, 7), solution.rightSideView(root));
    }
}
