package org.example.i_tree.binary_tree;

import org.example.i_tree.binary_tree.bfs.BfsFlatBinaryTreeTraversalImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BfsFlatBinaryTreeTraversalImplTest {

    private BinaryTreeNode<Integer> root;
    private BfsFlatBinaryTreeTraversalImpl<Integer> bfs;

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
        root.setLeft(new BinaryTreeNode<>(5));
        root.setRight(new BinaryTreeNode<>(15));
        root.getLeft().setLeft(new BinaryTreeNode<>(3));
        root.getLeft().setRight(new BinaryTreeNode<>(7));
        root.getRight().setRight(new BinaryTreeNode<>(20));

        bfs = new BfsFlatBinaryTreeTraversalImpl<>();
    }

    // --- Core traversal ---

    @Test
    @DisplayName("Flat BFS: [10, 5, 15, 3, 7, 20]")
    public void testFlatBfs() {
        assertEquals(List.of(10, 5, 15, 3, 7, 20), bfs.traverse(root));
    }

    // --- Edge cases ---

    @Test
    @DisplayName("Null root returns []")
    public void testNullRoot() {
        assertEquals(List.of(), bfs.traverse(null));
    }

    @Test
    @DisplayName("Single node returns [10]")
    public void testSingleNode() {
        assertEquals(List.of(10), bfs.traverse(new BinaryTreeNode<>(10)));
    }

    @Test
    @DisplayName("Left-skewed tree: [10, 5, 3]")
    public void testLeftSkewedTree() {
        BinaryTreeNode<Integer> skewed = new BinaryTreeNode<>(10);
        skewed.setLeft(new BinaryTreeNode<>(5));
        skewed.getLeft().setLeft(new BinaryTreeNode<>(3));
        assertEquals(List.of(10, 5, 3), bfs.traverse(skewed));
    }

    @Test
    @DisplayName("Right-skewed tree: [10, 15, 20]")
    public void testRightSkewedTree() {
        BinaryTreeNode<Integer> skewed = new BinaryTreeNode<>(10);
        skewed.setRight(new BinaryTreeNode<>(15));
        skewed.getRight().setRight(new BinaryTreeNode<>(20));
        assertEquals(List.of(10, 15, 20), bfs.traverse(skewed));
    }

    @Test
    @DisplayName("Complete binary tree: [1, 2, 3, 4, 5, 6, 7]")
    public void testCompleteBinaryTree() {
        BinaryTreeNode<Integer> node = new BinaryTreeNode<>(1);
        node.setLeft(new BinaryTreeNode<>(2));
        node.setRight(new BinaryTreeNode<>(3));
        node.getLeft().setLeft(new BinaryTreeNode<>(4));
        node.getLeft().setRight(new BinaryTreeNode<>(5));
        node.getRight().setLeft(new BinaryTreeNode<>(6));
        node.getRight().setRight(new BinaryTreeNode<>(7));
        assertEquals(List.of(1, 2, 3, 4, 5, 6, 7), bfs.traverse(node));
    }
}
