package org.example.i_tree.binary_search_tree;

import org.example.i_tree.binary_tree.BinaryTreeNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BinarySearchTreeTest {

    private BinaryTreeNode<Integer> root;
    private BinarySearchTree<Integer> bst;

    /**
     *         8
     *        / \
     *       3   12
     *      / \  / \
     *     1   6 9  15
     */
    @BeforeEach
    public void setUp() {
        root = new BinaryTreeNode<>(8);
        root.left = new BinaryTreeNode<>(3);
        root.right = new BinaryTreeNode<>(12);
        root.left.left = new BinaryTreeNode<>(1);
        root.left.right = new BinaryTreeNode<>(6);
        root.right.left = new BinaryTreeNode<>(9);
        root.right.right = new BinaryTreeNode<>(15);

        bst = new BinarySearchTree<>();
    }

    // --- search ---

    @Test
    @DisplayName("Search for leaf 1: returns node with no children")
    public void testSearchLeaf() {
        BinaryTreeNode<Integer> result = bst.search(root, 1);
        assertEquals(1, result.val);
        assertNull(result.left);
        assertNull(result.right);
    }

    @Test
    @DisplayName("Search for internal node 3: returns node with correct children 1 and 6")
    public void testSearchInternalNode() {
        BinaryTreeNode<Integer> result = bst.search(root, 3);
        assertEquals(3, result.val);
        assertEquals(1, result.left.val);
        assertEquals(6, result.right.val);
    }

    @Test
    @DisplayName("Search for non-existing value returns null")
    public void testSearchNotFound() {
        assertNull(bst.search(root, 99));
    }

    @Test
    @DisplayName("Search in null tree returns null")
    public void testSearchNullTree() {
        assertNull(bst.search(null, 8));
    }

    // --- insert ---

    @Test
    @DisplayName("Insert 5: placed as left child of 6")
    public void testInsertBecomesLeftChild() {
        BinaryTreeNode<Integer> result = bst.insert(root, 5);
        assertEquals(5, result.left.right.left.val);
        assertNull(result.left.right.left.left);
        assertNull(result.left.right.left.right);
    }

    @Test
    @DisplayName("Insert 20: placed as right child of 15")
    public void testInsertBecomesRightChild() {
        BinaryTreeNode<Integer> result = bst.insert(root, 20);
        assertEquals(20, result.right.right.right.val);
        assertNull(result.right.right.right.left);
        assertNull(result.right.right.right.right);
    }

    @Test
    @DisplayName("Insert into null tree: returns single root node with no children")
    public void testInsertIntoEmptyTree() {
        BinaryTreeNode<Integer> result = bst.insert(null, 10);
        assertEquals(10, result.val);
        assertNull(result.left);
        assertNull(result.right);
    }

    @Test
    @DisplayName("Insert duplicate 8: tree structure unchanged")
    public void testInsertDuplicate() {
        BinaryTreeNode<Integer> result = bst.insert(root, 8);
        assertEquals(3, result.left.val);
        assertEquals(12, result.right.val);
        assertNull(result.left.left.left);
    }

    // --- delete ---

    @Test
    @DisplayName("Delete leaf 1 — case 1: parent's left pointer becomes null")
    public void testDeleteLeaf() {
        BinaryTreeNode<Integer> result = bst.delete(root, 1);
        assertNull(result.left.left);
        assertEquals(3, result.left.val);
        assertEquals(6, result.left.right.val);
    }

    @Test
    @DisplayName("Delete node 12 with two children — case 3: replaced by inorder successor 15, which loses its left child")
    public void testDeleteNodeWithTwoChildren() {
        BinaryTreeNode<Integer> result = bst.delete(root, 12);
        assertEquals(15, result.right.val);
        assertEquals(9, result.right.left.val);
        assertNull(result.right.right);
    }

    @Test
    @DisplayName("Delete root 8 with two children — case 3: new root is inorder successor 9")
    public void testDeleteRoot() {
        BinaryTreeNode<Integer> result = bst.delete(root, 8);
        assertEquals(9, result.val);
        assertNull(result.right.left);
    }

    @Test
    @DisplayName("Delete non-existing value: tree structure unchanged")
    public void testDeleteNonExisting() {
        BinaryTreeNode<Integer> result = bst.delete(root, 99);
        assertEquals(8, result.val);
        assertEquals(3, result.left.val);
        assertEquals(12, result.right.val);
    }

    // --- findMin ---

    @Test
    @DisplayName("findMin returns 1 (leftmost node)")
    public void testFindMin() {
        assertEquals(1, bst.findMin(root));
    }

    @Test
    @DisplayName("findMin on single-node tree returns that node's value")
    public void testFindMinSingleNode() {
        assertEquals(5, bst.findMin(new BinaryTreeNode<>(5)));
    }

    // --- findMax ---

    @Test
    @DisplayName("findMax returns 15 (rightmost node)")
    public void testFindMax() {
        assertEquals(15, bst.findMax(root));
    }

    @Test
    @DisplayName("findMax on single-node tree returns that node's value")
    public void testFindMaxSingleNode() {
        assertEquals(5, bst.findMax(new BinaryTreeNode<>(5)));
    }

    // --- findMin null check ---

    @Test
    @DisplayName("findMin on null tree returns null")
    public void testFindMinNullTree() {
        assertNull(bst.findMin(null));
    }

    // --- findMax null check ---

    @Test
    @DisplayName("findMax on null tree returns null")
    public void testFindMaxNullTree() {
        assertNull(bst.findMax(null));
    }

    // --- findInorderSuccessor ---

    @Test
    @DisplayName("Inorder successor of 8 (root) is 9")
    public void testInorderSuccessorOfRoot() {
        assertEquals(9, bst.findInorderSuccessor(root, 8).val);
    }

    @Test
    @DisplayName("Inorder successor of 6 is 8 (ancestor)")
    public void testInorderSuccessorAncestor() {
        assertEquals(8, bst.findInorderSuccessor(root, 6).val);
    }

    @Test
    @DisplayName("Inorder successor of 1 is 3")
    public void testInorderSuccessorLeaf() {
        assertEquals(3, bst.findInorderSuccessor(root, 1).val);
    }

    @Test
    @DisplayName("Inorder successor of 15 (max) is null")
    public void testInorderSuccessorOfMax() {
        assertNull(bst.findInorderSuccessor(root, 15));
    }

    // --- findInorderPredecessor ---

    @Test
    @DisplayName("Inorder predecessor of 9 is 8 (root)")
    public void testInorderPredecessorOfNine() {
        assertEquals(8, bst.findInorderPredecessor(root, 9).val);
    }

    @Test
    @DisplayName("Inorder predecessor of 8 (root) is 6")
    public void testInorderPredecessorOfRoot() {
        assertEquals(6, bst.findInorderPredecessor(root, 8).val);
    }

    @Test
    @DisplayName("Inorder predecessor of 15 is 12")
    public void testInorderPredecessorLeaf() {
        assertEquals(12, bst.findInorderPredecessor(root, 15).val);
    }

    @Test
    @DisplayName("Inorder predecessor of 1 (min) is null")
    public void testInorderPredecessorOfMin() {
        assertNull(bst.findInorderPredecessor(root, 1));
    }

    // --- floor ---

    @Test
    @DisplayName("Floor of 7 (between 6 and 8) is 6")
    public void testFloorBetweenValues() {
        assertEquals(6, bst.floor(root, 7).val);
    }

    @Test
    @DisplayName("Floor of exact match 9 is 9")
    public void testFloorExactMatch() {
        assertEquals(9, bst.floor(root, 9).val);
    }

    @Test
    @DisplayName("Floor of 0 (smaller than all values) is null")
    public void testFloorSmallerThanAll() {
        assertNull(bst.floor(root, 0));
    }

    // --- ceiling ---

    @Test
    @DisplayName("Ceiling of 7 (between 6 and 8) is 8")
    public void testCeilingBetweenValues() {
        assertEquals(8, bst.ceiling(root, 7).val);
    }

    @Test
    @DisplayName("Ceiling of exact match 9 is 9")
    public void testCeilingExactMatch() {
        assertEquals(9, bst.ceiling(root, 9).val);
    }

    @Test
    @DisplayName("Ceiling of 16 (larger than all values) is null")
    public void testCeilingLargerThanAll() {
        assertNull(bst.ceiling(root, 16));
    }

    // --- isValidBST ---

    @Test
    @DisplayName("Valid BST returns true")
    public void testIsValidBST() {
        assertTrue(bst.isValidBST(root));
    }

    @Test
    @DisplayName("Right child smaller than root: invalid BST returns false")
    public void testRightChildViolatesRoot() {
        BinaryTreeNode<Integer> invalid = new BinaryTreeNode<>(8);
        invalid.left = new BinaryTreeNode<>(3);
        invalid.right = new BinaryTreeNode<>(12);
        invalid.right.left = new BinaryTreeNode<>(7);
        assertFalse(bst.isValidBST(invalid));
    }

    @Test
    @DisplayName("Locally valid but globally invalid BST returns false")
    public void testLocallyValidGloballyInvalid() {
        BinaryTreeNode<Integer> invalid = new BinaryTreeNode<>(5);
        invalid.left = new BinaryTreeNode<>(3);
        invalid.right = new BinaryTreeNode<>(4);
        assertFalse(bst.isValidBST(invalid));
    }

    @Test
    @DisplayName("Single-node tree is a valid BST")
    public void testSingleNodeIsValidBST() {
        assertTrue(bst.isValidBST(new BinaryTreeNode<>(1)));
    }

    // --- inorder ---

    @Test
    @DisplayName("Inorder returns sorted values [1, 3, 6, 8, 9, 12, 15]")
    public void testInorderRecursively() {
        assertEquals(List.of(1, 3, 6, 8, 9, 12, 15), bst.inorderRecursively(root));
    }

    @Test
    @DisplayName("Inorder on null tree returns []")
    public void testInorderRecursivelyNullTree() {
        assertEquals(List.of(), bst.inorderRecursively(null));
    }

    @Test
    @DisplayName("Inorder on single-node tree returns [8]")
    public void testInorderRecursivelySingleNode() {
        assertEquals(List.of(8), bst.inorderRecursively(new BinaryTreeNode<>(8)));
    }

    // --- inorderIteratively ---

    @Test
    @DisplayName("Inorder iteratively returns sorted values [1, 3, 6, 8, 9, 12, 15]")
    public void testInorderIteratively() {
        assertEquals(List.of(1, 3, 6, 8, 9, 12, 15), bst.inorderIteratively(root));
    }

    @Test
    @DisplayName("Inorder iteratively on null tree returns []")
    public void testInorderIterativelyNullTree() {
        assertEquals(List.of(), bst.inorderIteratively(null));
    }

    @Test
    @DisplayName("Inorder iteratively on single-node tree returns [8]")
    public void testInorderIterativelySingleNode() {
        assertEquals(List.of(8), bst.inorderIteratively(new BinaryTreeNode<>(8)));
    }

    @Test
    @DisplayName("Inorder iteratively on left-skewed tree returns [1, 3, 8]")
    public void testInorderIterativelyLeftSkewed() {
        BinaryTreeNode<Integer> skewed = new BinaryTreeNode<>(8);
        skewed.left = new BinaryTreeNode<>(3);
        skewed.left.left = new BinaryTreeNode<>(1);
        assertEquals(List.of(1, 3, 8), bst.inorderIteratively(skewed));
    }

    @Test
    @DisplayName("Inorder iteratively on right-skewed tree returns [8, 12, 15]")
    public void testInorderIterativelyRightSkewed() {
        BinaryTreeNode<Integer> skewed = new BinaryTreeNode<>(8);
        skewed.right = new BinaryTreeNode<>(12);
        skewed.right.right = new BinaryTreeNode<>(15);
        assertEquals(List.of(8, 12, 15), bst.inorderIteratively(skewed));
    }

    @Test
    @DisplayName("Inorder iteratively matches recursive result")
    public void testInorderIterativelyMatchesRecursive() {
        assertEquals(bst.inorderRecursively(root), bst.inorderIteratively(root));
    }
}
