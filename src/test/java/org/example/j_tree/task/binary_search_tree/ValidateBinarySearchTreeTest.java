package org.example.j_tree.task.binary_search_tree;

import org.example.j_tree.task.TreeNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ValidateBinarySearchTreeTest {

    private ValidateBinarySearchTree solution;

    @BeforeEach
    public void setUp() {
        solution = new ValidateBinarySearchTree();
    }

    // --- LeetCode examples ---

    @Test
    @DisplayName("Example 1: root = [2,1,3] → true")
    public void testExample1() {
        //     2
        //    / \
        //   1   3
        TreeNode root = new TreeNode(2, new TreeNode(1), new TreeNode(3));

        assertTrue(solution.isValidBST(root));
    }

    @Test
    @DisplayName("Example 2: root = [5,1,4,null,null,3,6] → false (right child 4 < root 5)")
    public void testExample2() {
        //       5
        //      / \
        //     1   4
        //        / \
        //       3   6
        TreeNode root = new TreeNode(5,
                new TreeNode(1),
                new TreeNode(4, new TreeNode(3), new TreeNode(6)));

        assertFalse(solution.isValidBST(root));
    }

    // --- Core Functionality ---

    @Test
    @DisplayName("Valid balanced BST")
    public void testValidBalancedBST() {
        //       4
        //      / \
        //     2   6
        //    / \ / \
        //   1  3 5  7
        TreeNode root = new TreeNode(4,
                new TreeNode(2, new TreeNode(1), new TreeNode(3)),
                new TreeNode(6, new TreeNode(5), new TreeNode(7)));

        assertTrue(solution.isValidBST(root));
    }

    @Test
    @DisplayName("Invalid: node in right subtree violates global minimum constraint")
    public void testInvalidGlobalMinViolation() {
        //     5
        //    / \
        //   1   7
        //      / \
        //     3   8   ← 3 is in right subtree of 5 but 3 < 5
        TreeNode root = new TreeNode(5,
                new TreeNode(1),
                new TreeNode(7, new TreeNode(3), new TreeNode(8)));

        assertFalse(solution.isValidBST(root));
    }

    @Test
    @DisplayName("Invalid: node in left subtree violates global maximum constraint")
    public void testInvalidGlobalMaxViolation() {
        //       5
        //      / \
        //     3   7
        //    / \
        //   2   6   ← 6 is in left subtree of 5 but 6 > 5
        TreeNode root = new TreeNode(5,
                new TreeNode(3, new TreeNode(2), new TreeNode(6)),
                new TreeNode(7));

        assertFalse(solution.isValidBST(root));
    }

    @Test
    @DisplayName("Invalid: duplicate values are not allowed in a BST")
    public void testDuplicateValues() {
        //     2
        //    / \
        //   2   3
        TreeNode root = new TreeNode(2, new TreeNode(2), new TreeNode(3));

        assertFalse(solution.isValidBST(root));
    }

    // --- Edge Cases ---

    @Test
    @DisplayName("Single node tree → true")
    public void testSingleNode() {
        assertTrue(solution.isValidBST(new TreeNode(1)));
    }

    @Test
    @DisplayName("Null root → false")
    public void testNullRoot() {
        assertFalse(solution.isValidBST(null));
    }

    @Test
    @DisplayName("Valid left-skewed BST")
    public void testValidLeftSkewed() {
        // 3 → 2 → 1
        TreeNode root = new TreeNode(3,
                new TreeNode(2, new TreeNode(1), null),
                null);

        assertTrue(solution.isValidBST(root));
    }

    @Test
    @DisplayName("Valid right-skewed BST")
    public void testValidRightSkewed() {
        // 1 → 2 → 3
        TreeNode root = new TreeNode(1,
                null,
                new TreeNode(2, null, new TreeNode(3)));

        assertTrue(solution.isValidBST(root));
    }

    @Test
    @DisplayName("Invalid: right child equals parent (strict inequality required)")
    public void testRightChildEqualsParent() {
        //   2
        //    \
        //     2
        TreeNode root = new TreeNode(2, null, new TreeNode(2));

        assertFalse(solution.isValidBST(root));
    }

    @Test
    @DisplayName("Invalid: left child equals parent (strict inequality required)")
    public void testLeftChildEqualsParent() {
        //   2
        //  /
        // 2
        TreeNode root = new TreeNode(2, new TreeNode(2), null);

        assertFalse(solution.isValidBST(root));
    }
}
