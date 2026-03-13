package org.example.j_tree.binary_tree;

import org.example.j_tree.binary_tree.dfs.InorderBinaryTreeTraversalImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InorderBinaryTreeTraversalImplTest {

    private BinaryTreeNode<Integer> root;
    private InorderBinaryTreeTraversalImpl<Integer> inorder;

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

        inorder = new InorderBinaryTreeTraversalImpl<>();
    }

    // --- Core traversal (Left → Root → Right) ---

    @Test
    @DisplayName("Inorder recursive: [3, 5, 7, 10, 15, 20]")
    public void testInorderRecursive() {
        assertEquals(List.of(3, 5, 7, 10, 15, 20), inorder.recursive(root));
    }

    @Test
    @DisplayName("Inorder iterative: [3, 5, 7, 10, 15, 20]")
    public void testInorderIterative() {
        assertEquals(List.of(3, 5, 7, 10, 15, 20), inorder.iterative(root));
    }

    // --- Edge cases ---

    @Test
    @DisplayName("Single node returns [10]")
    public void testSingleNode() {
        BinaryTreeNode<Integer> single = new BinaryTreeNode<>(10);
        assertEquals(List.of(10), inorder.recursive(single));
        assertEquals(List.of(10), inorder.iterative(single));
    }

    @Test
    @DisplayName("Null root returns []")
    public void testNullRoot() {
        assertEquals(List.of(), inorder.recursive(null));
        assertEquals(List.of(), inorder.iterative(null));
    }

    @Test
    @DisplayName("Left-skewed tree: [3, 5, 10]")
    public void testLeftSkewedTree() {
        BinaryTreeNode<Integer> skewed = new BinaryTreeNode<>(10);
        skewed.left = new BinaryTreeNode<>(5);
        skewed.left.left = new BinaryTreeNode<>(3);
        assertEquals(List.of(3, 5, 10), inorder.recursive(skewed));
        assertEquals(List.of(3, 5, 10), inorder.iterative(skewed));
    }

    @Test
    @DisplayName("Right-skewed tree: [10, 15, 20]")
    public void testRightSkewedTree() {
        BinaryTreeNode<Integer> skewed = new BinaryTreeNode<>(10);
        skewed.right = new BinaryTreeNode<>(15);
        skewed.right.right = new BinaryTreeNode<>(20);
        assertEquals(List.of(10, 15, 20), inorder.recursive(skewed));
        assertEquals(List.of(10, 15, 20), inorder.iterative(skewed));
    }

    @Test
    @DisplayName("Tree with right subtree having left child: [1, 3, 5, 6, 8]")
    public void testRightSubtreeWithLeftChild() {
        BinaryTreeNode<Integer> node = new BinaryTreeNode<>(5);
        node.left = new BinaryTreeNode<>(3);
        node.right = new BinaryTreeNode<>(8);
        node.left.left = new BinaryTreeNode<>(1);
        node.right.left = new BinaryTreeNode<>(6);

        assertEquals(List.of(1, 3, 5, 6, 8), inorder.recursive(node));
        assertEquals(List.of(1, 3, 5, 6, 8), inorder.iterative(node));
    }
}
