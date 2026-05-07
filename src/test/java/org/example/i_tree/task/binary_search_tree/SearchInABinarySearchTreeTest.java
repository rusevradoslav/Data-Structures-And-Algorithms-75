package org.example.i_tree.task.binary_search_tree;

import org.example.i_tree.task.TreeNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class SearchInABinarySearchTreeTest {

    private SearchInABinarySearchTree solution;

    /**
     *       4
     *      / \
     *     2   7
     *    / \
     *   1   3
     */
    @BeforeEach
    public void setUp() {
        solution = new SearchInABinarySearchTree();
    }

    // --- LeetCode examples ---

    @Test
    @DisplayName("Example 1: search(root, 2) → returns subtree rooted at 2 with children 1 and 3")
    public void testExample1() {
        TreeNode root = new TreeNode(4,
                new TreeNode(2, new TreeNode(1), new TreeNode(3)),
                new TreeNode(7));

        TreeNode result = solution.searchBST(root, 2);
        assertEquals(2, result.val);
        assertEquals(1, result.left.val);
        assertEquals(3, result.right.val);
    }

    @Test
    @DisplayName("Example 2: search(root, 5) → value not in tree, returns null")
    public void testExample2() {
        TreeNode root = new TreeNode(4,
                new TreeNode(2, new TreeNode(1), new TreeNode(3)),
                new TreeNode(7));

        assertNull(solution.searchBST(root, 5));
    }

    // --- Edge cases ---

    @Test
    @DisplayName("Search for root value returns the whole tree")
    public void testSearchRoot() {
        TreeNode root = new TreeNode(4,
                new TreeNode(2),
                new TreeNode(7));

        TreeNode result = solution.searchBST(root, 4);
        assertEquals(4, result.val);
        assertEquals(2, result.left.val);
        assertEquals(7, result.right.val);
    }

    @Test
    @DisplayName("Search for leaf node returns node with no children")
    public void testSearchLeaf() {
        TreeNode root = new TreeNode(4,
                new TreeNode(2, new TreeNode(1), new TreeNode(3)),
                new TreeNode(7));

        TreeNode result = solution.searchBST(root, 1);
        assertEquals(1, result.val);
        assertNull(result.left);
        assertNull(result.right);
    }

    @Test
    @DisplayName("Search in null tree returns null")
    public void testSearchNullTree() {
        assertNull(solution.searchBST(null, 4));
    }

    @Test
    @DisplayName("Search in single-node tree for existing value returns that node")
    public void testSingleNodeFound() {
        TreeNode result = solution.searchBST(new TreeNode(5), 5);
        assertEquals(5, result.val);
        assertNull(result.left);
        assertNull(result.right);
    }

    @Test
    @DisplayName("Search in single-node tree for missing value returns null")
    public void testSingleNodeNotFound() {
        assertNull(solution.searchBST(new TreeNode(5), 99));
    }
}
