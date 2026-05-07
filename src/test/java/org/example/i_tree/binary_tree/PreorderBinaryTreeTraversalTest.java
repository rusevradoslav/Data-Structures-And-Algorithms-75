package org.example.i_tree.binary_tree;

import org.example.i_tree.binary_tree.dfs.PreorderBinaryTreeTraversal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PreorderBinaryTreeTraversalTest {

    private BinaryTreeNode<Integer> root;
    private PreorderBinaryTreeTraversal<Integer> preorder;

    /**
     *        10
     *       /  \
     *      5    15
     *     / \     \
     *    3   7    20
     */
    @BeforeEach
    public void setUp() {
        root = new BinaryTreeNode<>(10);
        root.left = new BinaryTreeNode<>(5);
        root.right = new BinaryTreeNode<>(15);
        root.left.left = new BinaryTreeNode<>(3);
        root.left.right = new BinaryTreeNode<>(7);
        root.right.right = new BinaryTreeNode<>(20);

        preorder = new PreorderBinaryTreeTraversal<>();
    }

    // --- Core traversal (Root → Left → Right) ---

    @Test
    @DisplayName("Preorder recursive: [10, 5, 3, 7, 15, 20]")
    public void testPreorderRecursive() {
        assertEquals(List.of(10, 5, 3, 7, 15, 20), preorder.recursive(root));
    }

    @Test
    @DisplayName("Preorder iterative: [10, 5, 3, 7, 15, 20]")
    public void testPreorderIterative() {
        assertEquals(List.of(10, 5, 3, 7, 15, 20), preorder.iterative(root));
    }

    // --- Edge cases ---

    @Test
    @DisplayName("Single node returns [10]")
    public void testSingleNode() {
        BinaryTreeNode<Integer> single = new BinaryTreeNode<>(10);
        assertEquals(List.of(10), preorder.recursive(single));
        assertEquals(List.of(10), preorder.iterative(single));
    }

    @Test
    @DisplayName("Null root returns []")
    public void testNullRoot() {
        assertEquals(List.of(), preorder.recursive(null));
        assertEquals(List.of(), preorder.iterative(null));
    }

    @Test
    @DisplayName("Left-skewed tree: [10, 5, 3]")
    public void testLeftSkewedTree() {
        BinaryTreeNode<Integer> skewed = new BinaryTreeNode<>(10);
        skewed.left = new BinaryTreeNode<>(5);
        skewed.left.left = new BinaryTreeNode<>(3);
        assertEquals(List.of(10, 5, 3), preorder.recursive(skewed));
        assertEquals(List.of(10, 5, 3), preorder.iterative(skewed));
    }

    @Test
    @DisplayName("Right-skewed tree: [10, 15, 20]")
    public void testRightSkewedTree() {
        BinaryTreeNode<Integer> skewed = new BinaryTreeNode<>(10);
        skewed.right = new BinaryTreeNode<>(15);
        skewed.right.right = new BinaryTreeNode<>(20);
        assertEquals(List.of(10, 15, 20), preorder.recursive(skewed));
        assertEquals(List.of(10, 15, 20), preorder.iterative(skewed));
    }
}
